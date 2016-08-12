package com.ufla.glcmonitor.modelo;

import java.io.Serializable;
import java.util.List;

public class Sensor implements Serializable {

    private Long codigo;
    private String modelo;
    private LimitesDeTemperatura faixaDeOperacao;
    private Float erro;
    private Usuario usuario;
    private List<RegistroDeTemperatura> registrosDeTemperatura;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
        if (!(obj instanceof Sensor)) {
            return false;
        }
        Sensor other = (Sensor) obj;
        if (codigo == null) {
            if (other.codigo != null) {
                return false;
            }
        } else if (!codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sensor [codigo=" + codigo + ", modelo=" + modelo
                + ", faixaDeOperacao=" + faixaDeOperacao + ", erro="
                + erro + ", usuario=" + usuario + ", registrosDeTemperatura="
                + registrosDeTemperatura + "]";
    }

}
