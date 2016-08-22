package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.RegistroDeTemperatura;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

/**
 * A classe RegistroDeTemperaturaDao representa objetos de acesso à dados para
 * manipular registros de temperatura.Fornece métodos de adição, busca, e
 * recuperaçao de um último registro de temperatura.
 * 
 * @author glcmonitor
 *
 */


public class RegistroDeTemperaturaDao {
	public List<RegistroDeTemperatura> buscaIntervalo(Long sensorCodigo,String dataInicial,String dataFinal) throws SQLException {
		String sql = "select * from registroDeTemperatura " + "where sensor_codigo=?"+" and "+"momento" +" between "+"\'"+dataInicial+"\'"+" and "+"\'"+dataFinal+"\'";
		
		System.out.println(sql);
		Connection connection = null;
		PreparedStatement stmt = null;
		List<RegistroDeTemperatura> registroDeTemperaturas = new ArrayList<>();
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				RegistroDeTemperatura registroDeTemperatura = new RegistroDeTemperatura();
				registroDeTemperatura
						.setTemperatura(Util.getResultSetValueFloat(rs, "temperatura"));
				registroDeTemperatura.setMomento((rs.getTimestamp("momento")));
				registroDeTemperaturas.add(registroDeTemperatura);
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
	/**
	 * Adiciona um registro de temperatura de um sensor no banco de dados.
	 * 
	 * @param registroDeTemperatura
	 *            registro de temperatura.
	 * @param sensorCodigo
	 *            sensor relacionado ao registro de temperatura.
	 * @throws SQLException
	 *             exceção relacionada a problemas em adicionar registro de
	 *             temperatura no BD.
	 */
	public void adiciona(RegistroDeTemperatura registroDeTemperatura, Long sensorCodigo)
			throws SQLException {
		String sql = "insert into registroDeTemperatura " + "(temperatura, momento, sensor_codigo) "
				+ " values (?,?,?)";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			Util.setFloatPreparedStatement(stmt, 1, registroDeTemperatura.getTemperatura());
			Util.setDatePreparedStatement(stmt, 2, registroDeTemperatura.getMomento());
			stmt.setLong(3, sensorCodigo);
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

	/**
	 * Busca todos registros de temperatura de um determinado sensor no banco de
	 * dados.
	 * 
	 * @param sensorCodigo
	 *            sensor relacionado aos registros de temperatura.
	 * @return todos registros de temperatura do sensor.
	 * @throws SQLException
	 *             exceção relacionada a problemas em buscar registro de
	 *             temperatura no BD.
	 */
	public List<RegistroDeTemperatura> busca(Long sensorCodigo) throws SQLException {
		String sql = "select * from registroDeTemperatura " + "where sensor_codigo=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		List<RegistroDeTemperatura> registroDeTemperaturas = new ArrayList<>();
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				RegistroDeTemperatura registroDeTemperatura = new RegistroDeTemperatura();
				registroDeTemperatura
						.setTemperatura(Util.getResultSetValueFloat(rs, "temperatura"));
				registroDeTemperatura.setMomento((rs.getTimestamp("momento")));
				registroDeTemperaturas.add(registroDeTemperatura);
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

	/**
	 * Busca o último registro de temperatura de um determinado sensor no banco
	 * de dados.
	 * 
	 * @param sensorCodigo
	 *            sensor relacionado ao registro de temperatura.
	 * @return último registro de temperatura do sensor.
	 * @throws SQLException
	 *             exceção relacionada a problemas em buscar registro de
	 *             temperatura no BD.
	 */
	public RegistroDeTemperatura getUltimoRegistroDeTemperatura(Long sensorCodigo)
			throws SQLException {
		String sql = "select from registroDeTemperatura " + "where sensor_codigo=?";
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = new ConnectionFactory().getConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			RegistroDeTemperatura registroDeTemperatura = null;
			if (rs.next()) {
				registroDeTemperatura = new RegistroDeTemperatura();
				registroDeTemperatura
						.setTemperatura(Util.getResultSetValueFloat(rs, "temperatura"));
				registroDeTemperatura.setMomento(rs.getTimestamp("momento"));
			}
			rs.close();
			return registroDeTemperatura;
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
