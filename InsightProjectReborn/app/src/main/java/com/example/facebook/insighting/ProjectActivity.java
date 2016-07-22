package com.example.facebook.insighting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class ProjectActivity extends AppCompatActivity {
    public String newName = "Deafult";
    public String newDescription;
    public static ArrayList<Project> projects;
    int numId = 0;
    //TextView cardName = (TextView) findViewById(R.id.card_name);
    //Typeface fontinha = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.tff");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        projects = new ArrayList<Project>();
        SharedPreferences sharedPref = this.getSharedPreferences("Projects", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Map<String,?> keys = sharedPref.getAll();
       // editor.clear();editor.commit();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String name = extras.get("projectname").toString();
            String des = extras.get("projectdescription").toString();
            addCardView(name, des);
            Project p = new Project(name,"luca",des);
            projects.add(p);
            editor.putString(p.projectName, p.AsString());
            editor.commit();
        }


        if(keys != null) {
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Project proj = new Project(entry.getValue().toString());
                projects.add(proj);
            }
        }
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);
        for(int i = 0; i < projects.size();i++){
            View cv = gridlayout.getChildAt(i);
            ((TextView)cv.findViewById(R.id.card_name)).setText(projects.get(i).projectName);
            ((TextView)cv.findViewById(R.id.card_description)).setText(projects.get(i).projectDescription);
        }
    }

    public void showDialog(View v){
        Intent i = new Intent(this,project_creation_activity.class);
        startActivity(i);
    }

    public void addCardView(String name, String des){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);
        View cardView = inflater.inflate(R.layout.cardview, gridlayout);
        ((TextView)cardView.findViewById(R.id.card_name)).setText(name);
        ((TextView)cardView.findViewById(R.id.card_description)).setText(des);
        //((ImageView)cardView.findViewById(R.id.card_photo));

        numId ++;
    }
}
