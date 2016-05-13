package com.util;
   
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JPanel;   
   
import org.jfree.chart.ChartFactory;   
import org.jfree.chart.ChartPanel;   
import org.jfree.chart.JFreeChart;   
import org.jfree.chart.plot.PlotOrientation;   
import org.jfree.data.xy.DefaultXYDataset;   
import org.jfree.data.xy.XYDataset;   
import org.jfree.ui.ApplicationFrame;   
import org.jfree.ui.RefineryUtilities;   
   
/**  
 * A simple demonstration application showing a simple usage of the  
 * <code>DefaultXYDataset</code> class.  
 */   
@SuppressWarnings("serial")
public class QuantilePlot extends ApplicationFrame {
	
	private static int[] scoreA = {19, 18, 18, 15, 18, 11, 16, 19, 17, 17, 20, 17, 19, 20, 20, 15, 15, 16};
	private static int[] scoreB = {19, 15, 20, 20, 17, 18, 18, 15, 16, 18, 15, 19, 20, 19, 19, 16, 17, 13, 15};
   
    /**  
     * Constructs the demo application.  
     *  
     * @param title  the frame title.  
     */   
    public QuantilePlot(String title) {   
        super(title);   
        JPanel chartPanel = createDemoPanel();   
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));   
        setContentPane(chartPanel);   
    }   
       
    /**  
     * Creates a simple chart for the specified dataset.  
     *   
     * @param dataset  the dataset.  
     *   
     * @return The chart.  
     */   
    private static JFreeChart createChart(XYDataset dataset) {   
        return ChartFactory.createScatterPlot("QuantilePlot",   
                "Quantile", "Score", dataset, PlotOrientation.VERTICAL, true, false, false);   
    }   
       
    /**  
     * Creates a sample dataset.  
     *   
     * @return A sample dataset.  
     */   
    private static XYDataset createDataset() {   
        DefaultXYDataset dataset = new DefaultXYDataset();
                  
        //double[] x1 = new double[scoreA.length];  
        //double[] y1 = new double[scoreA.length];
        
        Arrays.sort(scoreA);
        int lengthA = 1;
        int start = scoreA[0];
        for(int i = 0; i < scoreA.length; i++) {
        	if(start == scoreA[i]) {
        		continue;
        	} 
        	else {
        		start = scoreA[i];
        		lengthA++;
        	}
        }
        //System.out.println(lengthA);
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();        
        map = getAmount(scoreA);        
        double[] x1 = new double[lengthA];  
        double[] y1 = new double[lengthA];
        
        for(int i = 11, j = 0, sum = 0; i < 21; i++) {
        	if(map.containsKey(i)) {
        		//System.out.println(i + " " + map.get(i));
        		sum += map.get(i);
        		y1[j] = i;
        		x1[j] = (double)sum / scoreA.length;
        		j++;
        	}
        }      
        double[][] data1 = new double[][] {x1, y1};   
        dataset.addSeries("A", data1);   
           
        Arrays.sort(scoreB);
        int lengthB = 1;
        start = scoreB[0];
        for(int i = 0; i < scoreB.length; i++) {
        	if(start == scoreB[i]) {
        		continue;
        	} 
        	else {
        		start = scoreB[i];
        		lengthB++;
        	}
        }
        //System.out.println(lengthA);
        map = new HashMap<Integer, Integer>();        
        map = getAmount(scoreB);        
        double[] x2 = new double[lengthB];  
        double[] y2 = new double[lengthB];
        
        for(int i = 11, j = 0, sum = 0; i < 21; i++) {
        	if(map.containsKey(i)) {
        		//System.out.println(i + " " + map.get(i));
        		sum += map.get(i);
        		y2[j] = i;
        		x2[j] = (double)sum / scoreB.length;
        		j++;
        	}
        } 
        
        double[][] data2 = new double[][] {x2, y2};   
        dataset.addSeries("B", data2);  
           
        return dataset;   
    }   
       
    public static HashMap<Integer, Integer> getAmount(int[] data) {
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
    /**  
     * Creates a panel for the demo.  This method exists only to allow   
     * aggregration within the JFreeChart <code>SuperDemo</code>.  
     *    
     * @return A panel containing the demo chart.  
     */   
    public static JPanel createDemoPanel() {   
        return new ChartPanel(createChart(createDataset()));   
    }   
       
    /**  
     * Starting point for the demonstration application when it is run as  
     * a stand-alone application.  
     *  
     * @param args  ignored.  
     */   
    public void ShowQuantile() {   
        QuantilePlot demo = new QuantilePlot(   
                "QuantilePlot");   
        demo.pack();   
        RefineryUtilities.centerFrameOnScreen(demo);   
        demo.setVisible(true);   
    }   
   
}   