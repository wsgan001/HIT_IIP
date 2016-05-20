package com.training;

import java.util.ArrayList;

public class Classfication {
	private ArrayList<ArrayList<Double>> valueSet = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> testSet = new ArrayList<ArrayList<Double>>();
	
	private ArrayList<Double> plus = new ArrayList<Double>();
	private ArrayList<Double> minus = new ArrayList<Double>();
	
	private int YES = 0;
	private int NO = 0;
	
	public int getYES() {
		return YES;
	}

	public int getNO() {
		return NO;
	}
	
	// 对训练集进行初始化赋值
	public Classfication(ArrayList<String> set) {	
		for(int i = 0; i < set.size(); i++) {
			String[] values = set.get(i).split(",");
			ArrayList<Double> valueRaw = new ArrayList<Double>();
			for(int j = 0; j < values.length; j++) {
				valueRaw.add(Double.parseDouble(values[j]));
			}
			valueSet.add(valueRaw);
		}
		
		for(int i = 0; i < 22; i++) {
			plus.add((double)0);
			minus.add((double)0);
		}
		// 求出正负类的中心店
		classify();
	}
	
	// 测试集测试
	public void testClass() {
		for(int i = 0; i < testSet.size(); i++) {
			
			double distancePlus = 0;
			double distanceMinus = 0;
			
			for(int j = 0; j < testSet.get(i).size() - 1; j++) {
				distancePlus += Math.pow(testSet.get(i).get(j) - plus.get(j), 2);
				distanceMinus += Math.pow(testSet.get(i).get(j) - minus.get(j), 2);
			}
			
			if(distancePlus > distanceMinus) {
				if(-1 * testSet.get(i).get(testSet.get(i).size() - 1) > 0) {
					YES++;
				}
				else
					NO++;
			}
			else {
				if(1 * testSet.get(i).get(testSet.get(i).size() - 1) > 0) {
					YES++;
				}
				else
					NO++;
			}
			
		}
	}
	
	// 最近中心分类
	public void classify() {
		int plusnum = 0;
		int minusnum = 0;
		for(int i = 0; i < valueSet.size(); i++) {
			if(valueSet.get(i).get(valueSet.get(i).size() - 1) > 0) {
				for(int j = 0; j < valueSet.get(i).size() - 1; j++) {
					plus.set(j, plus.get(j) + valueSet.get(i).get(j));
				}
				plusnum++;
			}
			else {
				for(int j = 0; j < valueSet.get(i).size() - 1; j++) {
					minus.set(j, minus.get(j) + valueSet.get(i).get(j));
				}
				minusnum++;
			}
		}
		
		for(int i = 0; i < plus.size(); i++) {
			plus.set(i, plus.get(i) / plusnum);
			minus.set(i, minus.get(i) / minusnum);
		}
		
		/*for(int i = 0; i < plus.size(); i++) {
			System.out.println(plus.get(i) + " ");;
		}
		System.out.println();
		for(int i = 0; i < minus.size(); i++) {
			System.out.println(minus.get(i) + " ");;
		}*/
	}
	
	// 记录测试集
	public void testTesting(ArrayList<String> set) {
		for(int i = 0; i < set.size(); i++) {
			String[] values = set.get(i).split(",");
			ArrayList<Double> valueRaw = new ArrayList<Double>();
			for(int j = 0; j < values.length; j++) {
				valueRaw.add(Double.parseDouble(values[j]));
			}
			testSet.add(valueRaw);
		}
	}
}
