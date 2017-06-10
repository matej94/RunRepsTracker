package com.example.matej.runrepstracker;

/**
 * Created by Matej on 9.6.2017..
 */

public class SitUp {
    private String SitUp;
    private String Sets;
    int ID;

    public SitUp(String fSitUp,String fSets){
        SitUp = fSitUp;
        Sets = fSets;
    }
    public SitUp(int fID,String fSitUp,String fSets){
        SitUp = fSitUp;
        Sets = fSets;
        ID = fID;
    }
    public String getSitUp() {
        return SitUp;
    }

    public String getSets() {
        return Sets;
    }
    public int getID() { return ID;}
}
