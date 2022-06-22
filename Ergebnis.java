package Klassen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

import org.json.JSONObject;

public class Ergebnis {
	
	static void createtable(Connection c, String tablename) { 
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tablename + "(etappennr int,  stnr int, streckenzeit time, eintragsdatum date ,"
					+ " primary key (stnr, etappennr),"
					+ "foreign key(stnr) references fahrer(startnr), "
					+ "foreign key(etappennr) references etappen(nr));";
			System.out.println("Table " + tablename + " wurde erstellt"); 
			System.out.println("\n");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	
	
	static void writeErgebnis(Connection c, String file) {
		try {
			FileWriter fw = new FileWriter(file);
			JSONObject j = new JSONObject();
			String s = "";

			Statement stmt = c.createStatement();
			String sql = "select etappennr,stnr,streckenzeit,eintragsdatum from ergebnis;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int etnr = rs.getInt("etappennr");
				int str  = rs.getInt("stnr");
				String sz = rs.getString("streckenzeit");
				Date d = rs.getDate("eintragsdatum");
				
			
				j.put("etappennr", etnr);	
				j.put("stnr", str);
				j.put("streckenzeit", sz);
				j.put("eintragsdatum",d);
				s = s + j;
			
			}
			fw.write(s); 
			fw.flush();
			fw.close();
			rs.close();
			stmt.close();
			System.out.println("funktioniert");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
	}
}
	
	static void eintragen(Connection c, String etappennr,String stnr, String streckenzeit ,String datum) { 
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		String in ="insert into ergebnis (etappennr, stnr, streckenzeit, eintragsdatum) values (?, ?, ?, ?);"; 
		try {  
			PreparedStatement stmt = c.prepareStatement(in); 
			stmt.setString(1, etappennr); 
			stmt.setString(2, stnr); 
			stmt.setString(3, streckenzeit);
			Date date1;
			date1 = Date.valueOf(datum);
			stmt.setDate(4,date1);
			System.out.println("Insert Ergebnis war erfolgreich"); 
			System.out.println("\n");
			stmt.executeUpdate();
			stmt.close();	
		
	
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void showErgebnis(Connection c) { 
		try {
			 Statement stmt = c.createStatement(); 
			 String sql = "Select etappennr,stnr,streckenzeit from ergebnis;"; 
			 ResultSet rs = stmt.executeQuery(sql); 
			 while(rs.next()) { 
				 int enr = rs.getInt("etappennr"); 
				 int str = rs.getInt("stnr"); 
				 String sz = rs.getString("streckenzeit"); 
				 
				 System.out.printf("%d||%d||%s||",enr,str,sz); 
				 System.out.println("\n");
			 	}
				 rs.close();
				 stmt.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
}	

