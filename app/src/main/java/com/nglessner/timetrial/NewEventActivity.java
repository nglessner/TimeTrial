package com.nglessner.timetrial;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class NewEventActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
		
		//create new event
		//get event id
		//select riders tied to this event (through the race table)
			//Select * from rider r join race ra on ra.riderId = r.riderId where
			//ra.eventId = @eventId
		//display list of those riders		

        ArrayList<String> riderStringList = new ArrayList<>();

        riderStringList.add("Rider1 (#1)");
        riderStringList.add("Rider2 (#2)");
        riderStringList.add("Rider3 (#3)");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.listitem,
                riderStringList);

        ListView lv = (ListView) findViewById(R.id.EventRiderList);
        lv.setAdapter(arrayAdapter);
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
	
	void addRiderToEventDialog(View view) {
		// show list of riders
		// on selecting a rider... call different method
	}
	
	void addRiderToEvent(View view) {		
		// on selecting a rider... 
		// add new race row with this rider id and eventId
		// 
	}
}
