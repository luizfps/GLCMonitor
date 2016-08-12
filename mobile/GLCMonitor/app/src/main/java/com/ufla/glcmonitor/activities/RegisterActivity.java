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

                URL url = new URL("http://192.168.56.1:8081/GLCMonitor/cadastrarUsuario.jsp");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                String urlParameters = "usuario="+gson.toJson(usuario, Usuario.class);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                //int responseCode = connection.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                final StringBuilder responseOutput = new StringBuilder();
                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();

                RegisterActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println(responseOutput.toString());
                        if(responseOutput.toString().equals("Sucesso!")) {
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context,responseOutput.toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

//        protected void onPostExecute() {
//            progress.dismiss();
//        }
    }

}
