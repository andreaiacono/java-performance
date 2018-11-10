package me.andreaiacono.gui.panel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel {

    private JFreeChart chart;
    private ChartPanel chartPanel;
    private List<XYSeries> timeSeriesList;
    private long counter;
    private XYSeriesCollection dataset;

    public GraphPanel() {

        chartPanel = new ChartPanel(null);
        reset();
        this.add(chartPanel);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);
        sl.putConstraint(SpringLayout.NORTH, chartPanel, 0, SpringLayout.NORTH, this);
        sl.putConstraint(SpringLayout.EAST, chartPanel, 0, SpringLayout.EAST, this);
        sl.putConstraint(SpringLayout.SOUTH, chartPanel, 0, SpringLayout.SOUTH, this);
        sl.putConstraint(SpringLayout.WEST, chartPanel, 0, SpringLayout.WEST, this);

        setVisible(true);
    }

    public void addValues(double... values) {
        counter ++;
        for (int i =0; i < values.length; i++) {
            timeSeriesList.get(i).addOrUpdate(counter, values[i]);
        }
    }

    public void addSeries(String... seriesArray) {
        for (String s : seriesArray) {
            XYSeries timeSeries = new XYSeries(s);
            timeSeriesList.add(timeSeries);
            dataset.addSeries(timeSeries);
        }
        chart.getXYPlot().setDataset(dataset);
    }

    public void reset() {
        counter = 0;
        chart = org.jfree.chart.ChartFactory.createXYLineChart("Benchmark", "iteration", "value", null);
        chartPanel.setChart(chart);
        dataset = new XYSeriesCollection();
        timeSeriesList = new ArrayList<>();
    }
}
