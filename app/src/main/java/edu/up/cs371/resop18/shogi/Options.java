package edu.up.cs371.resop18.shogi;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

/*
 * @author Javier Resop
 */

public class Options extends AppCompatActivity {

    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int)(width * .85), (int)(height * .85));

        final Spinner sounds = (Spinner)findViewById(R.id.Easter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_stuff, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sounds.setAdapter(adapter);

        initControls();

        /**
         * External Citation
         *      Date: 17 February 2017
         *      Problem: Couldn't figure out how to add sound
         *      Resource: http://stackoverflow.com/questions/18459122/play-
         *          sound-on-button-click-android
         *      Solution: Used some example code along with my own
         */

        //creates various sound effects for when a button is pressed
        final MediaPlayer timallen = MediaPlayer.create(this, R.raw.allenarrogh);
        final MediaPlayer carlin = MediaPlayer.create(this, R.raw.carlinlogic);
        final MediaPlayer ironman = MediaPlayer.create(this, R.raw.ironman);

        Button playMe = (Button)findViewById(R.id.EasterEgg);
        playMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sounds.getSelectedItem().toString().contentEquals("swoosh"))
                    {timallen.start();}
                if(sounds.getSelectedItem().toString().contentEquals("bazzinga"))
                    {carlin.start();}
                if(sounds.getSelectedItem().toString().contentEquals("waffles"))
                    {ironman.start();}
            }
        });
    }


    /**
    External Citation
        Date: 17 February 2017
        Problem: getting a seekbar to associate with the devices' volume control
        Resource: http://stackoverflow.com/questions/10134338/using-seekbar-to-control-volume-in
                    -android
        Solution: I used the example code from there.
     */
    /**
     * makes it so that a seekbar controls the volume
     */
    private void initControls()
    {
        try
        {
            volumeSeekbar = (SeekBar)findViewById(R.id.vol);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
