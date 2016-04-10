package com.example.victorfaria.justtestingstuff;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.lang.String;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//DO NOT USE THESE CARACTERS ;|#
public class Project {
    String projectID;
    String projectName;
    ArrayList<InsightCard> cards;
    ArrayList<String> categoriesList;
    public String stopwords;

    public Project(String name,String id){
        projectName = name;
        projectID = id;
    }

    public Project (String p){
        String[] info = p.split("|");
        projectID = info[0];
        projectName = info[1];

        String[] categories = info[2].substring(0,info.length-2).split("#");
        for(String c : categories){
            categoriesList.add(c);
        }
    }

    public void addInsightCard(String title, String data){
        cards.add(new InsightCard(title,data));
    }

    public String AsString(){
        String ic = "";
        String cl = "";
        for(InsightCard i : cards) {
            ic += i.AsString();
        }
        for(String c : categoriesList){
            cl += c+"#";
        }
        return projectID +"|"+projectName+"|"+cl+"|"+ic;
    }

    public ArrayList<InsightCard> findCardsWithCategory(String c){
        ArrayList<InsightCard> result = new ArrayList<InsightCard> ();
        for(InsightCard i : cards){
            for(String j : i.categories) {
                if(c == j){
                    result.add(i);
                    break;
                }
            }
        }
        return result;
    }

    public HashMap<String, Integer> countIntem( String[] array ) {

        Arrays.sort(array);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        Integer count = 0;
        String first = array[0];
        for( int counter = 0; counter < array.length; counter++ ) {
            if(first.hashCode() == array[counter].hashCode()) {
                count = count + 1;
            } else {
                map.put(first, count);
                count = 1;
            }
            first = array[counter];
            map.put(first, count);
        }

        return map;
    }

    public String cleanText(String original){
        String[] stopwords = MainActivity.r.getString(R.string.portuguese_stopwords).toString().split(" ");
        for(String sw : stopwords){
            original = original.replaceAll(" "+sw+" ", " ");
        }
        HashMap<String,Integer> occurrences = countIntem(original.split(" "));
        Stemmer st = new Stemmer();
        for (HashMap.Entry<String, Integer> entry : occurrences.entrySet()) {
            st.add(entry.getKey().toCharArray(),entry.getKey().length());
            st.stem();
            Log.d("MainActivity", "lulu : " + st.toString());

        }
        return original;
    }

}