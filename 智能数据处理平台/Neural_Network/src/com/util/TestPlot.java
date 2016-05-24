package com.util;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TestPlot {
  public static void main(String[] args) {
    StandardChartTheme mChartTheme = new StandardChartTheme("CN");
    mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
    mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
    mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
    ChartFactory.setChartTheme(mChartTheme);		
    XYSeriesCollection mCollection = GetCollection();
    JFreeChart mChart = ChartFactory.createXYLineChart(
        "折线图",
        "X",
        "Y",				
        mCollection,
        PlotOrientation.VERTICAL,
        true, 
        true, 
        false);
    ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
    mChartFrame.pack();
    mChartFrame.setVisible(true);

  }	
  public static XYSeriesCollection GetCollection()
  {
    XYSeriesCollection mCollection = new XYSeriesCollection();
    XYSeries mSeriesFirst = new XYSeries("First");
    mSeriesFirst.add(0, 0);
      mSeriesFirst.add(0.05, 0.8);
      mSeriesFirst.add(0.1, 0.85);
      mSeriesFirst.add(0.2, 0.9);
      mSeriesFirst.add(0.3, 0.95);
      mSeriesFirst.add(0.4, 0.99);
      mSeriesFirst.add(0.5, 0.999);
      mSeriesFirst.add(1.0, 1.0);
      XYSeries mSeriesSecond = new XYSeries("Second");
      mSeriesSecond.add(1.0D, 5D);
      mSeriesSecond.add(2D, 7D);
      mSeriesSecond.add(3D, 6D);
      mSeriesSecond.add(4D, 8D);
      mSeriesSecond.add(5D, 4D);
      mSeriesSecond.add(6D, 4D);
      mSeriesSecond.add(7D, 2D);
      mSeriesSecond.add(8D, 1.0D);
    mCollection.addSeries(mSeriesFirst);
    mCollection.addSeries(mSeriesSecond);
    return mCollection;
  }

}