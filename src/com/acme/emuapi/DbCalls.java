package com.acme.emuapi;

public class DbCalls {
	
	public String testing(){
		
		DbCalls call = new DbCalls();
		return this.getAllStat();
		
	}
		
	
	
	
	private String getAllStat(){
		String output = "";
		DbCon dbconnection = new DbCon();
		//dbconnection.connectToDb();
		//output = dbconnection.getAllStations();
//		dbconnection.disconnect();
		System.out.println(output);
		return output;
	}

}
