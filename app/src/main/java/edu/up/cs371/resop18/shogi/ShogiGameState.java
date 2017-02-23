package edu.up.cs371.resop18.shogi;

/**
 * @author Ryan Fredrickson
 */

public class ShogiGameState {
    shogiPiece Pieces[][] = new shogiPiece[9][9];
    shogiPiece playerCaptured[][] = new shogiPiece[9][9];
    shogiPiece opponentCaptured[][] = new shogiPiece[9][9];

    private int i, j; //for iterating and managing the Pieces array
    private int row, col; //for iterating and managing Pieces

    public ShogiGameState(){
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

        row = 2;
        for(col = 0; col < 9; col++){
            aPiece = new shogiPiece(row, col, "Pawn");
            aPiece.setPlayer(false);
            Pieces[row][col] = aPiece;
        }

        col = 1;
        aPiece = new shogiPiece(row-1, col, "Rook");
        aPiece.setPlayer(false);
        Pieces[row-1][col] = aPiece;

        col = 7;
        aPiece = new shogiPiece(row-1, col, "Bishop");
        aPiece.setPlayer(false);
        Pieces[row-1][col] = aPiece;

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

            aPiece = new shogiPiece(row-2, col, w);
            aPiece.setPlayer(false);
            Pieces[row-2][col] = aPiece;
        }
    }
}
