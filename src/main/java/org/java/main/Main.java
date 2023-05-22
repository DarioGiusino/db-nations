package org.java.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/nations_db";
		String user = "root";
		String password = "root";
		
		try(Scanner sc = new Scanner(System.in); Connection conn = DriverManager.getConnection(url, user, password)){
			
			String sql = "SELECT c.name AS nomeNazione, c.country_id AS idNazione, r.name AS nomeRegione, c2.name AS nomeContinente\r\n"
					+ "FROM countries c \r\n"
					+ "JOIN regions r \r\n"
					+ "ON c.region_id = r.region_id \r\n"
					+ "JOIN continents c2 \r\n"
					+ "ON r.continent_id = c2.continent_id \r\n"
					+ "WHERE c.name LIKE ?\r\n"
					+ "ORDER BY c.name";
			
			System.out.print("Inserisci parametro di ricerca:");
			String searchTerm = sc.nextLine();
			
			try(PreparedStatement ps = conn.prepareStatement(sql)){
				
				ps.setString(1, "%" + searchTerm + "%");
				
				try(ResultSet rs = ps.executeQuery()){
					
					while(rs.next()) {
						final String countryName = rs.getString("nomeNazione");
						final int countryId = rs.getInt("idNazione");
						final String regionName = rs.getString("nomeRegione");
						final String continentName = rs.getString("nomeContinente");
						
						System.out.println(countryName + " - " + countryId + " - " + regionName + " - " + continentName);
					}
				}
				
			}
			
		} catch(SQLException ex) {
			System.err.println("Error: " + ex);
		}
		
	}
}
