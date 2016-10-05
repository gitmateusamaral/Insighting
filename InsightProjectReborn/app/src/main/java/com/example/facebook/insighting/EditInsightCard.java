package com.example.facebook.insighting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class EditInsightCard extends AppCompatActivity {

    public String ic_p;
    public String ic_c;
    boolean isNew;
    int count = 0;
    public ArrayList<String> categories;
    DatabaseController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_insight_card);
        EditText title = (EditText) findViewById(R.id.ic_textTitle);
        EditText des = (EditText) findViewById(R.id.ic_textDescription);
        categories = new ArrayList<String>();
        Intent i = getIntent();
        db = new DatabaseController(getBaseContext());
        if(i != null){
             Bundle extras = i.getExtras();
            if(extras != null){
                ic_p = extras.getString("id_project");
                ic_c = extras.getString("id_card");
                isNew = extras.getBoolean("new");
                if(!isNew) {
                    Cursor c =  db.getInsightCardFromId(ic_c);
                    c.moveToFirst();
                    ArrayList<String> catList = new ArrayList<String>();
                    while (!c.isAfterLast()) {
                        title.setText(c.getString(c.getColumnIndex("name")));
                        des.setText(c.getString(c.getColumnIndex("description")));
                        catList = new ArrayList<String>(Arrays.asList(c.getString(c.getColumnIndex("tags")).split(";")));
                        c.moveToNext();
                    }
                    c.close();
                    for (String cat:catList ) {
                        addTagView(cat);
                    }
                }
            }
        }
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.ttf");
        title.setTypeface(face);
        des.setTypeface(face);
    }

    public void addImage(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_FIRST_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_FIRST_USER && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Log.v("ImageLog",selectedImage.toString());
            ImageView imageView = (ImageView) findViewById(R.id.card_img);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageURI(selectedImage);
        }
    }

    public void exitInsightCard(View v){
        String title = ((EditText)findViewById(R.id.ic_textTitle)).getText().toString();
        String description = ((EditText)findViewById(R.id.ic_textDescription)).getText().toString();
        Intent i = new Intent(this, EditInsightCard.class);
        Toast.makeText(this, "Cancelled Insight Card", Toast.LENGTH_LONG).show();
        startActivity(i);
    }

    public void onBackPressed() {
        String title = ((EditText) findViewById(R.id.ic_textTitle)).getText().toString();
        String description = ((EditText) findViewById(R.id.ic_textDescription)).getText().toString();
        handlerUpdate(title,description);
    }


    public void onPause(){
        super.onPause();
    }

    public void handlerUpdate(String title, String description){
        if ((!title.isEmpty() || !description.isEmpty())) {
            if (isNew) {
                Intent i = new Intent(this, InsightCardActivity.class);
                i.putExtra("project", ic_p);
                String cat_s = TextUtils.join(";",categories);
                String r = db.insertDataInsightCard(title, description, "#url", cat_s, ic_p);
                Toast.makeText(this, "Insight Card created", Toast.LENGTH_LONG).show();
                startActivity(i);
            } else {
                Intent i = new Intent(this, InsightCardActivity.class);
                i.putExtra("project", ic_p);
                String cat_s = TextUtils.join(";",categories);
                String r = db.updateInsightCard(title,description,cat_s,"#url",ic_c);
                Toast.makeText(this, "Insight Card saved", Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        } else {
            if (count == 1) {
                Intent i = new Intent(this, InsightCardActivity.class);
                i.putExtra("project",ic_p);
                startActivity(i);
            }else {
                count++;
                Toast.makeText(this, "Title or description are empty. Press again to delete Insight Card.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void createTag(View v){
        addTagView(((EditText) findViewById(R.id.tag_text)).getText().toString());
        categories.add(((EditText) findViewById(R.id.tag_text)).getText().toString());
        ((EditText)findViewById(R.id.tag_text)).setText("");
    }
    public void addTagView(String text){
        GridLayout tagDisposal = (GridLayout)findViewById(R.id.tag_disposal);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tag, tagDisposal);
        ((TextView)(tagDisposal.getChildAt(tagDisposal.getChildCount()-1)).findViewById(R.id.tag_name)).setText(text);
    }

}
