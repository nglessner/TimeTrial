package com.nglessner.timetrial;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EventActivity extends ActionBarActivity {

    private static final String FORMAT = "%02d:%02d:%02d";

    private TextView timerValue;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        timerValue = (TextView) findViewById(R.id.countDownText);
        Button startButton = (Button) findViewById(R.id.countDownStart);
        Button stopButton = (Button) findViewById(R.id.countDownStop);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

/*    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int minutes = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + minutes + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };*/

    public void countDownStart(View view) {
        //startTime = SystemClock.uptimeMillis();
        //customHandler.postDelayed(updateTimerThread, 0);

        timerValue=(TextView)findViewById(R.id.countDownText);

        new CountDownTimer(15000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                timerValue.setText("seconds remaining: " + millisUntilFinished / 1000);

/*                timerValue.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)),
                                millisUntilFinished - TimeUnit.SECONDS.toSeconds(millisUntilFinished)));*/
            }

            public void onFinish() {
                timerValue.setText("done!");
            }
        }.start();
    }

    public void countDownStop(View view) {
        //timeSwapBuff += timeInMilliseconds;
        //customHandler.removeCallbacks(updateTimerThread);
    }
}
