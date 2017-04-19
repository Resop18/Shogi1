package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.infoMsg.GameState;

/**
 * @author Ryan Fredrickson
 */

public class ShogiGameState extends GameState {
    ShogiPiece[][] pieces; //Keeps track of pieces on the board
    private ShogiPiece playerCaptured[] = new ShogiPiece[19]; //Keeps track of player's captured pieces
    private ShogiPiece opponentCaptured[] = new ShogiPiece[19]; //Keeps track of opponent's captured pieces
    private int currentPlayersTurn;
    private int p1 = 0;
    private int p2 = 1;//Boolean for player's turns
    private boolean[] playerHasKing = {true, true};
    
    private int row, col; //for iterating and managing Pieces

    /*
     * Constructor
     * Places all the pieces on the Pieces array
     * No parameters
     */
    public ShogiGameState(){
        //Defines Pieces to be drawn/
        ShogiPiece aPiece;
        String w = "";

        pieces = new ShogiPiece[10][9];

        row = 6;
        for(col = 0; col < 9; col++){
            aPiece = new ShogiPiece(row, col, "Pawn");
            pieces[row][col] = aPiece;
        }

        col = 1;
        aPiece = new ShogiPiece(row+1, col, "Bishop");
        pieces[row+1][col] = aPiece;

        col = 7;
        aPiece = new ShogiPiece(row+1, col, "Rook");
        pieces[row+1][col] = aPiece;

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

            aPiece = new ShogiPiece(row+2, col, w);
            pieces[row+2][col] = aPiece;
        }

        row = 2;
        for(col = 0; col < 9; col++){
            aPiece = new ShogiPiece(row, col, "Pawn");
            aPiece.setPlayer(false);
            pieces[row][col] = aPiece;
        }

        col = 1;
        aPiece = new ShogiPiece(row-1, col, "Rook");
        aPiece.setPlayer(false);
        pieces[row-1][col] = aPiece;

        col = 7;
        aPiece = new ShogiPiece(row-1, col, "Bishop");
        aPiece.setPlayer(false);
        pieces[row-1][col] = aPiece;

        for(col = 0; col < 9; col++) {
            if (col == 0 || col == 8) {
                w = "Lance";
            } else if (col == 1 || col == 7) {
                w = "Knight";
            } else if (col == 2 || col == 6) {
                w = "Silver";
            } else if (col == 3 || col == 5) {
                w = "Gold";
            } else {
                w = "King";
            }

            aPiece = new ShogiPiece(row - 2, col, w);
            aPiece.setPlayer(false);
            pieces[row - 2][col] = aPiece;
        }
       /* for(row = 0; row <10; row++){
            for(col = 0; col < 9; col++){
                if(pieces[row][col] != null){

                }else{
                    pieces[row][col] = null;
                }
            }
        }*/
    }

    /*
     * Deep Copy Constructor
     */
    public ShogiGameState(ShogiGameState orig){
        pieces = new ShogiPiece[10][9];
        for(row = 0; row < 10; row++){
            for(col = 0; col < 9; col++){
                if(orig.pieces[row][col] != null){
                    this.pieces[row][col] = new ShogiPiece(row, col, orig.pieces[row][col].getPiece());
                    this.pieces[row][col].setPlayer(orig.pieces[row][col].getPlayer());
                    this.pieces[row][col].promotePiece(orig.pieces[row][col].getPromoted());
                    this.pieces[row][col].setSelected(orig.pieces[row][col].getSelected());
                }
            }
        }

        for(row = 0; row < 19; row++){
            if(this.playerCaptured[row] != null) {
                this.playerCaptured[row] = new ShogiPiece(row, col, orig.playerCaptured[row].getPiece());
            }
            if(this.opponentCaptured[row] != null) {
                this.opponentCaptured[row] = new ShogiPiece(row, col, orig.opponentCaptured[row].getPiece());
            }
        }
        this.currentPlayersTurn = orig.currentPlayersTurn;
    }


    /*
     * Sets Player Turn
     */
    public void setPlayerTurn(int playerTurn){
        this.currentPlayersTurn = playerTurn;
    }

    //Gets Player Turn
    public int getPlayerTurn(){
        return currentPlayersTurn;
    }

    //Gets Captured Player Pieces
    public ShogiPiece[] getPlayerCaptured(){
        return playerCaptured;
    }

    public ShogiPiece getPlayerCaptured(int index){
        return playerCaptured[index];
    }

    //Get Captured Opponent Pieces
    public ShogiPiece[] getOpponentCaptured(){
        return opponentCaptured;
    }

    public ShogiPiece getOpponentCaptured(int index){
        return opponentCaptured[index];
    }

    //Get Current State of Board
    public ShogiPiece[][] getCurrentBoard(){
        return pieces;
    }

    public void setCurrentBoard(ShogiPiece board[][]){
        this.pieces = board;
    }

    public void setP1Captured(ShogiPiece captured, int index)
    {
        playerCaptured[index] = captured;
    }

    public void setP2Captured(ShogiPiece captured, int index) {
        opponentCaptured[index] = captured;
    }

    public boolean getPlayer1HasKing(int player)
    {
        return playerHasKing[player];
    }

    public void setPlayerHasKing(int player)
    {
        this.playerHasKing[player] = false;
    }
}
