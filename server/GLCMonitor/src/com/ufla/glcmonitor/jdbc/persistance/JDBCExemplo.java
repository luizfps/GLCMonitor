package com.ufla.glcmonitor.jdbc.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCExemplo {
	
	public void connection() throws SQLException {
		Connection conexao = DriverManager.getConnection(
		          "jdbc:mysql://localhost/fj21");
		        System.out.println("Conectado!");
		        conexao.close();
	}

}
