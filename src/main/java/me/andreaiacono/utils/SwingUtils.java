package me.andreaiacono.utils;

import me.andreaiacono.gui.misc.ErrorForm;

import javax.swing.*;
import java.awt.*;

public class SwingUtils {


    public static void centerFrame(JFrame frame) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((screenSize.width / 2) - (frameSize.width / 2), (screenSize.height / 2) - (frameSize.height / 2));
    }

    public static void centerFrame(JDialog jd) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = jd.getSize();
        jd.setLocation((screenSize.width / 2) - (frameSize.width / 2), (screenSize.height / 2) - (frameSize.height / 2));
    }

    public static void showFormError(Exception ex) {

        new ErrorForm(ex).setVisible(true);
    }

    public static void showFormError(String stackTrace) {

        new ErrorForm("Compile error", stackTrace).setVisible(true);
    }

}
