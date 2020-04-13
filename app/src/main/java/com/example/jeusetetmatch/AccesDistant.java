package com.example.jeusetetmatch;

import android.util.Log;

import org.json.JSONArray;

public class AccesDistant implements AsyncResponse {

    private static final String SERVERADDR = "http://10.0.0.5/match/serveurmatch.php";

    public AccesDistant(){
        super();
    }

    @Override
    public void processFInish(String output) { //recuperation des données du serveur
        Log.d("serveur", "serveur value : " + output);

        //decoupage du message reçu
        String[] message = output.split("%");
        //Dans message[0] : "enreg", "dernier", "erreur"
        //Dans message[1] : reste du message

        if(message.length > 1){
            if(message[0].equals("enreg")){
                Log.d("enreg", "value enreg : " + message[1]);
            }else{
                if(message[0].equals("dernier")){
                    Log.d("dernier", "value dernier : " + message[1]);
                }else{
                    if(message[0].equals("erreur")){
                        Log.d("erreur", "value erreur : " + message[1]);
                    }
                }
            }
        }
    }

    public void envoi(String operation, JSONArray JSONData){ //envoie des données au serveur
        AccessHTTP accesDonnees = new AccessHTTP();

        //Lien delegate
        accesDonnees.delegate = this;
        //ajout des parametres
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", JSONData.toString());

        //appel au serveur
        accesDonnees.execute();
    }
}
