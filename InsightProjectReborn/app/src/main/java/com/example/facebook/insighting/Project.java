package com.example.facebook.insighting;

import java.util.ArrayList;
import java.lang.String;
import java.util.StringTokenizer;

//DO NOT USE THESE CARACTERS ;|ᔦ + alt255
public class Project {
    String projectID;
    public String projectName;
    public String projectDescription;
    public ArrayList<InsightCard> cards;
    ArrayList<String> categoriesList;

    public Project(String id,String name,String description){
        projectName = name;
        projectID = id;
        projectDescription = description;
        cards = new ArrayList<InsightCard>();
        categoriesList =  new ArrayList<String>();
    }

    public void addInsightCard(String title, String data){
        cards.add(new InsightCard(title,data));
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
}