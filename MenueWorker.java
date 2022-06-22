package Klassen;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import javafx.scene.transform.Scale;

public class MenueWorker {
	
	static String url = "jdbc:mysql://localhost:3306/girodiitalia?seTimezone=true&serverTimezone=UTC";
	static String user = "root";
	static String pass = "Marco123"; 
	
	static String eingabe; 
	static String csv;
	static String json;
	
	static String etappennr; 
	static String stnr;
	static String streckenzeit;
	static String Datum;
	
	static Connection getConnection(String url, String user, String pass) {
		try {
			return DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
public static void main(String[] args) throws FileNotFoundException {
	Connection c =getConnection(url, user, pass); 
	Scanner s = new Scanner(System.in);  
	while(true) { 
	System.out.println("Menü:");
	System.out.println("Einlesen(1)\nAusgabe_Console(2)\nAusgabe_Json(3)\nErgebniss_Eintragen(4)\n");
	eingabe = s.nextLine();	 
	
	if(eingabe.equals("1")) { 
		System.out.println("In welche Tabelle wollen sie einlesen:");
		System.out.println("Fahrer(1)\nEtappen(2\n"); 
		eingabe = s.nextLine(); 
			if(eingabe.equals("1")) { 
				System.out.println("Bitte den Pfad zum CSV-File angeben:");
				csv=s.nextLine(); 
				Fahrer.insertFahrer(c, csv);
			}else
			if(eingabe.equals("2")) { 
				System.out.println("Bitte den Pfad zum CSV-File angeben:");
				csv=s.nextLine(); 
				Etappen.insertEtappen(c, csv);
			}
	}else 
	if(eingabe.equals("2")) { 
		System.out.println("Welche Tabelle wollen sie ausgeben:"); 
		System.out.println("Fahrer(1)\nEtappen(2)\nErgebnis(3)\n");
		eingabe=s.nextLine(); 
			if(eingabe.equals("1")) { 
				Fahrer.showfahrer(c); 
			}else
			if(eingabe.equals("2")) { 
				Etappen.showetappen(c);
			}else
			if(eingabe.equals("3")) { 
			Ergebnis.showErgebnis(c);
			}
	}else
	if(eingabe.equals("3")) { 
		System.out.println("Welche Tabelle wollen sie im Json Format ausgeben:"); 
		System.out.println("Fahrer(1)\nEtappen(2)\nErgebnis(3)\n");
		eingabe=s.nextLine(); 
			if(eingabe.equals("1")) { 
				System.out.println("Bitte den Ausgabe Pfad angeben");
				json=s.nextLine();
				Fahrer.writeFahrer(c, json);
			}else 
			if(eingabe.equals("2")) { 
				System.out.println("Bitte den Ausgabe Pfad angeben");
				json=s.nextLine(); 
				Etappen.writeEtappen(c, json);
			}
			if(eingabe.equals("3")) { 
				System.out.println("Bitte den Ausgabe Pfad angeben");
				json=s.nextLine(); 
				Ergebnis.writeErgebnis(c, json);
			
			}
	}else
	if(eingabe.equals("4")) { 
		System.out.println("Etappennummer eingeben:");
		etappennr=s.nextLine();
		System.out.println("Startnummer eingeben:");
		stnr =s.nextLine(); 
		System.out.println("Streckenzeit angeben:");
		streckenzeit =s.nextLine(); 
		System.out.println("Datum angeben:Bsp:2000-01-01 ");
		Datum =s.nextLine();
		Ergebnis.eintragen(c, etappennr, stnr, streckenzeit, Datum);
	}
	
}

	
	
} 
}


