package com.acme.emuapi;
import java.sql.*;
import java.util.ArrayList;


public class DbCon {
	public Connection connection;
	public Statement statement;
	
	private  void connectToDb (){
		try {
			
			Class.forName("oracle.jdbc.OracleDriver");
			String dataBase = "jdbc:oracle:thin:@localhost:1521:orcl";
			connection = DriverManager.getConnection(dataBase, "emuapi","oracle");
			statement = connection.createStatement();
			if (connection != null){
				System.out.println("Connection succesful");
			}else{
				System.out.print("Connection error");
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	

	
	private void disconnectFromDb(){
		try{
			connection.close();
			System.out.println("Disconnected from db");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public  ArrayList getAllStations(){
		
		String result = "";
		
		
		
		try{
			connectToDb();
			ResultSet rs = statement.executeQuery("select * from station");
			int numcols = rs.getMetaData().getColumnCount();
			ArrayList<ArrayList<String>> result1 = new ArrayList<>();
			while (rs.next()){
				ArrayList<String> row = new ArrayList<>(numcols);
				for (int i=1; i<=numcols;i++){
					row.add(rs.getString(i));
					//System.out.println(rs.getString(i) + "\t");
				}
				result1.add(row);
				//System.out.println("\n");
				int stationID = rs.getInt("STATIONID");
				String name = rs.getString("NAME");
				String description = rs.getString("DESCRIPTION");
				String status = rs.getString("STATUS");
				Float latitude = rs.getFloat("LATITUDE");
				Float longitude = rs.getFloat("LONGITUDE");
				result += stationID+"\t"+name+"\t"+description+"\t"+status+"\t"+latitude+"\t"+longitude+"\n";
	
					
				}
				System.out.println(result1);
				return result1;
				//disconnectFromDb();
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
			
		}
	}
	
	public String getStationDet(){
	  String result1 = "";
	  try{
		  connectToDb();
		  ResultSet rs = statement.executeQuery("select station.stationid, sensor.sensorid, sensor.name, sensor.type from station, sensor where station.stationid = sensor.STATION_STATIONID");
		  while(rs.next()){
		  	 int stationId = rs.getInt("stationid");
			  int sensorId = rs.getInt("sensorid");
			  String sensorName = rs.getString("name");
			  String sensorType = rs.getString("type");
			  //System.out.println(stationId+"\t"+sensorId+"\t"+sensorName+"\t"+sensorType+"\n");
			  result1 += stationId+"\t"+sensorId+"\t"+sensorName+"\t"+sensorType+"\n";
		  }
		  System.out.println(result1);
		  return "Success "+result1;
		  //disconnectFromDb();
	  }catch(Exception e){
		  e.getStackTrace();
		  return "error";
	  }
		
	}
	
	public String getSensorData(){
		String result ="";
		try{
			connectToDb();
			
			ResultSet rs = statement.executeQuery("select s.station_stationid, s.SENSORID, s.name, s.type, d.data, d.timestamp from sensor s join DATASENSOR d on s.SENSORID = d.SENSOR_SENSORID");
			while(rs.next()){
				int stationId = rs.getInt("STATION_STATIONID");
				int sensorId = rs.getInt("SENSORID");
				String sensorName =  rs.getString("NAME");
				String sensorType =  rs.getString("TYPE");
				int sensorData = rs.getInt("DATA");
				String timestamp = rs.getString("TIMESTAMP");
				result += stationId+"\t"+sensorId+"\t"+sensorName+"\t"+sensorType+"\t"+sensorData+"\t"+timestamp+"\n";
			}
			System.out.println(result);
			return result;
		}catch(Exception e){
			return "error";
		}
		
		
		
	}
	
}

