package com.example.matej.runrepstracker;

import java.util.Date;

/**
 * Created by Matej on 9.6.2017..
 */

public class Result {
    private String PushUp;
    private String Sets;
    int ID;
    String date;

    public Result(String fdate,String fPushUp,String fSets){
        PushUp = fPushUp;
        Sets = fSets;
        date = fdate;
    }
    public Result(int fID,String fdate,String fPushUp,String fSets){
        PushUp = fPushUp;
        Sets = fSets;
        ID = fID;
        date = fdate;
    }
    public String getPushUp() {
        return PushUp;
    }

    public String getSets() {
        return Sets;
    }
    public int getID() { return ID;}

    public String getDate() {
        return date;
    }
}
