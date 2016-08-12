package com.ufla.glcmonitor.jdbc.dao;

public class MensagensDeExcecao {
	
	public static String getMensagemDeExcecao(String mensagemDeExcecao) {
		
		switch(mensagemDeExcecao) {
		case "Column 'login' cannot be null": return "Campo login é obrigatório!";
		case "ba": return null;
		case "b": return null;
		case "aab": return null;
		default: return mensagemDeExcecao;
		}
	}
	
	private MensagensDeExcecao() {
		
	}

}
