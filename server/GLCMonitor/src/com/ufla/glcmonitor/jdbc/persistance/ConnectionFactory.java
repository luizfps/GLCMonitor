package com.ufla.glcmonitor.jdbc.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static String URL = "jdbc:mysql://localhost:3306/glcmonitortestes";

	public Connection getConnection() {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) {
		    e.printStackTrace();
		} 
        try {
            return DriverManager.getConnection(
          URL, "root", "32252132");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
