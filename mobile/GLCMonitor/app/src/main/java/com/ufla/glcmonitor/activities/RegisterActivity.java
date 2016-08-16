package com.ufla.glcmonitor.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ufla.glcmonitor.conection.RemoteDatabaseConection;
import com.ufla.glcmonitor.modelo.Usuario;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usuario = new Usuario();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void registrar(View view) {
        usuario.setNome(((EditText) findViewById(R.id.name_register)).getText().toString());
        usuario.setEmail(((EditText) findViewById(R.id.email_register)).getText().toString());
        usuario.setLogin(((EditText) findViewById(R.id.login_register)).getText().toString());
        usuario.setSenha(((EditText) findViewById(R.id.senha_register)).getText().toString());
        if(!(((EditText) findViewById(R.id.senha_retry_register))
                .getText().toString()).equals(usuario.getSenha())) {
            Toast.makeText(this, "Senhas incompatíveis!",
                    Toast.LENGTH_SHORT).show();
        } else {
            new Post(this).execute();
        }

        usuario.setEmail(((EditText) findViewById(R.id.email_register)).getText().toString());

    }

    private class Post extends AsyncTask<String, Void, Void> {

        /*
        requests are never cached
        requests have no restrictions on data length
        */

        final Context context;
        private Gson gson;

        public Post(Context c){
            this.context = c;
            this.gson = new Gson();
        }

//        protected void onPreExecute(){
//            progress = new ProgressDialog(this.context);
//            progress.setMessage("Loading");
//            progress.show();
//        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                final String msg = RemoteDatabaseConection.remoteRegisterUserDatabase(usuario);

                RegisterActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if(msg.equals("Sucesso!")) {
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                        }
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
