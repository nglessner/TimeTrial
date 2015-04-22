package com.nglessner.timetrial;

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
        db.execSQL("CREATE TABLE IF NOT EXISTS `Rider` (`RiderId` INTEGER,`RiderNumber` INTEGER NOT NULL UNIQUE, `FirstName` VARCHAR NOT NULL, `LastName` VARCHAR NOT NULL, PRIMARY KEY(RiderId));");
        db.execSQL("CREATE TABLE IF NOT EXISTS `Race` (`RaceId`	INTEGER, `RiderId` INTEGER NOT NULL, `EventId` INTEGER, `StartTime` INTEGER, `EndTime` INTEGER,`RaceTime` INTEGER, `RacePace` INTEGER, PRIMARY KEY(RaceId));");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'Event' (`EventId` INTEGER, `Distance` REAL, `CourseName` VARCHAR, PRIMARY KEY(EventId));");
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
