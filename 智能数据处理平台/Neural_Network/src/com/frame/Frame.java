package com.frame;

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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.training.*;
import javax.swing.JScrollPane;

public class Frame extends JFrame {

	/**
	 * 生成序列化ID
	 */
	private static final long serialVersionUID = 9127307525498161619L;
	private JPanel contentPane;
	
	private ArrayList<String> trainingSet = new ArrayList<String>();
	private ArrayList<String> testSet = new ArrayList<String>();
	private JTextArea textArea;

	public Training training;
	public Training multiTraining;
	public Classfication classfication;
	public MultiSense multiSense;
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
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("文件");
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("打开训练集文件");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent open) {
				FileDialog fileDialog;
				//An abstract representation of file and directory pathnames. 
				File file = null;
				Frame frame = null;
				fileDialog = new FileDialog(frame, "打开", FileDialog.LOAD);
				fileDialog.setVisible(true);
				try {
					
					file = new File(fileDialog.getDirectory(), fileDialog.getFile());
					String fileName = file.getName();
					String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
					// 输入文件非Excel文件
					if (!"xlsx".equals(prefix)) {
						FileReader filereader = new FileReader(file);
						BufferedReader bufferreader = new BufferedReader(filereader);
						String aline;
						while ((aline = bufferreader.readLine()) != null)
							trainingSet.add(aline);
						filereader.close();
						bufferreader.close();
					} 
				} catch (IOException exception) {
					System.out.println(exception);
				}
								
			    textArea.append("\n训练集数据读取成功 " + new Date().toString() + "\n");				
			}
		});
		menu.add(menuItem);
		
		JMenuItem menuItem_3 = new JMenuItem("打开测试集文件");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent opentest) {
				FileDialog fileDialog;
				//An abstract representation of file and directory pathnames. 
				File file = null;
				Frame frame = null;
				fileDialog = new FileDialog(frame, "打开", FileDialog.LOAD);
				fileDialog.setVisible(true);
				try {
					
					file = new File(fileDialog.getDirectory(), fileDialog.getFile());
					String fileName = file.getName();
					String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
					// 输入文件非Excel文件
					if (!"xlsx".equals(prefix)) {
						FileReader filereader = new FileReader(file);
						BufferedReader bufferreader = new BufferedReader(filereader);
						String aline;
						while ((aline = bufferreader.readLine()) != null)
							testSet.add(aline);
						filereader.close();
						bufferreader.close();
					} 
				} catch (IOException exception) {
					System.out.println(exception);
				}
								
			    textArea.append("\n测试集数据读取成功 " + new Date().toString() + "\n");
			}
		});
		menu.add(menuItem_3);
		
		JMenuItem menuItem_1 = new JMenuItem("关闭");
		menu.add(menuItem_1);
		
		JMenu menu_1 = new JMenu("单层感知机操作");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_2 = new JMenuItem("单层感知机学习");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent train) {
				textArea.append("\n单层感知机训练集数据,中心分类器开始进行训练 " + new Date().toString() + "\n");
				training = new Training(trainingSet);
				training.learning(1000);
				training.testTraining();
				textArea.append("\n单层感知机训练集数据训练后判断 " + "Yes: " + training.getYES() + " " + "NO: " + training.getNO() + "\n");
							
				classfication = new Classfication(trainingSet);
				
			}
		});
		menu_1.add(menuItem_2);
		
		JMenuItem menuItem_4 = new JMenuItem("单层感知机测试");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent test) {
				training.testTesting(testSet); 
				textArea.append("\n单层感知机测试集数据测试后判断 " + "Yes: " + training.getYES() + " " + "NO: " + training.getNO() + "\n");
				
				classfication.testTesting(testSet);
				classfication.testClass();
				textArea.append("\n中心分类训练集数据训练后判断 " + "Yes: " + classfication.getYES() + " " + "NO: " + classfication.getNO() + "\n");
			}
		});
		menu_1.add(menuItem_4);
		
		JMenuItem mntmroc = new JMenuItem("生成ROC曲线");
		mntmroc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent roc) {
				ROC r = new ROC(training, classfication);
				//r.getRange();
				r.trainingROC();
			}
		});
		menu_1.add(mntmroc);
		
		JMenu menu_2 = new JMenu("多层感知机操作");
		menuBar.add(menu_2);
		
		JMenuItem menuItem_5 = new JMenuItem("多层感知机学习");
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent multitraining) {
				textArea.append("\n多层感知机训练集数据开始进行训练 " + new Date().toString() + "\n");
				
				//multiSense = new MultiSense(trainingSet, testSet);
				//multiSense.training();
				//multiSense.testing(0);
				multiTraining = new Training(trainingSet);
				multiTraining.learning(2000);		
				multiTraining.testTesting(testSet); 
				textArea.append("\n多层感知机测试集数据测试后判断 " + "Yes: " + multiTraining.getYES() + " " + "NO: " + multiTraining.getNO() + "\n");
				
			}
		});
		menu_2.add(menuItem_5);
		
		JMenuItem mntmRoc = new JMenuItem("ROC性能比较");
		mntmRoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ROCmulti r = new ROCmulti(training, multiTraining);
				r.trainingROC();
			}
		});
		menu_2.add(mntmRoc);
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
