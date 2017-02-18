package edu.up.cs371.resop18.shogi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/*
 * @author Ryan Fredrickson
 * @author Chase Des Laurier
 */

public class shogiPiece {
    private boolean shortHand = false; //Denotes whether to use short hand (i.e. single character+english) --- Leave Here
    private boolean useEnglish = false; //Denotes whether to use english letter --- Leave Here
    private boolean player = true; //Denotes if the piece belongs to the player

    //Defines variables for user later
    private int x;
    private int y;
    private boolean promoted = false;
    private boolean selected = false;
    private String[] s;

    //Defines the pieces
    private String[][] pieces = {{"王", "將", "王", "King"}, {"飛", "車", "飛", "Rook"}, {"角", "行", "角", "Bishop"},
            {"金", "將", "金", "Gold"}, {"銀", "將", "銀", "Silver"}, {"桂", "馬", "桂", "Knight"},
            {"香", "車", "香", "Lance"}, {"歩", "兵", "歩", "Pawn"}};

    //Defines the promoted pieces
    private String[][] promotedPieces = {{"", "", "", "King"}, {"龍", "王", "龍", "Rook"}, {"龍", "馬", "馬", "Bishop"},
            {"", "", "", "Gold"}, {"成", "銀", "全", "Silver"}, {"成", "桂", "圭", "Knight"}, {"成", "香", "杏", "Lance"},
            {"と", "金", "と", "Pawn"}};

    public shogiPiece(int initRow, int initCol, String piece){
        this.x = (int)(ShogiGui.topLeftX + initCol * ShogiGui.spaceDim + ShogiGui.spaceDim / 2); //Defines starting row
        this.y = (int)(ShogiGui.topLeftY + initRow * ShogiGui.spaceDim + ShogiGui.spaceDim / 2); //Defines starting col

        //Defines the Piece
        for(String[] aww : pieces){
            if(aww[3].equals(piece)){
                this.s = aww; //Defines the piece
                break; //Breaks loop after correct piece is found and defined
            }
        }
    }

    //Promotes a piece
    public void promotePiece(boolean p){
        this.promoted = p;
    }

    //Sets whether piece belongs to the player
    public void setPlayer(boolean p){
        this.player = p;
    }

    //Returns true if the piece belongs to the player or false if the piece belongs to the opponent
    public boolean getPlayer(){ return this.player; }

