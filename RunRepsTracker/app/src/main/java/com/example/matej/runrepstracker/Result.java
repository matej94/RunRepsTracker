package com.example.matej.runrepstracker;

/**
 * Created by Matej on 9.6.2017..
 */

public class Result {
    private String PushUp;
    private String Sets;
    int ID;

    public Result(String fPushUp,String fSets){
        PushUp = fPushUp;
        Sets = fSets;
    }
    public Result(int fID,String fPushUp,String fSets){
        PushUp = fPushUp;
        Sets = fSets;
        ID = fID;
    }
    public String getPushUp() {
        return PushUp;
    }

    public String getSets() {
        return Sets;
    }
    public int getID() { return ID;}

}
