package com.nglessner.timetrial;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.app.AlertDialog.Builder;

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;


public class AddNewRiderActivity extends ActionBarActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText riderNumber;
    private EditText prTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_rider);

        firstNameEditText=(EditText)findViewById(R.id.firstNameEditText);
        lastNameEditText=(EditText)findViewById(R.id.lastNameEditText);
        riderNumber=(EditText)findViewById(R.id.riderNumberEditText);
		prTime=(EditText)findViewById(R.id.riderPRTimeEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_rider, menu);
        return true;
    }

    public void addRider() {
        // Checking empty fields
        boolean goToManageRiders = true;

        if (firstNameEditText.getText().toString().trim().length() == 0 ||
                lastNameEditText.getText().toString().trim().length() == 0 ||
                riderNumber.getText().toString().trim().length() == 0 ||
                prTime.getText().toString().trim().length() == 0) {
            showMessage("Error", "Please enter all values");
            goToManageRiders = false;
        }
        int minutes = 0;
        int seconds = 0;
        long milliseconds = 0;

        if (goToManageRiders) {
            try {
                StringTokenizer tokens = new StringTokenizer(prTime.getText().toString(), ":");
                if (tokens.countTokens() == 2) {
                    minutes = Integer.parseInt(tokens.nextToken());
                    seconds = Integer.parseInt(tokens.nextToken());
                    milliseconds = TimeUnit.MINUTES.toMillis(minutes);
                    milliseconds += TimeUnit.SECONDS.toMillis(seconds);
                } else {
                    throw new NumberFormatException();
                }
            } catch (Exception e) {
                showMessage("Error", "PR Time entered in an incorrect format. Use MM:SS " + e.toString());
                goToManageRiders = false;
            }
        }

        if (goToManageRiders) {
            // Inserting record
            try {
                Cursor c = MainActivity.db.rawQuery("SELECT MAX(RiderId)+ 1 from Rider", null);
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
