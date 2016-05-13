package com.util;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/** 
* 该类用于演示最简单的柱状图生成
* @author Wang Jianyu
*/ 
public class Histogram { 
	
	private static int[] scoreA = {19, 18, 18, 15, 18, 11, 16, 19, 17, 17, 20, 17, 19, 20, 20, 15, 15, 16};
	private static int[] scoreB = {19, 15, 20, 20, 17, 18, 18, 15, 16, 18, 15, 19, 20, 19, 19, 16, 17, 13, 15};
	
	public HashMap<Integer, Integer> getAmount(int[] data) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i = 0; i < data.length; i++) {
			if(map.containsKey(data[i])) {
				map.put(data[i], map.get(data[i]) + 1);
			} else {
				map.put(data[i], 1);
			}
		}	
		return map;
	}
	
    public void getHistogram() throws IOException{ 
        
        CategoryDataset dataset = new Histogram().getDataSet2(); 
        JFreeChart chart = ChartFactory.createBarChart( 
                           "工数考试成绩图", // 图表标题
                           "成绩", // 目录轴的显示标签
                           "个数", // 数值轴的显示标签
                            dataset, // 数据集
                            PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                            true,  // 是否显示图例(对于简单的柱状图必须是 false)
                            false, // 是否生成工具
                            false  // 是否生成 URL 链接
                            ); 
        //中文乱码
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();  
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 20));      
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));  
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));  
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  
        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));  
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
                           
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    } 
    /** 
    * 获取一个演示用的组合数据集对象
    * @return 
    */ 
    public CategoryDataset getDataSet2() { 
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
        
        for(int i = 11; i < 21; i++) {
        	dataset.addValue(0, "A班", Integer.toString(i)); 
        	dataset.addValue(0, "B班", Integer.toString(i)); 
        }
       
        HashMap<Integer, Integer> mapA = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> mapB = new HashMap<Integer, Integer>();
        
        mapA = getAmount(scoreA);
        mapB = getAmount(scoreB);
        
        Iterator<Entry<Integer, Integer>> iter = mapA.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iter.next();
            int key = entry.getKey();
            int value = entry.getValue();
            dataset.addValue(value, "A班", Integer.toString(key));
        }
        
        iter = mapB.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iter.next();
            int key = entry.getKey();
            int value = entry.getValue();
            dataset.addValue(value, "B班", Integer.toString(key));
        }
        
        return dataset; 
    } 
}