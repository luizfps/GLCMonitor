package com.ufla.glcmonitor.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ufla.glcmonitor.conection.RemoteDatabaseConection;
import com.ufla.glcmonitor.modelo.RegistroDeTemperatura;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.AsyncTask;
import java.util.Date;
import java.util.Locale;
import android.text.InputType;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
public class GraphActivity extends AppCompatActivity implements OnClickListener {

    private GraphView graph;
    //campo data inicial
    private EditText datainicial;
    //campo data final
    private EditText datafinal;
    //data picker para o campo inicial
    private DatePickerDialog pickerInicial;
    //data picker para o campo final
    private DatePickerDialog pickerFinal;

    private java.text.SimpleDateFormat dateFormatter;

    private SimpleDateFormat formato;



    ArrayList<RegistroDeTemperatura> registros;

    Date Inicialdate;

    Date Finaldate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        dateFormatter = new java.text.SimpleDateFormat("dd-MM-yyyy");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        graph = (GraphView) findViewById(R.id.graph);
        graphExib();

        datainicial = (EditText) findViewById(R.id.data_inicial);
        datainicial.setInputType(InputType.TYPE_NULL);
        datainicial.requestFocus();

        datafinal = (EditText) findViewById(R.id.data_final);
        datafinal.setInputType(InputType.TYPE_NULL);

        Inicialdate = new Date();
        Finaldate = new Date();

        setDateTimeField();


        Button gerargrafico = (Button) findViewById(R.id.button2);
        gerargrafico.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarTemperaturaIntervalo();

            }
        });

    }

        private void setDateTimeField() {
        datainicial.setOnClickListener(this);
        datafinal.setOnClickListener(this);

            Inicialdate = new Date();
            Finaldate = new Date();
        Calendar newCalendar = Calendar.getInstance();
        pickerInicial = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                view.animate();
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Inicialdate = newDate.getTime();
                datainicial.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        pickerFinal = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Finaldate = newDate.getTime();
                datafinal.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void graphExib() {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }

    @Override
    public void onClick(View view) {
        System.out.println("entrou\n\n");
        if (view == datainicial) {
            pickerInicial.show();
        } else if (view == datafinal) {
            pickerFinal.show();
        }

    }

    private static  class Dados{

        Date iniciald;
        Date finald;
        ArrayList<RegistroDeTemperatura> registros;
        Long codigo_sensor;

        Dados(Long codigo_sensor2,Date date1, Date date2,ArrayList<RegistroDeTemperatura> registros2)
        {
            iniciald = date1;
            finald = date2;
            registros = registros2;
            codigo_sensor = codigo_sensor2;
        }
    }
    public void buscarTemperaturaIntervalo(){

        if(Inicialdate.before(Finaldate)) {
            ArrayList<RegistroDeTemperatura> registros = new ArrayList<RegistroDeTemperatura>();
            Long codigo_sensor = Long.valueOf(1);
            Dados datas = new Dados(codigo_sensor,Inicialdate,Finaldate,registros);

           new Post().execute(datas);

        } else{
                Toast.makeText(GraphActivity.this, "a data inicial deve ser antes da final!", Toast.LENGTH_SHORT).show();
            }
    }
// classe que executa outra thread
    private class Post extends AsyncTask<Dados,Void,ArrayList<RegistroDeTemperatura>>{


        @Override
        protected ArrayList<RegistroDeTemperatura>doInBackground(Dados...params){
            SimpleDateFormat format1= new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(params[0].codigo_sensor);
            System.out.println(params[0].iniciald);
            System.out.println(params[0].finald);
                // fazer a requisição para o servidor a fim de pegar as dadas e enviar para outra activit
            try{
                //lembrar de pegar o id do sensor
                final String msg = RemoteDatabaseConection.getBuscaTemperaturaIntervalosParameter(params[0].codigo_sensor,format1.format(params[0].iniciald),format1.format(params[0].finald));

                if(msg.contains("sem registros")){

                }
                else
                {

                    params[0].registros = new Gson().fromJson(msg,new TypeToken<ArrayList<RegistroDeTemperatura>>(){}.getType());

                    //mandar os registros de temperatura para outra activity para gerar o novo gráfico
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }



            return params[0].registros;
        }
            protected void onPostExecute(ArrayList<RegistroDeTemperatura>result)
            {
                //se a lista retornada não estiver vázia passamos o parametro para a proxima activity
               if(!result.isEmpty())
               {
                   Intent intent = new Intent(GraphActivity.this,NewgraphActivity.class);
                   intent.putExtra("registros",result);
                   /*for(int i=0;i<result.size();i++){
                       System.out.println(result.get(i).getMomento());
                   }*/

                   startActivity(intent);

               }
                else
               {
                   Toast.makeText(GraphActivity.this, "Não existe registros no período selecionado", Toast.LENGTH_SHORT).show();
               }
            }
    }
}