    //Draws piece when called
    public void drawShogiPiece(Canvas canvas) {
        int r = 100; //Size of the piece
        float[] xCords = {x - r / 2, x - r / 4, x, x + r / 4, x + r / 2}; //Defines the x Coordinates for outline
        float[] yCords = {y + r / 2, y - r / 4, y - r / 2, y - r / 4, y + r / 2}; //Defines the y Coordinate for outline

        double n;  //Helps center english words if shortHand = true
        float a = 0; //Does something --- leave this alone

        int font = (r/4); //Sets font
        int start = 5; //A sort of padding

        int xText = x - font / 2;
        int yText1 = y - r / 4 + font;
        int yText2 = y - r / 4 + 2 * font;

        //Deals with weather the piece is promoted and changes the characters accordingly
        for (String[] aww : promotedPieces) {
            if (s[3].equals(aww[3]) && promoted && !s[3].equals("King") && !s[3].equals("Gold")) {
                s = aww;
            }
        }

        //Creates the various paints used
        Paint shogiPaint = new Paint();
        Paint shogiOut = new Paint();
        Paint shogiText = new Paint();

        //Changes background color of piece if it is selected
        if(selected){
            shogiPaint.setColor(Color.WHITE);
        }else{
            shogiPaint.setColor(0xFFD2B48C);
        }

        //Sets info for outline of Shogi Piece
        shogiOut.setColor(Color.BLACK);
        shogiOut.setStyle(Paint.Style.STROKE);
        shogiOut.setStrokeWidth(4);

        //Draws shape of the Shogi Piece
        Path shogiPiece = new Path();
        shogiPiece.reset();
        shogiPiece.moveTo(xCords[0], yCords[0]);
        shogiPiece.lineTo(xCords[1], yCords[1]);
        shogiPiece.lineTo(xCords[2], yCords[2]);
        shogiPiece.lineTo(xCords[3], yCords[3]);
        shogiPiece.lineTo(xCords[4], yCords[4]);
        shogiPiece.lineTo(xCords[0], yCords[0]);
        shogiPiece.lineTo(xCords[1], yCords[1]);

        //Rotates piece if it does not belong to user
        if(!player){
            canvas.rotate(180f, x, y);
        }

        //Draws Shogi Piece and Outline
        canvas.drawPath(shogiPiece, shogiPaint);
        canvas.drawPath(shogiPiece, shogiOut);

        //Changes color of text on the piece if it is promoted
        if(promoted && !s[3].equals("King") && !s[3].equals("Gold")){
            shogiText.setColor(Color.RED);
        }else{
            shogiText.setColor(Color.BLACK);
        }

        //Draws text of piece
        if(useEnglish){ //Uses english letter if 'useEnglish' is true
            shogiText.setTextSize((float)5*font/2);

            //Helps Center Stuff
            if(s[3].substring(0, 1).equals("L")){
                canvas.drawText(s[3].substring(0, 1), xText - start, (yText1+yText2)/2 + 3*start, shogiText);
            }else{
                canvas.drawText(s[3].substring(0, 1), xText - 2*start, (yText1+yText2)/2 + 3*start, shogiText);
            }

            //Draws Underline under P for pawn
            if(s[3].equals("Pawn")) {
                int y1 = (yText1+yText2)/2 + 5*start;

                shogiText.setStrokeWidth(5);
                canvas.drawLine(xText-2*start, y1, xText+6*start, y1, shogiText);
            }
        }else{ //Draws Japanese characters with English Translation
            if(!shortHand) {
                shogiText.setTextSize((float)3*font/2);
                canvas.drawText(s[0], xText - 8, yText1, shogiText);
                canvas.drawText(s[1], xText - 8, yText2 + 16, shogiText);
            }else{
                if(promoted){
                    a = 3;
                }

                //Draws Japanese
                shogiText.setTextSize((float)2*font);
                canvas.drawText(s[2], xText - 3*start + a, (yText1+yText2)/2 + 6, shogiText);


                //Weird math for centering English Translation
                if(s[3].equals("Silver")){
                    n = 2;
                }else if(s[3].equals("Pawn")){
                    n = 3;
                }else if(s[3].equals("Knight")){
                    n = 2.8;
                }else if(s[3].equals("Bishop")){
                    n = 2.5;
                }else if(s[3].equals("Rook")){
                    n = 2;
                }else if(s[3].equals("Lance")){
                    n = 2.5;
                }else if(s[3].equals("King") || s[3].equals("Gold")){
                    n = 2.5;
                }else{
                    n = 3;
                }

                //Draws English Translation
                shogiText.setTextSize(3*font/4);
                canvas.drawText(s[3], xText - (int)(n*s[3].length()), yText2 + (yText2 - yText1) - 6, shogiText);
            }
        }

        //Resets canvas if piece has been rotated
        if(!player){
            canvas.rotate(180f, x, y);
        }
    }

