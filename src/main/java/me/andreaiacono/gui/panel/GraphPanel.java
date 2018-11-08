package me.andreaiacono.gui.panel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphPanel extends JPanel {

    private JFreeChart chart;
    private ChartPanel chartPanel;
    private List<TimeSeries> timeSeriesList;
    private TimeSeriesCollection dataset;

    public GraphPanel() {

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        chartPanel = new ChartPanel(null);
        reset();
        this.add(chartPanel);

        sl.putConstraint(SpringLayout.NORTH, chartPanel, 0, SpringLayout.NORTH, this);
        sl.putConstraint(SpringLayout.EAST, chartPanel, 0, SpringLayout.EAST, this);
        sl.putConstraint(SpringLayout.SOUTH, chartPanel, 0, SpringLayout.SOUTH, this);
        sl.putConstraint(SpringLayout.WEST, chartPanel, 0, SpringLayout.WEST, this);

        setVisible(true);
    }

    public void addValues(double... values) {
        Millisecond now = new Millisecond(new Date());

        for (int i =0; i < values.length; i++) {
            timeSeriesList.get(i).addOrUpdate(now, values[i]);
        }
    }

    public void addSeries(String... seriesArray) {

        for (String series : seriesArray) {
            TimeSeries timeSeries = new TimeSeries(series);
            timeSeriesList.add(timeSeries);
            dataset.addSeries(timeSeries);
        }
        chart.getXYPlot().setDataset(dataset);
    }

    public void reset() {
        chart = org.jfree.chart.ChartFactory.createTimeSeriesChart("Benchmark", "time", "value", null, true, true, false);
        chartPanel.setChart(chart);
        dataset = new TimeSeriesCollection();
        timeSeriesList = new ArrayList<>();
    }
}
