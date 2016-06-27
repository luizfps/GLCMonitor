package com.monitor.glcmonitor.vo;

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
}
