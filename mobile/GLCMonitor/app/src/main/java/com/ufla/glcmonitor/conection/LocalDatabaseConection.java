package com.ufla.glcmonitor.conection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.String;

import com.ufla.glcmonitor.activities.R;


public class LocalDatabaseConection extends SQLiteOpenHelper {
    private static final String NAME_DB = "glcmonitor";
    private static final int VERSION_DB = 1;

    private static final String CREATE_TABLE_USER =
            "create table usuario (" +
            "  login VARCHAR(255) NOT NULL," +
            "  senha VARCHAR(30)," +
            "  nome VARCHAR(255)," +
            "  telefone BIGINT," +
            "  email VARCHAR(255) UNIQUE," +
            "  rg BIGINT UNIQUE," +
            "  cpf BIGINT UNIQUE" +
            ");" ;

    private static final String CREATE_TABLE_REGISTRO_TEMPERATURA =
            "create table registroDeTemperatura (" +
            "  temperatura FLOAT," +
            "  momento DATETIME," +
            "  sensor_codigo BIGINT NOT NULL" +
            ");";



    /*Construtor cria o banco */

    public LocalDatabaseConection(Context context){
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_REGISTRO_TEMPERATURA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }

    public static boolean userIsLogged(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("preference",context.MODE_PRIVATE);

        if(!sharedPref.contains(context.getString(R.string.preference_user_logged))){
            setLogget(context,false,"not");
            return false;
        }

        return (sharedPref.getBoolean(context.getString(R.string.preference_user_logged),false));

    }

    public  static  String getLocalLogin(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("preference",context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.preference_user_login),"User");
    }

    public  static  Long getActualSensor(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("preference",context.MODE_PRIVATE);
        return sharedPref.getLong("sensor",-1);
    }

    public static void setLogget(Context context,boolean logged, String loggin){
        SharedPreferences sharedPref = context.getSharedPreferences("preference",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(context.getString(R.string.preference_user_logged), logged);
        editor.putString(context.getString(R.string.preference_user_login), loggin);
        editor.commit();
    }

    public static void setActualSensor(Context context,Long codigoSensor){
        SharedPreferences sharedPref = context.getSharedPreferences("preference",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("sensor", codigoSensor);
        editor.commit();
    }
}
