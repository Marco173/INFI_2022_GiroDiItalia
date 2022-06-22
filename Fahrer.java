package Klassen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.json.JSONObject;

public class Fahrer {
	
	static void createtable(Connection c, String tablename) { 
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tablename + "(startnr int auto_increment, vorname varchar(255), "
					+ " nachname varchar(255), nationalitaet varchar(3), team varchar(255), primary key (startnr));";
			System.out.println("Table " + tablename + " wurde erstellt"); 
			System.out.println("\n");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	} 
	
	
	static void insertFahrer(Connection c, String file) throws FileNotFoundException {
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			String string = "";
			int i = 0;
			
			while (s.hasNextLine()) {
				string = s.nextLine();
				String[] str = string.split(";");
				String sql = "insert ignore into fahrer (vorname,nachname,nationalitaet,team) values (?,?,?,?);";
				PreparedStatement stmt = c.prepareStatement(sql);
				stmt.setString(1, str[0]);
				stmt.setString(2, str[1]); 
				stmt.setString(3, str[2]);
				stmt.setString(4, str[3]);
				stmt.executeUpdate();
				stmt.close();
				System.out.printf("insert Fahrer war Erfolgreich");
				System.out.println("\n");
				i++;
			}
			s.close();
			 	
		} catch (SQLException e) {

		}
	}  
	
	static void writeFahrer(Connection c, String file) {
		try {
			FileWriter fw = new FileWriter(file);
			JSONObject j = new JSONObject();
			String s = "";

			Statement stmt = c.createStatement();
			String sql = "select startnr,vorname,nachname,nationalitaet,team from fahrer;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int nr = rs.getInt("startnr");
				String vn = rs.getString("vorname");
				String nn = rs.getString("nachname");
				String na= rs.getString("nationalitaet");
				String team = rs.getString("team");
			
				j.put("nr", nr);	
				j.put("start", vn);
				j.put("ziel", nn);
				j.put("kilometer",na);
				j.put("hoehenmeter", team);
				s = s + j;
			
			}
			fw.write(s); 
			fw.flush();
			fw.close();
			rs.close();
			stmt.close();
			System.out.println("funktioniert Fahrer");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
	}
}
	
	
	static void showfahrer(Connection c) { 
		try {
			 Statement stmt = c.createStatement(); 
			 String sql = "Select * from fahrer;"; 
			 ResultSet rs = stmt.executeQuery(sql); 
			 while(rs.next()) { 
				 int nr = rs.getInt("startnr"); 
				 String s = rs.getString("vorname"); 
				 String z = rs.getString("nachname"); 
				 String nt = rs.getString("nationalitaet"); 
				 String team = rs.getString("team"); 
				 
				 System.out.printf("%d||%s||%s||%s||%s||",nr,s,z,nt,team); 
				 System.out.println("\n");
			 	}
				 rs.close();
				 stmt.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
	
	
}
