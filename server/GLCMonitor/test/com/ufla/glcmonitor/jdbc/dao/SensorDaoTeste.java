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

import com.ufla.glcmonitor.jdbc.modelo.Sensor;
import com.ufla.glcmonitor.jdbc.modelo.Sexo;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;
import com.ufla.glcmonitor.jdbc.persistance.ConnectionFactory;

public class SensorDaoTeste {
	
	private Usuario usuario1;
	private Usuario usuario2;
	private UsuarioDao usuarioDao;
	private Sensor sensor1;
	private Sensor sensor2;
	private Sensor sensor3;
	private SensorDao sensorDao;
	
	@Before
	public void inicializa() throws SQLException {
		usuario1 = UtilTestes.getUsuario(23522342L, new Date(1000000000L), new Date(100L), 
				"teste1@email.com", UtilTestes.getEndereco("Centro", 37200000L, 
						"Lavras", null, "MG", "Rua A", 100), 
				"teste1@email.com", "Teste1", null, "senha1", new ArrayList<>(), 
				Sexo.getSexo('M'), 12423525L);
		usuario2 = UtilTestes.getUsuario(23423252344L, new Date(100353234000L), new Date(13242L), 
				"teste2@email.com", UtilTestes.getEndereco("Centro", 37200000L, 
						"Lavras", null, "MG", "Rua B", 241), 
				"teste2@email.com", "Teste2", 92378462L, "senha2", new ArrayList<>(), 
				Sexo.getSexo('F'), 13112131L);
		usuarioDao = new UsuarioDao();
		sensor1 = UtilTestes.getSensor(0L, "FG35", null, 0.2F, usuario1, null);
		sensor2 = UtilTestes.getSensor(1L, "GE42", UtilTestes
				.getLimitesDeTemperatura(-10F, 25F), 0.5F, usuario2, null);
		sensor3 = UtilTestes.getSensor(2L, "HS76", UtilTestes
				.getLimitesDeTemperatura(-70F, 120F), 0.003F, usuario1, null);
		sensorDao = new SensorDao();
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
	public void getListaSemSensoresTeste() throws SQLException {
		System.out.println("ola");
		List<Sensor> sensores = sensorDao.getLista();
		assertTrue(sensores.isEmpty());
	}
	
	@Test
	public void buscaSemSensoresTeste() throws SQLException  {
		Sensor sensor = sensorDao.busca(0L);
		assertEquals(null, sensor);
	}
	
	@Test
	public void remocaoSemSensoresTeste() throws SQLException {
		sensorDao.remove(0L);
	}
	
	@Test
	public void adicionaSensorAtrNullTeste() throws SQLException {
		usuarioDao.adiciona(usuario1);
		sensorDao.adiciona(UtilTestes.getSensor(0L, null, null, null, null, null), "teste1@email.com");
		List<Sensor> sensores = sensorDao.getLista();
		assertEquals(1, sensores.size());
	}
	
	
	

}
