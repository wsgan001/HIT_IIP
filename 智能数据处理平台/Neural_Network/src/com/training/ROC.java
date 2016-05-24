package com.training;

import java.util.ArrayList;

public class ROC {

	private Training training;
	private Classfication classfication;
	
	private ArrayList<ArrayList<Double>> testSet = new ArrayList<ArrayList<Double>>();
	private ArrayList<Double> values = new ArrayList<Double>();
	
	private double[][] rocTraining;
	private double[][] rocClassify;
	// 初始化真正，与真负数量
	private int truePlus = 0;
	private int trueMinus = 0;
	
	private double maxDistance;
	
	
	public ROC(Training training, Classfication classfication) {
		this.classfication = classfication;
		this.training = training;
		this.testSet = training.getTestSet();
		
		this.maxDistance = classfication.getMaxDistance();
		
		getValues();
		calculateTrue();
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
		rocClassify = new double[21][2];
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
		
		// 中心分类描点
		for(int i = 0; i < 21; i++) {
			// 真正
			int yes = 0;
			// 假负
			int no = 0; 
			
			double optional = maxDistance - 0.1 * i * maxDistance;
			
			for(int j = 0; j < testSet.size(); j++) {
				if(classfication.getDistance()[j][0] + optional < classfication.getDistance()[j][1]) {
					if(1 * testSet.get(j).get(testSet.get(j).size() - 1) > 0) {
						yes++;
					}
					else
						no++;
				}
			}
			//System.out.println((double)no/trueMinus + " " + (double)yes/truePlus);
			rocClassify[i][0] = (double)no/trueMinus;
			rocClassify[i][1] = (double)yes/truePlus;
		}
		
		ROCplot cplot = new ROCplot(rocTraining, rocClassify);
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
	
	public void getValues() {
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
}
