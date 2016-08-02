package com.ufla.glcmonitor.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBcore extends SQLiteOpenHelper {

    private static final String NAME_DB = "DB_GLC_Monitor";
    private static final int VERSION_DB = 1;

    private static final String CREATE_GRAPH_TABLE = "CREATE TABLE historical_graphics( )";
    private static final String DROP_GRAPH_TABLE = "DROP TABLE historical_graphics";

    /*Construtor cria o banco */

    public DBcore(Context context){
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GRAPH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_GRAPH_TABLE);
        onCreate(db);
    }
}
