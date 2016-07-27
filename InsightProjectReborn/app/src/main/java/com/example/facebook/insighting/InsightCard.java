package com.example.facebook.insighting;

import android.util.Log;

import java.util.ArrayList;
import java.lang.String;
import java.util.StringTokenizer;

public class InsightCard {
    String title;
    String data;
    ArrayList<String> categories;

    public InsightCard(String t, String d){
        title = t;
        data = d;
    }

    public InsightCard (String i){
        StringTokenizer r = new StringTokenizer(i,"#");
        title  = r.nextToken();
        data = r.nextToken().replace(";","");
    }

    public String AsString(){
        return title +"#"+ data+";";
    }

}
