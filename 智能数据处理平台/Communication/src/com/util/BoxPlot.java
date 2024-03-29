/** 
* 该类用于测试盒图的展示
* @author Wang Jianyu
*/ 
package com.util;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

/** @see http://stackoverflow.com/questions/6844759 */
public class BoxPlot {

    private static final int COLS = 3;
    private static final int VISIBLE = 3;
    private static final int ROWS = 3;
    private List<String> columns;
    private List<List<List<Double>>> data;
    private DefaultBoxAndWhiskerCategoryDataset dataset;
    private CategoryPlot plot;
    private ChartPanel chartPanel;
    private JPanel controlPanel;
    private int start = 0;
    
    private ArrayList<Double> telelist = new ArrayList<Double>();

    public BoxPlot(ArrayList<Double> telelist) {
    	this.telelist = telelist;
        createData();
        createDataset(start);
        createChartPanel();
        createControlPanel();
    }

    private void createData() {
        columns = new ArrayList<String>(COLS);
        data = new ArrayList<List<List<Double>>>();
        for (int i = 0; i < COLS; i++) {
            String name = "Category" + String.valueOf(i + 1);
            columns.add(name);
            List<List<Double>> list = new ArrayList<List<Double>>();
            for (int j = 0; j < ROWS; j++) {
                list.add(createValues(telelist));
            }
            data.add(list);
        }
    }

    private List<Double> createValues(List<Double> list) {
        return list;  	
    }
    /*
    private List<Double> createValues() {
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < VALUES; i++) {
            list.add(rnd.nextGaussian());
        }
        return list;
    }*/

    private void createDataset(int start) {
        dataset = new DefaultBoxAndWhiskerCategoryDataset();
        for (int i = start; i < start + VISIBLE; i++) {
            List<List<Double>> list = data.get(i);
            int row = 0;
            for (List<Double> values : list) {
                String category = columns.get(i);
                dataset.add(values, "数据集" + row++, category);
            }
        }
    }

    private void createChartPanel() {
        CategoryAxis xAxis = new CategoryAxis("Category");
        NumberAxis yAxis = new NumberAxis("Value");
        BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        JFreeChart chart = new JFreeChart("BoxPlot", plot);
        chartPanel = new ChartPanel(chart);
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.add(new JButton(new AbstractAction("\u22b2Prev") {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1737041579861067863L;

			@Override
            public void actionPerformed(ActionEvent e) {
                start -= VISIBLE;
                if (start < 0) {
                    start = 0;
                    return;
                }
                createDataset(start);
                plot.setDataset(dataset);
            }
        }));
        controlPanel.add(new JButton(new AbstractAction("Next\u22b3") {

			private static final long serialVersionUID = 4939665778877662716L;

			@Override
            public void actionPerformed(ActionEvent e) {
                start += VISIBLE;
                if (start > COLS - VISIBLE) {
                    start = COLS - VISIBLE;
                    return;
                }
                createDataset(start);
                plot.setDataset(dataset);
            }
        }));
    }

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public JPanel getControlPanel() {
        return controlPanel;
    }

    /*
    public static void main(String[] args) throws IOException {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GUITest demo = new GUITest();
                frame.add(demo.getChartPanel(), BorderLayout.CENTER);
                frame.add(demo.getControlPanel(), BorderLayout.SOUTH);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
    */
}