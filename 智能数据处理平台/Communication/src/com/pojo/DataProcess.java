package com.pojo;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.util.DBConnection;

public class DataProcess {
	
	public static void main(String[] args) {
		
		DataProcess dataProcess = new DataProcess();
		
		// 数据缺失处理
		
		/*ArrayList<String> col = new ArrayList<String>();
		col = dataProcess.queryCol();
		for (int i = 6; i < col.size(); i++) {
			//System.out.println(col.get(i));
			double average = dataProcess.queryEffective(col.get(i));
			System.out.println(average);
			dataProcess.updateNA(average, col.get(i));
		}*/
		
		// 数据规范化
		
		/*ArrayList<String> col = new ArrayList<String>();
		ArrayList<Double> doubleCol = new ArrayList<Double>();
		col = dataProcess.queryData("WindDirection..Resultant_1");
		for (String string : col) {
			doubleCol.add(Double.parseDouble(string));
		}
		
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for(int i = 0; i < doubleCol.size(); i++) {
			if(doubleCol.get(i) < min) {
				min = doubleCol.get(i);
			}
			if(doubleCol.get(i) > max) {
				max = doubleCol.get(i);
			}
		}
		
		double dev = max - min;
		for(int i = 0; i < doubleCol.size(); i++) {
			doubleCol.set(i, (doubleCol.get(i) - min) / dev);
		}
		
		double stand_dev = Math.sqrt(dataProcess.getVariance(doubleCol));
		
		double mean = dataProcess.getMeanValue(doubleCol);
		
		for(int i = 0; i < doubleCol.size(); i++) {
			doubleCol.set(i, (doubleCol.get(i) - mean) / stand_dev);
		}
		
		for(int i = 0; i < doubleCol.size(); i++) {
			System.out.println(doubleCol.get(i));
		}*/
		
		// 相关分析
		
		/*ArrayList<String> col = new ArrayList<String>();
		ArrayList<Double> doubleSolar = new ArrayList<Double>();
		ArrayList<Double> doubleTarget = new ArrayList<Double>();
		col = dataProcess.queryData("Solar.radiation_64");
		for (String string : col) {
			doubleSolar.add(Double.parseDouble(string));
		}
		
		col = new ArrayList<String>();
		col = dataProcess.queryData("target_1_57");
		for (String string : col) {
			doubleTarget.add(Double.parseDouble(string));
		}
		
		double meanSolar = dataProcess.getMeanValue(doubleSolar);
		double meanTarget = dataProcess.getMeanValue(doubleTarget);
		
		double stand_devSolar = Math.sqrt(dataProcess.getVariance(doubleSolar));
		double stand_devTarget = Math.sqrt(dataProcess.getVariance(doubleTarget));
		
		double sum = 0;
		for(int i = 0; i < doubleSolar.size(); i++) {
			sum += ((doubleSolar.get(i) - meanSolar) * (doubleTarget.get(i) - meanTarget));
		}
		sum /= ((doubleSolar.size() - 1) * stand_devSolar * stand_devTarget);
		System.out.println(sum);*/
		
		//卡方检验
		
		int[][] Chi_Square = new int[8][11];
		double[][] expection = new double[8][11];
		String[] weekday = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		
		for(int m = 0; m < weekday.length; m++) {
			for(int i = 730, j = 0; i < 780; i+=5, j++) {
				Chi_Square[m][j] = dataProcess.queryAmount(i, i+4, weekday[m]);
			}
		}
		
		for(int i = 0; i < 10; i++) {
			int totalCol = 0;
			for(int j = 0; j < 7; j++) {
				totalCol += Chi_Square[j][i];
			}
			Chi_Square[7][i] = totalCol;
		}
		
		for(int i = 0; i < 7; i++) {
			int totalRaw = 0;
			for(int j = 0; j < 10; j++) {
				totalRaw += Chi_Square[i][j];
			}
			Chi_Square[i][10] = totalRaw;
		}
		
		int sum = 0;
		for(int i = 0; i < 10; i++) {
			sum += Chi_Square[7][i];
		}
		Chi_Square[7][10] = sum;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 11; j++) {
				expection[i][j] = Chi_Square[i][j];
			}
		}
		
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 10; j++) {
				expection[i][j] = ((double)expection[i][10] / expection[7][10]) * expection[7][j];
			}
		}
		
		double chiSquare = 0;
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 10; j++) {
				chiSquare += ((double)Chi_Square[i][j] - expection[i][j]) * ((double)Chi_Square[i][j] - expection[i][j]) / expection[i][j];
			}
		}
		
		System.out.println(chiSquare);
		
		
	}
	
	public double getMeanValue(ArrayList<Double> list) {
		double average = 0;
		
		for(int i = 0; i < list.size(); i++) {
			average += list.get(i);
		}
		
		average /= list.size();
		
		return average;
	}
	
	public double getVariance(ArrayList<Double> list) {
		double result = 0;		
		double average = getMeanValue(list);
		
		for(int i = 0; i < list.size(); i++) {
			result += (list.get(i) - average) * (list.get(i) - average);
		}
		
		result /= list.size();	
		return result;
	}

	public int queryAmount(int start, int end, String weekday) {
		int i = 0;
		String sql = "select count(`Sample.Baro.Pressure_52`) from trainingdata WHERE `Sample.Baro.Pressure_52` <= "+end+" and `Sample.Baro.Pressure_52` >= "+start+" and weekday = '"+weekday+"'";
		//System.out.println(sql);
		DBConnection db = new DBConnection();
		try {
			db.pst = db.conn.prepareStatement(sql);
		
			ResultSet resultSet = db.pst.executeQuery();
			
			while(resultSet.next()) {				
			    i = resultSet.getInt(1);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return i;
	}
	
	public ArrayList<String> queryData(String name) {
		ArrayList<String> col = new ArrayList<String>();
		String sql = "select `"+ name +"` from trainingdata";
		DBConnection db = new DBConnection();
		try {
			db.pst = db.conn.prepareStatement(sql);
		
			ResultSet resultSet = db.pst.executeQuery();
			
			while(resultSet.next()) {				
				col.add(resultSet.getString(name));			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return col;
	}
	
	public ArrayList<String> queryCol() {
		ArrayList<String> col = new ArrayList<String>();
		String sql = "select column_name from information_schema.columns where table_name='trainingdata'";
		DBConnection db = new DBConnection();
		try {
			db.pst = db.conn.prepareStatement(sql);
		
			ResultSet resultSet = db.pst.executeQuery();
			
			while(resultSet.next()) {				
				col.add(resultSet.getString("column_name"));			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		return col;
	}
	
	public void updateNA(double average, String colname) {
		String sql = "UPDATE trainingdata SET `" + colname +"`=" + average + " WHERE `" + colname +"`='NA'";
		DBConnection db = new DBConnection();
		try {
			db.pst = db.conn.prepareStatement(sql);		
			db.pst.executeUpdate(sql);						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public double queryEffective(String colname) {	
		String sql = "select `" + colname +"` from trainingdata WHERE `" + colname +"`!='NA'";
		DBConnection db = new DBConnection();
		double sum = 0;
		try {
			db.pst = db.conn.prepareStatement(sql);
		
			ResultSet resultSet = db.pst.executeQuery();
			
			int index = 0;
			while(resultSet.next()) {
				
				//System.out.println(resultSet.getString(colname));
				sum += Double.parseDouble(resultSet.getString(colname));
				index++;
			}
			sum /= index;
			//System.out.println(sum);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return sum;
	}
}
