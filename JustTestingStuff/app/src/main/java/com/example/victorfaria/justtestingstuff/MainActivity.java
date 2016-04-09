package com.example.victorfaria.justtestingstuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView t = (TextView)findViewById(R.id.label);
        final EditText New = (EditText) findViewById(R.id.newT);
        New.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                EditText original = (EditText) findViewById(R.id.originalT);
                t.setText(Merge.text(original.getText().toString(), New.getText().toString()));
               // Log.d("MainActivity", Merge.text(original.getText().toString(), New.getText().toString()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {


            }
        });

    }
}
