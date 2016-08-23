package com.ufla.glcmonitor;

import java.util.Date;
import java.util.Random;

import com.ufla.glcmonitor.jdbc.dao.RegistroDeTemperaturaDao;
import com.ufla.glcmonitor.jdbc.dao.SensorDao;
import com.ufla.glcmonitor.jdbc.dao.UsuarioDao;
import com.ufla.glcmonitor.jdbc.modelo.RegistroDeTemperatura;
import com.ufla.glcmonitor.jdbc.modelo.Sensor;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;

public class PopularTUS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			Usuario usuario = new Usuario();
			usuario.setLogin("luiz@gmail.com");
			usuario.setSenha("12345");
			
			
			//new UsuarioDao().adiciona(usuario);
			
			
			for(int j = 1; j < 7; j++) {
			Sensor sensor = new Sensor();
			sensor.setCodigo((long) j);
			

			
			new SensorDao().adiciona(sensor,"luiz@gmail.com");
			
			Float temperatura = null;
			for(int i=0;i<30;i++){
				 temperatura = (float) Math.round(Math.random()*70.00);
				RegistroDeTemperatura registroDeTemperatura = new RegistroDeTemperatura();
				registroDeTemperatura.setMomento(new Date());
				registroDeTemperatura.setTemperatura(temperatura);
				new RegistroDeTemperaturaDao().adiciona(registroDeTemperatura,sensor.getCodigo() );
				System.out.println(sensor.getCodigo()+ ";" +temperatura);
				
				Thread.sleep(1000);
			}
			}
			
			 
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	//insert into sensor (codigo,modelo,temperaturaMinima,temperaturaMaxima,erro) values (2,A,20,25,2,null);

}
