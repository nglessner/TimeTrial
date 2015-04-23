package com.nglessner.timetrial;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
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
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < RaceList.size(); i++) {
            TimeUtils.formatDuration(RaceList.get(i).Time, sb);
            String listItemString = sb.toString();
            raceStringList.add(listItemString);
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
            Cursor c = MainActivity.db.rawQuery("Select * from Race WHERE RiderId = " + riderId, null);

            while (c.moveToNext()) {
                Race newRace = new Race();
                newRace.RaceId = c.getInt(0);
                newRace.RiderId = c.getInt(1);
                newRace.StartTime = c.getInt(2);
                newRace.EndTime = c.getInt(3);
                newRace.Time = c.getInt(4);
                newRace.MPH = c.getInt(5);
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
