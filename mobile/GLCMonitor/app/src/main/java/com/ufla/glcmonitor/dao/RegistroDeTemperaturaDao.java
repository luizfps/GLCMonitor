package com.ufla.glcmonitor.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufla.glcmonitor.conection.LocalDatabaseConection;
import com.ufla.glcmonitor.modelo.RegistroDeTemperatura;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by administrador on 21/08/16.
 */
public class RegistroDeTemperaturaDao {

    private SQLiteDatabase db;

    public RegistroDeTemperaturaDao(Context context) {
        LocalDatabaseConection localDatabase = new LocalDatabaseConection(context);
        this.db = localDatabase.getWritableDatabase();
    }

    public void insertLocal(RegistroDeTemperatura registro, int codigo){

        ContentValues cValues = new ContentValues();
        cValues.put("temperatura",registro.getTemperatura());
        cValues.put("momento",registro.getMomento().getTime());
        cValues.put("sensor_codigo",codigo);

        db.insert("registroDeTemperatura",null,cValues);
    }

    public void updateLocal(int codigo){
        //deleteLocal(codigo);
        //insertLocal(usuario);
    }

    public void deleteLocal(int codigo){
        db.delete("registroDeTemperatura", " sensor_codigo = '" + codigo + "';" , null);
    }

    public ArrayList<RegistroDeTemperatura> buscaLocal(int codigo){
        String[] colunas = new String[]{"temperatura","momento"};
        Cursor cursor = db.query("registroDeTemperatura",colunas,"  sensor_codigo = '" + codigo + "';",null,null,null,null);

        ArrayList<RegistroDeTemperatura> registros = new ArrayList<>();
        RegistroDeTemperatura registro = new RegistroDeTemperatura();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            while(cursor.moveToNext()) {
                registro.setTemperatura(cursor.getFloat(0));
                registro.setMomento(new Date(cursor.getLong(1)));
            }
            registros.add(registro);
        }
        return registros;
    }

}
