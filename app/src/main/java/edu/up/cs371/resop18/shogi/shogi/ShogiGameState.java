package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.infoMsg.GameState;

/**
 * @author Ryan Fredrickson
 */

public class ShogiGameState extends GameState {
    ShogiPiece[][] pieces = new ShogiPiece[9][9]; //Keeps track of pieces on the board
    private ShogiPiece playerCaptured[] = new ShogiPiece[19]; //Keeps track of player's captured pieces
    private ShogiPiece opponentCaptured[] = new ShogiPiece[19]; //Keeps track of opponent's captured pieces
    boolean isPlayersTurn = true; //Boolean for player's turns

    private int row, col; //for iterating and managing Pieces

    /*
     * Constructor
     * Places all the pieces on the Pieces array
     * No parameters
     */
    public ShogiGameState(){
        //Defines Pieces to be drawn
        ShogiPiece aPiece;
        String w = "";

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
    }

    /*
     * Deep Copy Constructor
     */
    public ShogiGameState(ShogiGameState orig){
        for(row = 0; row < 9; row++){
            for(col = 0; col < 9; col++){
                this.pieces[row][col] = new ShogiPiece(row, col, orig.pieces[row][col].getPiece());
            }
        }

        for(row = 0; row < 19; row++){
            this.playerCaptured[row] = new ShogiPiece(row, col, orig.playerCaptured[row].getPiece());
            this.opponentCaptured[row] = new ShogiPiece(row, col, orig.opponentCaptured[row].getPiece());
        }

        this.isPlayersTurn = orig.isPlayersTurn;
    }


    //Sets Player Turn
    /*
     * carries out an action of moving a piece to a new location
     * on the board
     *
     * @param moveAction
     *      the action whose info is to be used to move a piece
     *      and update the game state
     *
     */
    public void resolveMoveAction(ShogiMoveAction moveAction) {

        //variables for simplification
        ShogiPiece Piece = moveAction.getMovedPiece();
        int row = moveAction.getNewRow();
        int col = moveAction.getNewCol();


        //check if the new location of the moved piece is empty
        //if not, do not move the piece
        if(pieces[row][col] != null)
            return;


        //determine if the new location is legal for this piece
        //if it is, move the piece
        if(Piece.legalMove(this, row, col))
            pieces[row][col] = new ShogiPiece(row, col, Piece.getPiece());
            pieces[Piece.getRow()][Piece.getCol()] = null;

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

    //Gets Captured Player Pieces
    public ShogiPiece[] getPlayerCaptured(){
        return playerCaptured;
    }

    //Get Captured Opponent Pieces
    public ShogiPiece[] getOpponentCaptured(){
        return opponentCaptured;
    }

    //Get Current State of Board
    public ShogiPiece[][] getCurrentBoard(){
        return pieces;
    }
}
