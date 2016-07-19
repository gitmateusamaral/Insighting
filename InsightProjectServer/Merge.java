package com.example.victorfaria.justtestingstuff;

/**
 * Created by Victor Faria on 09/04/2016.
 */
public class Merge {











    public static String text(String originalText,String newText){
        String mergeText = "";
        int maxNumCaracters = (originalText.length()>newText.length())?originalText.length():newText.length();
        for(int i = 0; i < maxNumCaracters ;i++){
            if(originalText.toCharArray().length -1 < i){
                mergeText += newText.toCharArray()[i];
            }else if(newText.toCharArray().length -1 < i){
                mergeText += originalText.toCharArray()[i];
            }else{
                mergeText += newText.toCharArray()[i];
            }
        }
        return mergeText;
    }

}
