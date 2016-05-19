package com.training;

import java.util.ArrayList;

public class Training {
    
	private ArrayList<ArrayList<Double>> valueSet = new ArrayList<ArrayList<Double>>();
	
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
		
	}
	
	public void displaySet() {
		for (int i = 0; i < valueSet.get(0).size(); i++) {
			System.out.print(valueSet.get(0).get(i) + " ");
		}
	}
}
