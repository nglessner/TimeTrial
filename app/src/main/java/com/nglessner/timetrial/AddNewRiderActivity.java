package com.nglessner.timetrial;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog.Builder;
import android.widget.NumberPicker;

import java.util.concurrent.TimeUnit;


public class AddNewRiderActivity extends ActionBarActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText riderNumber;
    private NumberPicker minutePicker;
    private NumberPicker secondPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_rider);

        firstNameEditText=(EditText)findViewById(R.id.firstNameEditText);
        lastNameEditText=(EditText)findViewById(R.id.lastNameEditText);
        riderNumber=(EditText)findViewById(R.id.riderNumberEditText);
        minutePicker=(NumberPicker)findViewById(R.id.minutePicker);
        secondPicker=(NumberPicker)findViewById(R.id.secondPicker);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);
        minutePicker.setValue(32);
        secondPicker.setValue(0);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_rider, menu);
        return true;
    }

    public void addRider(View view) {
        // Checking empty fields
        boolean goToManageRiders = true;

        if (firstNameEditText.getText().toString().trim().length() == 0 ||
                lastNameEditText.getText().toString().trim().length() == 0 ||
                riderNumber.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            goToManageRiders = false;
        }

        long milliseconds = 0;
        milliseconds = TimeUnit.MINUTES.toMillis(minutePicker.getValue());
        milliseconds += TimeUnit.SECONDS.toMillis(secondPicker.getValue());

        if (goToManageRiders) {
            // Inserting record
            try {
                Cursor c = MainActivity.db.rawQuery("SELECT MAX(RiderId)+ 1 from Rider", null);
                c.moveToFirst();
                Integer riderId = c.getInt(0);
                c.close();

                MainActivity.db.execSQL("INSERT INTO Rider VALUES(" + riderId + ","
                        + riderNumber.getText() + ",'" + firstNameEditText.getText()
                        + "','" + lastNameEditText.getText() + "');");

                MainActivity.db.execSQL("INSERT INTO Race VALUES((SELECT MAX(RaceId)+ 1 from Race),"
                        + riderId + "," + null + "," + null + "," + null + "," + milliseconds
                        + "," + null + ");");

            } catch (Exception e) {
                showMessage("Error", "There was an error adding this rider, please try again. " + e.toString());
                goToManageRiders = false;
            }
        }

        if (goToManageRiders) {
            clearText();
            Intent intent = new Intent(this, ManageRidersActivity.class);
            startActivity(intent);
        }
    }

    void showMessage(String title, String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    void clearText()
    {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        riderNumber.setText("");
        firstNameEditText.requestFocus();
    }

}
