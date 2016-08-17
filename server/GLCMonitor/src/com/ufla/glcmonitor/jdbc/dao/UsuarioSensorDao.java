package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.Sensor;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;
import com.ufla.glcmonitor.jdbc.modelo.UsuarioSensor;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class UsuarioSensorDao {

	public void adiciona(UsuarioSensor usuarioSensor, String usuarioLogin, Long sensorCodigo)
			throws SQLException {
		String sql = "insert into sensor " + "(temperaturaMinima, temperaturaMaxima, "
				+ "intervaloDeAtualizacaoDeDados, sensor_codigo, usuario_login)"
				+ " values (?,?,?,?,?)";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			Util.setLimitesDeTemperaturaPreparedStatement(stmt, 1,
					usuarioSensor.getLimitesDeTemperatura());
			Util.setIntegerPreparedStatement(stmt, 3,
					usuarioSensor.getIntervaloDeAtualizacaoDeDados());
			stmt.setLong(4, sensorCodigo);
			stmt.setString(5, usuarioLogin);
			stmt.execute();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao.getMensagemDeExcecao(e.getMessage()));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public List<UsuarioSensor> getLista() throws SQLException {
		String sql = "select * from usuarioSensor";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<UsuarioSensor> usarioSensores = new ArrayList<>();
			List<Usuario> usuarios = new UsuarioDao().getLista();
			Collections.sort(usuarios);
			Usuario usuario = new Usuario();
			while (rs.next()) {
				UsuarioSensor usuarioSensor = new UsuarioSensor();
				usuarioSensor
						.setTemperaturaMinima(Util.getResultSetValueFloat(rs, "temperaturaMinima"));
				usuarioSensor
						.setTemperaturaMaxima(Util.getResultSetValueFloat(rs, "temperaturaMaxima"));
				usuarioSensor.setIntervaloDeAtualizacaoDeDados(
						Util.getResultSetValueInteger(rs, "intervaloDeAtualizacaoDeDados"));
				usuario.setLogin(rs.getString("usuario_login"));
				usuario = usuarios.get(Collections.binarySearch(usuarios, usuario));
				usuarioSensor.setSensor(
						usuario.getSensor(Long.parseLong(rs.getString("usuario_login"))));
				usuarioSensor.setUsuario(usuario);
				usarioSensores.add(usuarioSensor);
			}
			rs.close();
			return usarioSensores;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao.getMensagemDeExcecao(e.getMessage()));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public List<UsuarioSensor> buscaPorUsuario(String usuarioLogin) throws SQLException {
		String sql = "select * from usuarioSensor " + "where login_usuario=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuarioLogin);
			ResultSet rs = stmt.executeQuery();
			List<UsuarioSensor> registroDeTemperaturas = new ArrayList<>();
			Usuario usuario = null;
			boolean primeiro = true;
			while (rs.next()) {
				if (primeiro) {
					usuario = new UsuarioDao().busca(rs.getString("usuario_login"));
					primeiro = false;
				}
				UsuarioSensor usuarioSensor = new UsuarioSensor();
				usuarioSensor
						.setTemperaturaMinima(Util.getResultSetValueFloat(rs, "temperaturaMinima"));
				usuarioSensor
						.setTemperaturaMaxima(Util.getResultSetValueFloat(rs, "temperaturaMaxima"));
				usuarioSensor.setIntervaloDeAtualizacaoDeDados(
						Util.getResultSetValueInteger(rs, "intervaloDeAtualizacaoDeDados"));
				usuarioSensor.setSensor(
						usuario.getSensor(Long.parseLong(rs.getString("usuario_login"))));
				usuarioSensor.setUsuario(usuario);
				registroDeTemperaturas.add(usuarioSensor);
			}
			rs.close();
			return registroDeTemperaturas;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao.getMensagemDeExcecao(e.getMessage()));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public UsuarioSensor buscaPorSensor(String sensorCodigo) throws SQLException {
		String sql = "select * from usuarioSensor " + "where sensor_codigo=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			UsuarioSensor usuarioSensor = null;
			if (rs.next()) {
				usuarioSensor = new UsuarioSensor();
				usuarioSensor
						.setTemperaturaMinima(Util.getResultSetValueFloat(rs, "temperaturaMinima"));
				usuarioSensor
						.setTemperaturaMaxima(Util.getResultSetValueFloat(rs, "temperaturaMaxima"));
				usuarioSensor.setIntervaloDeAtualizacaoDeDados(
						Util.getResultSetValueInteger(rs, "intervaloDeAtualizacaoDeDados"));
				Sensor sensor = new SensorDao().busca(rs.getLong("sensor_codigo"));
				usuarioSensor.setSensor(sensor);
				usuarioSensor.setUsuario(sensor.getUsuario());
			}
			rs.close();
			return usuarioSensor;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao.getMensagemDeExcecao(e.getMessage()));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public void altera(UsuarioSensor usuarioSensor, String usuarioLogin, Long sensorCodigo)
			throws SQLException {
		String sql = "update usuarioSensor set " + "temperaturaMinima=?, temperaturaMaxima=?, "
				+ "intervaloDeAtualizacaoDeDados=? " + "where usuario_login=? and sensor_codigo";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			Util.setLimitesDeTemperaturaPreparedStatement(stmt, 1,
					usuarioSensor.getLimitesDeTemperatura());
			Util.setIntegerPreparedStatement(stmt, 3,
					usuarioSensor.getIntervaloDeAtualizacaoDeDados());
			stmt.setString(4, usuarioLogin);
			stmt.setLong(5, sensorCodigo);
			stmt.execute();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao.getMensagemDeExcecao(e.getMessage()));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	}

}
