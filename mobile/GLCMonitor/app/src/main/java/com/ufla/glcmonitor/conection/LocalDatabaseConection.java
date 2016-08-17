package com.ufla.glcmonitor.conection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.String;

import com.ufla.glcmonitor.activities.R;


public class LocalDatabaseConection extends SQLiteOpenHelper {

    public static final int USER_LOGGED = 1;
    public static final int USER_NOT_LOGGED = 0;

    private static final String NAME_DB = "DB_GLC_Monitor";
    private static final int VERSION_DB = 1;

    private static final String CREATE_GRAPH_TABLE = "CREATE TABLE historical_graphics( )";
    private static final String DROP_GRAPH_TABLE = "DROP TABLE historical_graphics";


    /*Construtor cria o banco */

    public LocalDatabaseConection(Context context){
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

    public static boolean userIsLogged(Activity activity){

        String preferenceKey = activity.getString(R.string.preference_user_login);
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

        if(!sharedPref.contains(preferenceKey)){
            setLogget(activity,USER_NOT_LOGGED);
            return false;
        }

        if(sharedPref.getInt(preferenceKey,USER_NOT_LOGGED) == USER_NOT_LOGGED) {
            return false;
        }

        return true;

    }

    public static void setLogget(Activity activity,int value){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(activity.getString(R.string.preference_user_login), value);
        editor.commit();
    }
}
