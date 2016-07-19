package com.example.victorfaria.justtestingstuff;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.lang.String;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    public ArrayList<String> keyWordFinder(String original,int num){
        original = original.toLowerCase();
        String[] stopwords = MainActivity.r.getString(R.string.portuguese_stopwords).toString().split(" ");
        for(String sw : stopwords){
            original = original.replaceAll(" "+sw+" ", " ");
        }
        Stemmer st = new Stemmer();
        String[] tokens = original.split(" ");
        String[] candidates = original.replace(".","").replace("(","").replace(")","").split(" ");
        for(int i = 0; i < tokens.length;i++){
            tokens[i] = tokens[i].replace(".","").replace("(","").replace(")","");
            tokens[i] = st.wordStemming(tokens[i]);
            Log.d("MainActivity",tokens[i]);
        }
        HashMap<String,Integer> occurrences = countIntem(tokens);
        occurrences.remove("");
        int i = 1;
        Log.d("MainActivity",occurrences.toString());
        while(occurrences.containsValue(i)){
            i++;
            if(!occurrences.containsValue(i)){
                int count = 1;
                ArrayList<String> res = new ArrayList<String>();
                for(int j = 0; j < num;j++){
                    for(Entry<String,Integer> k:occurrences.entrySet()){
                        if(k.getValue() == i-count){
                            res.add( radic(candidates, k.getKey(),0));
                        }else if(res.size() == num)
                            return  res;
                    }
                    count++;
                }
                return  res;
            }
        }
        return new ArrayList<String>();
    }

    public String radic(String[] k, String p,int ll){
        for(String l:k){
            if(p.regionMatches(0,l,0,p.length()-ll)){
                return l;
            }
        }
        return radic(k,p,ll+1);
    }



}