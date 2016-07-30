package com.ufla.glcmonitor.jdbc.modelo;

public enum Sexo { 
	
	MASCULINO('M'), FEMININO('F');
	
	private Character value;
	
	private Sexo(Character value) {
		this.value = value;
	}
	
	public Character getValue() {
		return value;
	}
	
	public static Sexo getSexo(Character value) {
		switch(value) {
		case 'M': return MASCULINO;
		case 'F': return FEMININO;
		default: return null;
		}
	}
}
