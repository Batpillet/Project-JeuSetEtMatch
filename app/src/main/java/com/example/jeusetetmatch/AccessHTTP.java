package com.example.jeusetetmatch;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AccessHTTP extends AsyncTask<String, Integer, Long> {

    private ArrayList<NameValuePair> param;
    private String ret = null;
    public AsyncResponse delegate = null; //Pour appeler une methode qui est à l'exterieur du thread, on passe par un delegate

    public AccessHTTP(){
        param = new ArrayList<NameValuePair>();
    }

    public void addParam(String name, String value){
        param.add(new BasicNameValuePair(name, value));
    }

    @Override
    protected Long doInBackground(String... strings) {
        HttpClient cnxHTTP = new DefaultHttpClient(); //Objet de connexion HTTP
        HttpPost paramCnx = new HttpPost(strings[0]); //Envoi des parametres en post

        try {
            //Encodage des parametres
            paramCnx.setEntity(new UrlEncodedFormEntity(param));

            //Connexion et envoie des parametres, attente de reponse
            HttpResponse response = cnxHTTP.execute(paramCnx);
            //trad de la response
            ret = EntityUtils.toString(response.getEntity());

        } catch (UnsupportedEncodingException e) {
            Log.d("Erreur encodage", "val : " + e.toString());
        } catch (ClientProtocolException e) { //Erreur de protocole
            Log.d("Erreur protocole", "val : " + e.toString());
        } catch (IOException e) { //Erreur d'input/output
            Log.d("Erreur IO", "val : " + e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Long result){
        delegate.processFInish((ret.toString()));
    }
}
