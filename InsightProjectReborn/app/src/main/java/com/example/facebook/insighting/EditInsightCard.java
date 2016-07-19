package com.example.facebook.insighting;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditInsightCard extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_insight_card);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.ttf");
        EditText et = (EditText) findViewById(R.id.ic_textTitle);
        EditText et2 = (EditText) findViewById(R.id.ic_textDescription);
        et.setTypeface(face);
        et2.setTypeface(face);
    }
}
