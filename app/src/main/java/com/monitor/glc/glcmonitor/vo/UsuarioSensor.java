package com.monitor.glc.glcmonitor.vo;

import java.util.List;

public class UsuarioSensor {

    private Usuario usuario;
    private Sensor sensor;
    private Float temperaturaMinima;
    private Float temperaturaMaxima;

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

    public List<RegistroDeTemperatura> getRegistrosDeTemperatura() {
        return registrosDeTemperatura;
    }

    public void setRegistrosDeTemperatura(List<RegistroDeTemperatura> registrosDeTemperatura) {
        this.registrosDeTemperatura = registrosDeTemperatura;
    }

    private List<RegistroDeTemperatura> registrosDeTemperatura;


}
