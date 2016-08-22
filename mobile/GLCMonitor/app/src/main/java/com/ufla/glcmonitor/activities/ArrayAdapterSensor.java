package com.ufla.glcmonitor.activities;

import android.content.Context;
import com.ufla.glcmonitor.modelo.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 03/08/16.
 */
public class ArrayAdapterSensor extends ArrayAdapter<Sensor> {

    public ArrayAdapterSensor(SelectSensorActivity context, List<Sensor> sensores) {
        super(context, R.layout.sensor_item_view, sensores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sensor sensorItem = getItem(position);

        LayoutInflater buckysInflater = LayoutInflater.from(getContext());
        View arrayAdapterGrammar = buckysInflater.inflate(R.layout.sensor_item_view, parent,
                false);

        ((TextView) arrayAdapterGrammar.findViewById(R.id.codigo)).setText(sensorItem.getCodigo()
                .toString());
        ((TextView) arrayAdapterGrammar.findViewById(R.id.modelo)).setText
                (sensorItem.getModelo());
        ((TextView) arrayAdapterGrammar.findViewById(R.id.tempMin)).setText
                (sensorItem.getTemperaturaMinima().toString());
        ((TextView) arrayAdapterGrammar.findViewById(R.id.tempMax)).setText
                (sensorItem.getTemperaturaMaxima().toString());
        ((TextView) arrayAdapterGrammar.findViewById(R.id.erro)).setText
                (sensorItem.getErro().toString());
        return arrayAdapterGrammar;
    }

}

