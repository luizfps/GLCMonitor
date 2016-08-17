package com.ufla.glcmonitor.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ufla.glcmonitor.conection.LocalDatabaseConection;
import com.ufla.glcmonitor.dao.UsuarioDao;
import com.ufla.glcmonitor.modelo.Usuario;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private double danger = 0.9;
    public RelativeLayout relativeLayout;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent() != null && getIntent().getExtras() != null
                && getIntent().getExtras().get("usuario") != null) {
            usuario = (Usuario) getIntent().getExtras().get("usuario");
        }
        Toast.makeText(this, usuario.getLogin() + "\n" + usuario.getSenha(),
                Toast.LENGTH_SHORT).show();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //relativeLayout = (RelativeLayout) findViewById(R.id.content_home);

        //LinearLayout navHeaderApp = (LinearLayout) getResources().getLayout(R.layout.nav_header_app);
        //((TextView) navHeaderApp.findViewById(R.id.navName)).setText("");
        //((TextView) navHeaderApp.findViewById(R.id.navLogin)).setText("");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            settingsClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // nada a fazaer deixei igual no desenho da interface
        } else if (id == R.id.nav_graficos) {
            graphClick();
        } else if (id == R.id.nav_cadastrar_sensor) {
            registerSensorClick();
        } else if (id == R.id.nav_exibir_outro_sensor) {

        } else if (id == R.id.nav_configuracoes) {
            settingsClick();
        } else if(id == R.id.nav_sair){

            if (LocalDatabaseConection.userIsLogged(this)){
                UsuarioDao usuarioDao = new UsuarioDao(this);
                usuarioDao.deleteLocal(usuario);
                LocalDatabaseConection.setLogget(this,false,"not");
            }
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void registerSensorClick(){
        Intent intent = new Intent(this, RegisterSensorActivity.class);
        startActivity(intent);
    }

    public void graphClick(){
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }

    public void settingsClick(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void setBackgroudColor(double temperature, double temperatureMin, double temperatureMax){

        if(temperature > temperatureMax){
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.backgroudFire));
        }
        else if(temperature > temperatureMax - danger ){
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.backgroudDanger));
        }
        else if (temperature < temperatureMin){
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.backgroudIce));
        }
    }

    public void resetBackgroudColor(){
        relativeLayout.setBackgroundColor(getResources().getColor(R.color.bakcgroudNormal));
    }


}
