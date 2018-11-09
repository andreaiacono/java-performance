package me.andreaiacono.gui.panel;

import me.andreaiacono.utils.FileUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;

public class SourceCodePanel extends JPanel {

    private final RSyntaxTextArea textArea;

    public SourceCodePanel() {

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);

        try {
            Theme theme = Theme.load(getClass().getResourceAsStream("/rsyntaxtheme_dark.xml"));
            theme.apply(textArea);
        }
        catch (Exception ignored) { }

        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        add(scrollPane);

        sl.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
        sl.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
        sl.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
        sl.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
    }

    public String getSourceCode() {
        return textArea.getText();
    }

    public void setSource(String sourceName) throws Exception {
        textArea.setText(FileUtils.readTextFile("src/main/resources/sources/" + sourceName + ".java"));
    }
}
