package com.example.victorfaria.insight;

import java.util.ArrayList;
import java.lang.String;
//DO NOT USE THESE CARACTERS ;|#
public class Project {
    String projectID;
    String projectName;
    ArrayList<InsightCard> cards;
    ArrayList<String> categoriesList;

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
}
