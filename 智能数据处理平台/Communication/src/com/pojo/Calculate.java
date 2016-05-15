package com.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Calculate {
	
	private String text;
	private JTable jtable1;
	private JTable jtable2;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JTextField jTextField3;
	private JTextField jTextField4;
	private ArrayList<Record> records = new ArrayList<Record>();
	private ArrayList<TotalTime> totalTime = new ArrayList<TotalTime>();
	private ArrayList<Integer> teleTime = new ArrayList<Integer>();
	
	public Calculate(String text, JTable jtable1, JTable jtable2,
			JTextField jTextField1, JTextField jTextField2, JTextField jTextField3, JTextField jTextField4)
	{
		this.text = text;
		this.jtable1 = jtable1;
		this.jtable2 = jtable2;
		this.jTextField1 = jTextField1;
		this.jTextField2 = jTextField2;
		this.jTextField3 = jTextField3;
		this.jTextField4 = jTextField4;
	}
	
	public void analyze() {
		String[] texts = text.split("\n");
		
		for(int i = 1; i < texts.length; i++) {
			String[] types = texts[i].split("\t");
			Record record = new Record();
			for(int j = 0; j < types.length; j++) {
				if(j == 0) {
					record.setId(Integer.parseInt(types[j]));
				}
				else if(j == 1) {
					record.setStart(types[j]);
				}
				else if(j == 2) {
					String second = countChar(types[j]);
					record.setSeconds(Integer.parseInt(second));
				}
				else if(j == 3) {
					record.setMethod(types[j]);
				}
				else if(j == 4) {
					record.setTelenumber(types[j]);
				}
				else if(j == 5) {
					record.setPosition(types[j]);
				}
				else if(j == 6) {
					record.setType(types[j]);
				}
				else if(j == 7) {
					record.setClose(Integer.parseInt(types[j]));
				}
				else if(j == 8) {
					record.setSex(types[j]);
				}
			}
			records.add(record);
		}
		
		/*for(int i = 0; i < records.size(); i++) {
			System.out.println(records.get(i).getId());
			System.out.println(records.get(i).getStart());
			System.out.println(records.get(i).getSeconds());
			System.out.println(records.get(i).getMethod());
			System.out.println(records.get(i).getTelenumber());
			System.out.println(records.get(i).getPosition());
			System.out.println(records.get(i).getType());
			System.out.println(records.get(i).getClose());
			System.out.println(records.get(i).getSex());		
		}*/
		for(int i = 0; i < records.size(); i++) {
			DefaultTableModel tableModel1 = (DefaultTableModel) jtable1.getModel();
	        tableModel1.addRow(new Object[] {
	        		records.get(i).getSeconds()+"秒", 
	        		records.get(i).getMethod(), 
	        		records.get(i).getStart(),
	        		records.get(i).getPosition(),
	        		records.get(i).getType()});
	        jtable1.invalidate();
		}
		
		for(int i = 0; i < records.size(); i++) {
			if(isContainsNumber(records.get(i).getTelenumber())) {
				int index = findNumber(records.get(i).getTelenumber());
				totalTime.get(index).setTotalTime(totalTime.get(index).getTotalTime() + records.get(i).getSeconds());
			} else {
				TotalTime time = new TotalTime();
				time.setTelenumber(records.get(i).getTelenumber());
				time.setTotalTime(records.get(i).getSeconds());
				time.setClose(records.get(i).getClose());
				totalTime.add(time);
			}
		}
		
		for(int i = 0; i < totalTime.size(); i++) {
			DefaultTableModel tableModel2 = (DefaultTableModel) jtable2.getModel();
	        tableModel2.addRow(new Object[] {
	        		totalTime.get(i).getTelenumber(),
	        		totalTime.get(i).getTotalTime(),
	        		totalTime.get(i).getClose()});
	        jtable2.invalidate();
		}
		
		int[][] chiSquare = new int[3][4];
		double[][] expection = new double[3][4];
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				chiSquare[i][j] = 0;
			}
		}
		int[] position = new int[2];
		for(int i = 0; i < totalTime.size(); i++) {
			position = getPosition(totalTime.get(i));
			chiSquare[position[0]][position[1]]++;
		}
		
		for(int i = 0; i < 3; i++) {
			int totalCol = 0;
			for(int j = 0; j < 2; j++) {
				totalCol += chiSquare[j][i];
			}
			chiSquare[2][i] = totalCol;
		}
		
		for(int i = 0; i < 2; i++) {
			int totalRaw = 0;
			for(int j = 0; j < 3; j++) {
				totalRaw += chiSquare[i][j];
			}
			chiSquare[i][3] = totalRaw;
		}
		
		int sum1 = 0;
		for(int i = 0; i < 3; i++) {
			sum1 += chiSquare[2][i];
		}
		chiSquare[2][3] = sum1;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				expection[i][j] = chiSquare[i][j];
			}
		}
			
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				expection[i][j] = ((double)expection[i][3] / expection[2][3]) * expection[2][j];
			}
		}
		
		double chi = 0;
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				chi += ((double)chiSquare[i][j] - expection[i][j]) * ((double)chiSquare[i][j] - expection[i][j]) / expection[i][j];
			}
		}
		
		System.out.println(chi);
		
		for(int i = 0; i < records.size(); i++) {
			teleTime.add(records.get(i).getSeconds());
		}
		
		Collections.sort(teleTime);
		
		/*for(int i = 0; i < teleTime.size(); i++)
			System.out.println(teleTime.get(i));*/
		
		//求均值
		double sum = 0;
		for(int i = 0; i < teleTime.size(); i++) {
			sum += teleTime.get(i);
		}
		double average = sum / teleTime.size();
		jTextField1.setText(Double.toString(average));
		
		//求中位数
		int middle = teleTime.get(teleTime.size() / 2);
		jTextField2.setText(Integer.toString(middle));
		
		//求众数
		int mode = 0;
		int max = 1;
		int temp = 1;
		for(int i = 0; i < teleTime.size() - 1; i++) {
			if(teleTime.get(i+1) == teleTime.get(i)) {
				temp++;
			} else {
				if(max < temp) {
					max = temp;
					mode = teleTime.get(i);
				}
				temp = 1;
			}
		}
		jTextField3.setText(Integer.toString(mode));
		
		//五数概括
		int lower = teleTime.get(0);
		int low = teleTime.get(teleTime.size() / 4);
		int high = teleTime.get(teleTime.size() / 4 * 3);
		int higher = teleTime.get(teleTime.size() - 1);
		jTextField4.setText(
				Integer.toString(lower) + ", "
			  + Integer.toString(low) + ", "
			  + Integer.toString(middle) + ", "
			  + Integer.toString(high) + ", "
			  + Integer.toString(higher)
				);
	}
	
	public int[] getPosition(TotalTime time) {
		int[] result = new int[2];
		
		if(time.getClose() == 1) {
			result[0] = 0;
			if(time.getTotalTime() < 100) {
				result[1] = 0;
			}
			else {
				if(time.getTotalTime() < 1000) {
					result[1] = 1;
				}
				else {
					result[1] = 2;
				}
			}
		}
		else {
			result[0] = 1;
			if(time.getTotalTime() < 100) {
				result[1] = 0;
			}
			else {
				if(time.getTotalTime() < 1000) {
					result[1] = 1;
				}
				else {
					result[1] = 2;
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Double> getTeleTime() {
		ArrayList<Double> list = new ArrayList<Double>();
		for(int i = 0; i < teleTime.size(); i++) {
			double data = teleTime.get(i);
			list.add(data);
		}
		return list;
	}
	
	public boolean isContainsNumber(String number) {
		for(int i = 0; i < totalTime.size(); i++) {
			if(totalTime.get(i).getTelenumber().equals(number)) {
				return true;
			}
		}
		return false;
	}
	
	public int findNumber(String number) {
		for(int i = 0; i < totalTime.size(); i++) {
			if(totalTime.get(i).getTelenumber().equals(number)) {
				return i;
			}
		}
		return 0;
	}
	
	private static String countChar(String itemlife){	
		String regEx = "[\\u4e00-\\u9fa5]"; 
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(itemlife); 
		return m.replaceAll("");
	}
}
