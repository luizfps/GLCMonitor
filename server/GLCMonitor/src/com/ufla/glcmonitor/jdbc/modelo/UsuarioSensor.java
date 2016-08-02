package com.ufla.glcmonitor.jdbc.modelo;

public class UsuarioSensor {

    private LimitesDeTemperatura limitesDeTemperatura;
    private Integer intervaloDeAtualizacaoDeDados;
    private Usuario usuario;
    private Sensor sensor;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Integer getIntervaloDeAtualizacaoDeDados() {
		return intervaloDeAtualizacaoDeDados;
	}

	public void setIntervaloDeAtualizacaoDeDados(Integer intervaloDeAtualizacaoDeDados) {
		this.intervaloDeAtualizacaoDeDados = intervaloDeAtualizacaoDeDados;
	}

	public LimitesDeTemperatura getLimitesDeTemperatura() {
		return limitesDeTemperatura;
	}

	public void setLimitesDeTemperatura(LimitesDeTemperatura limitesDeTemperatura) {
		this.limitesDeTemperatura = limitesDeTemperatura;
	}

	public Float getTemperaturaMinima() {
		return limitesDeTemperatura.getTemperaturaMinima();
	}

	public void setTemperaturaMinima(Float temperaturaMinima) {
		limitesDeTemperatura.setTemperaturaMinima(temperaturaMinima);
	}

	public Float getTemperaturaMaxima() {
		return limitesDeTemperatura.getTemperaturaMaxima();
	}

	public void setTemperaturaMaxima(Float temperaturaMaxima) {
		limitesDeTemperatura.setTemperaturaMaxima(temperaturaMaxima);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sensor == null) ? 0 : sensor.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UsuarioSensor)) {
			return false;
		}
		UsuarioSensor other = (UsuarioSensor) obj;
		if (sensor == null) {
			if (other.sensor != null) {
				return false;
			}
		} else if (!sensor.equals(other.sensor)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioSensor [limitesDeTemperatura=" + limitesDeTemperatura 
				+ ", intervaloDeAtualizacaoDeDados=" + intervaloDeAtualizacaoDeDados 
				+ ", usuario=" + usuario + ", sensor=" + sensor + "]";
	}
	
}
