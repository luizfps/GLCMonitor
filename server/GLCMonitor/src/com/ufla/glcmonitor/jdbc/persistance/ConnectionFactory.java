package com.ufla.glcmonitor.jdbc.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {


	public Connection getConnection() {
		try {
		    Class.forName(ConnectionConfiguration.DRIVER);
		} 
		catch (ClassNotFoundException e) {
		    e.printStackTrace();
		} 
        try {
            return DriverManager.getConnection(
            	ConnectionConfiguration.URL, ConnectionConfiguration.USUARIO, 
            	ConnectionConfiguration.SENHA);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
