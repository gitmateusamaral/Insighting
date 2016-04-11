package com.example.victorfaria.justtestingstuff;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.FacebookSdk;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static Resources r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r = getResources();

    }

    public void analizeText(View v){
        Project p = new Project("P1","001");
        TextView j = ((TextView)v);
        ArrayList<String> k = p.keyWordFinder(((EditText) findViewById(R.id.editText)).getText().toString(), 4);
        j.setText(k.get(0)+":"+k.get(1)+":"+k.get(2)+":"+k.get(3));
    }

}
