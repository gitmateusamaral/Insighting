package com.example.facebook.insighting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Map;

public class ProjectActivity extends AppCompatActivity {
    public String newName = "Default";
    public String newDescription;
    public static ArrayList<Project> projects;
    int num = 0;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    //TextView cardName = (TextView) findViewById(R.id.card_name);
    //Typeface fontinha = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.tff");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        projects = new ArrayList<Project>();
        sharedPref = this.getSharedPreferences("Projects", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        Map<String,?> keys = sharedPref.getAll();
        editor = editor.clear();editor.apply();

        Intent i = getIntent();
        Log.d("ProjectActivity",keys.size()+"piru");
        if(keys.size() != 0) {
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.d("ProjectActivity",entry.getValue().toString());
                Project p = new Project(entry.getValue().toString());
                p.categoriesList.add("all");
                if(!p.projectName.isEmpty() || !p.projectName.equals("null") )
                projects.add(p);
            }
        }

        if(i != null){
                Bundle extras = i.getExtras();
                if(extras != null){
                String name = extras.getString("projectname");
                String des = extras.getString("projectdescription");
                Project p = new Project(name,"luca",des);
                    p.categoriesList.add("all");
                projects.add(p);
                editor.putString(p.projectName, p.AsString());
                editor.apply();
            }
        }
        addCardView();
    }

    public void showMenu(View v){
        Firebase myFirebaseRef = new Firebase("https://insighting-aa229.firebaseio.com/");
        myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");
        Toast.makeText(this, "Rada", Toast.LENGTH_SHORT).show();
    }

    public void showDialog(View v){
        Intent i = new Intent(this,project_creation_activity.class);
        startActivity(i);
    }

    public void showOptions(View v){
        View deleteButton = ((View)v.getParent()).findViewById(R.id.deleteButton);
        //deleteButton.
        if(deleteButton.getVisibility() == View.VISIBLE){
            deleteButton.setVisibility(View.GONE);
        }else{
            deleteButton.setVisibility(View.VISIBLE);
        }
    }

    public void deleteProject(View v){
        ((ViewGroup)((ViewGroup)v.getParent()).getParent()).removeView(((ViewGroup) v.getParent()));
        String pn = ((TextView)((ViewGroup)v.getParent()).findViewById(R.id.project_name)).getText().toString();
        editor.remove(pn);
        editor.apply();
    }

    public void addCardView(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);

        for(int i = 0; i < projects.size();i++){
            inflater.inflate(R.layout.project_file, gridlayout);
            View cv = gridlayout.getChildAt(i);
            ((TextView)cv.findViewById(R.id.project_name)).setText(projects.get(i).projectName);
        }
    }

    public void enterProject(View v){
        SharedPreferences sharedPref = this.getSharedPreferences("Projects", Context.MODE_PRIVATE);
        Intent i = new Intent(this,InsightCardActivity.class);
        TextView x = (TextView)(v.findViewById(R.id.project_name));
        i.putExtra("project", sharedPref.getString(x.getText().toString(), ""));
        startActivity(i);
    }

    public void onBackPressed()
    {
        if(num == 0) {
            Toast.makeText(this, "Press again to close", Toast.LENGTH_LONG).show();
            num++;
        }else{
                finishAffinity();
        }
    }


}
