package edu.up.cs371.resop18.shogi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Javier on 14-Feb-17.
 */

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int)(width * .98), (int)(height * .98));

    }
}
