package com.example.facebook.insighting;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsightCardActivity extends AppCompatActivity {

    public Project p;
    String id_project;
    public String formattedDate;
    DatabaseController db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight_card);
        Bundle extras = getIntent().getExtras();
        db = new DatabaseController(getBaseContext());
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (extras != null) {
            id_project = extras.getString("project");
            Cursor c = db.getSpecificProject(id_project);
            Button b = (Button) findViewById(R.id.title_project);
            //Log.i("MainAcitvity",id_project);
            if (c.moveToFirst()) {
                b.setText(c.getString(1));
            }
            c.close();
        }

        SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        Boolean ft = sp.getBoolean("firstTime",true);
        if(Build.VERSION.SDK_INT >= 23) {
            if(ft){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            else if( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            else {
                addInsightCard();
            }
                ed.putBoolean("firstTime",false);
                ed.apply();
        }else{
            addInsightCard();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED && requestCode == 2){
            addInsightCard();
        }
    }

    /*public void addTagToView(View v,String text){
        Log.d("TAGTEXT", text);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArrayList<String> catList = new ArrayList<String>(Arrays.asList(text.split(" ")));
        TextView ll = (TextView) v.findViewById(R.id.card_description);

        for (String tt:catList) {
            ll.setText(ll.getText().toString()+tt+" ");
        }
    }*/

    public void addInsightCard(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);
        Cursor c = db.getInsightCardsFromProjects(id_project);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            inflater.inflate(R.layout.cardview, gridlayout);
            View cv = gridlayout.getChildAt(gridlayout.getChildCount() - 1);
            cv.setId(Integer.parseInt(c.getString(c.getColumnIndex("id_card"))));
            ((TextView)cv.findViewById(R.id.card_name)).setText(c.getString(0));
            //TextView rada = (TextView)cv.findViewById(R.id.card_description);

            if (!c.getString(c.getColumnIndex("url")).isEmpty() && !c.getString(c.getColumnIndex("url")).equals("#url")) {
                Uri r = Uri.parse(c.getString(c.getColumnIndex("url")));
                ImageView img = (ImageView) cv.findViewById(R.id.card_photo);
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                img.setImageURI(r);
                img.setColorFilter(Color.argb(50,0,0,0));
                Log.d("TAGTEXT", c.getString(3));
            }

            c.moveToNext();
        }
    }

    public void editCreateInsightCard(View v)
    {
        Intent i = new Intent(this, EditInsightCard.class);
        i.putExtra("new",true);
        i.putExtra("id_project",id_project);
        i.putExtra("id_card", v.getId()+"");
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this,ProjectActivity.class);
        startActivity(i);
    }

    public void editInsightCard(View v){
        Intent i = new Intent(this, EditInsightCard.class);
        i.putExtra("new",false);
        i.putExtra("id_card", v.getId()+"");
        i.putExtra("id_project",id_project);
        startActivity(i);
    }
}