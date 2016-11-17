package com.example.facebook.insighting;

import android.*;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class IC extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Dialog_Tag.EditNameDialogListener {
    private int mStackLevel = 0;
    public Project p;
    String id_project;
    public String formattedDate;
    DatabaseController db;
    ArrayList<String> selTags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        selTags = new ArrayList<String>();
        selTags.add("All");

        Bundle extras = getIntent().getExtras();
        db = new DatabaseController(getBaseContext());
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE);
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
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            else if( checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            else {
                addInsightCard(selTags);
            }
            ed.putBoolean("firstTime",false);
            ed.apply();
        }else{
            addInsightCard(selTags);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED && requestCode == 2){

            addInsightCard(selTags);
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
    //ArrayList<String> tags
    public void addInsightCard(ArrayList<String> tags ){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);
        Cursor c = db.getInsightCardsFromProjects(id_project);
        c.moveToFirst();
        Log.v("ALL",tags.contains("All")+": "+ tags);
        if(!tags.contains("All") ) {
            while (!c.isAfterLast()) {
                String[] c_tags = c.getString(c.getColumnIndex("tags")).split(" ");
                for (String i : c_tags) {
                    if (tags.contains(i)) {
                        inflater.inflate(R.layout.cardview, gridlayout);
                        View cv = gridlayout.getChildAt(gridlayout.getChildCount() - 1);
                        cv.setId(Integer.parseInt(c.getString(c.getColumnIndex("id_card"))));
                        ((TextView) cv.findViewById(R.id.card_name)).setText(c.getString(0));

                        if (!c.getString(c.getColumnIndex("url")).isEmpty() && !c.getString(c.getColumnIndex("url")).equals("#url")) {
                            Uri r = Uri.parse(c.getString(c.getColumnIndex("url")));
                            ImageView img = (ImageView) cv.findViewById(R.id.card_photo);
                            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            img.setImageURI(r);
                            img.setColorFilter(Color.argb(50, 0, 0, 0));
                        }
                        break;
                    }
                }
                c.moveToNext();
            }
        }
        else if (tags.contains("All") || tags.size() == 0){

            while (!c.isAfterLast()) {
                inflater.inflate(R.layout.cardview, gridlayout);
                View cv = gridlayout.getChildAt(gridlayout.getChildCount() - 1);
                cv.setId(Integer.parseInt(c.getString(c.getColumnIndex("id_card"))));
                ((TextView) cv.findViewById(R.id.card_name)).setText(c.getString(0));
                //TextView rada = (TextView)cv.findViewById(R.id.card_description);

                if (!c.getString(c.getColumnIndex("url")).isEmpty() && !c.getString(c.getColumnIndex("url")).equals("#url")) {
                    Uri r = Uri.parse(c.getString(c.getColumnIndex("url")));
                    ImageView img = (ImageView) cv.findViewById(R.id.card_photo);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setImageURI(r);
                    img.setColorFilter(Color.argb(50, 0, 0, 0));
                }
                c.moveToNext();
            }
        }
    }
    @Override
    public void onFinishEditDialog(ArrayList<String> inputText){
    }

    public void open(View v){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i = new Intent(this,ProjectActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        menu.add("Rada");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            showDialog();
        } else if (id == R.id.nav_gallery) {

        }  else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = Dialog_Tag.newInstance(id_project);
        newFragment.show(ft, "dialog");

        ((Dialog_Tag)newFragment).listener = new Dialog_Tag.EditNameDialogListener() {
            @Override
            public void onFinishEditDialog(ArrayList<String> inputText) {
                selTags = inputText;
                ViewGroup gridlayout = (ViewGroup) findViewById(R.id.grid);
                gridlayout.removeAllViews();
                addInsightCard(selTags);
            }
        };
    }




    public void editCreateInsightCard(View v)
    {
        Intent i = new Intent(this, EditInsightCard.class);
        i.putExtra("new",true);
        i.putExtra("id_project",id_project);
        i.putExtra("id_card", v.getId()+"");
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
