package com.example.facebook.insighting;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by teste12 on 11/11/2016.
 */

public  class Dialog_Tag extends DialogFragment {
    String id_project;
    ArrayList<String> selTags;
    public EditNameDialogListener listener;
    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static DialogFragment newInstance(String project) {
        Dialog_Tag f = new Dialog_Tag();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("project", project);
        f.setArguments(args);
        return f;
    }

    public interface EditNameDialogListener {
        void onFinishEditDialog(ArrayList<String> inputText);
    }

    public void exitDialog(){
        listener.onFinishEditDialog(selTags);
        this.dismiss();
    }

    public void onCheckBoxClick(View v){
        CheckBox c = (CheckBox)v;
        if(c.isChecked()){
            if(!selTags.contains(c.getText().toString()))
                selTags.add(c.getText().toString());
        }else{
            selTags.remove(c.getText().toString());
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selTags = new ArrayList<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.dialog_tag, container, false);
        id_project = getArguments().getString("project");
        Log.e("XABLAU",id_project);
        DatabaseController dbc = new DatabaseController(this.getActivity());
        Cursor ic = dbc.getInsightCardsFromProjects(id_project);
        ic.moveToFirst();
        ArrayList<String> t = new ArrayList<String>();
        (v.findViewById(R.id.checkBoxAll)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onCheckBoxClick(v);
            }
        });
        ViewGroup g = (ViewGroup) v.findViewById(R.id.checkBoxList);
        while(!ic.isAfterLast()){
            String[] allt = ic.getString(3).split(" ");
            for (String i: allt) {
                Log.v("TADA",i);
                if(!t.contains(i) && !i.isEmpty()) {
                    t.add(i);
                    CheckBox ck = new CheckBox(this.getActivity());
                    ck.setText(i);
                    ck.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                            onCheckBoxClick(v);
                        }
                    });
                    g.addView(ck);

                }
            }
            ic.moveToNext();
        }

        Button dialogButton = (Button)v.findViewById(R.id.exitDialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("PIRU","RADA2");
                exitDialog();
            }
        });
        return v;
    }
}