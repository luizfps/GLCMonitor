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
			
			
			
			Sensor sensor = new Sensor();
			sensor.setCodigo((long) 1);
			
			//Usuario usuario = new Usuario();
			//usuario.setLogin("luiz");
			
			//new UsuarioDao().adiciona(usuario);
			
			new SensorDao().adiciona(sensor,"luiz");
			
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
			
			 
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

}
