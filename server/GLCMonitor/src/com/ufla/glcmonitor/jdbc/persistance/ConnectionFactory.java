package com.ufla.glcmonitor.jdbc.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public static String URL =  "jdbc:mysql://localhost/glcmonitor";
	
	public Connection getConnection() {
        try {
            return DriverManager.getConnection(
          URL, "root", "chp123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
