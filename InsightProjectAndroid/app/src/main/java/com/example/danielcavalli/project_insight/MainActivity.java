package com.example.danielcavalli.project_insight;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences.Editor;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    SharedPreferences s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        s = this.getSharedPreferences("com.example.danielcavalli.project_insight", Activity.MODE_PRIVATE);
        editor = s.edit();
    }

    public void showDialog(View v){

    }

}
