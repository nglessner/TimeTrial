package com.nglessner.timetrial;

import android.app.Notification;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog.Builder;



public class AddNewRiderActivity extends ActionBarActivity {

    EditText firstNameEditText,lastNameEditText,riderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_rider);

        firstNameEditText=(EditText)findViewById(R.id.firstNameEditText);
        lastNameEditText=(EditText)findViewById(R.id.lastNameEditText);
        riderNumber=(EditText)findViewById(R.id.riderNumberEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_rider, menu);
        return true;
    }

    public void addRider(View view) {
        // Checking empty fields

        if(firstNameEditText.getText().toString().trim().length()==0||
                lastNameEditText.getText().toString().trim().length()==0||
                riderNumber.getText().toString().trim().length()==0)
        {
            showMessage("Error", "Please enter all values");
            return;
        }

        // Inserting record
        try {
            MainActivity.db.execSQL("INSERT INTO rider VALUES((SELECT Max(riderId)+ 1 from rider),'"
                    + riderNumber.getText() + "','" + firstNameEditText.getText()
                    + "','" + lastNameEditText.getText() + "');");

        }catch (Exception e)
        {
            showMessage("Error", "There was an error adding this rider, please try again.");
        }

        clearText();
        Intent intent = new Intent(this, ManageRidersActivity.class);
        startActivity(intent);
    }

    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText()
    {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        riderNumber.setText("");
        firstNameEditText.requestFocus();
    }

}
