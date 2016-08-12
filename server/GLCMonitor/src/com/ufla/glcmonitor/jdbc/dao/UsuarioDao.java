package com.ufla.glcmonitor.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.Endereco;
import com.ufla.glcmonitor.jdbc.modelo.Sexo;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class UsuarioDao {

	private Connection connection;

	public UsuarioDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	private String getMensagemEntradaDuplicadaAdiciona(Usuario usuario) 
			throws SQLException {
		StringBuilder msg = new StringBuilder("Entrada duplicada! Campo(s) ");
		if(busca(usuario.getLogin(), "login") != null) {
			msg.append("login, ");
		}
		if(usuario.getCpf() != null &&
				busca(usuario.getCpf().toString(), "cpf") != null) {
			msg.append("cpf, ");
		}
		if(usuario.getRg() != null && 
				busca(usuario.getRg().toString(), "rg") != null) {
			msg.append("rg, ");
		}
		if(busca(usuario.getEmail(), "email") != null) {
			msg.append("email, ");
		}
		msg.delete(msg.length()-2, msg.length()).append(" duplicado(s)!");
		return msg.toString();
	}
	
	private String getMensagemEntradaDuplicadaAltera(Usuario usuario) 
			throws SQLException {
		StringBuilder msg = new StringBuilder("Entrada duplicada! Campo(s) ");
		if(usuario.getCpf() != null) {
			Usuario u = busca(usuario.getCpf().toString(), "cpf");
			if(u != null && !u.getLogin().equals(usuario.getLogin())) {
				msg.append("cpf, ");
			}
		}
		if(usuario.getRg() != null) {
			Usuario u = busca(usuario.getRg().toString(), "rg");
			if(u != null && !u.getLogin().equals(usuario.getLogin())) {
				msg.append("rg, ");
			}
		}
		Usuario u = busca(usuario.getEmail(), "email");
		if(u != null && !u.getLogin().equals(usuario.getLogin())) {
			msg.append("email, ");
		}
		msg.delete(msg.length()-2, msg.length()).append(" duplicado(s)!");
		return msg.toString();
	}

	public void adiciona(Usuario usuario) throws SQLException {
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
			Util.setDatePreparedStatement(stmt, 9, usuario.getDataDeCadastramento());
			Util.setDatePreparedStatement(stmt, 10, usuario.getDataDeNascimento());
			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if(e.getMessage().contains("Duplicate entry")) {
				throw new SQLException(getMensagemEntradaDuplicadaAdiciona(usuario));
			}
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
		if (usuario.getEndereco() != null) {
			EnderecoDao enderecoDao = new EnderecoDao();
			enderecoDao.adiciona(usuario.getEndereco(), usuario.getLogin());
		}
	}

	public List<Usuario> getLista() throws SQLException {
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
				usuario.setTelefone(Util.getResultSetValueLong(rs, "telefone"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setRg(Util.getResultSetValueLong(rs, "rg"));
				usuario.setCpf(Util.getResultSetValueLong(rs, "cpf"));	
				String sexoStr = rs.getString("sexo");
				if(sexoStr == null) {
					Sexo sexo = null;
					usuario.setSexo(sexo);
				} else {
					usuario.setSexo(rs.getString("sexo").charAt(0));
				}
				usuario.setDataDeCadastramento(rs.getDate("dataDeCadastramento"));
				usuario.setDataDeNascimento(rs.getDate("dataDeNascimento"));
				usuario.setEndereco(new EnderecoDao().busca(usuario.getLogin()));
				usuario.setSensores(new SensorDao()
						.buscaPorUsuario(usuario.getLogin()));
				// adicionando o objeto à lista
				usuarios.add(usuario);
			}
			rs.close();
			stmt.close();
			return usuarios;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	@SuppressWarnings("unused")
	private List<Usuario> buscaLista(String filtro, String campo) 
			throws SQLException {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("select * from usuario "
							+ "where "+campo+"=?");
			stmt.setString(1, filtro);
			ResultSet rs = stmt.executeQuery();
			List<Usuario> usuarios = new ArrayList<>();
			while(rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setCpf(Util.getResultSetValueLong(rs, "cpf"));
				usuario.setDataDeCadastramento(rs.getDate("dataDeCadastramento"));
				usuario.setDataDeNascimento(rs.getDate("dataDeNascimento"));
				usuario.setEmail(rs.getString("email"));
				usuario.setEndereco(new EnderecoDao().busca(rs.getString("login")));
				usuario.setLogin(rs.getString("login"));
				usuario.setNome(rs.getString("nome"));
				usuario.setRg(Util.getResultSetValueLong(rs, "rg"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setSensores(new SensorDao().buscaPorUsuario(rs.getString("login")));
				String sexoStr = rs.getString("sexo");
				if(sexoStr == null) {
					Sexo sexo = null;
					usuario.setSexo(sexo);
				} else {
					usuario.setSexo(rs.getString("sexo").charAt(0));
				}
				usuario.setTelefone(Util.getResultSetValueLong(rs, "telefone"));
				usuarios.add(usuario);
			}
			rs.close();
			stmt.close();
			return usuarios;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	private Usuario busca(String filtro, String campo) throws SQLException {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("select * from usuario "
							+ "where "+campo+"=?");
			stmt.setString(1, filtro);
			ResultSet rs = stmt.executeQuery();
			Usuario usuario = null;
			if(rs.next()) {
				usuario = new Usuario();
				usuario.setCpf(Util.getResultSetValueLong(rs, "cpf"));
				usuario.setDataDeCadastramento(rs.getDate("dataDeCadastramento"));
				usuario.setDataDeNascimento(rs.getDate("dataDeNascimento"));
				usuario.setEmail(rs.getString("email"));
				usuario.setEndereco(new EnderecoDao().busca(rs.getString("login")));
				usuario.setLogin(rs.getString("login"));
				usuario.setNome(rs.getString("nome"));
				usuario.setRg(Util.getResultSetValueLong(rs, "rg"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setSensores(new SensorDao().buscaPorUsuario(rs.getString("login")));
				String sexoStr = rs.getString("sexo");
				if(sexoStr == null) {
					Sexo sexo = null;
					usuario.setSexo(sexo);
				} else {
					usuario.setSexo(rs.getString("sexo").charAt(0));
				}
				usuario.setTelefone(Util.getResultSetValueLong(rs, "telefone"));
			}
			rs.close();
			stmt.close();
			return usuario;
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	public Usuario busca(String login) throws SQLException {
		return busca(login, "login");
	}
	
	public void altera(Usuario usuario) throws SQLException {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("update usuario set cpf=?, dataDeCadastramento=?, "
							+ "dataDeNascimento=?, email=?, nome=?, rg=?, senha=?, "
							+ "sexo=?, telefone=? where login=?");
			Util.setLongPreparedStatement(stmt, 1, usuario.getCpf());
			Util.setDatePreparedStatement(stmt, 2, usuario.getDataDeCadastramento());
			Util.setDatePreparedStatement(stmt, 3, usuario.getDataDeNascimento());
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
			if(e.getMessage().contains("Duplicate entry")) {
				throw new SQLException(getMensagemEntradaDuplicadaAltera(usuario));
			}
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
	public void alteraEndereco(Endereco endereco, String loginUsuario) 
			throws SQLException {
		if(endereco == null) {
			endereco = new Endereco();
			endereco.setBairro(null);
			endereco.setCep(null);
			endereco.setCidade(null);
			endereco.setComplemento(null);
			endereco.setEstado(null);
			endereco.setLogradouro(null);
			endereco.setNumero(null);

		} 
		new EnderecoDao().altera(endereco, loginUsuario);
	}
	
	public void remove(String login) throws SQLException {
		try {
			PreparedStatement stmt = this.connection.
					prepareStatement("delete from usuario "
							+ "where login=?");
			stmt.setString(1, login);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new SQLException(MensagensDeExcecao
					.getMensagemDeExcecao(e.getMessage()));
		}
	}
	
}
