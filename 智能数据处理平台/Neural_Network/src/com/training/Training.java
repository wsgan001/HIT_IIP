package com.training;

import java.util.ArrayList;

public class Training {
    
	private ArrayList<ArrayList<Double>> valueSet = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> testSet = new ArrayList<ArrayList<Double>>();

	private ArrayList<Double> weight = new ArrayList<Double>();

	private int YES = 0;
	private int NO = 0;
	
	public int getYES() {
		return YES;
	}

	public int getNO() {
		return NO;
	}

	public ArrayList<Double> getWeight() {
		return weight;
	}
	
	public ArrayList<ArrayList<Double>> getTestSet() {
		return testSet;
	}

	// 对训练集进行初始化赋值
	public Training(ArrayList<String> set) {	
		for(int i = 0; i < set.size(); i++) {
			String[] values = set.get(i).split(",");
			ArrayList<Double> valueRaw = new ArrayList<Double>();
			for(int j = 0; j < values.length; j++) {
				valueRaw.add(Double.parseDouble(values[j]));
			}
			valueSet.add(valueRaw);
		}
		// 初始化权值向量
		setWeightValue();
	}
	
	// 初始化权值向量
	public void setWeightValue() {
		for(int i = 0; i < 23;i++)
			weight.add((double)(-1 + Math.random()*2));
	}
	
	// 训练集学习函数
	public void learning(int step) {
		// 判断步数	step
		
		// 学习率
		double rate = 0.01;
		
		for(int k = 0; k < step; k++) {
			for(int i = 0; i < valueSet.size(); i++) {		
				double sum = 0;
				// 矩阵向量乘法
				for(int j = 0; j < weight.size(); j++) {
					if(j != weight.size() - 1) {
						sum += valueSet.get(i).get(j) * weight.get(j);
					}
					else {
						sum += weight.get(j);
					}
				}
				
				if(sum * valueSet.get(i).get(valueSet.get(i).size() - 1) > 0) {
					continue;
				}
				else {
					for(int l = 0; l < weight.size(); l++) {
						if(l != weight.size() - 1)
						    weight.set(l, 
								       weight.get(l) + rate * valueSet.get(i).get(valueSet.get(i).size() - 1) * valueSet.get(i).get(l));
						else
							weight.set(l, weight.get(l) + rate * valueSet.get(i).get(valueSet.get(i).size() - 1));					
					}
				}
			}
		}	
	}
	
	// 显示训练后的权值向量
	public void displaySet() {
		for (int i = 0; i < weight.size(); i++) {
			System.out.print(weight.get(i) + " ");
		}
	}
	
	// 记录测试集正负分类数量
	public void testTesting(ArrayList<String> set) {
		for(int i = 0; i < set.size(); i++) {
			String[] values = set.get(i).split(",");
			ArrayList<Double> valueRaw = new ArrayList<Double>();
			for(int j = 0; j < values.length; j++) {
				valueRaw.add(Double.parseDouble(values[j]));
			}
			testSet.add(valueRaw);
		}
		
		int yes = 0;
		int no = 0;
		for(int i = 0; i < testSet.size(); i++) {		
			double sum = 0;
			// 矩阵向量乘法
			for(int j = 0; j < weight.size(); j++) {
				if(j != weight.size() - 1) {
					sum += testSet.get(i).get(j) * weight.get(j);
				}
				else {
					sum += weight.get(j);
				}
			}
			
			if(sum * testSet.get(i).get(testSet.get(i).size() - 1) > 0) {
				yes++;
			}
			else {
				no++;
			}
		}
		YES = yes;
		NO = no;
	}
	
	// 记录训练集正负分类数量
	public void testTraining() {
		int yes = 0;
		int no = 0;
		for(int i = 0; i < valueSet.size(); i++) {		
			double sum = 0;
			// 矩阵向量乘法
			for(int j = 0; j < weight.size(); j++) {
				if(j != weight.size() - 1) {
					sum += valueSet.get(i).get(j) * weight.get(j);
				}
				else {
					sum += weight.get(j);
				}
			}
			
			if(sum * valueSet.get(i).get(valueSet.get(i).size() - 1) > 0) {
				yes++;
			}
			else {
				no++;
			}
		}
		YES = yes;
		NO = no;
	}
}
