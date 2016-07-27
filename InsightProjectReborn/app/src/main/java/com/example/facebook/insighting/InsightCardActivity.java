package com.example.facebook.insighting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InsightCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight_card);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Project p = new Project(extras.getString("project"));
            //((TextView) (findViewById(R.id.title_project))).setText(p.projectName);
        }
    }

    public void editInsightCard(View v)
    {
        Intent i = new Intent(this, EditInsightCard.class);
        startActivity(i);
        Log.v("InsightCardActivity", "Ediiiit °°°");
    }

}
