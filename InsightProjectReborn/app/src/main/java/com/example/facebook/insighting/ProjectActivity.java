package com.example.facebook.insighting;

import android.content.Context;
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

public class ProjectActivity extends AppCompatActivity {
    public String newName = "Deafult";
    public String newDescription;
    public ArrayList<Project> projects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        projects = new ArrayList<Project>();
    }

    public void setProjectName(View v){
        EditText dialog_text = (EditText)v.findViewById(R.id.dialog_text);
       // newName = dialog_text.getText().toString();
        dialog_text.setHint("Description");
        Button ok_button = (Button) v.findViewById(R.id.ok);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText dt = (EditText)view.findViewById(R.id.dialog_text);
                projects.add(new Project(newName,"01001",dt.getText().toString()));
                addCardView(newName,dt.getText().toString());
            }
        });
    }

    public void showDialog(View v){
        if(findViewById(R.id.dialog_box) == null) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup overlay = (ViewGroup) findViewById(R.id.overlay);
            inflater.inflate(R.layout.dialogbox,overlay);
        }else {
           Toast t = Toast.makeText(this,"Insert your Project Name",Toast.LENGTH_SHORT);
           t.show();
        }
    }

    public void addCardView(String name, String des){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);
        View cardView = inflater.inflate(R.layout.cardview, gridlayout);
        ((TextView)cardView.findViewById(R.id.card_name)).setText(name);
        ((TextView)cardView.findViewById(R.id.card_description)).setText(des);
        //((ImageView)cardView.findViewById(R.id.card_photo));
    }
}
