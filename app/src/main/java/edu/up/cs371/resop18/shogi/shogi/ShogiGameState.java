package edu.up.cs371.resop18.shogi.shogi;

import android.util.Log;

import java.io.Serializable;

import edu.up.cs371.resop18.shogi.game.infoMsg.GameState;

/**
 * @author Ryan Fredrickson
 * @author Javier Resop
 */

public class ShogiGameState extends GameState implements Serializable{

    ShogiPiece[][] pieces; //Keeps track of pieces on the board
    private ShogiPiece playerCaptured[] = new ShogiPiece[19]; //Keeps track of player's captured pieces
    private ShogiPiece opponentCaptured[] = new ShogiPiece[19]; //Keeps track of opponent's captured pieces
    private int currentPlayersTurn;
    private boolean[] playerHasKing = {true, true};
    boolean pZeroInCheck; //indicates whether the player whose index is 0 is in check
    boolean pOneInCheck; //indicates whether the player whose index is 1 is in check


    private int row, col; //for iterating and managing Pieces

    /**
     * Constructor
     * Places all the pieces on the Pieces array
     * No parameters
     */
    public ShogiGameState(){
        //Defines Pieces to be drawn/
        ShogiPiece aPiece;
        String w = "";

        pieces = new ShogiPiece[11][9];

        row = 7;
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

        row = 3;
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
        /*for(row = 0; row < 10; row++){
            for(col = 0; col < 9; col++){
                if(pieces[row][col] != null){

                }else{
                    pieces[row][col] = null;
                }
            }
        }*/
    }

    /**
     * Deep Copy Constructor
     */
    public ShogiGameState(ShogiGameState orig){
        pieces = new ShogiPiece[11][9];
        for(row = 0; row < pieces.length; row++){
            for(col = 0; col < pieces[row].length; col++){
                if(orig.pieces[row][col] != null){
                    this.pieces[row][col] = new ShogiPiece(row, col, orig.pieces[row][col].getPiece());
                    this.pieces[row][col].setPlayer(orig.pieces[row][col].getPlayer());
                    this.pieces[row][col].promotePiece(orig.pieces[row][col].getPromoted());
                    this.pieces[row][col].setSelected(orig.pieces[row][col].getSelected());
                    this.pieces[row][col].setInCheck(orig.pieces[row][col].getInCheck());
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

    public void setP2Captured(ShogiPiece captured, int index) { opponentCaptured[index] = captured; }

    public boolean getPlayer1HasKing(int player)
    {
        return playerHasKing[player];
    }

    public void setPlayerHasKing(int player)
    {
        this.playerHasKing[player] = false;
    }


    /**
     * used for determining if a given player is in check. see determinePlayerInCheck
     * method below for actually calculating whether a player's king is in check
     *
     * @param idx the index of the player whose status of check we want to get.
     *            MUST BE 0 OR 1, or else problems will occur
     * @return whether the specified player is in check
     */
    public boolean getPlayerInCheck(int idx) {

        if(idx == 0) return pZeroInCheck;
        else return pOneInCheck;

    }


    /**
     * this method changes the status of a player, whether their king is in check.
     * Returns without doing anything if the idx parameter is not 0 or 1.
     *
     * @param idx //the index of the player whose status of check is being changed
     * @param value //the value to change the given player's state to
     */
    public void setPlayerInCheck(int idx, boolean value) {

        if(idx == 0) pZeroInCheck = value;
        else if(idx == 1) pOneInCheck = value;
        else return;
    }


    /**
     * this method determines whether a player's king is in check.
     *
     * @param idx the index of the player whom we want to know is in check
     * @param board the state of the board in which we want to determine
     *              if the specified player is in check
     *
     * @return true if the specified player's king is in check, false if not
     */
    public boolean determinePlayerInCheck(int idx, ShogiPiece[][] board) {

        //necessary variables
        int r = 0, c = 0; //for iterating through the board
        ShogiPiece king = null; //the to-be-found king of the specified player
        boolean thisPlayerPiece; //for determining if the king is the specified player's
        boolean foundKing = false; //for determining whether we found the king yet
        boolean playerInCheck = false;

        //determine which player's piece we should be looking for
        if(idx == 0) thisPlayerPiece = true;
        else thisPlayerPiece = false;

        //go through the board and find the king
        for(r = 1; r < 10; r++) {
            for(c = 0; c < 9; c++) {
                if(board[r][c] != null &&
                        board[r][c].getPiece().equals("King") &&
                        board[r][c].getPlayer() == thisPlayerPiece) {
                    king = board[r][c];
                    foundKing = true;
                    break;
                }
            }
            if(foundKing) break;
        }


        //determine if the king is in check. Either way, update the gamestate
        // so that it reflects this player's status of check
        for(r = 1; r < 10; r++) {
            for(c = 0; c < 9; c++) {
                if (board[r][c] != null &&
                        board[r][c].getPlayer() != thisPlayerPiece &&
                        king != null &&
                        board[r][c].legalMove(board,
                                king.getRow(), king.getCol())) {

                    playerInCheck = true;

                    break;
                }
            }
            if(playerInCheck) break; //don't continue if player is already in check
        }

        setPlayerInCheck(idx, playerInCheck);

        Log.i("ShogiLocalGame", "player " + idx + " in check: " + getPlayerInCheck(idx));

        return playerInCheck;

    }//playerKingInCheck


    /**
     * This method determines if a players based on a location
     *
     * @param idx the index of the player whom we want to know is in check
     * @param board the state of the board in which we want to determine
     *              if the specified player is in check
     * @param row takes row of where the king is
     * @param col take col of where the king is
     *
     * @return true if the specified player's king is in check, false if not
     */
    public boolean determinePlayerInCheck(int idx, ShogiPiece[][] board, int row, int col) {

        //necessary variables
        int r = 0, c = 0; //for iterating through the board
        ShogiPiece king = board[row][col]; //the to-be-found king of the specified player
        boolean thisPlayerPiece; //for determining if the king is the specified player's
        boolean playerInCheck = false;

        //determine which player's piece we should be looking for
        if(idx == 0) thisPlayerPiece = true;
        else thisPlayerPiece = false;


        //determine if the king is in check. Either way, update the gamestate
        // so that it reflects this player's status of check
        for(r = 1; r < 10; r++) {
            for(c = 0; c < 9; c++) {
                if (board[r][c] != null &&
                        board[r][c].getPlayer() != thisPlayerPiece &&
                        king != null &&
                        board[r][c].legalMove(board,
                                king.getRow(), king.getCol())) {

                    playerInCheck = true;

                    break;
                }
            }
            if(playerInCheck) break; //don't continue if player is already in check
        }

        setPlayerInCheck(idx, playerInCheck);

        Log.i("ShogiLocalGame", "player " + idx + " in check: " + getPlayerInCheck(idx));

        return playerInCheck;

    }
}
