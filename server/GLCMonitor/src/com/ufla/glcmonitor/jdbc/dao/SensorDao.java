package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.Sensor;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class SensorDao {

	public void adiciona(Sensor sensor, String usuarioLogin) throws SQLException {
		String sql = "insert into sensor " + "(codigo, modelo, temperaturaMinima, "
				+ "temperaturaMaxima, erro, usuario_login)" + " values (?,?,?,?,?,?)";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, sensor.getCodigo());
			stmt.setString(2, sensor.getModelo());
			Util.setLimitesDeTemperaturaPreparedStatement(stmt, 3, sensor.getFaixaDeOperacao());
			Util.setFloatPreparedStatement(stmt, 5, sensor.getErro());
			stmt.setString(6, usuarioLogin);
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

	public List<Sensor> getLista() throws SQLException {
		String sql = "select * from sensor";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<Sensor> sensores = new ArrayList<>();
			while (rs.next()) {
				Sensor sensor = new Sensor();
				sensor.setCodigo(rs.getLong("codigo"));
				sensor.setModelo(rs.getString("modelo"));
				sensor.setTemperaturaMinima(Util.getResultSetValueFloat(rs, "temperaturaMinima"));
				sensor.setTemperaturaMaxima(Util.getResultSetValueFloat(rs, "temperaturaMaxima"));
				sensor.setErro(Util.getResultSetValueFloat(rs, "erro"));
				sensor.setUsuario(new UsuarioDao().busca(rs.getString("usuario_login")));
				sensor.setRegistrosDeTemperatura(
						new RegistroDeTemperaturaDao().busca(sensor.getCodigo()));
				sensores.add(sensor);
			}
			rs.close();
			return sensores;
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

	public Sensor busca(Long codigo) throws SQLException {
		String sql = "select * from sensor " + "where codigo=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, codigo);
			ResultSet rs = stmt.executeQuery();
			Sensor sensor = null;
			if (rs.next()) {
				sensor = new Sensor();
				sensor.setCodigo(rs.getLong("codigo"));
				sensor.setErro(Util.getResultSetValueFloat(rs, "erro"));
				sensor.setModelo(rs.getString("modelo"));
				sensor.setTemperaturaMaxima(Util.getResultSetValueFloat(rs, "temperaturaMaxima"));
				sensor.setTemperaturaMinima(Util.getResultSetValueFloat(rs, "temperaturaMinima"));
				sensor.setUsuario(new UsuarioDao().busca(rs.getString("usuario_login")));
				sensor.setRegistrosDeTemperatura(new RegistroDeTemperaturaDao().busca(codigo));
			}
			rs.close();
			return sensor;
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

	public void altera(Sensor sensor) throws SQLException {
		String sql = "update sensor set erro=?," + " temperaturaMinima=?, temperaturaMaxima=?, "
				+ "where codigo=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			Util.setFloatPreparedStatement(stmt, 1, sensor.getErro());
			Util.setLimitesDeTemperaturaPreparedStatement(stmt, 2, sensor.getFaixaDeOperacao());
			stmt.setLong(4, sensor.getCodigo());
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

	public void remove(Long codigo) throws SQLException {
		String sql = "delete from sensor " + "where codigo=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, codigo);
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

	public List<Sensor> buscaPorUsuario(String loginUsuario) throws SQLException {
		String sql = "select * from sensor " + "where usuario_login=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, loginUsuario);
			ResultSet rs = stmt.executeQuery();
			List<Sensor> sensores = new ArrayList<>();
			while (rs.next()) {
				Sensor sensor = new Sensor();
				sensor.setCodigo(rs.getLong("codigo"));
				sensor.setErro(Util.getResultSetValueFloat(rs, "erro"));
				sensor.setModelo(rs.getString("modelo"));
				sensor.setTemperaturaMaxima(Util.getResultSetValueFloat(rs, "temperaturaMaxima"));
				sensor.setTemperaturaMinima(Util.getResultSetValueFloat(rs, "temperaturaMinima"));
//				sensor.setUsuario(new UsuarioDao().busca(rs.getString("usuario_login")));
//				sensor.setRegistrosDeTemperatura(
				//						new RegistroDeTemperaturaDao().busca(rs.getLong("codigo")));
				sensores.add(sensor);
			}
			rs.close();
			return sensores;
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

	public List<Sensor> buscaPorUsuario(Usuario usuario) throws SQLException {
		String sql = "select * from sensor " + "where usuario_login=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getLogin());
			ResultSet rs = stmt.executeQuery();
			List<Sensor> sensores = new ArrayList<>();
			while (rs.next()) {
				Sensor sensor = new Sensor();
				sensor.setCodigo(rs.getLong("codigo"));
				sensor.setErro(Util.getResultSetValueFloat(rs, "erro"));
				sensor.setModelo(rs.getString("modelo"));
				sensor.setTemperaturaMaxima(Util.getResultSetValueFloat(rs, "temperaturaMaxima"));
				sensor.setTemperaturaMinima(Util.getResultSetValueFloat(rs, "temperaturaMinima"));
//				sensor.setUsuario(usuario);
//				sensor.setRegistrosDeTemperatura(
//						new RegistroDeTemperaturaDao().busca(rs.getLong("codigo")));
				sensores.add(sensor);
			}
			rs.close();
			return sensores;
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
