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
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class ProjectActivity extends AppCompatActivity {
    public String newName = "Deafult";
    public String newDescription;
    public static ArrayList<Project> projects;
    int num = 0;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
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
        //editor.clear();editor.apply();

        Intent i = getIntent();

        if(keys != null ) {
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Project proj = new Project(entry.getValue().toString());
                projects.add(proj);
            }
        }

        if(i != null){
                Bundle extras = i.getExtras();
                if(extras != null){
                String name = extras.getString("projectname");
                String des = extras.getString("projectdescription");
                Project p = new Project(name,"luca",des);
                projects.add(p);
                editor.putString(p.projectName, p.AsString());
                editor.apply();
            }
        }
        addCardView();
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
            final View cv = gridlayout.getChildAt(i);
            cv.setOnTouchListener(new View.OnTouchListener() {
                long start;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        start = System.currentTimeMillis();
                    }
                    else if (event.getAction() == MotionEvent.ACTION_UP) {

                        if( System.currentTimeMillis() - start >= 2000){
                            ((ViewGroup) cv.getParent()).removeView(cv);
                            Log.d("Released", "Button released :" + (System.currentTimeMillis() - start));
                            SharedPreferences.Editor editor = getSharedPreferences("Projects", Context.MODE_PRIVATE).edit();
                            TextView t = ((TextView)(cv.findViewById(R.id.project_name)));
                            editor.remove( t.getText().toString() );
                        }
                    }
                    // TODO Auto-generated method stub
                    return false;
                }
            });
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
