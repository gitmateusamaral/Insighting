package com.example.victorfaria.insight;

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
    }

    public String AsString(){
        return title +"#"+ data+";";
    }

}
