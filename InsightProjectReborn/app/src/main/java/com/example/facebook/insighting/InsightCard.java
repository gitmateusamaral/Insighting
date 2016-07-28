package com.example.facebook.insighting;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.lang.String;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class InsightCard {
    String title;
    String data;
    String date;
    ArrayList<String> categories;

    public void setTitle(String t){
        title = t;
        date = getDate();
    }

    public void setData(String d){
        data = d;
        date = getDate();
    }

    public InsightCard(String t, String d){
        title = t;
        data = d;
        date = getDate();
    }

    public InsightCard (String i){
        StringTokenizer r = new StringTokenizer(i," ");
        title  = r.nextToken();
        data = r.nextToken();
        date = r.nextToken().replace(";","");
    }
    public String getDate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        df.applyPattern("dd/MM/yyyy");
        return df.format(c.getTime());
    }
    public String AsString(){
        return title +" "+ data+" "+date+";";
    }

}
