package me.andreaiacono.gui.panel;

import me.andreaiacono.code.SampleRun;
import me.andreaiacono.gui.Main;
import me.andreaiacono.utils.SwingUtils;

import javax.swing.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

public class ControlPanel extends JPanel implements ActionListener {

    private final JButton runButton;
    private Main main;
    private String filename;

    public ControlPanel(Main main) {
        this.main = main;

        runButton = new JButton("Run");
        add(runButton);
        runButton.addActionListener(this);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Run")) {
            CodeExecutor codeExecutor = new CodeExecutor(main.getSourceCode(), filename);
            codeExecutor.execute();
        }
    }

    private File compile(String source, String className) throws Exception {

        // saves the source to disk
        File root = new File(System.getProperty("java.io.tmpdir") + "/JavaPerformance");
        if (root.exists()) {
            Arrays.stream(Objects.requireNonNull(root.listFiles())).forEach(File::delete);
            root = new File(System.getProperty("java.io.tmpdir") + "/JavaPerformance");
        }
        File sourceFile = new File(root, className + ".java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

        ByteArrayOutputStream errStream = new ByteArrayOutputStream();

        // compiles the source
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, errStream, sourceFile.getPath());

        String errors = errStream.toString();
        if (errors.length() > 0) {
            main.updateStatusBar("Compilation failed.");
            SwingUtils.showFormError(errors);
            return null;
        }

        return root;
    }

    private void runClass(String className, File root) throws Exception {

        // creates a new classloader and loads the sample class
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
        Class sampleClass = classLoader.loadClass(className);

        // runs the class
        Constructor c = sampleClass.getConstructor();
        SampleRun d = (SampleRun) c.newInstance();
        d.run();
    }

    class CodeExecutor extends SwingWorker<Void, Void> {

        private String sourceCode;
        private String className;

        CodeExecutor(String sourceCode, String className) {
            this.sourceCode = sourceCode;
            this.className = className;
        }

        @Override
        protected Void doInBackground() throws Exception {

                main.resetGraph();
                main.updateStatusBar("Compiling class...");
                File rootDir = compile(sourceCode, className);
                if (rootDir != null) {
                    main.updateStatusBar("Running class...");
                    runClass(className, rootDir);
                }
            return null;
        }
    }
}
