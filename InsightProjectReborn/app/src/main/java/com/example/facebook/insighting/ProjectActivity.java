package com.example.facebook.insighting;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
    DatabaseController db;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    //TextView cardName = (TextView) findViewById(R.id.card_name);
    //Typeface fontinha = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.tff");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseController(getBaseContext());
        projects = new ArrayList<Project>();
        Cursor c = db.readProjectData();

        c.moveToFirst();
        while (c.isAfterLast() == false) {
            projects.add(new Project(c.getString(0), c.getString(1), c.getString(2)));
            c.moveToNext();
        }
        addCardView();
    }

    public void showMenu(View v){
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
        int id = ((TextView)((ViewGroup)v.getParent()).findViewById(R.id.project_name)).getId();
        db.deleteProjectData(id+"");
        ((ViewGroup)v.getParent()).removeAllViews();
    }

    public void addCardView(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);

        for(int i = 0; i < projects.size();i++){
            inflater.inflate(R.layout.project_file, gridlayout);
            View cv = gridlayout.getChildAt(i);
            cv.setId(Integer.parseInt(projects.get(i).projectID));
            Log.e("MainActivity", cv.getId()+"");
            ((TextView)cv.findViewById(R.id.project_name)).setText(projects.get(i).projectName);
        }
    }

    public void enterProject(View v){
        Intent i = new Intent(this,InsightCardActivity.class);
        TextView x = (TextView)(v.findViewById(R.id.project_name));
        i.putExtra("project", v.getId()+"");
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
