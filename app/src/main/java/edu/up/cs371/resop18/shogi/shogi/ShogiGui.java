package edu.up.cs371.resop18.shogi.shogi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import edu.up.cs371.resop18.shogi.R;

/*
 * @author Javier Resop
 * @author Ryan Fredrickson
 * @author Chase Des Laurier
 * @author Jake Nguyen
 */

public class ShogiGui extends SurfaceView{
    public ShogiPiece pieces[][];

    public static final float spaceDim = 150; //150 is height/width of rows & cols
    public static final float backBoardTopLeftX = 20; //20 is good
    public static final float backBoardTopLeftY = 125; //100 is good
    public static final float topLeftX = backBoardTopLeftX + spaceDim / 2; //95 is good
    public static final float topLeftY = backBoardTopLeftY + spaceDim; //350 is good
    public boolean pieceIsSelected = false;
    private Bitmap background; //the bamboo background; made global so it wont have to be redrawn every onDraw
    private Bitmap board; // make  a board

    private int i, j; //for iterating and managing the pieces array
    private int row, col; //for iterating and managing pieces

    public ShogiGui(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        //set up bamboo background
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bam222);

        board = BitmapFactory.decodeResource(getResources(), R.drawable.shougi_board);
        board = Bitmap.createScaledBitmap(board, 1450, 1400, false); //1450 1400
        pieces = new ShogiGameState().pieces;
    }

    @Override
    public void onDraw(Canvas canvas) {
        //Gets screen size
        /*WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;*/



        //creates the various paints used in the board
        Paint shogiboard = new Paint();
        Paint opponent = new Paint();
        Paint player = new Paint();
        Paint captured = new Paint();
        Paint square = new Paint();
        Paint text = new Paint();

        //creates the colors used
        shogiboard.setColor(0xFFB69B4C);
        opponent.setColor(0xFFFF0F0F);
        player.setColor(0xFF85bff8);
        captured.setColor(0xFFC7AC5D);
        square.setColor(0xFF000000);
        text.setColor(0xFF000000);

        //misc settings for paints
        square.setStyle(Paint.Style.STROKE);
        text.setTextSize(48f);
        //Paint text2 = text;
        square.setStrokeWidth(5f);

        //background for the main display
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(background, 0, 0, null);

        //the board and player sides
        canvas.drawRect(230.5f, 100f, 1500f,250f, captured);
        canvas.drawCircle(230.5f, 275f, 180f, opponent);
        canvas.drawText("Opponent", 130f, 200f, text);
        canvas.drawRect(50f, 1650f, 1319f,1800f, captured);
        canvas.drawCircle(1319f, 1625f, 180f,player);
        text.setUnderlineText(true);
        canvas.drawText("Player",1250f,1725f,text);
        //canvas.drawRect(50f, 250f, 1500f, 1650f, shogiboard);
        //canvas.drawRect(50f, 250f, 1500f, 1650f, square);
        canvas.drawCircle(1210, 1710, 20, text);

        canvas.drawBitmap(board, 50f, 250f, null);


        //draw vertical lines; start xy is top point, end xy is bottom point
        for(i = 0; i < 10; i++) {
            canvas.drawLine(topLeftX + i * spaceDim, topLeftY, topLeftX + i * spaceDim, topLeftY + 9 * spaceDim, square);
            canvas.drawLine(topLeftX, topLeftY + i * spaceDim, topLeftX + 9 * spaceDim, topLeftY+ i * spaceDim, square);
        }


        //This draws the pieces from the array
        for(i = 0; i < 9; i++) {
            for(j = 0; j < 9; j++){
                if(pieces[i][j] != null){
                    pieces[i][j].drawShogiPiece(canvas);

                    if(pieces[i][j].getSelected())
                        pieces[i][j].drawMoves(canvas, pieces);
                }
            }
        }
    }

    public boolean isPieceIsSelected(){return this.pieceIsSelected;}
}