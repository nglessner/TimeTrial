package com.nglessner.timetrial;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.content.Context;
import android.widget.Button;

public class MainActivity extends ActionBarActivity{

    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=openOrCreateDatabase("TimeTrialDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS `rider` (`riderId` INTEGER,`riderNumber` INTEGER NOT NULL UNIQUE, `firstName` INTEGER NOT NULL, `lastName` INTEGER NOT NULL, PRIMARY KEY(riderId));");
        db.execSQL("CREATE TABLE IF NOT EXISTS race(raceId INT, riderId INT, courseId INT, eventDate VARCHAR, time VARCHAR, PRIMARY KEY(raceId));");
        db.execSQL("CREATE TABLE IF NOT EXISTS course(courseId INT, distance REAL, courseName VARCHAR, PRIMARY KEY(courseId));");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void startAction(View view) {
        Button b = (Button) view;
        CharSequence x = b.getText();

        Intent intent = new Intent();

        if (x.equals("Manage Riders")) {
            intent = new Intent(this, ManageRidersActivity.class);
        }
        else{
            intent = new Intent(this, NewEventActivity.class);
        }

        startActivity(intent);
    }
}
