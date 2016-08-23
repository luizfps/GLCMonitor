package com.ufla.glcmonitor.activities;

import android.content.Context;

import com.ufla.glcmonitor.conection.LocalDatabaseConection;
import com.ufla.glcmonitor.modelo.Sensor;
import com.ufla.glcmonitor.modelo.Usuario;

import android.content.Intent;
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
        final Sensor sensorItem = getItem(position);

        LayoutInflater buckysInflater = LayoutInflater.from(getContext());
        View arrayAdapterGrammar = buckysInflater.inflate(R.layout.sensor_item_view, parent,
                false);

        ((TextView) arrayAdapterGrammar.findViewById(R.id.codigo)).setText(
                "Código: "+String.valueOf(sensorItem.getCodigo()));
        ((TextView) arrayAdapterGrammar.findViewById(R.id.modelo)).setText
                ("Modelo: "+sensorItem.getModelo());
        ((TextView) arrayAdapterGrammar.findViewById(R.id.tempMin)).setText
                ("Temperatura Mínima: "+String.valueOf(sensorItem.getTemperaturaMinima()));
        ((TextView) arrayAdapterGrammar.findViewById(R.id.tempMax)).setText
                ("Temperatura Máxima: "+String.valueOf(sensorItem.getTemperaturaMaxima()));
        ((TextView) arrayAdapterGrammar.findViewById(R.id.erro)).setText
                ("Erro: "+String.valueOf(sensorItem.getErro()));
        arrayAdapterGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDatabaseConection.setActualSensor(getContext(),
                        sensorItem.getCodigo());
                System.out.println(sensorItem.getCodigo());
                Usuario usuario = new Usuario();
                usuario.setNome("Ola");
                usuario.setLogin("ola@email");
                Intent intent = new Intent(getContext(), HomeActivity.class);
                intent.putExtra("usuario", usuario);
                getContext().startActivity(intent);
            }
        });

        return arrayAdapterGrammar;
    }

}

