package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.UsuarioSensor;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class UsuarioSensorDao {
	
	private Connection connection;

	public UsuarioSensorDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(UsuarioSensor usuarioSensor, String usuarioLogin,
			Long sensorCodigo) {
		String sql = "insert into sensor "
				+ "(temperaturaMinima, temperaturaMaxima, "
				+ "intervaloDeAtualizacaoDeDados, sensor_codigo, usuario_login)"
				+ " values (?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			Util.setFloatPreparedStatement(stmt, 1, usuarioSensor
					.getTemperaturaMinima());
			Util.setFloatPreparedStatement(stmt, 2, usuarioSensor
					.getTemperaturaMaxima());
			Util.setIntegerPreparedStatement(stmt, 3, usuarioSensor
					.getIntervaloDeAtualizacaoDeDados());
			stmt.setLong(4, sensorCodigo);
			stmt.setString(5, usuarioLogin);
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<UsuarioSensor> getLista() {
		try {
			List<UsuarioSensor> registroDeTemperaturas = new ArrayList<>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarioSensor ");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// criando o objeto registroDeTemperatura
				UsuarioSensor usuarioSensor = new UsuarioSensor();
				usuarioSensor.setTemperaturaMinima(rs.getFloat("temperaturaMinima"));
				usuarioSensor.setTemperaturaMaxima(rs.getFloat("temperaturaMaxima"));
				usuarioSensor.setIntervaloDeAtualizacaoDeDados(rs.
						getInt("intervaloDeAtualizacaoDeDados"));
				usuarioSensor.setSensor(new SensorDao().busca(rs.getLong("sensor_codigo")));
				usuarioSensor.setUsuario(new UsuarioDao().busca(rs.getString("usuario_login")));
				// adicionando o objeto à lista
				registroDeTemperaturas.add(usuarioSensor);
			}
			rs.close();
			stmt.close();
			return registroDeTemperaturas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<UsuarioSensor> buscaPorUsuario(String usuarioLogin) {
		try {
			List<UsuarioSensor> registroDeTemperaturas = new ArrayList<>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarioSensor " +
							"where login_usuario=?");
			stmt.setString(1, usuarioLogin);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// criando o objeto registroDeTemperatura
				UsuarioSensor usuarioSensor = new UsuarioSensor();
				usuarioSensor.setTemperaturaMinima(rs.getFloat("temperaturaMinima"));
				usuarioSensor.setTemperaturaMaxima(rs.getFloat("temperaturaMaxima"));
				usuarioSensor.setIntervaloDeAtualizacaoDeDados(rs.
						getInt("intervaloDeAtualizacaoDeDados"));
				usuarioSensor.setSensor(new SensorDao().busca(rs.getLong("sensor_codigo")));
				usuarioSensor.setUsuario(new UsuarioDao().busca(rs.getString("usuario_login")));
				// adicionando o objeto à lista
				registroDeTemperaturas.add(usuarioSensor);
			}
			rs.close();
			stmt.close();
			return registroDeTemperaturas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<UsuarioSensor> buscaPorSensor(String sensorCodigo) {
		try {
			List<UsuarioSensor> registroDeTemperaturas = new ArrayList<>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarioSensor " +
							"where sensor_codigo=?");
			stmt.setString(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// criando o objeto registroDeTemperatura
				UsuarioSensor usuarioSensor = new UsuarioSensor();
				usuarioSensor.setTemperaturaMinima(rs.getFloat("temperaturaMinima"));
				usuarioSensor.setTemperaturaMaxima(rs.getFloat("temperaturaMaxima"));
				usuarioSensor.setIntervaloDeAtualizacaoDeDados(rs.
						getInt("intervaloDeAtualizacaoDeDados"));
				usuarioSensor.setSensor(new SensorDao().busca(rs.getLong("sensor_codigo")));
				usuarioSensor.setUsuario(new UsuarioDao().busca(rs.getString("usuario_login")));
				// adicionando o objeto à lista
				registroDeTemperaturas.add(usuarioSensor);
			}
			rs.close();
			stmt.close();
			return registroDeTemperaturas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void altera(UsuarioSensor usuarioSensor, String usuarioLogin,
			Long sensorCodigo) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("update usuarioSensor set "
							+ "temperaturaMinima=?, temperaturaMaxima=?, "
							+ "intervaloDeAtualizacaoDeDados=? "
							+ "where usuario_login=? and sensor_codigo");
			Util.setFloatPreparedStatement(stmt, 1, usuarioSensor
					.getTemperaturaMinima());
			Util.setFloatPreparedStatement(stmt, 2, usuarioSensor
					.getTemperaturaMaxima());
			Util.setIntegerPreparedStatement(stmt, 3, usuarioSensor
					.getIntervaloDeAtualizacaoDeDados());
			stmt.setString(4, usuarioLogin);
			stmt.setLong(5, sensorCodigo);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
