package com.example.victorfaria.justtestingstuff;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity {
    public static Resources r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Project p = new Project("P1","001");
        r = getResources();
        TextView t = (TextView)findViewById(R.id.label);
        t.setText(p.cleanText(t.getText().toString()));
    }
}
