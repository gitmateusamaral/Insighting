package com.example.facebook.insighting;

import android.content.Context;
import android.content.Intent;
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

public class SelectInsightActivity extends AppCompatActivity {
    public Project project;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_insightcard);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Project p = new Project(extras.getString("project"));
            Log.d("SelectInsightActivity",extras.getString("project").toString());
            ((TextView) (findViewById(R.id.title_project))).setText(p.projectName);
        }
        ((findViewById(R.id.addInsightCard))).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("SelectInsightActivity","hhhe");
            }
        });
    }

    public void editInsightCard(View v)
    {
        Log.d("SelectInsightActivity","hhhe");
        Intent i = new Intent(this, EditInsightCard.class);
        startActivity(i);
        //if(v.getId() == R.id.add_ic){startActivity(i);}
    }


}
