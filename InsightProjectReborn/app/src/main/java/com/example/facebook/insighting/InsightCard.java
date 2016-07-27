package com.example.facebook.insighting;

import java.util.ArrayList;
import java.lang.String;

public class InsightCard {
    String title;
    String data;
    ArrayList<String> categories;

    public InsightCard(String t, String d){
        title = t;
        data = d;
    }

    public InsightCard (String i){
        title  = i.split("#")[0];
        data = i.split("#")[1].replace(";","");
    }

    public String AsString(){
        return title +"#"+ data+";";
    }

}
