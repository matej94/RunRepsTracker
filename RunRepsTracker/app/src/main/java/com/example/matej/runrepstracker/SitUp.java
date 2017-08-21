package com.example.matej.runrepstracker;

/**
 * Created by Matej on 9.6.2017..
 */

public class SitUp {
    private String SitUp;
    private String Sets;
    int ID;
    String date;

    public SitUp(String fdate, String fSitUp,String fSets){
        SitUp = fSitUp;
        Sets = fSets;
        date = fdate;
    }
    public SitUp(int fID,String fdate, String fSitUp,String fSets){
        SitUp = fSitUp;
        Sets = fSets;
        ID = fID;
        date = fdate;
    }
    public String getSitUp() {
        return SitUp;
    }

    public String getSets() {
        return Sets;
    }
    public int getID() { return ID;}
    public String getDate() {
        return date;
    }
}
