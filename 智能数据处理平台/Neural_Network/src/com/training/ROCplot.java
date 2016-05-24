package com.training;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ROCplot {
	
	private double[][] data1 = new double[21][2];
	private double[][] data2 = new double[21][2];
	public ROCplot(double[][] data1, double[][] data2) {
		this.data1 = data1;
		this.data2 = data2;
	}
	
	public void plot() {
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
		mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
		mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
		ChartFactory.setChartTheme(mChartTheme);
		XYSeriesCollection mCollection = GetCollection();
		JFreeChart mChart = ChartFactory
				.createXYLineChart("ROC曲线图", "FPR", "TPR", mCollection,
						PlotOrientation.VERTICAL, true, true, false);
		ChartFrame mChartFrame = new ChartFrame("ROC曲线图", mChart);
		mChartFrame.pack();
		mChartFrame.setVisible(true);

	}

	public XYSeriesCollection GetCollection() {
		XYSeriesCollection mCollection = new XYSeriesCollection();
		XYSeries mSeriesFirst = new XYSeries("感知机");
		
		for(int i = 0; i < 21; i++) {
			mSeriesFirst.add(data1[i][0], data1[i][1]);
		}
				
		XYSeries mSeriesSecond = new XYSeries("中心分类");
		
		for(int i = 0; i < 21; i++) {
			mSeriesSecond.add(data2[i][0], data2[i][1]);
		}
		
		mCollection.addSeries(mSeriesFirst);
		mCollection.addSeries(mSeriesSecond);
		
		return mCollection;
	}

}