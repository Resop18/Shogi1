package edu.up.cs371.resop18.shogi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * @author Javier Resop
 */

/**
 * The actual game
 */
public class Game extends AppCompatActivity {


    Button undo;
    Button options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates and sets the Options Button
        options = (Button)findViewById(R.id.Options);
        options.setOnClickListener(new optionsButt());

        //creates and sets the Undo button
        undo = (Button)findViewById(R.id.Undo);
        undo.setOnClickListener(new undoButt());
    }

    /**
     * Makes a onClickListener for the undo button
     * makes a Vibrator to vibrate when undo is pressed (Currently it is the
     * Mortal Kombat theme)
     */
    public class undoButt implements View.OnClickListener {

    Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        @Override
        public void onClick(View v) {

         vb.vibrate(new long[]{0,200,100,200,275,425,100,200,100,200,275,425,100,75,25,75,125,75,25,75,125,100,100}, -1);
        }
    }

    /**
     * Makes a onClickListener for Options
     * opens up to a new activity
     */
    public class optionsButt implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            startActivity(new Intent(Game.this, Options.class));
        }
    }

}
