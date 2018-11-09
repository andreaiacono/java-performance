package me.andreaiacono.gui;

import me.andreaiacono.gui.panel.ControlPanel;
import me.andreaiacono.gui.panel.GraphPanel;
import me.andreaiacono.gui.panel.SourceCodePanel;
import me.andreaiacono.utils.SwingUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.Arrays;

public class Main extends JFrame {

    private static Main instance;
    private final ControlPanel controlPanel;

    private JSplitPane viewSplitPane;
    private SourceCodePanel sourceCodePanel;
    private JLabel statusLabel;
    private GraphPanel graphPanel;

    private Main() {
        super();
        setSize(1400, 800);
        SwingUtils.centerFrame(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // creates the menu bar
        JMenuBar menuBar = new JMenuBar();

        // creates the file menu
        JMenu sampleMenu = new JMenu("Samples");
        createSamplesItems(sampleMenu);
        menuBar.add(sampleMenu);
        setJMenuBar(menuBar);

        graphPanel = new GraphPanel();
        sourceCodePanel = new SourceCodePanel();

        viewSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sourceCodePanel, graphPanel);
        viewSplitPane.setDividerLocation(0.4d);
        viewSplitPane.setResizeWeight(1);
        viewSplitPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                viewSplitPane.setDividerLocation(0.4);
//                    viewSplitPane.removeComponentListener(this);
            }
        });

        JPanel viewPanel = new JPanel();
        BorderLayout bl = new BorderLayout();
        viewPanel.setLayout(bl);
        viewPanel.add(viewSplitPane);

        controlPanel = new ControlPanel(this);
        JSplitPane controlSplitPlane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, viewPanel, controlPanel);

        controlSplitPlane.setResizeWeight(1);
//            controlSplitPlane.setDividerLocation(1);

        getContentPane().add(controlSplitPlane, BorderLayout.CENTER);


        statusLabel = new JLabel(" Ready");
        statusLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        getContentPane().add("South", statusLabel);

        setSource(sampleMenu.getItem(0).getText());
        this.setFocusable(true);
    }

    private void createSamplesItems(JMenu menu) {
        File[] files = new File("src/main/resources/java").listFiles();
        Arrays
                .stream(files)
                .map(file -> file.getName().substring(0, file.getName().indexOf(".")))
                .forEach(filename -> createMenuItem(menu, filename, (event) -> setSource(event.getActionCommand())));
    }

    private void setSource(String fileName) {
        try {
            controlPanel.setFilename(fileName);
            sourceCodePanel.setSource(fileName);
        }
        catch (Exception ex) {
            SwingUtils.showFormError(ex);
        }
    }

    private void createMenuItem(JMenu menu, String label, ActionListener listener) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(listener);
        menu.add(item);
    }

    public static Main getInstance() {
        return instance;
    }

    public void updateStatusBar(String message) {
        if (message != null) {
            statusLabel.setText(message);
            repaint();
        }
    }

    public String getSourceCode() {
        return sourceCodePanel.getSourceCode();
    }

    public void resetGraph() {
        graphPanel.reset();
    }

    public GraphPanel getGraphPanel() {
        return graphPanel;
    }

    public static void main(String[] args) {

        // sets antialiasing
        System.setProperty("swing.aatext", "true");

        // tries to set the look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ignored) {}

        instance = new Main();
        instance.setVisible(true);
    }
}