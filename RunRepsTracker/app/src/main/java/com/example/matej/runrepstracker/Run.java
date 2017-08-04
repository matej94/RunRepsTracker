package com.example.matej.runrepstracker;

/**
 * Created by Matej on 4.8.2017..
 */

public class Run {
    private String Time;
    private String Dist;
    int ID;

    public Run (String fTime, String fDist){
        Time = fTime;
        Dist = fDist;
    }
    public Run(int fID, String fTime, String fDist){
        Time = fTime;
        Dist = fDist;
        ID = fID;
    }
    public String getTime() {
        return Time;
    }

    public String getDist() {
        return Dist;
    }
    public int getID() { return ID;}

}
