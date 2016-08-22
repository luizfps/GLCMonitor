package com.ufla.glcmonitor.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.ufla.glcmonitor.modelo.RegistroDeTemperatura;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class NewgraphActivity extends AppCompatActivity {

    private GraphView graph;
    private  static int contador = 0;
    private  double maiortemp = 0.0;
    private ArrayList<RegistroDeTemperatura> registros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgraph);
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
        graph = new GraphView(this);
        graph = (GraphView) findViewById(R.id.graph2);

        registros = (ArrayList<RegistroDeTemperatura>) getIntent().getSerializableExtra("registros");

        final SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        DataPoint[] points = new DataPoint[registros.size()];
        for (int i = 0; i < registros.size(); i++) {
            System.out.println(registros.get(i).toString());
            if(registros.get(i).getTemperatura()> maiortemp)
            {
                maiortemp = registros.get(i).getTemperatura();
            }
            points[i] = new DataPoint(registros.get(i).getMomento(), registros.get(i).getTemperatura());
        }
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext(),formatador));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7); // only 4 because of the space


        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(points);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(graph.getContext(), formatador.format(dataPoint.getX()) , Toast.LENGTH_SHORT).show();

            }
        });
        series.setSpacing(4);
        graph.setTitle("Temperatura do período");
        graph.setTitleTextSize(25);
        series.setColor(Color.RED);

        graph.getViewport().setBackgroundColor(Color.argb(255, 222, 222, 222));
        graph.getViewport().setDrawBorder(true);
        graph.getViewport().setBorderColor(Color.BLUE);


        graph.getGridLabelRenderer().setGridColor(Color.BLACK);

        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);

        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);
        series.setValuesOnTopSize(25);




        graph.addSeries(series);



        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);

        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-5);
        graph.getViewport().setMaxY(maiortemp+10.0);


        // set manual X bounds
        System.out.println(registros.get(0).getMomento().getTime());
        System.out.println(registros.get(registros.size()-1).getMomento().getTime());
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(6000);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.onDataChanged(false, false);

        // enable scrolling
        graph.getViewport().setScrollable(true);




    }

}
