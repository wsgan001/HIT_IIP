package com.pojo;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.util.DBConnection;

public class DataProcess {
	
	public static void main(String[] args) {
		
		DataProcess dataProcess = new DataProcess();
		
		/*ArrayList<String> col = new ArrayList<String>();
		col = dataProcess.queryCol();
		for (int i = 6; i < col.size(); i++) {
			//System.out.println(col.get(i));
			double average = dataProcess.queryEffective(col.get(i));
			System.out.println(average);
			dataProcess.updateNA(average, col.get(i));
		}*/
		
		/*ArrayList<String> col = new ArrayList<String>();
		ArrayList<Double> doubleCol = new ArrayList<Double>();
		col = dataProcess.queryData();
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

	
	public ArrayList<String> queryData() {
		ArrayList<String> col = new ArrayList<String>();
		String sql = "select `WindDirection..Resultant_1` from trainingdata";
		DBConnection db = new DBConnection();
		try {
			db.pst = db.conn.prepareStatement(sql);
		
			ResultSet resultSet = db.pst.executeQuery();
			
			while(resultSet.next()) {				
				col.add(resultSet.getString("WindDirection..Resultant_1"));			
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
