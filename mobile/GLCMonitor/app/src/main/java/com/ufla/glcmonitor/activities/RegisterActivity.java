package com.ufla.glcmonitor.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufla.glcmonitor.conection.RemoteDatabaseConection;
import com.ufla.glcmonitor.modelo.Usuario;


public class RegisterActivity extends AppCompatActivity {


    private Usuario usuario;

    //UI interfaces
    private EditText nameView;
    private EditText passwordView;
    private EditText passwordRetryView;
    private AutoCompleteTextView emailView;
    private View mProgressView;
    private View mLoginFormView;
    private Button registerButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usuario = new Usuario();
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

        registerButtonView = (Button) findViewById(R.id.person_register);
        registerButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        nameView = (EditText) findViewById(R.id.name_register);
        emailView = (AutoCompleteTextView) findViewById(R.id.email_register);
        passwordView = (EditText)findViewById(R.id.senha_register);
        passwordRetryView = (EditText) findViewById(R.id.senha_retry_register);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public void registrar(View view) {
        showProgress(true);
        usuario.setEmail(((EditText) findViewById(R.id.email_register)).getText().toString());
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
        finish();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return (password.length() > 4);
    }

    private void attemptLogin() {

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);
        passwordRetryView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String passwordRetry =  passwordRetryView.getText().toString();
        String name = nameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(name)){
            nameView.setError("Name not null add");
            focusView = nameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)){
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;

        }else if(!password.equals(passwordRetry)){
            passwordView.setError("Passwrds not equals");
            passwordRetryView.setError("Passwrds not equals");
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            usuario.setNome(name);
            usuario.setEmail(email);
            usuario.setLogin(email); // Login por padrao e o e-mail
            usuario.setSenha(password);
            new Post(this).execute();
        }
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
                final String msg = RemoteDatabaseConection.remoteRegisterUserDatabase(usuario);

                RegisterActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if(msg.equals("Sucesso!")) {
                            registrar(getCurrentFocus());
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
