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
	
	private Connection connection;

	/**Inicializa um objeto UsuarioSensorDao estabelecendo uma conexão com o SGBD.
	 */
	public UsuarioSensorDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(UsuarioSensor usuarioSensor, String usuarioLogin,
			Long sensorCodigo) throws SQLException {
		String sql = "insert into sensor "
				+ "(temperaturaMinima, temperaturaMaxima, "
				+ "intervaloDeAtualizacaoDeDados, sensor_codigo, usuario_login)"
				+ " values (?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			Util.setLimitesDeTemperaturaPreparedStatement(stmt, 1, 
					usuarioSensor.getLimitesDeTemperatura());
			Util.setIntegerPreparedStatement(stmt, 3, usuarioSensor
					.getIntervaloDeAtualizacaoDeDados());
			stmt.setLong(4, sensorCodigo);
			stmt.setString(5, usuarioLogin);
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	public List<UsuarioSensor> getLista() throws SQLException {
		try {
			List<UsuarioSensor> usarioSensores = new ArrayList<>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarioSensor ");
			ResultSet rs = stmt.executeQuery();
			List<Usuario> usuarios = new UsuarioDao().getLista();
			Collections.sort(usuarios);
			Usuario usuario = new Usuario();
			while (rs.next()) {
				// criando o objeto usarioSensor
				UsuarioSensor usuarioSensor = new UsuarioSensor();
				usuarioSensor.setTemperaturaMinima(Util
						.getResultSetValueFloat(rs, "temperaturaMinima"));
				usuarioSensor.setTemperaturaMaxima(Util
						.getResultSetValueFloat(rs, "temperaturaMaxima"));
				usuarioSensor.setIntervaloDeAtualizacaoDeDados(Util
						.getResultSetValueInteger(rs, 
								"intervaloDeAtualizacaoDeDados"));
				usuario.setLogin(rs.getString("usuario_login"));
				usuario = usuarios.get(Collections.binarySearch(usuarios, usuario));
				usuarioSensor.setSensor(usuario.getSensor(Long.parseLong(rs
						.getString("usuario_login"))));
				usuarioSensor.setUsuario(usuario);
				// adicionando o objeto à lista
				usarioSensores.add(usuarioSensor);
			}
			rs.close();
			stmt.close();
			return usarioSensores;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	public List<UsuarioSensor> buscaPorUsuario(String usuarioLogin) throws SQLException {
		try {
			List<UsuarioSensor> registroDeTemperaturas = new ArrayList<>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarioSensor " +
							"where login_usuario=?");
			stmt.setString(1, usuarioLogin);
			ResultSet rs = stmt.executeQuery();
			Usuario usuario = null;
			boolean primeiro = true;
			while (rs.next()) {
				if(primeiro) {
					usuario = new UsuarioDao().busca(rs.getString("usuario_login"));
					primeiro = false;
				}
				// criando o objeto registroDeTemperatura
				UsuarioSensor usuarioSensor = new UsuarioSensor();
				usuarioSensor.setTemperaturaMinima(Util
						.getResultSetValueFloat(rs, "temperaturaMinima"));
				usuarioSensor.setTemperaturaMaxima(Util
						.getResultSetValueFloat(rs, "temperaturaMaxima"));
				usuarioSensor.setIntervaloDeAtualizacaoDeDados(Util
						.getResultSetValueInteger(rs, 
								"intervaloDeAtualizacaoDeDados"));
				usuarioSensor.setSensor(usuario.getSensor(Long.parseLong(rs
						.getString("usuario_login"))));
				usuarioSensor.setUsuario(usuario);
				// adicionando o objeto à lista
				registroDeTemperaturas.add(usuarioSensor);
			}
			rs.close();
			stmt.close();
			return registroDeTemperaturas;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	public UsuarioSensor buscaPorSensor(String sensorCodigo) throws SQLException {
		try {
			UsuarioSensor usuarioSensor = null;
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarioSensor " +
							"where sensor_codigo=?");
			stmt.setString(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				usuarioSensor = new UsuarioSensor();
				usuarioSensor.setTemperaturaMinima(Util
						.getResultSetValueFloat(rs, "temperaturaMinima"));
				usuarioSensor.setTemperaturaMaxima(Util
						.getResultSetValueFloat(rs, "temperaturaMaxima"));
				usuarioSensor.setIntervaloDeAtualizacaoDeDados(Util
						.getResultSetValueInteger(rs, 
								"intervaloDeAtualizacaoDeDados"));
				Sensor sensor = new SensorDao().busca(rs
						.getLong("sensor_codigo"));
				usuarioSensor.setSensor(sensor);
				usuarioSensor.setUsuario(sensor.getUsuario());
			}
			rs.close();
			stmt.close();
			return usuarioSensor;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	public void altera(UsuarioSensor usuarioSensor, String usuarioLogin,
			Long sensorCodigo) throws SQLException {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("update usuarioSensor set "
							+ "temperaturaMinima=?, temperaturaMaxima=?, "
							+ "intervaloDeAtualizacaoDeDados=? "
							+ "where usuario_login=? and sensor_codigo");
			Util.setLimitesDeTemperaturaPreparedStatement(stmt, 1, 
					usuarioSensor.getLimitesDeTemperatura());
			Util.setIntegerPreparedStatement(stmt, 3, usuarioSensor
					.getIntervaloDeAtualizacaoDeDados());
			stmt.setString(4, usuarioLogin);
			stmt.setLong(5, sensorCodigo);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
}
