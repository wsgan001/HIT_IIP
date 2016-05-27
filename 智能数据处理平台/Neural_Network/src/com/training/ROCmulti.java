package com.training;

import java.util.ArrayList;

public class ROCmulti {
	private Training training;
	private Training multiTraining;
	
	private ArrayList<ArrayList<Double>> testSet = new ArrayList<ArrayList<Double>>();
	private ArrayList<Double> values = new ArrayList<Double>();
	private ArrayList<Double> multi = new ArrayList<Double>();
	private double[][] rocTraining;
	private double[][] rocMulti;
	// 初始化真正，与真负数量
	private int truePlus = 0;
	private int trueMinus = 0;
	
	public ROCmulti(Training training, Training multiTraining) {
		this.multiTraining = multiTraining;
		this.training = training;
		this.testSet = training.getTestSet();
		
		calculateTrue();
		getValues1();
		getValues2();
	}
	
	public void calculateTrue() {
		for (int i = 0; i < testSet.size(); i++) {
			if(testSet.get(i).get(testSet.get(i).size() - 1) > 0)
				truePlus++;
			else
				trueMinus++;
		}
	}
	
	public void trainingROC() {
		
		rocTraining = new double[21][2];
		rocMulti = new double[21][2];
		// 感知机描点
		for(double i = -0.5, k = 0; i < 0.51; i += 0.05, k++) {
			// 真正
			int yes = 0;
			// 假负
			int no = 0;
			
			for(int j = 0; j < testSet.size(); j++) {
				if( ((values.get(j) + i) * testSet.get(j).get(testSet.get(j).size() - 1) > 0) && 
						(values.get(j) + i) > 0)
					yes++;
				else if( ((values.get(j) + i) * testSet.get(j).get(testSet.get(j).size() - 1) < 0) && 
						(values.get(j) + i) > 0) {
					no++;
				}
			}
			//System.out.println((double)yes/truePlus + " " + (double)no/trueMinus);
			rocTraining[(int)k][0] = (double)no/trueMinus;
			rocTraining[(int)k][1] = (double)yes/truePlus;
		}
		
		// 感知机描点
		for(double i = -0.5, k = 0; i < 0.51; i += 0.05, k++) {
			// 真正
			int yes = 0;
			// 假负
			int no = 0;
			
			for(int j = 0; j < testSet.size(); j++) {
				if( ((multi.get(j) + i) * testSet.get(j).get(testSet.get(j).size() - 1) > 0) && 
						(multi.get(j) + i) > 0)
					yes++;
				else if( ((multi.get(j) + i) * testSet.get(j).get(testSet.get(j).size() - 1) < 0) && 
						(multi.get(j) + i) > 0) {
					no++;
				}
			}
			//System.out.println((double)yes/truePlus + " " + (double)no/trueMinus);
			rocMulti[(int)k][0] = (double)no/trueMinus;
			rocMulti[(int)k][1] = (double)yes/truePlus;
		}
			
		
		ROCmultiplot cplot = new ROCmultiplot(rocTraining, rocMulti);
		cplot.plot();
	}
	
	public void getRange() {
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for(int i = 0; i < values.size(); i++) {
			if(values.get(i) > max) 
				max = values.get(i);
			if(values.get(i) < min)
				min = values.get(i);
		}
		
		System.out.println(max + " " + min);
	}
	
	public void getValues1() {
		for(int i = 0; i < testSet.size(); i++) {		
			double sum = 0;
			// 矩阵向量乘法
			for(int j = 0; j < training.getWeight().size(); j++) {
				if(j != training.getWeight().size() - 1) {
					sum += testSet.get(i).get(j) * training.getWeight().get(j);
				}
				else {
					sum += training.getWeight().get(j);
				}
			}		
			values.add(sum);
		}
	}
	
	public void getValues2() {
		for(int i = 0; i < testSet.size(); i++) {		
			double sum = 0;
			// 矩阵向量乘法
			for(int j = 0; j < multiTraining.getWeight().size(); j++) {
				if(j != multiTraining.getWeight().size() - 1) {
					sum += testSet.get(i).get(j) * multiTraining.getWeight().get(j);
				}
				else {
					sum += multiTraining.getWeight().get(j);
				}
			}		
			multi.add(sum);
		}
	}
}
