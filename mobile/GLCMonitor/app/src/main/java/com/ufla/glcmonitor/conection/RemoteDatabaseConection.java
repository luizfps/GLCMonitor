package com.ufla.glcmonitor.conection;

import com.google.gson.Gson;
import com.ufla.glcmonitor.modelo.Usuario;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
public class RemoteDatabaseConection {

    private final static String REMOTE_GET_USER_CONNECTION = "http://"+ConnectionConfiguration.IP+
            ":" + ConnectionConfiguration.PORTA + "/GLCMonitor/getUsuario.jsp";
    private final static String REMOTE_REGISTER_USER_CONNECTION = "http://"+ConnectionConfiguration.IP+
            ":" + ConnectionConfiguration.PORTA + "/GLCMonitor/cadastrarUsuario.jsp";
    private final static String REMOTE_LOGIN_CONNECTION = "http://"+ConnectionConfiguration.IP+
            ":" + ConnectionConfiguration.PORTA + "/GLCMonitor/logar.jsp";
    private final static String REMOTE_GET_TEMPERATUREINTERVAL_CONNECTION = "http://"+ConnectionConfiguration.IP+
            ":" + ConnectionConfiguration.PORTA + "/GLCMonitor/getBuscaTemperaturaIntervalosParameter.jsp";

    private static URL url;
    private static HttpURLConnection connection;
    private static DataOutputStream dStream;
    private static String urlParameters,urlParameters2,urlParameters3;

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

    private static void getLoginParameter(String login){
        Gson gson = new Gson();
        urlParameters = "login=" + login;
    }
    public static String getBuscaTemperaturaIntervalosParameter(long codigo_sensor,String data_inicial,String data_final){

        getRemoteConnection(REMOTE_GET_TEMPERATUREINTERVAL_CONNECTION);

        System.out.println(codigo_sensor);
        System.out.println(data_inicial);
        System.out.println( data_final);
        //esses parameteros serão enviados via post
        urlParameters = "codigo_sensor=" +String.valueOf(codigo_sensor)+"&";
        urlParameters2= "data_inicial=" +data_inicial+"&";
        urlParameters3=  "data_final=" +data_final;

            try {
                dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.writeBytes(urlParameters2);
                dStream.writeBytes(urlParameters3);
                dStream.flush();
                dStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return getRemoteMsgOutput();
    }

    private static void setRemoteOutputStream(){
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
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return "Connection Filed";

    }


    public static String remoteLoginDatabase(Usuario usuario) {

        getRemoteConnection(REMOTE_LOGIN_CONNECTION);
        getRemoteUserParameters(usuario);
        setRemoteOutputStream();
        return getRemoteMsgOutput();

    }

    public static String remoteRegisterUserDatabase(Usuario usuario) {

        getRemoteConnection(REMOTE_REGISTER_USER_CONNECTION);
        getRemoteUserParameters(usuario);
        setRemoteOutputStream();
        return getRemoteMsgOutput();

    }

    public static Usuario remoteGetUsuario(String login){
        getRemoteConnection(REMOTE_GET_USER_CONNECTION);
        getLoginParameter(login);
        setRemoteOutputStream();
        return (new Gson().fromJson(getRemoteMsgOutput(),Usuario.class));
    }


}
