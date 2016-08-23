package com.ufla.glcmonitor.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ufla.glcmonitor.conection.LocalDatabaseConection;

import com.ufla.glcmonitor.conection.RemoteDatabaseConection;
import com.ufla.glcmonitor.modelo.Sensor;

/**
 * Created by carlos on 8/22/16.
 */
public class SelectSensorActivity extends AppCompatActivity {

    private ArrayAdapter<Sensor> listAdapterSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sensor);
        new Post(this).execute();
       ((ListView) findViewById(R.id.sensors)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LocalDatabaseConection.setActualSensor(SelectSensorActivity.this,
                        listAdapterSensor.getItem(i).getCodigo());
                Toast.makeText(SelectSensorActivity.this, "Sensor "+
                        listAdapterSensor.getItem(i).getCodigo()+" selecionado!", Toast.LENGTH_SHORT);
                Intent intent = new Intent(SelectSensorActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
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
                listAdapterSensor = new ArrayAdapterSensor(SelectSensorActivity.this,
                        RemoteDatabaseConection.remoteGetSensor
                        (LocalDatabaseConection.getLocalLogin(getBaseContext())));

                SelectSensorActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ((ListView) findViewById(R.id.sensors)).setAdapter(listAdapterSensor);
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
