package com.ufla.glcmonitor.conection;

import android.content.Context;
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

public class RemoteDatabaseConection {

    private final static String REMOTE_LOGIN_CONNECTION = "http://192.168.56.1:8080/GLCMonitor/logar.jsp";
    private final static String REMOTE_REGISTER_USER_CONNECTION = "http://192.168.56.1:8080/GLCMonitor/cadastrarUsuario.jsp";


    private static URL url;
    private static HttpURLConnection connection;
    private static DataOutputStream dStream;
    private static String urlParameters;

    private static void getRemoteConnection(String connectionType) {
        try {
            url = new URL(connectionType);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setDoOutput(true);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getRemoteUserParameters(Usuario usuario){
        Gson gson = new Gson();
        urlParameters = "usuario=" + gson.toJson(usuario, Usuario.class);
    }

    private static void getRemoteOutputStream(){
        try {
            dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(urlParameters);
            dStream.flush();
            dStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getRemoteMsgOutput(){

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            StringBuilder responseOutput = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseOutput.append(line);
            }
            br.close();

            return responseOutput.toString();

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Error getRemoteOutputStream";

    }


    public static String remoteLoginDatabase(Usuario usuario) {

        getRemoteConnection(REMOTE_LOGIN_CONNECTION);
        getRemoteUserParameters(usuario);
        getRemoteOutputStream();
        return getRemoteMsgOutput();

    }

    public static String remoteRegisterUserDatabase(Usuario usuario) {

        getRemoteConnection(REMOTE_REGISTER_USER_CONNECTION);
        getRemoteUserParameters(usuario);
        getRemoteOutputStream();
        return getRemoteMsgOutput();

    }


}
