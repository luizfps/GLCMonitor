package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.Sensor;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class SensorDao {

	private Connection connection;

	public SensorDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Sensor sensor, String usuarioLogin) {
		String sql = "insert into sensor "
				+ "(codigo, modelo, temperaturaMinima, "
				+ "temperaturaMaxima, erro, usuario_login)"
				+ " values (?,?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setLong(1, sensor.getCodigo());
			stmt.setString(2, sensor.getModelo());
			Util.setFloatPreparedStatement(stmt, 3, sensor
					.getTemperaturaMinima());
			Util.setFloatPreparedStatement(stmt, 4, sensor
					.getTemperaturaMaxima());
			Util.setFloatPreparedStatement(stmt, 5, sensor.getErro());
			stmt.setString(6, usuarioLogin);
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Sensor> getLista() {
		try {
			List<Sensor> sensores = new ArrayList<>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from sensor ");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// criando o objeto Usuario
				Sensor sensor = new Sensor();
				sensor.setCodigo(rs.getLong("codigo"));
				sensor.setModelo(rs.getString("modelo"));
				sensor.setTemperaturaMinima(Util
						.getResultSetValueFloat(rs, "temperaturaMinima"));
				sensor.setTemperaturaMaxima(Util
						.getResultSetValueFloat(rs, "temperaturaMaxima"));
				sensor.setErro(Util.getResultSetValueFloat(rs, "erro"));
				sensor.setUsuario(new UsuarioDao().busca(rs.getString("usuario_login")));
				sensor.setRegistrosDeTemperatura(new RegistroDeTemperaturaDao().busca(sensor.getCodigo()));
				// adicionando o objeto à lista
				sensores.add(sensor);
			}
			rs.close();
			stmt.close();
			return sensores;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Sensor busca(Long codigo) {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("select * from sensor "
							+ "where codigo=?");
			stmt.setLong(1, codigo);
			ResultSet rs = stmt.executeQuery();
			Sensor sensor = null;
			if(rs.next()) {
				sensor = new Sensor();
				sensor.setCodigo(rs.getLong("codigo"));
				sensor.setErro(Util.getResultSetValueFloat(rs, "erro"));
				sensor.setModelo(rs.getString("modelo"));
				sensor.setTemperaturaMaxima(Util
						.getResultSetValueFloat(rs, "temperaturaMaxima"));
				sensor.setTemperaturaMinima(Util
						.getResultSetValueFloat(rs, "temperaturaMinima"));
				sensor.setUsuario(new UsuarioDao().busca(rs
						.getString("usuario_login")));
				sensor.setRegistrosDeTemperatura(new RegistroDeTemperaturaDao()
						.busca(codigo));
			}
			rs.close();
			stmt.close();
			return sensor;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void altera(Sensor sensor) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("update sensor set erro=?,"
							+ " temperaturaMinima=?, temperaturaMaxima=?, "
							+ "where codigo=?");
			Util.setFloatPreparedStatement(stmt, 1, sensor.getErro());
			Util.setFloatPreparedStatement(stmt, 2, sensor
					.getTemperaturaMinima());
			Util.setFloatPreparedStatement(stmt, 3, sensor
					.getTemperaturaMaxima());
			stmt.setLong(4, sensor.getCodigo());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remove(Long codigo) {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("delete from sensor "
							+ "where codigo=?");
			stmt.setLong(1, codigo);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Sensor> buscaPorUsuario(String loginUsuario) {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("select * from sensor "
							+ "where usuario_login=?");
			stmt.setString(1, loginUsuario);
			ResultSet rs = stmt.executeQuery();
			List<Sensor> sensores = new ArrayList<>();
			while(rs.next()) {
				Sensor sensor = new Sensor();
				sensor.setCodigo(rs.getLong("codigo"));
				sensor.setErro(Util.getResultSetValueFloat(rs, "erro"));
				sensor.setModelo(rs.getString("modelo"));
				sensor.setTemperaturaMaxima(Util
						.getResultSetValueFloat(rs, "temperaturaMaxima"));
				sensor.setTemperaturaMinima(Util
						.getResultSetValueFloat(rs, "temperaturaMinima"));
				sensor.setUsuario(new UsuarioDao().busca(rs
						.getString("usuario_login")));
				sensor.setRegistrosDeTemperatura(new RegistroDeTemperaturaDao()
						.busca(rs.getLong("codigo")));
				sensores.add(sensor);
			}
			rs.close();
			stmt.close();
			return sensores;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
