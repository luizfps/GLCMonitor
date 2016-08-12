package com.ufla.glcmonitor.jdbc.dao;

import java.util.Date;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.Endereco;
import com.ufla.glcmonitor.jdbc.modelo.Sensor;
import com.ufla.glcmonitor.jdbc.modelo.Sexo;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;

public class UtilTestes {
	
	public static Endereco getEndereco(String bairro, Long cep, String cidade, 
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

	public static Usuario getUsuario(Long cpf, Date dataDeCadastramento, 
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

}
