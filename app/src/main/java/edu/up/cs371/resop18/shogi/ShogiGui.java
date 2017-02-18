package edu.up.cs371.resop18.shogi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

/*
 * @author Javier Resop
 * @author Ryan Fredrickson
 * @author Chase Des Laurier
 * @author Jake Nguyen
 */

public class ShogiGui extends SurfaceView implements View.OnTouchListener {
    shogiPiece Pieces[][] = new shogiPiece[9][9]; //

    public static final float spaceDim = 150; //150 is height/width of rows & cols
    public static final float backBoardTopLeftX = 20; //20 is good
    public static final float backBoardTopLeftY = 125; //100 is good
    public static final float topLeftX = backBoardTopLeftX + spaceDim / 2; //95 is good
    public static final float topLeftY = backBoardTopLeftY + spaceDim; //350 is good
    private boolean pieceIsSelected = false;

    private int i, j; //for iterating and managing the Pieces array
    private int row, col; //for iterating and managing the Pieces array

    public ShogiGui(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);


        //Defines Pieces to be drawn
        shogiPiece aPiece;
        String w = "";

        row = 6;
        for(col = 0; col < 9; col++){
            aPiece = new shogiPiece(row, col, "Pawn");
            Pieces[row][col] = aPiece;
        }

        col = 1;
        aPiece = new shogiPiece(row+1, col, "Bishop");
        Pieces[row+1][col] = aPiece;

        col = 7;
        aPiece = new shogiPiece(row+1, col, "Rook");
        Pieces[row+1][col] = aPiece;

        for(col = 0; col < 9; col++){
            if(col == 0 || col == 8){
                w = "Lance";
            }else if(col == 1 || col == 7){
                w = "Knight";
            }else if(col == 2 || col == 6){
                w = "Silver";
            }else if(col == 3 || col == 5){
                w = "Gold";
            }else{
                w = "King";
            }

            aPiece = new shogiPiece(row+2, col, w);
            Pieces[row+2][col] = aPiece;
        }
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

        //Instantiate touch listener
        this.setOnTouchListener(this);

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
        Paint text2 = text;
        square.setStrokeWidth(5f);

        //background for the main display
        Bitmap background =
                BitmapFactory.decodeResource(getResources(), R.drawable.bam222);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(background, 0, 0, null);

        //the board and player sides
        canvas.drawRect(230.5f, 100f, 1500f,250f, captured);
        canvas.drawCircle(230.5f, 275f, 180f, opponent);
        canvas.drawText("Opponent", 130f, 200f, text2);
        canvas.drawRect(50f, 1650f, 1319f,1800f, captured);
        canvas.drawCircle(1319f, 1625f, 180f,player);
        text.setUnderlineText(true);
        canvas.drawText("Player",1250f,1725f,text);
        canvas.drawRect(50f, 250f, 1500f, 1650f, shogiboard);
        canvas.drawRect(50f, 250f, 1500f, 1650f, square);
        canvas.drawCircle(1210, 1710, 20, text);

        //draw vertical lines; start xy is top point, end xy is bottom point
        for(i = 0; i < 10; i++) {
            canvas.drawLine(topLeftX + i * spaceDim, topLeftY, topLeftX + i * spaceDim, topLeftY + 9 * spaceDim, square);
            canvas.drawLine(topLeftX, topLeftY + i * spaceDim, topLeftX + 9 * spaceDim, topLeftY+ i * spaceDim, square);
        }


        //This draws the pieces from the array
        for(i = 0; i < 9; i++) {
            for(j = 0; j < 9; j++){
                if(Pieces[i][j] != null){
                    Pieces[i][j].drawShogiPiece(canvas);

                    if(Pieces[i][j].getSelected())
                        Pieces[i][j].drawMoves(canvas);
                }
            }
        }
    }

    //This checks if a piece has been selected and/or moved
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //Don't do anything when dragging or lifting touch
        if(event.getActionMasked() != MotionEvent.ACTION_DOWN) {
            return false;
        }


        //This determine space that was tapped
        for(row = 0; row < 9; row++) {
            if(event.getY() < topLeftY + (row + 1) * spaceDim) {
                for (col = 0; col < 9; col++) {
                    if(event.getX() < topLeftX + (col + 1) * spaceDim){
                        break;
                    }
                }
                break;
            }
        }


        //If you tap if a position when a piece is selected it will move the piece there
        if(Pieces[row][col] == null) {
            if(pieceIsSelected){
                for(int i = 0; i < 9; i++){
                    for(int j = 0; j < 9; j++){
                        if(Pieces[i][j] != null){
                            if(Pieces[i][j].getSelected()){
                                Pieces[row][col] = new shogiPiece(row, col, Pieces[i][j].getPiece());
                                if(!Pieces[i][j].getPlayer()){
                                    Pieces[row][col].setPlayer(false);
                                }
                                Pieces[i][j] = null;
                            }
                        }
                    }
                }
                Pieces[row][col].setSelected(false);
                pieceIsSelected = false;
            }else {
                return false;
            }
        }else{
            //This deals with selected and deselecting pieces
            if(Pieces[row][col].getSelected()){
                //This deselects a piece if it is selected
                Pieces[row][col].setSelected(false);
                pieceIsSelected = false;
            }else{
                //This will select the piece if it is not selected
                for(int i = 0; i < 9; i++){
                    for(int j = 0; j < 9; j++){
                        if(Pieces[i][j] != null){
                            if(Pieces[i][j].getSelected()){
                                Pieces[i][j].setSelected(false);
                            }
                        }
                    }
                }

                Pieces[row][col].setSelected(true);
                pieceIsSelected = true;
            }
        }

        //redraw board with pieces updated
        this.invalidate();

        return true;
    }
}