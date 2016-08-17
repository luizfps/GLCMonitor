package com.ufla.glcmonitor.jdbc.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.ufla.glcmonitor.jdbc.modelo.Sexo;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;

public class GsonTestes {

	//Gson não guarda os milisegundos de um Date
	private Gson gson;
	private Usuario usuario1;
	// private Usuario usuario2;
	// private Usuario usuario3;
	// private UsuarioDao usuarioDao;

	@Before
	public void inicializa() throws SQLException {
		usuario1 = UtilTestes
				.getUsuario(23522342L, new Date(1000000000L), new Date(1000L), "teste1@email.com",
						UtilTestes.getEndereco("Centro", 37200000L, "Lavras", null, "MG", "Rua A",
								100),
						"teste1@email.com", "Teste1", null, "senha1", new ArrayList<>(),
						Sexo.getSexo('M'), 12423525L);
		/*usuario2 = UtilTestes.getUsuario(23423252344L, new Date(100353234000L), new Date(13242000L),
				"teste2@email.com",
				UtilTestes.getEndereco("Centro", 37200000L, "Lavras", null, "MG", "Rua B", 241),
				"teste2@email.com", "Teste2", 92378462L, "senha2", new ArrayList<>(),
				Sexo.getSexo('F'), 13112131L);
		usuario3 = UtilTestes.getUsuario(null, new Date(1000000000L), new Date(1000L),
				"teste3@email.com",
				UtilTestes.getEndereco("Centro", 37200000L, "Lavras", "Ap 301", "MG", "Rua C", 123),
				"teste3@email.com", "Teste3", 62728327L, "senha3", null, Sexo.getSexo('M'), null);
		usuarioDao = new UsuarioDao();*/
		gson = new Gson();
	}

	@Test
	public void getListaSemUsuariosTeste() {
		String usuarioJson = gson.toJson(usuario1, Usuario.class);
		assertEquals(usuario1, gson.fromJson(usuarioJson, Usuario.class));
	}

}
