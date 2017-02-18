package edu.up.cs371.resop18.shogi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.SurfaceView;

/*
 * @author Javier Resop
 */

public class MenuGui extends SurfaceView {

    Typeface yozakura;

    public MenuGui(Context context, AttributeSet attrs){
        super(context, attrs);
        setWillNotDraw(false);
        yozakura = Typeface.createFromAsset(context.getAssets(), "fonts/yozakura.ttf");
    }


    @Override
    public void onDraw(Canvas canvas){
        Bitmap back =
                BitmapFactory.decodeResource(getResources(), R.drawable.stocksamurai);
        canvas.drawColor(Color.BLACK);
        back = back.createScaledBitmap(back, 1550, 1900, false);
        canvas.drawBitmap(back, 0, 0, null);

        //draws the menu screen --Works best on API 24
        Paint menustuff = new Paint();
        menustuff.setColor(Color.RED);
        menustuff.setTypeface(yozakura);
        menustuff.setTextSize(150f);
        Paint title = new Paint();
        title.setColor(0xFFF41201);
        title.setTypeface(yozakura);
        title.setTextSize(200f);
        canvas.drawText("SHOGI", 600, 100, title);
        canvas.drawText("PLAY", 100, 1500, menustuff);
        canvas.drawText("OPTIONS", 1100, 1500, menustuff);

    }
}
