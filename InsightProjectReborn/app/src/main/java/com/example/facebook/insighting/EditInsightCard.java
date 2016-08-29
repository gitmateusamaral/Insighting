package com.example.facebook.insighting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Map;

public class EditInsightCard extends AppCompatActivity {

    public Project p;
    public int ic_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_insight_card);
        EditText title = (EditText) findViewById(R.id.ic_textTitle);
        EditText des = (EditText) findViewById(R.id.ic_textDescription);

        Intent i = getIntent();
        if(i != null){
             Bundle extras = i.getExtras();
            if(extras != null){
                p = new Project(extras.getString("project"));
                ic_id = extras.getInt("ic_id");
                if(!extras.getBoolean("new")) {
                    title.setText(p.cards.get(ic_id).title);
                    des.setText(p.cards.get(ic_id).data);
                }
            }
        }
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.ttf");
        title.setTypeface(face);
        des.setTypeface(face);
    }

    public void addImage(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_FIRST_USER);
    }

    public void saveInsightCard(View v){
        EditText title = (EditText) findViewById(R.id.ic_textTitle);
        EditText des = (EditText) findViewById(R.id.ic_textDescription);
        if(getIntent().getExtras().getBoolean("new")){p.addInsightCard(".",".");}
        p.cards.get(ic_id).setTitle(title.getText().toString());
        p.cards.get(ic_id).setData(des.getText().toString());

        Intent i = new Intent(this, InsightCardActivity.class);
        i.putExtra("project", p.AsString());
        saveProject();
        startActivity(i);
    }

    public void onBackPressed()
    {
        Intent i = new Intent(this,InsightCardActivity.class);
        i.putExtra("project", p.AsString());
        startActivity(i);
    }

    public void saveProject(){
        SharedPreferences sharedPref = this.getSharedPreferences("Projects", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(p.projectName,p.AsString());
        editor.apply();
    }



}
