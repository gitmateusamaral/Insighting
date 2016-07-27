package com.example.facebook.insighting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InsightCardActivity extends AppCompatActivity {

    public Project p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight_card);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            p = new Project(extras.getString("project"));
            ((TextView) (findViewById(R.id.title_project))).setText(p.projectName);
        }
    }

    public void editInsightCard(View v)
    {
        Intent i = new Intent(this, EditInsightCard.class);
        p.addInsightCard("t","d");
        i.putExtra("project", p.AsString());
        i.putExtra("ic_id", p.cards.size()-1);
        //Log.d("InsightCardActivity",p.AsString());
        //EditText t = (EditText)v;
       /* if(p.cards.size() != 0) {
            for (int n = 0; n < p.cards.size(); n++) {
                if (p.cards.get(n).title.equals(t.getText().toString())) {
                    i.putExtra("ic_id", n);
                    break;
                }
            }
        }*/
        startActivity(i);
    }

}
