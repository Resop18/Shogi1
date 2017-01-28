package edu.up.cs371.resop18.shogi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by Javier on 28-Jan-17.
 */

public class MenuGui extends SurfaceView {

    public MenuGui(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas){
        Bitmap myImageBMP =
                BitmapFactory.decodeResource(getResources(), R.drawable.samurai);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(myImageBMP, 0, 0, null);

    }
}
