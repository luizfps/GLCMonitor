package com.ufla.glcmonitor.jdbc.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sensor implements Comparable<Sensor>, Serializable  {

	private Long codigo;
	private String modelo;
	private LimitesDeTemperatura faixaDeOperacao;
	private Float erro;
	private Usuario usuario;
	private List<RegistroDeTemperatura> registrosDeTemperatura;

	public Sensor() {
		super();
		this.faixaDeOperacao = new LimitesDeTemperatura();
		this.registrosDeTemperatura = new ArrayList<>();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<RegistroDeTemperatura> getRegistrosDeTemperatura() {
		return registrosDeTemperatura;
	}

	public void setRegistrosDeTemperatura(List<RegistroDeTemperatura> registrosDeTemperatura) {
		this.registrosDeTemperatura = registrosDeTemperatura;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public LimitesDeTemperatura getFaixaDeOperacao() {
		return faixaDeOperacao;
	}

	public void setFaixaDeOperacao(LimitesDeTemperatura faixaDeOperacao) {
		this.faixaDeOperacao = faixaDeOperacao;
	}

	public Float getErro() {
		return erro;
	}

	public void setErro(Float erro) {
		this.erro = erro;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Float getTemperaturaMinima() {
		return faixaDeOperacao.getTemperaturaMinima();
	}

	public void setTemperaturaMinima(Float temperaturaMinima) {
		faixaDeOperacao.setTemperaturaMinima(temperaturaMinima);
	}

	public Float getTemperaturaMaxima() {
		return faixaDeOperacao.getTemperaturaMaxima();
	}

	public void setTemperaturaMaxima(Float temperaturaMaxima) {
		faixaDeOperacao.setTemperaturaMaxima(temperaturaMaxima);
	}

	@Override
	public String toString() {
		return "Sensor [codigo=" + codigo + ", modelo=" + modelo + ", faixaDeOperacao="
				+ faixaDeOperacao + ", erro=" + erro + ", usuario=" + usuario
				+ ", registrosDeTemperatura=" + registrosDeTemperatura + "]";
	}

	@Override
	public int compareTo(Sensor outroSensor) {
		if (this.codigo > outroSensor.codigo) {
			return 1;
		} else if (this.codigo < outroSensor.codigo) {
			return -1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((erro == null) ? 0 : erro.hashCode());
		result = prime * result + ((faixaDeOperacao == null) ? 0 : faixaDeOperacao.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result
				+ ((registrosDeTemperatura == null) ? 0 : registrosDeTemperatura.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sensor other = (Sensor) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (erro == null) {
			if (other.erro != null)
				return false;
		} else if (!erro.equals(other.erro))
			return false;
		if (faixaDeOperacao == null) {
			if (other.faixaDeOperacao != null)
				return false;
		} else if (!faixaDeOperacao.equals(other.faixaDeOperacao))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (registrosDeTemperatura == null) {
			if (other.registrosDeTemperatura != null)
				return false;
		} else if (!registrosDeTemperatura.equals(other.registrosDeTemperatura))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
