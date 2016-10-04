package com.example.facebook.insighting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class EditInsightCard extends AppCompatActivity {

    public String ic_p;
    public String ic_c;
    int count = 0;
    public ArrayList<String> categories;
    DatabaseController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_insight_card);
        EditText title = (EditText) findViewById(R.id.ic_textTitle);
        EditText des = (EditText) findViewById(R.id.ic_textDescription);
        categories = new ArrayList<String>();
        Intent i = getIntent();
        db = new DatabaseController(getBaseContext());
        if(i != null){
             Bundle extras = i.getExtras();
            if(extras != null){
                ic_p = extras.getString("id_project");
                ic_c = extras.getString("id_card");
                if(!extras.getBoolean("new")) {
                    Cursor c =  db.getInsightCardFromId(ic_c);
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        title.setText(c.getString(0));
                        des.setText(c.getString(1));
                        c.moveToNext();
                    }
                    c.close();
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
        //galleryIntent.putExtra("project",p.AsString());
        startActivityForResult(galleryIntent, RESULT_FIRST_USER);
    }

    public void exitInsightCard(View v){
        String title = ((EditText)findViewById(R.id.ic_textTitle)).getText().toString();
        String description = ((EditText)findViewById(R.id.ic_textDescription)).getText().toString();

        if(title.isEmpty() || description.isEmpty()){
            if(count == 1){
                Intent i = new Intent(this, EditInsightCard.class);
                i.putExtra("project",ic_p);
                String r = db.insertDataInsightCard(title, description, "#url", "#tags", ic_p);
                Toast.makeText(this, r ,Toast.LENGTH_LONG).show();
                startActivity(i);
            }
            else{
                Toast.makeText(this, "Title or description are empty. Press again to delete Insight Card.", Toast.LENGTH_SHORT).show();
            }
            count ++;
        }
    }

    public void onBackPressed()
    {
        String title = ((EditText)findViewById(R.id.ic_textTitle)).getText().toString();
        String description = ((EditText)findViewById(R.id.ic_textDescription)).getText().toString();

        if((!title.isEmpty() || !description.isEmpty())) {
            if(getIntent().getExtras().getBoolean("new")){
                Intent i = new Intent(this, EditInsightCard.class);
                i.putExtra("project",ic_p);
                String r = db.insertDataInsightCard(title, description, "#url", "#tags", ic_p);
                Toast.makeText(this, r ,Toast.LENGTH_LONG).show();
                startActivity(i);
            }
            else{
            }
            Intent i = new Intent(this, InsightCardActivity.class);
            //i.putExtra("project", p.AsString());
            startActivity(i);
        }
        else{
            if(count == 1){
                Intent i = new Intent(this, InsightCardActivity.class);
                //i.putExtra("project", p.AsString());
                startActivity(i);
            }
            else{
                Toast.makeText(this,"Title or description are empty. Press again to delete Insight Card.",Toast.LENGTH_SHORT).show();
            }
            count ++;
        }
    }

    public void onPause(){
        super.onPause();
        String title = ((EditText)findViewById(R.id.ic_textTitle)).getText().toString();
        String description = ((EditText)findViewById(R.id.ic_textDescription)).getText().toString();

        if((!title.isEmpty() || !description.isEmpty())) {
            if(getIntent().getExtras().getBoolean("new")){
            }
            else{
            }
        }
        else{
            if(count == 1){
                Intent i = new Intent(this, EditInsightCard.class);
                //i.putExtra("project", p.AsString());
                startActivity(i);
            }
            else{
                Toast.makeText(this,"Title or description are empty. Press again to delete Insight Card.",Toast.LENGTH_SHORT).show();
            }
            count ++;
        }
    }

    public void createTag(View v){
        GridLayout tagDisposal = (GridLayout)findViewById(R.id.tag_disposal);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tag, tagDisposal);
        ((TextView)(tagDisposal.getChildAt(tagDisposal.getChildCount()-1)).findViewById(R.id.tag_name)).setText(((EditText) findViewById(R.id.tag_text)).getText());
        ((EditText)findViewById(R.id.tag_text)).setText("");
        categories.add(((EditText) findViewById(R.id.tag_text)).getText().toString());
    }
}
