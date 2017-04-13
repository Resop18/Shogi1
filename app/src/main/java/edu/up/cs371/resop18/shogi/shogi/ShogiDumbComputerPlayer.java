package edu.up.cs371.resop18.shogi.shogi;

import android.util.Log;

import java.util.Random;

import edu.up.cs371.resop18.shogi.game.GameComputerPlayer;
import edu.up.cs371.resop18.shogi.game.infoMsg.GameInfo;

/**
 * @author Ryan Fredrickson
 * @author Javier Resop
 */

public class ShogiDumbComputerPlayer extends GameComputerPlayer {
    private ShogiGameState state; //The Game State
    private ShogiPiece[][] board; //The Board
    private LegalMoves m = new LegalMoves(1); //Sets legal moves to look for opponent moves only
    int row, col, newRow, newCol; //Old Row, Old Col, New Row, New Col
    ShogiPiece piece; //Piece selected to be moved

    /**
     * Constructor for ShogiDumbComputerPlayer
     * Passes through the Computer Player's name
     *
     * @param name
     */
    public ShogiDumbComputerPlayer(String name) {
        super(name);
    }

    /**
     * Recieves ShogiGameState and then runs dumbAI() to send the new actions
     *
     * @param info
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        try {
            if (info instanceof ShogiGameState) {
				this.state = (ShogiGameState) info;

				if(state.getPlayerTurn() == 1){
					Thread.sleep(300);
					dumbAI();
				}
                Log.i("Computer Turn", "Made Move");
            }
        }
        catch (Exception ie) {
        }
    }

    /**
     * Randomly selected an opponent piece and send the actions
     */
    public void dumbAI(){
        ShogiPiece[][] newBoard = state.getCurrentBoard(); //Gets the current gameState
        int[][] possibleMoves;

        while(true){
            row = randInt(0, 8); //Randomly selects a row between 0 and 8
            col = randInt(0, 8); //Randomly selects a col between 0 and 8

            //Checks if the piece in the row and col selected is not null and belongs to the dumb AI
            if(newBoard[row][col] != null && !newBoard[row][col].getPlayer()){
                piece = newBoard[row][col];
                possibleMoves = m.moves(state.getCurrentBoard(), piece.getPiece(), piece.getRow(), piece.getCol());
                if(piece.getPiece().equals("Pawn") && new Random().nextDouble() > 0.15){ break; } //Chooses pawns less frequently
                if(possibleMoves[0] != null){ break; } //If there is a legal move for the piece selected, loop breaks
            }
        }

        int a = 0; //Sets the default possible moves to 0

        //Checks the number of legal moves available
        for(a = 0; a < possibleMoves.length; a++){
            if(possibleMoves[a] == null){ break; } //Breaks at max number of legal moves
        }

        int randMove = (a == 0) ? 0 : randInt(0, a-1); //Random selects a legal move if there are more than one legal move
        newRow = possibleMoves[randMove][0];
        newCol = possibleMoves[randMove][1];

        //Sends move action
        game.sendAction(new ShogiMoveAction(this, newBoard[row][col], newRow, newCol, row, col));
    }

    /**
     * Randomly Generated a number bewteen the min and max values passed through
     *
     * @param min
     * @param max
     */
    public int randInt(int min, int max){ return new Random().nextInt((max - min) + 1) + min; }
}