package com.example.jeusetetmatch;

public class Match extends Joueur{

     private int id;
     private int duration;
     private int ace;
     private int faults;
     private double longitude;
     private double latitude;
     private Joueur joueur1;
     private Joueur joueur2;

    public Match(int duration, int ace, int faults, double longitude, double latitude, Joueur joueur1, Joueur joueur2){
        super();
        this.duration = duration;
        this.ace = ace;
        this.latitude = latitude;
        this.longitude = longitude;
        this.faults = faults;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
    }

    public Match(int duration, int ace, int faults){
        super();
        this.duration = duration;
        this.ace = ace;
        this.faults = faults;
    }

    public int getId() {
        return id;
    }

    public int getAce() {
        return ace;
    }

    public int getDuration() {
        return duration;
    }

    public int getFaults() {
        return faults;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setAce(int ace) {
        this.ace = ace;
    }

    public void setFaults(int faults) {
        this.faults = faults;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Joueur getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Joueur joueur1) {
        this.joueur1 = joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Joueur joueur2) {
        this.joueur2 = joueur2;
    }

    @Override
    public String toString(){
        return id + " " + duration + " " + faults + " " + ace+ " " + joueur1.string() + " " + joueur2.string();
    }
}