    //draws circles in the spaces where a selected piece can move
    public void drawMoves(Canvas C){
        //canvas.drawCircle(topLeftX + spaceDim / 2, topLeftY + spaceDim / 2, spaceDim / 3, CirclePaint);
        Paint CirclePaint = new Paint();
        CirclePaint.setColor(Color.BLUE);

        float xPos, yPos;

        //find piece type, draw circles in spaces where this type of piece can move
        if(s[3].equals("Silver")){
            //top left
            xPos = x - ShogiGui.spaceDim;
            yPos = y - ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            //top middle
            xPos = x;
            yPos = y - ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            //top right
            xPos = x + ShogiGui.spaceDim;
            yPos = y - ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            //bottom left
            xPos = x - ShogiGui.spaceDim;
            yPos = y + ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            //bottom right
            xPos = x + ShogiGui.spaceDim;
            yPos = y + ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);
        }else if(s[3].equals("Pawn")){
            xPos = x;
            yPos = y - ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);
        }else if(s[3].equals("Knight")){
            xPos = x - ShogiGui.spaceDim;
            yPos = y - 2*ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            xPos = x + ShogiGui.spaceDim;
            yPos = y - 2*ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);
        }else if(s[3].equals("Bishop")){
            //Diagonal top left
            xPos = x - ShogiGui.spaceDim;
            yPos = y - ShogiGui.spaceDim;
            while(yPos > ShogiGui.topLeftY && xPos > ShogiGui.topLeftX){
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim/3, CirclePaint);
                yPos -= ShogiGui.spaceDim;
                xPos -= ShogiGui.spaceDim;
            }

            //Diagonal top right
            xPos = x + ShogiGui.spaceDim;
            yPos = y - ShogiGui.spaceDim;
            while(yPos > ShogiGui.topLeftY && xPos < 15*ShogiGui.topLeftX){ //Leave -- 15 is weird but it works
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim/3, CirclePaint);
                yPos -= ShogiGui.spaceDim;
                xPos += ShogiGui.spaceDim;
            }

            //Diagonal bottom left
            xPos = x - ShogiGui.spaceDim;
            yPos = y + ShogiGui.spaceDim;
            while(yPos < 9*ShogiGui.topLeftY && xPos > ShogiGui.topLeftX){
                if(yPos == ShogiGui.topLeftY + 9*ShogiGui.spaceDim){
                    break;
                }
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim/3, CirclePaint);
                yPos += ShogiGui.spaceDim;
                xPos -= ShogiGui.spaceDim;
            }

            //Diagonal bottom right
            xPos = x + ShogiGui.spaceDim;
            yPos = y + ShogiGui.spaceDim;
            while(yPos < 9*ShogiGui.topLeftY && xPos < 15*ShogiGui.topLeftX){ //Leave -- 15 is weird but it works
                if(yPos + ShogiGui.spaceDim == ShogiGui.topLeftY + 7*ShogiGui.spaceDim){
                    break;
                }
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim/3, CirclePaint);
                yPos += ShogiGui.spaceDim;
                xPos += ShogiGui.spaceDim;
            }
        }else if(s[3].equals("Rook")){
            xPos = x;
            yPos = y - ShogiGui.spaceDim;

            //draw circles in every space above the piece
            while(yPos > ShogiGui.topLeftY){
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim/3, CirclePaint);
                yPos -= ShogiGui.spaceDim;
            }

            //draw circles in every space below the piece
            yPos = y + ShogiGui.spaceDim;
            while(yPos < ShogiGui.topLeftY + 9*ShogiGui.spaceDim){
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim/3, CirclePaint);
                yPos += ShogiGui.spaceDim;
            }

            //draw circles in every space left the piece
            yPos = y;
            xPos = x - ShogiGui.spaceDim;
            while(xPos > ShogiGui.topLeftX){
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim/3, CirclePaint);
                xPos -= ShogiGui.spaceDim;
            }

            //draw circles in every space right the piece
            xPos = x + ShogiGui.spaceDim;
            while(xPos < ShogiGui.topLeftX + 9*ShogiGui.spaceDim){
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim/3, CirclePaint);
                xPos += ShogiGui.spaceDim;
            }
        }else if(s[3].equals("Lance")){
            xPos = x;
            yPos = y - ShogiGui.spaceDim;

            //draw circles in every space above the piece
            while(yPos > ShogiGui.topLeftY){
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim/3, CirclePaint);
                yPos -= ShogiGui.spaceDim;
            }
        }else if(s[3].equals("King") || s[3].equals("Gold")){
            //top left
            xPos = x - ShogiGui.spaceDim;
            yPos = y - ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            //top middle
            xPos = x;
            yPos = y - ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            //top right
            xPos = x + ShogiGui.spaceDim;
            yPos = y - ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            //left middle
            xPos = x - ShogiGui.spaceDim;
            yPos = y;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            //right middle
            xPos = x + ShogiGui.spaceDim;
            yPos = y;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            //bottom middle
            xPos = x;
            yPos = y + ShogiGui.spaceDim;
            C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

            if(s[3].equals("King")){
                //bottom left
                xPos = x - ShogiGui.spaceDim;
                yPos = y + ShogiGui.spaceDim;
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);

                //bottom right
                xPos = x + ShogiGui.spaceDim;
                yPos = y + ShogiGui.spaceDim;
                C.drawCircle(xPos, yPos, ShogiGui.spaceDim / 3, CirclePaint);
            }
        }
    }

    //Doesn't do shit right now
    public void setPos(int row, int col) {
        this.x = (int)(ShogiGui.topLeftX + col * ShogiGui.spaceDim + ShogiGui.spaceDim / 2);
        this.y = (int)(ShogiGui.topLeftY + row * ShogiGui.spaceDim + ShogiGui.spaceDim / 2);
    }

    //Gets the piece name for being redrawn
    public String getPiece(){
        return s[3];
    }

    //Returns if the piece is selected
    public boolean getSelected()
    {
        return this.selected;
    }

    //ALlows for if piece is selected to be changed
    public void setSelected(boolean value)
    {
        this.selected = value;
    }
}