package com.correlation.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import com.correlation.analysis.Apriori;
import com.correlation.analysis.FPTree;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Frame extends JFrame {

	/**
	 * 生成序列化ID
	 */
	private static final long serialVersionUID = 8917076687452427554L;
	
	private JPanel contentPane;
	private JTextArea textArea;
	
	private ArrayList<String> testSet = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 429);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("文件");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("打开");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fileDialog;
				//An abstract representation of file and directory pathnames. 
				File file = null;
				Frame frame = null;
				fileDialog = new FileDialog(frame, "打开", FileDialog.LOAD);
				fileDialog.setVisible(true);
				try {
					
					file = new File(fileDialog.getDirectory(), fileDialog.getFile());
					
				    FileReader filereader = new FileReader(file);
				    BufferedReader bufferreader = new BufferedReader(filereader);
					String aline;
					while ((aline = bufferreader.readLine()) != null)
						testSet.add(aline);
					filereader.close();
					bufferreader.close();			
				} catch (IOException exception) {
					System.out.println(exception);
				}
								
			    textArea.append("\n训练集数据读取成功 " + new Date().toString() + "\n");
			}
		});
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("关闭");
		menu.add(menuItem_1);
		
		JMenu menu_1 = new JMenu("测试");
		menuBar.add(menu_1);
		
		JMenuItem mntmApriori = new JMenuItem("Apriori算法测试");
		mntmApriori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append("Apriori算法测试结果:\n");
				Apriori apriori = new Apriori(testSet, textArea);
				apriori.display();
				apriori.aprioriAnalysis();
				
				/*FPTree fptree = new FPTree(testSet);
		        fptree.setMinSuport(2);
		        List<List<String>> transRecords = fptree.readTransRocords();
		        fptree.FPGrowth(transRecords, null);*/
			}
		});
		menu_1.add(mntmApriori);
		
		JMenuItem mntmFp = new JMenuItem("FP增长算法测试");
		mntmFp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.append("FP增长算法测试结果:\n");
				FPTree fptree = new FPTree(testSet, textArea);
		        fptree.setMinSuport(2);
		        List<List<String>> transRecords = fptree.readTransRocords();
		        fptree.FPGrowth(transRecords, null);
			}
		});
		menu_1.add(mntmFp);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setText("/*系统操作提示面板*/");
	}
}
