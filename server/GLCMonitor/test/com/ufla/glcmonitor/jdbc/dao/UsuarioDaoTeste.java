package com.ufla.glcmonitor.jdbc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ufla.glcmonitor.jdbc.modelo.Endereco;
import com.ufla.glcmonitor.jdbc.modelo.Sexo;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class UsuarioDaoTeste {

	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;
	private UsuarioDao usuarioDao;

	@Before
	public void inicializa() throws SQLException {
		usuario1 = UtilTestes
				.getUsuario(23522342L, new Date(1000000000L), new Date(1000L), "teste1@email.com",
						UtilTestes.getEndereco("Centro", 37200000L, "Lavras", null, "MG", "Rua A",
								100),
						"teste1@email.com", "Teste1", null, "senha1", new ArrayList<>(),
						Sexo.getSexo('M'), 12423525L);
		usuario2 = UtilTestes.getUsuario(23423252344L, new Date(100353234000L), new Date(13242000L),
				"teste2@email.com",
				UtilTestes.getEndereco("Centro", 37200000L, "Lavras", null, "MG", "Rua B", 241),
				"teste2@email.com", "Teste2", 92378462L, "senha2", new ArrayList<>(),
				Sexo.getSexo('F'), 13112131L);
		usuario3 = UtilTestes.getUsuario(null, new Date(1000000000L), new Date(1000L),
				"teste3@email.com",
				UtilTestes.getEndereco("Centro", 37200000L, "Lavras", "Ap 301", "MG", "Rua C", 123),
				"teste3@email.com", "Teste3", 62728327L, "senha3", null, Sexo.getSexo('M'), null);
		usuarioDao = new UsuarioDao();
	}

	@After
	public void limpaBD() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		PreparedStatement stmt = connection.prepareStatement("delete from usuario");
		stmt.execute();
		stmt.close();
		connection.close();
	}

	@Test
	public void getListaSemUsuariosTeste() throws SQLException {
		List<Usuario> usuarios = usuarioDao.getLista();
		assertTrue(usuarios.isEmpty());
	}

	@Test
	public void buscaSemUsuariosTeste() throws SQLException {
		Usuario usuario = usuarioDao.busca("teste1@email.com");
		assertEquals(null, usuario);
	}

	@Test
	public void remocaoSemUsuariosTeste() throws SQLException {
		usuarioDao.remove("teste1@email.com");
	}

	@Test
	public void adicionaUsuarioAtrNullTeste() throws SQLException {
		usuarioDao.adiciona(UtilTestes.getUsuario(null, null, null, null, null, "login", null, null,
				null, new ArrayList<>(), null, null));
		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(1, usuarios.size());
	}

	@Test
	public void adicionaEBuscaUsuarioAtrNullTeste() throws SQLException {
		usuarioDao.adiciona(usuario1 = UtilTestes.getUsuario(null, null, null, null, null, "login",
				null, null, null, new ArrayList<>(), null, null));
		Usuario usuarioEsperado = usuarioDao.busca("login");
		assertEquals(usuarioEsperado, usuario1);
	}

	@Test
	public void adicionaUsuarioLoginNull() throws SQLException {
		usuario1.setLogin(null);
		try {
			usuarioDao.adiciona(usuario1);
		} catch (SQLException e) {
			assertEquals("Campo login é obrigatório!", e.getMessage());
		}
	}

	@Test
	public void adicionaUsuarioLoginDuplicado() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuario2.setLogin(usuario1.getLogin());
		try {
			usuarioDao.adiciona(usuario2);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) login duplicado(s)!", e.getMessage());
		}
	}

	@Test
	public void adicionaUsuarioCpfDuplicado() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuario2.setCpf(usuario1.getCpf());
		try {
			usuarioDao.adiciona(usuario2);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) cpf duplicado(s)!", e.getMessage());
		}
	}

	@Test
	public void adicionaUsuarioRgDuplicado() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuario2.setRg(usuario1.getRg());
		try {
			usuarioDao.adiciona(usuario2);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) rg duplicado(s)!", e.getMessage());
		}
	}

	@Test
	public void adicionaUsuarioEmailDuplicado() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuario2.setEmail(usuario1.getEmail());
		try {
			usuarioDao.adiciona(usuario2);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) email duplicado(s)!", e.getMessage());
		}
	}

	@Test
	public void adicionaUsuarioLoginRgEEmailDuplicados() throws SQLException {
		usuarioDao.adiciona(usuario3);
		try {
			usuarioDao.adiciona(usuario3);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) login, rg, " + "email duplicado(s)!",
					e.getMessage());
		}
	}

	@Test
	public void adicionaUsuarioLoginCpfEEmailDuplicados() throws SQLException {
		usuarioDao.adiciona(usuario1);
		try {
			usuarioDao.adiciona(usuario1);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) login, cpf, email duplicado(s)!",
					e.getMessage());
		}
	}

	@Test
	public void alteraUsuarioCpfDuplicado() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuario2.setCpf(usuario1.getCpf());
		try {
			usuarioDao.altera(usuario2);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) cpf duplicado(s)!", e.getMessage());
		}
	}

	@Test
	public void alteraUsuarioRgDuplicado() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuario2.setRg(usuario1.getRg());
		try {
			usuarioDao.altera(usuario2);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) rg duplicado(s)!", e.getMessage());
		}
	}

	@Test
	public void alteraUsuarioEmailDuplicado() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuario2.setEmail(usuario1.getEmail());
		try {
			usuarioDao.altera(usuario2);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) email duplicado(s)!", e.getMessage());
		}
	}

	@Test
	public void alteraUsuarioRgEEmailDuplicados() throws SQLException {
		usuarioDao.adiciona(usuario3);
		usuarioDao.adiciona(usuario2);
		usuario2.setRg(usuario3.getRg());
		usuario2.setEmail(usuario3.getEmail());
		try {
			usuarioDao.altera(usuario2);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) rg, " + "email duplicado(s)!",
					e.getMessage());
		}
	}

	@Test
	public void alteraUsuarioCpfEEmailDuplicados() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuario2.setCpf(usuario1.getCpf());
		usuario2.setEmail(usuario1.getEmail());
		try {
			usuarioDao.altera(usuario2);
		} catch (SQLException e) {
			assertEquals("Entrada duplicada! Campo(s) cpf, email duplicado(s)!", e.getMessage());
		}
	}

	@Test(expected = NullPointerException.class)
	public void adicionaUsuarioNullTeste() throws SQLException {
		usuarioDao.adiciona(null);
	}

	@Test
	public void adiciona1UsuarioTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(1, usuarios.size());
	}

	@Test
	public void adicionaEBusca1UsuarioTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		Usuario usuarioEsperado = usuarioDao.busca("teste1@email.com");
		assertEquals(usuarioEsperado, usuario1);
	}

	@Test
	public void adicionaERemove1UsuarioTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.remove("teste1@email.com");
		List<Usuario> usuarios = usuarioDao.getLista();
		assertTrue(usuarios.isEmpty());
	}

	@Test
	public void adiciona2UsuariosTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(2, usuarios.size());
	}

	@Test
	public void adicionaEBusca2UsuariosTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);

		Usuario usuarioEsperado = usuarioDao.busca("teste1@email.com");
		assertEquals(usuarioEsperado, usuario1);
		usuarioEsperado = usuarioDao.busca("teste2@email.com");
		assertEquals(usuarioEsperado, usuario2);
	}

	@Test
	public void adicionaERemove2UsuariosTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuarioDao.remove("teste1@email.com");
		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(1, usuarios.size());
		usuarioDao.remove("teste@email.com");
		usuarios = usuarioDao.getLista();
		assertEquals(1, usuarios.size());
		usuarioDao.remove("teste2@email.com");
		usuarios = usuarioDao.getLista();
		assertTrue(usuarios.isEmpty());
	}

	@Test
	public void adiciona3UsuariosTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuarioDao.adiciona(usuario3);

		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(3, usuarios.size());
	}

	@Test
	public void adicionaEBusca3UsuariosTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuarioDao.adiciona(usuario3);

		assertEquals(usuario1, usuarioDao.busca("teste1@email.com"));
		assertEquals(usuario2, usuarioDao.busca("teste2@email.com"));
		assertEquals(null, usuarioDao.busca("teste@email.com"));
		assertEquals(usuario3, usuarioDao.busca("teste3@email.com"));
	}

	@Test
	public void adicionaERemove3UsuariosTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuarioDao.adiciona(usuario3);
		usuarioDao.remove("teste@email.com");

		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(3, usuarios.size());
		usuarioDao.remove("teste1@email.com");
		usuarios = usuarioDao.getLista();
		assertEquals(2, usuarios.size());
		usuarioDao.remove("teste2@email.com");
		usuarios = usuarioDao.getLista();
		assertEquals(1, usuarios.size());
		usuarioDao.remove("teste3@email.com");
		usuarios = usuarioDao.getLista();
		assertTrue(usuarios.isEmpty());
	}

	@Test
	public void alteraTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuario1 = UtilTestes.getUsuario(213213L, null, null, "ola@email.com", null,
				usuario1.getLogin(), "ola", 21313L, "123456", new ArrayList<>(), null,
				12731241341L);
		usuarioDao.altera(usuario1);
		assertEquals(usuario1, usuarioDao.busca(usuario1.getLogin()));
	}

	@Test
	public void alteraNullTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuario1 = UtilTestes.getUsuario(null, null, null, null, null, usuario1.getLogin(), null,
				null, null, new ArrayList<>(), null, null);
		usuarioDao.altera(usuario1);
		assertEquals(usuario1, usuarioDao.busca(usuario1.getLogin()));
	}

	@Test
	public void alteraEnderecoTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		Endereco endereco = UtilTestes.getEndereco("bairro 1", 37145000L, "Alterosa", null, "MG",
				"Rua 32", 242);
		usuarioDao.alteraEndereco(endereco, usuario1.getLogin());
		assertEquals(endereco, usuarioDao.busca(usuario1.getLogin()).getEndereco());
	}

	@Test
	public void alteraEnderecoNullTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		usuarioDao.alteraEndereco(null, usuario1.getLogin());
		assertEquals(null, usuarioDao.busca(usuario1.getLogin()).getEndereco());
	}

	@Test
	public void alteraEnderecoAtrNullTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		Endereco endereco = UtilTestes.getEndereco(null, null, null, null, null, null, null);
		usuarioDao.alteraEndereco(endereco, usuario1.getLogin());
		assertEquals(endereco, usuarioDao.busca(usuario1.getLogin()).getEndereco());
	}

}
