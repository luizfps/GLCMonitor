package com.ufla.glcmonitor.jdbc.modelo;

public class LimitesDeTemperatura {
	
    private Float temperaturaMinima;
    private Float temperaturaMaxima;
    
    public Float getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(Float temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public Float getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(Float temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((temperaturaMaxima == null) ? 0 : temperaturaMaxima.hashCode());
		result = prime * result + ((temperaturaMinima == null) ? 0 : temperaturaMinima.hashCode());
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
		if (!(obj instanceof LimitesDeTemperatura)) {
			return false;
		}
		LimitesDeTemperatura other = (LimitesDeTemperatura) obj;
		if (temperaturaMaxima == null) {
			if (other.temperaturaMaxima != null) {
				return false;
			}
		} else if (!temperaturaMaxima.equals(other.temperaturaMaxima)) {
			return false;
		}
		if (temperaturaMinima == null) {
			if (other.temperaturaMinima != null) {
				return false;
			}
		} else if (!temperaturaMinima.equals(other.temperaturaMinima)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "LimitesDeTemperatura [temperaturaMinima=" + temperaturaMinima 
				+ ", temperaturaMaxima=" + temperaturaMaxima + "]";
	}
    
}
