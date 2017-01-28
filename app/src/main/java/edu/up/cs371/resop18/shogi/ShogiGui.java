package edu.up.cs371.resop18.shogi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by Javier on 25-Jan-17.
 */

public class ShogiGui extends SurfaceView {

    public ShogiGui(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        Paint shogiboard = new Paint();
        Paint opponent = new Paint();
        Paint player = new Paint();
        Paint captured = new Paint();
        Paint square = new Paint();
        Paint text = new Paint();
        shogiboard.setColor(0xFFB69B4C);
        opponent.setColor(0xFFFF0F0F);
        player.setColor(0xFF0F0FFF);
        captured.setColor(0xFFC7AC5D);
        square.setColor(0xFF000000);
        text.setColor(0xFF000000);
        square.setStyle(Paint.Style.STROKE);
        text.setTextSize(48f);
        Bitmap myImageBMP =
                BitmapFactory.decodeResource(getResources(), R.drawable.bam222);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(myImageBMP, 0, 0, null);
        canvas.drawRect(230.5f, 150f, 1500f,250f, captured);
        canvas.drawCircle(230.5f, 275f,180f,opponent);
        canvas.drawText("Opponent",130f,200f,text);
        canvas.drawRect(50f, 1650f, 1319f,1750f, captured);
        canvas.drawCircle(1319f, 1625f,180f,player);
        canvas.drawText("Player",1250f,1725f,text);
        canvas.drawRect(50f, 250f, 1500f, 1650f, shogiboard);
        canvas.drawRect(50f, 250f, 1500f, 1650f, square);
        canvas.drawRect(75f, 275f, 1475f, 1625f, square);
        canvas.drawLine(230.5f, 275f, 230.5f, 1625f, square);
        canvas.drawLine(386f, 275f, 386f, 1625f, square);
        canvas.drawLine(541.5f, 275f, 541.5f, 1625f, square);
        canvas.drawLine(697f, 275f, 697f, 1625f, square);
        canvas.drawLine(852.5f, 275f, 852.5f, 1625f, square);
        canvas.drawLine(1008f, 275f, 1008f, 1625f, square);
        canvas.drawLine(1163.5f, 275f, 1163.5f, 1625f, square);
        canvas.drawLine(1319f, 275f, 1319f, 1625f, square);
        canvas.drawLine(75f, 425f, 1475f, 425f, square);
        canvas.drawLine(75f, 575f, 1475f, 575f, square);
        canvas.drawLine(75f, 725f, 1475f, 725f, square);
        canvas.drawLine(75f, 875f, 1475f, 875f, square);
        canvas.drawLine(75f, 1025f, 1475f, 1025f, square);
        canvas.drawLine(75f, 1175f, 1475f, 1175f, square);
        canvas.drawLine(75f, 1325f, 1475f, 1325f, square);
        canvas.drawLine(75f, 1475f, 1475f, 1475f, square);
        Bitmap jewelGen =
                BitmapFactory.decodeResource(getResources(), R.drawable.jewel);
        canvas.drawBitmap(jewelGen, 697f, 1475f, null);
    }


}
