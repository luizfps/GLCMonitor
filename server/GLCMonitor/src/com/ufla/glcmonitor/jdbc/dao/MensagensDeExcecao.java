package com.ufla.glcmonitor.jdbc.dao;

/**
 * A classe MensagensDeExcecao fornece métodos estáticos para mapear as
 * mensagens de exceção. Recebendo uma mensagem de exceção gerada por uma
 * SQLException e retornando a mensagem que deve chegar para o usuário.
 * 
 * @author glcmonitor
 *
 */
public class MensagensDeExcecao {

	/**
	 * Classe apenas fornece métodos estáticos.
	 */
	private MensagensDeExcecao() {

	}

	/**
	 * Recebe uma mensagem de exceção gerada por uma SQLException e retorna a
	 * mensagem que deve chegar para o usuário.
	 * 
	 * @param mensagemDeExcecao
	 *            mensagem de exceção gerada por uma SQLException.
	 * @return mensagem de exceção para o usuário.
	 */
	public static String getMensagemDeExcecao(String mensagemDeExcecao) {
		switch (mensagemDeExcecao) {
		case "Column 'login' cannot be null":
			return "Campo login é obrigatório!";
		case "Column 'usuario_login' cannot be null":
			return "Campo login de " + "usuário é obrigatório!";
		case "Column 'codigo' cannot be null":
			return "Campo código é obrigatório!";
		case "Column 'sensor_codigo' cannot be null":
			return "Campo código do " + "sensor é obrigatório!";
		default:
			return mensagemDeExcecao;
		}
	}

}
