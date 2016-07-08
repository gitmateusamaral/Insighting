package com.example.facebook.insighting;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addCardView(View v){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);
        View cardView = inflater.inflate(R.layout.cardview, gridlayout);
        ((TextView)cardView.findViewById(R.id.card_name)).setText("Name Here");
        ((TextView)cardView.findViewById(R.id.card_description)).setText("description");
        //((ImageView)cardView.findViewById(R.id.card_photo));
    }
}
