package com.ufla.glcmonitor.jdbc.dao;

import java.util.Date;
import java.util.List;

import com.ufla.glcmonitor.jdbc.modelo.Endereco;
import com.ufla.glcmonitor.jdbc.modelo.LimitesDeTemperatura;
import com.ufla.glcmonitor.jdbc.modelo.RegistroDeTemperatura;
import com.ufla.glcmonitor.jdbc.modelo.Sensor;
import com.ufla.glcmonitor.jdbc.modelo.Sexo;
import com.ufla.glcmonitor.jdbc.modelo.Usuario;
import com.ufla.glcmonitor.jdbc.modelo.UsuarioSensor;

public class UtilTestes {

	public static Endereco getEndereco(String bairro, Long cep, String cidade, String complemento,
			String estado, String logradouro, Integer numero) {
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

	public static Usuario getUsuario(Long cpf, Date dataDeCadastramento, Date dataDeNascimento,
			String email, Endereco endereco, String login, String nome, Long rg, String senha,
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

	public static Sensor getSensor(Long codigo, String modelo, LimitesDeTemperatura faixaDeOperacao,
			Float erro, Usuario usuario, List<RegistroDeTemperatura> registrosDeTemperatura) {
		Sensor sensor = new Sensor();
		sensor.setCodigo(codigo);
		sensor.setModelo(modelo);
		sensor.setFaixaDeOperacao(faixaDeOperacao);
		sensor.setErro(erro);
		sensor.setUsuario(usuario);
		sensor.setRegistrosDeTemperatura(registrosDeTemperatura);
		return sensor;
	}

	public static LimitesDeTemperatura getLimitesDeTemperatura(Float temperaturaMinima,
			Float temperaturaMaxima) {
		LimitesDeTemperatura limitesDeTemperatura = new LimitesDeTemperatura();
		limitesDeTemperatura.setTemperaturaMinima(temperaturaMinima);
		limitesDeTemperatura.setTemperaturaMaxima(temperaturaMaxima);
		return limitesDeTemperatura;
	}

	public static RegistroDeTemperatura getRegistroDeTemperatura(Float temperatura, Date momento) {
		RegistroDeTemperatura registroDeTemperatura = new RegistroDeTemperatura();
		registroDeTemperatura.setTemperatura(temperatura);
		registroDeTemperatura.setMomento(momento);
		return registroDeTemperatura;
	}

	public static UsuarioSensor getUsuarioSensor(LimitesDeTemperatura limitesDeTemperatura,
			Integer intervaloDeAtualizacaoDeDados, Usuario usuario, Sensor sensor) {
		UsuarioSensor usuarioSensor = new UsuarioSensor();
		usuarioSensor.setLimitesDeTemperatura(limitesDeTemperatura);
		usuarioSensor.setIntervaloDeAtualizacaoDeDados(intervaloDeAtualizacaoDeDados);
		usuarioSensor.setUsuario(usuario);
		usuarioSensor.setSensor(sensor);
		return usuarioSensor;
	}

}
