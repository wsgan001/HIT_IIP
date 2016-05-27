package com.training;

import java.util.ArrayList;

public class MultiSense {

	private ArrayList<ArrayList<Double>> valueSet = new ArrayList<ArrayList<Double>>();
	private ArrayList<ArrayList<Double>> testSet = new ArrayList<ArrayList<Double>>();
	
	private ArrayList<Double> hiddenOutput = new ArrayList<Double>();
	//private ArrayList<ArrayList<Double>> hiddenValue;
	private double[][] weight1;
	private double[][] loseWeight1;
	private double[] weight2;
	private double[] loseWeight2;
	private double res;
	private static int hiddenLayout = 10;
	
    public void displayWeight() {
    	for(int i = 0; i < hiddenLayout;i++) {
			for(int j = 0; j < valueSet.get(0).size(); j++)
			    System.out.print(weight1[i][j] + " " );
			System.out.println();
    	}
		
		
		for(int j = 0; j < hiddenLayout; j++)
			System.out.print(weight2[j] + " " );
	    System.out.println();
		
    }
	
    public void displayHidden() {
    	System.out.println();
    	for(int i = 0; i < hiddenOutput.size(); i++) {
    		System.out.print(hiddenOutput.get(i) + " ");
    	}
    	System.out.println();
    }
    
    public void displayRes() {
    	System.out.println(res);
    }
    
	// 对训练集进行初始化赋值
	public MultiSense(ArrayList<String> set, ArrayList<String> test) {	
		for(int i = 0; i < set.size(); i++) {
			String[] values = set.get(i).split(",");
			ArrayList<Double> valueRaw = new ArrayList<Double>();
			for(int j = 0; j < values.length; j++) {
				valueRaw.add(Double.parseDouble(values[j]));
			}
			valueSet.add(valueRaw);
		}
		
		for(int i = 0; i < test.size(); i++) {
			String[] values = test.get(i).split(",");
			ArrayList<Double> valueRaw = new ArrayList<Double>();
			for(int j = 0; j < values.length; j++) {
				valueRaw.add(Double.parseDouble(values[j]));
			}
			testSet.add(valueRaw);
		}

	}
	
	public void sigmoid() {
		for(int i = 0; i < hiddenOutput.size(); i++) {
		    double mother = 1 + Math.pow( Math.E, - hiddenOutput.get(i) );
		    double result = (double)1 / mother;		
		    hiddenOutput.set(i, result);
		}
	}
	
	public double f(double x) {
		double son = Math.pow(Math.E, x);
		double mother = Math.pow(Math.pow(Math.E, x) + 1, 2);
		return son / mother;
	}
	
	public void range() {
		double mother = 1 + Math.pow( Math.E, - res );
	    double result = (double)1 / mother;
	    result = 2 * result - 1;
	    res = result;
	}
	
	// 初始化权值向量
	public void setWeightValue() {
		for(int i = 0; i < hiddenLayout;i++)
			for(int j = 0; j < valueSet.get(0).size(); j++)
			    weight1[i][j] = ((double)(-1 + Math.random()*2));
		
		
		for(int j = 0; j < hiddenLayout; j++)
		    weight2[j] = ((double)(-1 + Math.random()*2));
	}
	
	// 对多层感知机测试
	public void testing(double compare) {
		
		int yes = 0;
		int no = 0;
		
		for(int i = 0; i < testSet.size(); i++) {
			// 每一个输入与第一层权值矩阵相乘
			hiddenOutput = new ArrayList<Double>();
			for (int j = 0; j < weight1.length; j++) {					
				double sum = 0;	
				for (int k = 0; k < weight1[0].length; k++) {
					if(k < weight1[0].length - 1)
					    sum += valueSet.get(i).get(k) * weight1[j][k];
					else
						sum += weight1[j][k];
				}				
				hiddenOutput.add(sum);
			}			
					
			sigmoid();
			
			res = 0;
			for (int k = 0; k < weight2.length; k++) {
				if(k < weight2.length - 1)
				    res += hiddenOutput.get(k) * weight2[k];
				else
					res += weight2[k];
			}							

            range();
            
            if(res * testSet.get(i).get(22) > compare) {
            	yes++;
            }
            else
            	no++;
		}
		
		System.out.println(yes + " " + no);
	}
	
	// 对多层感知机开始学习
	public void training() {
		// 对权值向量矩阵赋初值
		weight1 = new double[hiddenLayout][valueSet.get(0).size()];
		weight2 = new double[hiddenLayout + 1];
		loseWeight2 = new double[hiddenLayout + 1];
		loseWeight1 = new double[hiddenLayout][valueSet.get(0).size()];
		//System.out.println(hiddenLayout + " " + valueSet.get(0).size());	
		setWeightValue();
		
		// 判断步数	
		int step = 100;
		// 学习率
		double rate = 0.01;
		
		for(int p = 0; p < step; p++) {
			for(int i = 0; i < valueSet.size(); i++) {
				
				// 每一个输入与第一层权值矩阵相乘
				hiddenOutput = new ArrayList<Double>();
				ArrayList<Double> hiddenOutput_origin = new ArrayList<Double>();
				for (int j = 0; j < weight1.length; j++) {					
					double sum = 0;	
					for (int k = 0; k < weight1[0].length; k++) {
						if(k < weight1[0].length - 1)
						    sum += valueSet.get(i).get(k) * weight1[j][k];
						else
							sum += weight1[j][k];
					}				
					hiddenOutput.add(sum);
				}
				
				for(int j = 0; j < hiddenOutput.size(); j++) {
					hiddenOutput_origin.add(hiddenOutput.get(j));
				}
						
				sigmoid();							
                			
	            res = 0;
				for (int k = 0; k < weight2.length; k++) {
					if(k < weight2.length - 1)
					    res += hiddenOutput.get(k) * weight2[k];
					else
						res += weight2[k];
				}							
				double res_origin = res;
                range();
                
                double deviation = 0.5 * Math.pow(res - valueSet.get(i).get(22), 2);
                if(deviation < 0.001)
                	continue;
                else {
                	double p1 = res - valueSet.get(i).get(22);
                	double p2 = p1 * f(res_origin) * 2;
                	for(int t = 0; t < loseWeight2.length; t++) {
                		if(t < loseWeight2.length - 1)
                		    loseWeight2[t] = p2 * hiddenOutput.get(t);
                		else
                			loseWeight2[t] = p2;
                	}
                	// 更新第一层
                	for(int t = 0; t < weight2.length; t++) {
                		weight2[t] = weight2[t] - rate * loseWeight2[t];
                	}
                	
                	for(int t = 0; t < loseWeight2.length - 1; t++) {
                		loseWeight2[t] = loseWeight2[t] * f(hiddenOutput_origin.get(t));             		
                	}
                	
                	
                	for(int m = 0; m < loseWeight1.length; m++) {
                		for(int n = 0; n < loseWeight1[m].length; n++) {
                			loseWeight1[m][n] = loseWeight2[m] * valueSet.get(i).get(n);
                		}
                	}
                	
                	for(int m = 0; m < weight1.length; m++) {
                		for(int n = 0; n < weight1.length; n++) {
                			weight1[m][n] = weight1[m][n] - rate * loseWeight1[m][n];
                		}
                	}
                	
                }
                
			}		
		}
		
		displayWeight();
	}
}
