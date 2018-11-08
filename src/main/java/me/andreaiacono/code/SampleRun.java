package me.andreaiacono.code;

import me.andreaiacono.gui.Main;
import me.andreaiacono.gui.panel.GraphPanel;

public abstract class SampleRun {

    private GraphPanel graphPanel;
    private Main main;

    protected SampleRun() {
        this.graphPanel = Main.getInstance().getGraphPanel();
        this.main = Main.getInstance();
    }

    public abstract void run() throws Exception;

    protected void addSeries(String... series) {
        graphPanel.addSeries(series);
    }

    protected void addDataPoints(double... values) {
        graphPanel.addValues(values);
    }

    protected void updateStatusBar(String msg) {
        main.updateStatusBar(msg);
    }

}
