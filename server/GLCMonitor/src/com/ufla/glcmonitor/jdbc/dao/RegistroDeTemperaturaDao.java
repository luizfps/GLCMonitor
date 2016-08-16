package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.RegistroDeTemperatura;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

/** A classe RegistroDeTemperaturaDao representa objetos de acesso à dados para 
 * manipular registros de temperatura.Fornece métodos de adição, busca, e 
 * recuperaçao de um último registro de temperatura.
 * @author glcmonitor
 *
 */
public class RegistroDeTemperaturaDao {
	
	private Connection connection;

	/**Inicializa um objeto RegistroDeTemperaturaDao estabelecendo uma 
	 * conexão com o SGBD.
	 */
	
	/*public RegistroDeTemperaturaDao() {
		this.connection = new ConnectionFactory().getConnection();
	}*/

	/**Adiciona um registro de temperatura de um sensor no banco de dados. 
	 * @param registroDeTemperatura registro de temperatura.
	 * @param sensorCodigo sensor relacionado ao registro de temperatura.
	 * @throws SQLException exceção relacionada a problemas em adicionar 
	 * registro de temperatura no BD.
	 */
	public void adiciona(RegistroDeTemperatura registroDeTemperatura,
			Long sensorCodigo) throws SQLException {
		String sql = "insert into registroDeTemperatura "
				+ "(temperatura, momento, sensor_codigo) "
				+ " values (?,?,?)";
		this.connection = new ConnectionFactory().getConnection();
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			Util.setFloatPreparedStatement(stmt, 1, registroDeTemperatura
					.getTemperatura());
			Util.setDatePreparedStatement(stmt, 2, registroDeTemperatura.getMomento());
			stmt.setLong(3, sensorCodigo);
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}finally {
			this.connection.close();
		}
	}
	
	/**Busca todos registros de temperatura de um determinado sensor no 
	 * banco de dados. 
	 * @param sensorCodigo sensor relacionado aos registros de temperatura.
	 * @return todos registros de temperatura do sensor.
	 * @throws SQLException exceção relacionada a problemas em buscar 
	 * registro de temperatura no BD.
	 */
	public List<RegistroDeTemperatura> busca(Long sensorCodigo) throws SQLException {
		this.connection = new ConnectionFactory().getConnection();
		try {
			List<RegistroDeTemperatura> registroDeTemperaturas = new ArrayList<>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from registroDeTemperatura "
							+ "where sensor_codigo=?");
			stmt.setLong(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// criando o objeto registroDeTemperatura
				RegistroDeTemperatura registroDeTemperatura = new RegistroDeTemperatura();
				registroDeTemperatura.setTemperatura(Util
						.getResultSetValueFloat(rs, "temperatura"));
				registroDeTemperatura.setMomento(rs.getDate("momento"));
				// adicionando o objeto à lista
				registroDeTemperaturas.add(registroDeTemperatura);
			}
			rs.close();
			stmt.close();
			return registroDeTemperaturas;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}finally {
			this.connection.close();
		}
	}
	
	/**Busca o último registro de temperatura de um determinado sensor no 
	 * banco de dados. 
	 * @param sensorCodigo sensor relacionado ao registro de temperatura.
	 * @return último registro de temperatura do sensor.
	 * @throws SQLException exceção relacionada a problemas em buscar 
	 * registro de temperatura no BD.
	 */
	public RegistroDeTemperatura getUltimoRegistroDeTemperatura(Long sensorCodigo) 
			throws SQLException {
		this.connection = new ConnectionFactory().getConnection();
		try {
			RegistroDeTemperatura registroDeTemperatura = null;
			PreparedStatement stmt = this.connection
					.prepareStatement("select from registroDeTemperatura "
							+ "where sensor_codigo=?");
			stmt.setLong(1, sensorCodigo);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				// criando o objeto registroDeTemperatura
				registroDeTemperatura = new RegistroDeTemperatura();
				registroDeTemperatura.setTemperatura(Util
						.getResultSetValueFloat(rs, "temperatura"));
				registroDeTemperatura.setMomento(rs.getDate("momento"));
			}
			rs.close();
			stmt.close();
			return registroDeTemperatura;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}finally {
			this.connection.close();
		}
	}
	
}
