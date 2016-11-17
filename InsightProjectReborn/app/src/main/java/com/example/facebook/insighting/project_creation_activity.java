package com.example.facebook.insighting;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

public class project_creation_activity extends AppCompatActivity {
    DatabaseController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_creation_layout);
        db = new DatabaseController(getBaseContext());
    }

    public void sendDataToProjectActivity(View v){
        EditText pname = (EditText)findViewById(R.id.project_name);
        EditText pdes = (EditText)findViewById(R.id.project_description);

        if(!pname.getText().toString().equals("")) {
            Intent i = new Intent(this, ProjectActivity.class);
            String result = db.insertData(pname.getText().toString(), pdes.getText().toString());
            startActivity(i);
        }else{
            Toast.makeText(this,"Projects need to be named",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ProjectActivity.class);
        startActivity(i);
    }


}
