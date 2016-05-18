package com.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class Frame extends JFrame {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 3400777751121425485L;
	private JPanel contentPane;

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
		menu.add(menuItem);
		
		JMenuItem menuItem_3 = new JMenuItem("打开测试集文件");
		menu.add(menuItem_3);
		
		JMenuItem menuItem_1 = new JMenuItem("关闭");
		menu.add(menuItem_1);
		
		JMenu menu_1 = new JMenu("数据操作");
		menuBar.add(menu_1);
		
		JMenuItem menuItem_2 = new JMenuItem("数据训练");
		menu_1.add(menuItem_2);
		
		JMenuItem menuItem_4 = new JMenuItem("数据测试");
		menu_1.add(menuItem_4);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("/*系统操作提示面板*/");
		contentPane.add(textArea, BorderLayout.CENTER);
	}

}
