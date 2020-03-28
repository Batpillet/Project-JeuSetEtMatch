package com.example.jeusetetmatch;

public class Match {

    private int id;
    private int duration;
    private int ace;
    private int faults;
    private int j1set1, j1set2, j1set3, j2set1, j2set2, j2set3;
    private int longitude;
    private int latitude;

    public Match(){

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

    public int getJ1set1() {
        return j1set1;
    }

    public int getJ1set2() {
        return j1set2;
    }

    public int getJ1set3() {
        return j1set3;
    }

    public int getJ2set1() {
        return j2set1;
    }

    public int getJ2set2() {
        return j2set2;
    }

    public int getJ2set3() {
        return j2set3;
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

    public void setJ1set1(int j1set1) {
        this.j1set1 = j1set1;
    }

    public void setJ1set2(int j1set2) {
        this.j1set2 = j1set2;
    }

    public void setJ1set3(int j1set3) {
        this.j1set3 = j1set3;
    }

    public void setJ2set1(int j2set1) {
        this.j2set1 = j2set1;
    }

    public void setJ2set2(int j2set2) {
        this.j2set2 = j2set2;
    }

    public void setJ2set3(int j2set3) {
        this.j2set3 = j2set3;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public Match(int id, int duration, int ace, int faults, int j1set1, int j1set2, int j1set3, int j2set1, int j2set2, int j2set3, int longitude, int latitude){
        this.id = id;
        this.duration = duration;
        this.ace = ace;
        this.faults = faults;
        this.j1set1 = j1set1;
        this.j1set2 = j2set2;
        this.j1set3 = j1set3;
        this.j2set1 = j2set1;
        this.j2set2 = j2set2;
        this.j2set3 = j2set3;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Match(int duration, int ace, int faults){
        this.duration = duration;
        this.ace = ace;
        this.faults = faults;
    }

    @Override
    public String toString(){
        return id + " " + duration + " " + faults + " " + ace;
    }
}
