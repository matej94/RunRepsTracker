package com.example.matej.runrepstracker;

/**
 * Created by Matej on 4.8.2017..
 */

public class Run {
    private String Time;
    private String Dist;
    int ID;
    String date;

    public Run (String fdate,String fTime, String fDist){
        Time = fTime;
        Dist = fDist;
        date = fdate;
    }
    public Run(int fID,String fdate, String fTime, String fDist){
        Time = fTime;
        Dist = fDist;
        ID = fID;
        date = fdate;
    }
    public String getTime() {
        return Time;
    }

    public String getDist() {
        return Dist;
    }
    public int getID() { return ID;}
    public String getDate() {
        return date;
    }

}
