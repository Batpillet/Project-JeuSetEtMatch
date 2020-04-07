package com.example.jeusetetmatch;

public class Match {

     private int id;
     private int duration;
     private int ace;
     private int faults;
     private int longitude;
     private int latitude;

    public Match(){

    }

    public Match(int id, int duration, int ace, int faults, int longitude, int latitude){
        super();
        this.id = id;
        this.duration = duration;
        this.ace = ace;
        this.latitude = latitude;
        this.longitude = longitude;
        this.faults = faults;
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

    @Override
    public String toString(){
        return id + " " + duration + " " + faults + " " + ace;
    }
}
