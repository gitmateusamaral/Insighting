package com.example.facebook.insighting;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditInsightCard extends AppCompatActivity {

    public Project p;
    public int ic_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent i = getIntent();
        if(i != null){
             Bundle extras = i.getExtras();
            if(extras != null){
                p = new Project(extras.getString("project"));
                ic_id = extras.getInt("ic_id");
                EditText title = (EditText) findViewById(R.id.ic_textTitle);
                EditText des = (EditText) findViewById(R.id.ic_textDescription);

            }
        }

        setContentView(R.layout.activity_edit_insight_card);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.ttf");
        EditText et = (EditText) findViewById(R.id.ic_textTitle);
        EditText et2 = (EditText) findViewById(R.id.ic_textDescription);
        et.setTypeface(face);
        et2.setTypeface(face);
    }

    public void saveInsightCard(View v){
        EditText title = (EditText) findViewById(R.id.ic_textTitle);
        EditText des = (EditText) findViewById(R.id.ic_textDescription);
        des.setText(p.cards.size()+"");
        //p.cards.get(ic_id).title = title.getText().toString();
        //p.cards.get(ic_id).data = des.getText().toString();
/*
        Intent i = new Intent(this, InsightCardActivity.class);
        i.putExtra("project",p.AsString());
        startActivity(i);*/
    }

}
