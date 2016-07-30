package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.Usuario;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class UsuarioDao {

	private Connection connection;

	public UsuarioDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void adiciona(Usuario usuario) {
		String sql = "insert into usuario "
				+ "(login,senha,nome,telefone,email,rg,cpf,"
				+ "sexo,dataDeCadastramento,dataDeNascimento)"
				+ " values (?,?,?,?,?,?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());
			stmt.setString(3, usuario.getNome());
			Util.setLongPreparedStatement(stmt, 4, usuario.getTelefone());
			stmt.setString(5, usuario.getEmail());
			Util.setLongPreparedStatement(stmt, 6, usuario.getRg());
			Util.setLongPreparedStatement(stmt, 7, usuario.getCpf());
			stmt.setString(8, usuario.getSexoValueStr());
			stmt.setDate(9, new Date(usuario.getDataDeCadastramento().getTime()));
			stmt.setDate(10, new Date(usuario.getDataDeNascimento().getTime()));
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if (usuario.getEndereco() != null) {
			EnderecoDao enderecoDao = new EnderecoDao();
			enderecoDao.adiciona(usuario.getEndereco(), usuario.getLogin());
		}
	}

	public List<Usuario> getLista() {
		try {
			List<Usuario> usuarios = new ArrayList<>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuario ");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// criando o objeto Usuario
				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setNome(rs.getString("nome"));
				usuario.setTelefone(rs.getLong("telefone"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("telefone"));
				usuario.setRg(rs.getLong("rg"));
				usuario.setCpf(rs.getLong("cpf"));
				usuario.setSexo(rs.getString("sexo").charAt(0));
				usuario.setDataDeCadastramento(rs.getDate("dataDeCadastramento"));
				usuario.setDataDeNascimento(rs.getDate("dataDeNascimento"));
				usuario.setEndereco(new EnderecoDao().busca(usuario.getLogin()));
				usuario.setSensores(new SensorDao().buscaPorUsuario(usuario.getLogin()));
				// adicionando o objeto à lista
				usuarios.add(usuario);
			}
			rs.close();
			stmt.close();
			return usuarios;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Usuario busca(String login) {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("select * from usuario "
							+ "where login=?");
			stmt.setString(1, login);
			ResultSet rs = stmt.executeQuery();
			Usuario usuario = null;
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setCpf(rs.getLong("cpf"));
				usuario.setDataDeCadastramento(rs.getDate("dataDeCadastramento"));
				usuario.setDataDeNascimento(rs.getDate("dataDeNascimento"));
				usuario.setEmail(rs.getString("email"));
				usuario.setEndereco(new EnderecoDao().busca(login));
				usuario.setLogin(rs.getString("login"));
				usuario.setNome(rs.getString("nome"));
				usuario.setRg(rs.getLong("rg"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setSensores(new SensorDao().buscaPorUsuario(login));
				usuario.setSexo(rs.getString("sexo").charAt(0));
				usuario.setTelefone(rs.getLong("telefone"));
			}
			rs.close();
			stmt.close();
			return usuario;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void altera(Usuario usuario) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("update usuario set cpf=?, dataDeCadastramento=?, "
							+ "dataDeNascimento=?, email=?, nome=?, rg=?, senha=?, "
							+ "sexo=?, telefone=? where login=?");
			Util.setLongPreparedStatement(stmt, 1, usuario.getCpf());
			stmt.setDate(2, new Date(usuario.getDataDeCadastramento().getTime()));
			stmt.setDate(3, new Date(usuario.getDataDeNascimento().getTime()));
			stmt.setString(4, usuario.getEmail());
			stmt.setString(5, usuario.getNome());
			Util.setLongPreparedStatement(stmt, 6, usuario.getRg());
			stmt.setString(7, usuario.getSenha());
			stmt.setString(8, usuario.getSexoValueStr());
			Util.setLongPreparedStatement(stmt, 9, usuario.getTelefone());
			stmt.setString(10, usuario.getLogin());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void remove(String login) {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("delete from usuario "
							+ "where login=?");
			stmt.setString(1, login);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
