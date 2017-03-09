package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.infoMsg.GameState;

/**
 * @author Ryan Fredrickson
 */

public class ShogiGameState extends GameState {
    shogiPiece[][] Pieces = new shogiPiece[9][9]; //Keeps track of pieces on the board
    private shogiPiece playerCaptured[] = new shogiPiece[19]; //Keeps track of player's captured pieces
    private shogiPiece opponentCaptured[] = new shogiPiece[19]; //Keeps track of opponent's captured pieces
    boolean isPlayersTurn = true; //Boolean for player's turns

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

            aPiece = new shogiPiece(row - 2, col, w);
            aPiece.setPlayer(false);
            Pieces[row - 2][col] = aPiece;
        }
    }

    /*
     * Deep Copy Constructor
     */
    public ShogiGameState(ShogiGameState orig){
        for(row = 0; row < 9; row++){
            for(col = 0; col < 9; col++){
                this.Pieces[row][col] = new shogiPiece(row, col, orig.Pieces[row][col].getPiece());
            }
        }

        for(row = 0; row < 19; row++){
            this.playerCaptured[row] = new shogiPiece(row, col, orig.playerCaptured[row].getPiece());
            this.opponentCaptured[row] = new shogiPiece(row, col, orig.opponentCaptured[row].getPiece());
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
        shogiPiece Piece = moveAction.getMovedPiece();
        int row = moveAction.getNewRow();
        int col = moveAction.getNewCol();


        //check if the new location of the moved piece is empty
        //if not, do not move the piece
        if(Pieces[row][col] != null)
            return;


        //determine if the new location is legal for this piece
        //if it is, move the piece
        if(Piece.legalMove(this, row, col))
            Pieces[row][col] = new shogiPiece(row, col, Piece.getPiece());
            Pieces[Piece.getRow()][Piece.getCol()] = null;

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
    public shogiPiece[] getPlayerCaptured(){
        return playerCaptured;
    }

    //Get Captured Opponent Pieces
    public shogiPiece[] getOpponentCaptured(){
        return opponentCaptured;
    }

    //Get Current State of Board
    public shogiPiece[][] getCurrentBoard(){
        return Pieces;
    }

    //Checks to see if the pieces are in the allowed location(s) and promotes the piece
    public void resolvePromoteAction(ShogiPromoteAction action){
        shogiPiece piece = action.getPromotedPiece();
        if(piece.getPlayer() && piece.getRow() <= 2 && piece.getRow() >= 0){
            piece.promotePiece(true);
        }else if(!piece.getPlayer() && piece.getRow() >= 6 && piece.getRow() < 9){
            piece.promotePiece(true);
        }
    }

    //Places the selected captured piece back onto the board
    public void resolvePlaceAction(ShogiPlaceAction action){
        Pieces[action.getRow()][action.getCol()] = action.getPlacedPiece();
    }
}
