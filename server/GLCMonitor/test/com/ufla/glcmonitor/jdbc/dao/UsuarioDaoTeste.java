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
import com.ufla.glcmonitor.jdbc.modelo.Sensor;
import com.ufla.glcmonitor.jdbc.modelo.Sexo;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class UsuarioDaoTeste {
	
	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;
	private UsuarioDao usuarioDao;
	
	private Endereco getEndereco(String bairro, Long cep, String cidade, 
			String complemento, String estado, String logradouro, 
			Integer numero) {
		Endereco endereco = new Endereco();
		endereco.setBairro(bairro);
		endereco.setCep(cep);
		endereco.setCidade(cidade);
		endereco.setComplemento(complemento);
		endereco.setEstado(estado);
		endereco.setLogradouro(logradouro);
		endereco.setNumero(numero);
		return endereco;
	}
	
	private Usuario getUsuario(Long cpf, Date dataDeCadastramento, 
			Date dataDeNascimento, String email, Endereco endereco, 
			String login, String nome, Long rg, String senha, 
			List<Sensor> sensores, Sexo sexo, Long telefone) {
		Usuario usuario = new Usuario();
		usuario.setCpf(cpf);
		usuario.setDataDeCadastramento(dataDeCadastramento);
		usuario.setDataDeNascimento(dataDeNascimento);
		usuario.setEmail(email);
		usuario.setEndereco(endereco);
		usuario.setLogin(login);
		usuario.setNome(nome);
		usuario.setRg(rg);
		usuario.setSenha(senha);
		usuario.setSensores(sensores);
		usuario.setSexo(sexo);
		usuario.setTelefone(telefone);
		return usuario;
	}
	
	@Before
	public void inicializa() throws SQLException {
		usuario1 = getUsuario(23522342L, new Date(1000000000L), new Date(100L), 
				"teste1@email.com", getEndereco("Centro", 37200000L, 
						"Lavras", null, "MG", "Rua A", 100), 
				"teste1@email.com", "Teste1", null, "senha1", new ArrayList<>(), 
				Sexo.getSexo('M'), 12423525L);
		usuario2 = getUsuario(23423252344L, new Date(100353234000L), new Date(13242L), 
				"teste2@email.com", getEndereco("Centro", 37200000L, 
						"Lavras", null, "MG", "Rua B", 241), 
				"teste2@email.com", "Teste2", 92378462L, "senha2", new ArrayList<>(), 
				Sexo.getSexo('F'), 13112131L);
		usuario3 = getUsuario(null, new Date(1000000000L), new Date(100L), 
				"teste3@email.com", getEndereco("Centro", 37200000L, 
						"Lavras", "Ap 301", "MG", "Rua C", 123), 
				"teste3@email.com", "Teste3", 62728327L, "senha3", null, 
				Sexo.getSexo('M'), null);
		usuarioDao = new UsuarioDao();
	}
	
	@After
	public void limpaBD() throws SQLException {
    	Connection connection = new ConnectionFactory().getConnection();
    	PreparedStatement stmt = connection.
    			prepareStatement("delete from usuario");
    	stmt.execute();
    	stmt.close();
	}


	@Test
	public void getListaSemUsuariosTeste() {
		List<Usuario> usuarios = usuarioDao.getLista();
		assertTrue(usuarios.isEmpty());
	}
	
	@Test
	public void buscaSemUsuariosTeste() {
		Usuario usuario = usuarioDao.busca("teste1@email.com");
		assertEquals(null, usuario);
	}
	
	@Test
	public void remocaoSemUsuariosTeste() {
		usuarioDao.remove("teste1@email.com");
	}
	
	@Test
	public void adicionaUsuarioAtrNullTeste() {
		usuarioDao.adiciona(getUsuario(null, null, null, null, null, "login", 
				null, null, null, null, null, null));
		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(1, usuarios.size());
	}
	
	@Test
	public void adicionaEBuscaUsuarioAtrNullTeste() {
		usuarioDao.adiciona(usuario1 = getUsuario(null, null, null, null, 
				null, "login", null, null, null, null, null, null));
		Usuario usuarioEsperado = usuarioDao.busca("login");
		assertEquals(usuarioEsperado, usuario1);
	}
	
	@Test
	public void adicionaUsuarioNullTeste() {
		usuarioDao.adiciona(null);
	}
	
	@Test 
	public void adiciona1UsuarioTeste() {
		usuarioDao.adiciona(usuario1);
		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(1, usuarios.size());
	}
	
	@Test
	public void adicionaEBusca1UsuarioTeste() {
		usuarioDao.adiciona(usuario1);
		Usuario usuarioEsperado = usuarioDao.busca("teste1@email.com");
		assertEquals(usuarioEsperado, usuario1);
	}
	
	@Test
	public void adicionaERemove1UsuarioTeste() {
		usuarioDao.adiciona(usuario1);
		usuarioDao.remove("teste1@email.com");
		List<Usuario> usuarios = usuarioDao.getLista();
		assertTrue(usuarios.isEmpty());
	}
	
	@Test
	public void adiciona2UsuariosTeste() {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(2, usuarios.size());
	}
	
	@Test
	public void adicionaEBusca2UsuariosTeste() {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);

		Usuario usuarioEsperado = usuarioDao.busca("teste1@email.com");
		assertEquals(usuarioEsperado, usuario1);
		usuarioEsperado = usuarioDao.busca("teste2@email.com");
		assertEquals(usuarioEsperado, usuario2);
	}
	
	@Test
	public void adicionaERemove2UsuariosTeste() {
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
	public void adiciona3UsuariosTeste() {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuarioDao.adiciona(usuario3);
		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(3, usuarios.size());
	}
	
	@Test
	public void adicionaEBusca3UsuariosTeste() {
		usuarioDao.adiciona(usuario1);
		usuarioDao.adiciona(usuario2);
		usuarioDao.adiciona(usuario3);
		Usuario usuarioEsperado = usuarioDao.busca("teste1@email.com");
		assertEquals(usuarioEsperado, usuario1);
		usuarioEsperado = usuarioDao.busca("teste2@email.com");
		assertEquals(usuarioEsperado, usuario2);
		usuarioEsperado = usuarioDao.busca("teste@email.com");
		assertEquals(null, usuarioEsperado);
		usuarioEsperado = usuarioDao.busca("teste3@email.com");
		assertEquals(usuarioEsperado, usuario3);
	}
	
	@Test
	public void adicionaERemove3UsuariosTeste() {
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
	public void alteraTeste() {
		usuarioDao.adiciona(usuario1);
		usuario1 = getUsuario(213213L, null, null, "ola@email.com", null, 
				usuario1.getLogin(), "ola", 21313L, "123456", null, null, 
				12731241341L);
		usuarioDao.altera(usuario1);
		assertEquals(usuario1, usuarioDao.busca(usuario1.getLogin()));
	}
	
	@Test
	public void alteraNullTeste() {
		usuarioDao.adiciona(usuario1);
		usuario1 = getUsuario(null, null, null, null, null, 
				usuario1.getLogin(), null, null, null, null, null, 
				null);
		usuarioDao.altera(usuario1);
		assertEquals(usuario1, usuarioDao.busca(usuario1.getLogin()));
	}
	
	@Test
	public void alteraEnderecoTeste() {
		usuarioDao.adiciona(usuario1);
		Endereco endereco = getEndereco("bairro 1", 37145000L, "Alterosa", 
				null, "MG", "Rua 32", 242);
		usuarioDao.alteraEndereco(endereco, usuario1.getLogin());
		assertEquals(endereco, usuarioDao.busca(usuario1.getLogin())
				.getEndereco());
	}
	
	@Test
	public void alteraEnderecoNullTeste() {
		usuarioDao.adiciona(usuario1);
		usuarioDao.alteraEndereco(null, usuario1.getLogin());
		assertEquals(getEndereco(null, null, null, null, null, null, null), 
				usuarioDao.busca(usuario1.getLogin()).getEndereco());
	}
	
	@Test
	public void alteraEnderecoAtrNullTeste() {
		usuarioDao.adiciona(usuario1);
		Endereco endereco = getEndereco(null, null, null, 
				null, null, null, null);
		usuarioDao.alteraEndereco(endereco, usuario1.getLogin());
		assertEquals(endereco, usuarioDao.busca(usuario1.getLogin())
				.getEndereco());
	}
	

}
