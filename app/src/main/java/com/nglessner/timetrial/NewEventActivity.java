package com.nglessner.timetrial;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class NewEventActivity extends ActionBarActivity {

    private ArrayList<Rider> RiderList;
    private ArrayList<Rider> AllRidersList;
    private int EventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        Cursor c = MainActivity.db.rawQuery("SELECT MAX(EventId)+ 1 from Event", null);
        c.moveToFirst();
        EventId = c.getInt(0);
        c.close();

        MainActivity.db.execSQL("INSERT INTO Event VALUES(" + EventId + ","
                + "10.85" + ",'" + "New City Time Trial" + "');");

        showRiderList();
    }

    private void showRiderList()
    {
        this.populateRiderList(EventId);

        ArrayList<String> riderStringList = new ArrayList<>();

        for (int i = 0; i < RiderList.size(); i++) {
            riderStringList.add(RiderList.get(i).toString());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.listitem,
                riderStringList);

        ListView lv = (ListView) findViewById(R.id.EventRiderList);
        lv.setAdapter(arrayAdapter);
    }

    private void populateRiderList(int eventId) {
        if (RiderList == null || RiderList.size() == 0) {
            RiderList = new ArrayList<>();
            Cursor c = MainActivity.db.rawQuery("Select * from rider r join race ra on ra.riderId = r.riderId where ra.eventId = "
                    + eventId + " ORDER BY ra.RaceTime DESC", null);

            while (c.moveToNext()) {
                Rider newRider = new Rider();
                newRider.RiderId = c.getInt(0);
                newRider.RiderNumber = c.getInt(1);
                newRider.FirstName = c.getString(2);
                newRider.LastName = c.getString(3);
                RiderList.add(newRider);
            }
            c.close();
        }
    }

    private void populateAllRidersList() {
        if (AllRidersList == null || AllRidersList.size() == 0) {
            AllRidersList = new ArrayList<>();
            Cursor c = MainActivity.db.rawQuery("Select * from rider r", null);

            while (c.moveToNext()) {
                Rider newRider = new Rider();
                newRider.RiderId = c.getInt(0);
                newRider.RiderNumber = c.getInt(1);
                newRider.FirstName = c.getString(2);
                newRider.LastName = c.getString(3);
                AllRidersList.add(newRider);
            }
            c.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    public void startEvent(View view) {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }
	
	void addRiderToEvent(int riderId) {
        Cursor c = MainActivity.db.rawQuery("SELECT MAX(RaceId)+ 1 from Race", null);
        c.moveToFirst();
        int raceId = c.getInt(0);
        c.close();

        //RaceId`	INTEGER, `RiderId` INTEGER NOT NULL, `EventId` INTEGER, `StartTime` INTEGER, `EndTime` INTEGER,`RaceTime` INTEGER
        MainActivity.db.execSQL("INSERT INTO Race VALUES(" + raceId + "," + riderId + ","
                + EventId + "," + null + "," + null + "," + null + ");");

        showRiderList();
	}

    public void addRiderToEventDialog(View view) {
        // show list of riders
        populateAllRidersList();

        final Dialog d = new Dialog(NewEventActivity.this);
        d.setTitle("Add Rider to Event");
        d.setContentView(R.layout.add_rider_dialog);
        final ListView riderList = (ListView) d.findViewById(R.id.addRiderDialogList);

        ArrayList<String> riderStringList = new ArrayList<>();

        for (int i = 0; i < AllRidersList.size(); i++) {
            riderStringList.add(AllRidersList.get(i).toString());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.listitem,
                riderStringList);

        riderList.setAdapter(arrayAdapter);

        riderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Rider o = AllRidersList.get(i);
                addRiderToEvent(o.RiderId);
                d.dismiss();
            }
        });

        d.show();
        // on selecting a rider... call different method
    }
}
