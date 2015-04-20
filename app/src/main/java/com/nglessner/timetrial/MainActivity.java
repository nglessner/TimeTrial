package com.nglessner.timetrial;

import android.app.Activity;
import android.media.MediaPlayer;
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
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=openOrCreateDatabase("TimeTrialDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS `rider` (`riderId` INTEGER,`riderNumber` INTEGER NOT NULL UNIQUE, `firstName` VARCHAR NOT NULL, `lastName` VARCHAR NOT NULL, PRIMARY KEY(riderId));");
        db.execSQL("CREATE TABLE IF NOT EXISTS race(raceId INTEGER, riderId INTEGER NOT NULL, eventDate VARCHAR NOT NULL, time VARCHAR NOT NULL, PRIMARY KEY(raceId));");
        //db.execSQL("CREATE TABLE IF NOT EXISTS course(courseId INT, distance REAL, courseName VARCHAR, PRIMARY KEY(courseId));");
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

    public void beep(View view) {
        mediaPlayer = MediaPlayer.create(this, R.raw.startgate);
        mediaPlayer.start();
    }
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    };

    @Override
    protected void onDestroy() {
        if(null!=mediaPlayer){
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
