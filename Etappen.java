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

public class Etappen {
	
	static void createtable(Connection c, String tablename) { 
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tablename + "(nr int auto_increment, start varchar(255), "
					+ " ziel varchar(255), kilometer int, hoehenmeter int, primary key (Nr));";
			System.out.println("Table " + tablename + " wurde erstellt"); 
			System.out.println("\n");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	} 
	
	
	static void insertEtappen(Connection c, String file) throws FileNotFoundException {
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			String string = "";
			int i = 0;
			
			while (s.hasNextLine()) {
				string = s.nextLine();
				String[] str = string.split(";");
				String sql = "insert  ignore into etappen (start,ziel,kilometer,hoehenmeter) values (?,?,?,?);";
				PreparedStatement stmt = c.prepareStatement(sql);
				stmt.setString(1, str[0]);
				stmt.setString(2, str[1]); 
				stmt.setString(3, str[2]);
				stmt.setString(4, str[3]);
				stmt.executeUpdate();
				stmt.close();
				System.out.printf("insert Etappen war Erfolgreich");
				System.out.println("\n");
				i++;
			}
			s.close();
			 	
		} catch (SQLException e) {

		}
	}  
	
	static void writeEtappen(Connection c, String file) {
		try {
			FileWriter fw = new FileWriter(file);
			JSONObject j = new JSONObject();
			String s = "";

			Statement stmt = c.createStatement();
			String sql = "select nr,start,ziel,kilometer,hoehenmeter from etappen;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int nr = rs.getInt("nr");
				String st = rs.getString("start");
				String zl = rs.getString("ziel");
				int km = rs.getInt("kilometer");
				int hm = rs.getInt("hoehenmeter");
			
				j.put("nr", nr);	
				j.put("start", st);
				j.put("ziel", zl);
				j.put("kilometer",km);
				j.put("hoehenmeter", hm);
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
	
	
	
	
	static void showetappen(Connection c) { 
		try {
			 Statement stmt = c.createStatement(); 
			 String sql = "Select * from etappen;"; 
			 ResultSet rs = stmt.executeQuery(sql); 
			 while(rs.next()) { 
				 int nr = rs.getInt("nr"); 
				 String s = rs.getString("start"); 
				 String z = rs.getString("ziel"); 
				 int kilometer = rs.getInt("kilometer"); 
				 int hoehe = rs.getInt("hoehenmeter"); 
				 
				 System.out.printf("%d||%s||%s||%d||%d||",nr,s,z,kilometer,hoehe); 
				 System.out.println("\n");
			 	}
				 rs.close();
				 stmt.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
}
