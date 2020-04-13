package com.example.jeusetetmatch.controleur;

import android.content.Context;

import com.example.jeusetetmatch.AccesDistant;
import com.example.jeusetetmatch.Match;

import org.json.JSONArray;

public class Controle {

    private static Controle instance = null;
    private static Match match;
    private static String saveMatch = "savematch";

    private static AccesDistant accesDistant;

    private Controle(){
        super();
    }

    public static final Controle getInstance(Context context){
        if(Controle.instance == null){
            Controle.instance = new Controle();
            accesDistant = new AccesDistant();
            accesDistant.envoi("dernier", new JSONArray());
        }
        return Controle.instance;
    }
}
