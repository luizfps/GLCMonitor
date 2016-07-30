package com.ufla.glcmonitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.ufla.glcmonitor.jdbc.modelo.*;

public class Mock {
	
	public boolean registerUser(Usuario user) {
		return true;
	}
	
	public List<RegistroDeTemperatura> getRegistroDeTemperatura(Usuario usuario, 
			Sensor sensor, Date inicio, Date fim) {
		Random random = new Random();
		List<RegistroDeTemperatura> registrosDeTemperatura = new ArrayList<>();
		for(int i = 100; i >= 0; i--) {
			RegistroDeTemperatura registroDeTemperatura = new RegistroDeTemperatura();
			registroDeTemperatura.setMomento(new Date(new Date().getTime()-i*1000));
			registroDeTemperatura.setTemperatura(random.nextFloat());
			registrosDeTemperatura.add(registroDeTemperatura);
		}
		return registrosDeTemperatura;
	}
	
	public boolean solicitarInstalacaoSensor(Usuario usuario, Sensor sensor) {
		return true;
	}
	
	public String getStatusInstalacao(Usuario usuario, Sensor sensor) {
		return "Instalação em andamento";
	}
	
	public void definirLimitesDeTemperatura(Float limiteInferior, 
			Float limiteSuperior) {
		
	}
	
	public void definirTempoDeAtualizacao(Long tempo) {
		
	}
	

}
