package edu.up.cs371.resop18.shogi;

/**
 * @author Ryan Fredrickson
 */

public class ShogiGameState {
    shogiPiece Pieces[][] = new shogiPiece[9][9];
    shogiPiece playerCaptured[] = new shogiPiece[19];
    shogiPiece opponentCaptured[] = new shogiPiece[19];
    boolean isPlayersTurn = true;

    private int row, col; //for iterating and managing Pieces

    /*
     * Constructor
     * Places all the pieces on the Pieces array
     * No parameters
     */
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


    /*
     * Deep Copy Constructor
     */
    public ShogiGameState(ShogiGameState orig){
        for(row = 0; row < 9; row++){
            for(col = 0; col < 9; col++){
                this.Pieces[row][col] = orig.Pieces[row][col];
            }
        }

        for(row = 0; row < 19; row++){
            this.playerCaptured[row] = orig.playerCaptured[row];
            this.opponentCaptured[row] = orig.opponentCaptured[row];
        }

        this.isPlayersTurn = orig.isPlayersTurn;
    }


    /*
     * Sets Player Turn
     */
    public void setPlayerTurn(boolean playerTurn){
        this.isPlayersTurn = playerTurn;
    }

    //Gets Player Turn
    public boolean getPlayerTurn(){
        return isPlayersTurn;
    }
}
