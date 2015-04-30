package com.nglessner.timetrial;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class RaceResultsActivity extends ActionBarActivity {

    private List<Race> RaceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_results);

        ListView lv = (ListView) findViewById(R.id.raceResultsListView);

        Intent intent = getIntent();
        int riderId = intent.getIntExtra(getString(R.string.RiderIdMessage), 0);
        populateRaceList(riderId);
        loadListView(lv);

    }

    private void loadListView(ListView lv)
    {
        ArrayList<String> raceStringList = new ArrayList<>();

        for (int i = 0; i < RaceList.size(); i++) {
            String result = "";
            long ms = RaceList.get(i).Time;
            if (i == 0)
            {
                result = "PR: ";
            }
            result = result + String.valueOf(new DateFormat().format("mm:ss", new Date(ms)));
            double h = ((double)ms) /3600000;
            double speed = 10.85/h;
            result += " " + String.format("%.2f", speed) + "MPH";

            raceStringList.add(result);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.listitem,
                raceStringList);

        lv.setAdapter(arrayAdapter);
    }

    private void populateRaceList(int riderId) {
        if (RaceList == null || RaceList.size() == 0) {
            RaceList = new ArrayList<Race>();
            Cursor c = MainActivity.db.rawQuery("Select * from Race WHERE RiderId = " + riderId + " ORDER BY RaceTime DESC", null);

            while (c.moveToNext()) {
                Race newRace = new Race();
                newRace.RaceId = c.getInt(0);
                newRace.RiderId = c.getInt(1);
                newRace.EventId = c.getInt(2);
                newRace.StartTime = c.getInt(3);
                newRace.EndTime = c.getInt(4);
                newRace.Time = c.getInt(5);
                newRace.MPH = c.getInt(6);
                RaceList.add(newRace);
            }
            c.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_race_results, menu);
        return true;
    }

}
