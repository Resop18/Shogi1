package edu.up.cs371.resop18.shogi.shogi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceView;

import edu.up.cs371.resop18.shogi.R;

/**
 * Created by Admin on 4/21/2017.
 */

public class RuleOptions extends SurfaceView {

    public RuleOptions(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        Bitmap myImageBMP = BitmapFactory.decodeResource(getResources(), R.drawable.p111);
        //canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(myImageBMP, 10, 10, null);


        //Bitmap myImageBMP2 =
                //BitmapFactory.decodeResource(getResources(), R.drawable.p2);

        //canvas.drawBitmap(myImageBMP2, 10, 300, null);



    }

}
