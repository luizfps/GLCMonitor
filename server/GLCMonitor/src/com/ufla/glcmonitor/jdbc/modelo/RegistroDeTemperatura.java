package com.ufla.glcmonitor.jdbc.modelo;

import java.util.Date;

public class RegistroDeTemperatura {

    private Float temperatura;
    private Date momento;
    
	public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Date getMomento() {
        return momento;
    }

    public void setMomento(Date momento) {
        this.momento = momento;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((momento == null) ? 0 : momento.hashCode());
		result = prime * result + ((temperatura == null) ? 0 : temperatura.hashCode());
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
		if (!(obj instanceof RegistroDeTemperatura)) {
			return false;
		}
		RegistroDeTemperatura other = (RegistroDeTemperatura) obj;
		if (momento == null) {
			if (other.momento != null) {
				return false;
			}
		} else if (!momento.equals(other.momento)) {
			return false;
		}
		if (temperatura == null) {
			if (other.temperatura != null) {
				return false;
			}
		} else if (!temperatura.equals(other.temperatura)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RegistroDeTemperatura [temperatura=" + temperatura 
				+ ", momento=" + momento + "]";
	}
        
}
