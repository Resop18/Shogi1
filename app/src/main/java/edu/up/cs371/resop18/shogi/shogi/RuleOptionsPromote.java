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

public class RuleOptionsPromote extends SurfaceView {

    public RuleOptionsPromote(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        //canvas.drawColor(Color.WHITE);


        Bitmap myImageBMP2 = BitmapFactory.decodeResource(getResources(), R.drawable.p222);
        //canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(myImageBMP2, 10, 10, null);



    }

}
