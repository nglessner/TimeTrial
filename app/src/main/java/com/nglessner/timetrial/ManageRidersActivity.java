package com.nglessner.timetrial;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ManageRidersActivity extends ActionBarActivity {

    private List<Rider> RiderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_riders);
        ListView lv = (ListView) findViewById(R.id.listView);

        loadListView(lv);
        registerForContextMenu(lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_riders, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            populateRiderList();
            menu.setHeaderTitle(RiderList.get(info.position).FirstName + " " + RiderList.get(info.position).LastName);
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = RiderList.get(info.position).FirstName + " " + RiderList.get(info.position).LastName;

        switch (menuItemIndex) {
            case 0:
                break;
            case 1:
                deleteRider(RiderList.get(info.position).RiderId);
                return true;
            case 2:
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(menuItemName);
        builder.setMessage(listItemName);
        builder.show();

        return true;
    }

    public void deleteRider(int riderId) {
        MainActivity.db.delete("Rider", "RiderId=?", new String[]{String.valueOf(riderId)});
        RiderList = null;
        loadListView((ListView) findViewById(R.id.listView));
    }

    public void addNewRider(View view) {
        Intent intent = new Intent(this, AddNewRiderActivity.class);
        startActivity(intent);
    }

    private void loadListView(ListView lv)
    {
        populateRiderList();

        ArrayList<String> riderStringList = new ArrayList<>();

        for (int i = 0; i < RiderList.size(); i++) {
            riderStringList.add(RiderList.get(i).toString());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.listitem,
                riderStringList);

        lv.setAdapter(arrayAdapter);
    }
    private void populateRiderList() {
        if (RiderList == null || RiderList.size() == 0) {
            RiderList = new ArrayList<Rider>();
            Cursor c = MainActivity.db.rawQuery("Select * from Rider", null);

            while (c.moveToNext()) {
                Rider newRider = new Rider();
                newRider.RiderId = c.getInt(0);
                newRider.RiderNumber = c.getInt(1);
                newRider.FirstName = c.getString(2);
                newRider.LastName = c.getString(3);
                RiderList.add(newRider);
            }
        }
    }
}
