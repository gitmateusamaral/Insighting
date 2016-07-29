package com.example.facebook.insighting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsightCardActivity extends AppCompatActivity {

    public Project p;
    public String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight_card);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            p = new Project(extras.getString("project"));
            ((TextView) (findViewById(R.id.title_project))).setText(p.projectName);
        }
        addInsightCard();
    }

    public void addInsightCard(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);

        for(int i = 0; i < p.cards.size(); i++) {
            inflater.inflate(R.layout.cardview, gridlayout);
            View cv = gridlayout.getChildAt(i);
            ((TextView)cv.findViewById(R.id.card_name)).setText(p.cards.get(i).title);
            ((TextView) cv.findViewById(R.id.card_description)).setText("Last Edit: "+p.cards.get(i).date);
        }
    }

    public void editCreateInsightCard(View v)
    {
        Intent i = new Intent(this, EditInsightCard.class);
        i.putExtra("new",true);
        i.putExtra("project", p.AsString());
        i.putExtra("ic_id", p.cards.size());
        //Log.d("InsightCardActivity",p.AsString());
        startActivity(i);
    }

    public void onBackPressed()
    {
        Intent i = new Intent(this,ProjectActivity.class);
        startActivity(i);
    }

    public void editInsightCard(View v){
        Intent i = new Intent(this, EditInsightCard.class);
        i.putExtra("project", p.AsString());
        i.putExtra("ic_id", p.cards.size() - 1);
        TextView t =(TextView)v.findViewById(R.id.card_name);
        if(p.cards.size() != 0) {
            for (int n = 0; n < p.cards.size(); n++) {
                if (p.cards.get(n).title.equals(t.getText().toString())) {
                    i.putExtra("ic_id", n);
                    break;
                }
            }
        }
        startActivity(i);
    }
}