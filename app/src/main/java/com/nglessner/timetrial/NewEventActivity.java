package com.nglessner.timetrial;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.io.IOException;


public class NewEventActivity extends ActionBarActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    public void beep(View view) {
        Context context = this.getApplicationContext();

        Uri mp3 = Uri.parse("android.resource://"
                + context.getPackageName() + "/raw/startgate");

        try {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.startgate);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e) {
        }
    }
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    };

}
