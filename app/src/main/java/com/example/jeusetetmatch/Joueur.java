package com.example.jeusetetmatch;

import java.util.ArrayList;
import java.util.List;

public class Joueur {

    private String nom;
    private ArrayList<Integer> jeu;
    private boolean gagnant;

    public Joueur(String nom, ArrayList<Integer> jeu, boolean gagnant){
        this.nom = nom;
        this.jeu = jeu;
        this.gagnant = gagnant;
    }

    public Joueur(String nom, boolean gagnant){
        this.nom = nom;
        this.gagnant = gagnant;
    }

    public Joueur() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Integer>  getJeu() {
        return jeu;
    }

    public void setJeu(ArrayList<Integer> jeu) {
        this.jeu = jeu;
    }

    public boolean isGagnant() {
        return gagnant;
    }

    public void setGagnant(boolean gagnant) {
        this.gagnant = gagnant;
    }

    public String string(){
        return nom + " " + gagnant + " " + getJeu().toString();
    }
}
