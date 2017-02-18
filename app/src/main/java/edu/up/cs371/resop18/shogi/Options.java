package edu.up.cs371.resop18.shogi;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

/**
 * Created by Javier on 14-Feb-17.
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

        final MediaPlayer timallen = MediaPlayer.create(this, R.raw.allenarrogh);
        final MediaPlayer carlin = MediaPlayer.create(this, R.raw.carlinlogic);
        final MediaPlayer ironman = MediaPlayer.create(this, R.raw.ironman);
        Button playMe = (Button)findViewById(R.id.EasterEgg);
        playMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sounds.getSelectedItem().toString().contentEquals("swoosh")) {timallen.start();}
                if(sounds.getSelectedItem().toString().contentEquals("bazzinga")){carlin.start();}
                if(sounds.getSelectedItem().toString().contentEquals("waffles")){ironman.start();}
            }
        });
    }




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
