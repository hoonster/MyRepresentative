package com.example.hoon.myrepresentative;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hoon on 9/18/2015.
 */
public class ProfileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);



        Intent intent = getIntent();
        String st = intent.getStringExtra("iName");
        String st1 = intent.getStringExtra("iParty");
        String st2 = intent.getStringExtra("iPhone");
        String st3 = intent.getStringExtra("iOffice");
        String st4 = intent.getStringExtra("iLink");
        String st5 = intent.getStringExtra("iState");

        TextView txtView = (TextView) findViewById(R.id.name);
        txtView.setText(st);
        TextView txtView1 = (TextView) findViewById(R.id.party);
        txtView1.setText(st1);
        TextView txtView2 = (TextView) findViewById(R.id.phone);
        txtView2.setText(st2);
        TextView txtView3 = (TextView) findViewById(R.id.office);
        txtView3.setText(st3);
        TextView txtView4 = (TextView) findViewById(R.id.link);
        txtView4.setText(st4);
        TextView txtView5 = (TextView) findViewById(R.id.state);
        txtView5.setText(st5);

    }
}
