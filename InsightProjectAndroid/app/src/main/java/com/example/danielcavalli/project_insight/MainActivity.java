package com.example.danielcavalli.project_insight;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import carbon.widget.CardView;

public class MainActivity extends AppCompatActivity {
    int num = 0;
    //SharedPreferences.Editor editor;
    //SharedPreferences s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pscreen);

        //s = this.getSharedPreferences("com.example.danielcavalli.project_insight", Activity.MODE_PRIVATE);
        //editor = s.edit();
    }

    public void changeScreen(View v){
        //setContentView(R.layout.pscreen);
    }

    public void addProject(View v){
        Button lb = (Button) findViewById(R.id.button_layout);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid);

        EditText edit = (EditText)findViewById(R.id.project_name);

        if(num < gridLayout.getRowCount() *gridLayout.getColumnCount()  &&  edit.getText().toString() != "") {
            Log.d("MainActivity", gridLayout.getColumnCount() + " || " + gridLayout.getRowCount());
            Button b = new Button(this);
            b.setText(edit.getText().toString());
            b.setMinWidth(gridLayout.getWidth() / gridLayout.getRowCount());
            b.setMinHeight(gridLayout.getHeight() / gridLayout.getColumnCount());
            b.setBackgroundColor(Color.WHITE);
            b.setTextColor(Color.parseColor("#529e90"));
            num++;
            Log.d("MainActivity", b.toString());
            gridLayout.addView(b);
            edit.setText("");
            ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
        findViewById(R.id.namingView).setVisibility(View.GONE);
    }

    public void showDialog(View v) {
        View d = findViewById(R.id.namingView);
        d.setVisibility(View.VISIBLE);
    }

    public void hideDialog(View v){
        View d = findViewById(R.id.namingView);
        d.setVisibility(View.GONE);
    }

}
