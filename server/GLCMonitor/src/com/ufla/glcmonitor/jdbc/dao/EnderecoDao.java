package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ufla.glcmonitor.jdbc.modelo.Endereco;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class EnderecoDao {
	
	private Connection connection;
	
	protected EnderecoDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	protected void adiciona(Endereco endereco, String loginUsuario) {
		String sql = "insert into endereco "
				+ "(logradouro,numero,complemento,bairro,"
				+ "cidade,estado,cep,usuario_login)"
				+ " values (?,?,?,?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setString(1, endereco.getLogradouro());
			Util.setIntegerPreparedStatement(stmt, 2, endereco.getNumero());
			stmt.setString(3, endereco.getComplemento());
			stmt.setString(4, endereco.getBairro());
			stmt.setString(5, endereco.getCidade());
			stmt.setString(6, endereco.getEstado());
			Util.setLongPreparedStatement(stmt, 7, endereco.getCep());
			stmt.setString(8, loginUsuario);
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Endereco busca(String usuarioLogin) {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("select * from endereco "
							+ "where usuario_login=?");
			stmt.setString(1, usuarioLogin);
			ResultSet rs = stmt.executeQuery();
			Endereco endereco = null;
			if(rs.next()) {
				endereco = new Endereco();
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCep(Util.getResultSetValueLong(rs, "cep"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setComplemento(rs.getString("complemento"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setNumero(Util.
						getResultSetValueInteger(rs, "numero"));
			}
			rs.close();
			stmt.close();
			return endereco;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void altera(Endereco endereco, String loginUsuario) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("update endereco set bairro=?, cep=?, cidade=?," +
             "complemento=?, estado=?, logradouro=?, numero=? where usuario_login=?");
			stmt.setString(1, endereco.getBairro());
			Util.setLongPreparedStatement(stmt,2, endereco.getCep());
			stmt.setString(3, endereco.getCidade());
			stmt.setString(4, endereco.getComplemento());
			stmt.setString(5, endereco.getEstado());
			stmt.setString(6, endereco.getLogradouro());
			Util.setIntegerPreparedStatement(stmt, 7, endereco.getNumero());
			stmt.setString(8, loginUsuario);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
