package com.monitor.glcmonitor.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jessica on 22/07/16.
 */
public class DBcore extends SQLiteOpenHelper {

    private static final String CREATE_DB = "";

    private static final String NAME_DB = "DB_GLC_Monitor";
    private static final int VERSION_DB = 1;

    public DBcore(Context context){
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Criar o banco e as tabelas");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
