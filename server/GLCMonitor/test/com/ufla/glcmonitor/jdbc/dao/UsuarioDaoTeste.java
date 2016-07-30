package com.ufla.glcmonitor.jdbc.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ufla.glcmonitor.jdbc.modelo.Endereco;
import com.ufla.glcmonitor.jdbc.modelo.Sensor;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class UsuarioDaoTeste {
	
	private Usuario usuario;
	private UsuarioDao usuarioDao;
	
	@Before
	public void inicializa() throws SQLException {
		usuario = new Usuario();
		usuario.setCpf(0L);
		usuario.setDataDeCadastramento(new Date(1000000000L));
		usuario.setDataDeNascimento(new Date(100L));
		usuario.setEmail("teste@email.com");
		Endereco endereco = new Endereco();
		endereco.setBairro("Centro");
		endereco.setCep(37200000L);
		endereco.setCidade("Lavras");
		endereco.setComplemento(null);
		endereco.setEstado("MG");
		endereco.setLogradouro("Rua A");
		endereco.setNumero(100);
		usuario.setEndereco(endereco);
		usuario.setLogin("teste@email.com");
		usuario.setNome("Teste");
		usuario.setRg(null);
		usuario.setSenha("senha");
		usuario.setSensores(new ArrayList<Sensor>());
		usuario.setSexo('M');
		usuario.setTelefone(null);
		usuarioDao = new UsuarioDao();
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
	public void adiciona1UsuarioTeste() {
		usuarioDao.adiciona(usuario);
		List<Usuario> usuarios = usuarioDao.getLista();
		assertEquals(1, usuarios.size());
		Usuario usuarioEsperado = usuarioDao.busca("teste@email.com");
		assertEquals(usuarioEsperado, usuario);
		//usuarioDao.adiciona(usuario);
		usuarioDao.remove("teste@email.com");
		usuarios = usuarioDao.getLista();
		assertEquals(0, usuarios.size());
	}
	
	@Test
	public void adiciona2UsuariosTeste() {
		usuarioDao.adiciona(usuario);
	}

}
