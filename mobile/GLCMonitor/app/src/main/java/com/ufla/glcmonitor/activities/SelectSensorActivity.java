package com.ufla.glcmonitor.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ufla.glcmonitor.modelo.Sensor;

import com.ufla.glcmonitor.conection.RemoteDatabaseConection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 8/22/16.
 */
public class SelectSensorActivity extends AppCompatActivity {

    private ArrayAdapter listAdapterGrammar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sensor);
        List<Sensor> sensorsList = new ArrayList<>();
        listAdapterGrammar = new ArrayAdapterSensor(this, sensorsList);
        ((ListView) findViewById(R.id.sensors)).setAdapter(listAdapterGrammar);
    }


    private class Post extends AsyncTask<String, Void, Void> {

        /*
        requests are never cached
        requests have no restrictions on data length
        */

        final Context context;

        public Post(Context c){
            this.context = c;
        }

//        protected void onPreExecute(){
//            progress = new ProgressDialog(this.context);
//            progress.setMessage("Loading");
//            progress.show();
//        }

        @Override
        protected Void doInBackground(String... params) {

            try {

                final String msg = RemoteDatabaseConection.remoteLoginDatabase();
                SelectSensorActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

//        protected void onPostExecute() {
//            progress.dismiss();
//        }
    }



}
