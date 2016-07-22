package com.example.facebook.insighting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
       //editor.clear();editor.commit();

        Bundle extras = getIntent().getExtras();



        if(keys != null) {
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.e("projectActivity",entry.getValue().toString());
                Project proj = new Project(entry.getValue().toString());
                projects.add(proj);
            }
        }

        if((sharedPref.getAll().size() != 0|| extras != null)){
            String name = extras.get("projectname").toString();
            String des = extras.get("projectdescription").toString();
            Project p = new Project(name,"luca",des);
            projects.add(p);
            editor.putString(p.projectName, p.AsString());
            editor.commit();
        }

        addCardView();

    }

    public void showDialog(View v){
        Intent i = new Intent(this,project_creation_activity.class);
        startActivity(i);
    }

    public void addCardView(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);

        for(int i = 0; i < projects.size();i++){
            Log.d("ProjectActivity", projects.get(i).projectName + "!!");
            inflater.inflate(R.layout.project_file, gridlayout);
            View cv = gridlayout.getChildAt(i);
            ((TextView)cv.findViewById(R.id.project_name)).setText(projects.get(i).projectName);
        }
    }

    public void enterProject(View v){
        SharedPreferences sharedPref = this.getSharedPreferences("Projects", Context.MODE_PRIVATE);
        Intent i = new Intent(this,SelectInsightActivity.class);
        TextView x = (TextView)(v.findViewById(R.id.project_name));
        i.putExtra("project", sharedPref.getString(x.getText().toString(), ""));
        startActivity(i);
        Log.d("ProjectActivity",x.getText().toString());
    }

}
