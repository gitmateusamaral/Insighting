package com.example.victorfaria.insight;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    ArrayList<Project> allProjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        allProjects = new ArrayList<Project>();
    }
    
    public void showProjectNameBox(View v){
        View b = findViewById(R.id.setProjectNameBox);
        b.setVisibility(View.VISIBLE);
    }

    public void addProject(View v){
        EditText nameInput = (EditText)findViewById(R.id.nameInput);
        Button b = (Button)findViewById(R.id.project_button);
        b.setText(nameInput.getText());
        b.setVisibility(View.VISIBLE);
        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListView l = (ListView)layoutInflater.inflate(R.layout.activity_home, null).findViewById(R.id.listView);
        l.addHeaderView(b);
        View g = findViewById(R.id.setProjectNameBox);
        g.setVisibility(View.INVISIBLE);
        allProjects.add(new Project(nameInput.getText().toString(),allProjects.size()+""));
    }
}
