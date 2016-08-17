package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

@RunWith(Suite.class)

@SuiteClasses({ UsuarioDaoTeste.class, SensorDaoTeste.class })

public class TestesDao {

	@AfterClass
	public static void tearDown() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		PreparedStatement stmt = connection.prepareStatement("delete from usuarioSensor");
		stmt.execute();
		stmt = connection.prepareStatement("delete from usuario");
		stmt.execute();
		stmt = connection.prepareStatement("delete from endereco");
		stmt.execute();
		stmt = connection.prepareStatement("delete from sensor");
		stmt.execute();
		stmt = connection.prepareStatement("delete from registroDeTemperatura");
		stmt.execute();
		stmt.close();
		connection.close();
	}

}
