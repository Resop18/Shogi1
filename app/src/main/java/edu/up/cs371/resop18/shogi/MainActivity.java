package edu.up.cs371.resop18.shogi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
 * @author Javier Resop
 */

public class MainActivity extends AppCompatActivity {


    Button play;
    Button options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);


        Button play = (Button)findViewById(R.id.Play);
        play.setOnClickListener(new playButt());

        Button options = (Button)findViewById(R.id.Options1);
        options.setOnClickListener(new optionsButt());

    }

    public class playButt implements View.OnClickListener{

        public void onClick(View v){
            startActivity(new Intent(MainActivity.this, Game.class));
        }

    }

    public class optionsButt implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, Options.class));
        }
    }
}
