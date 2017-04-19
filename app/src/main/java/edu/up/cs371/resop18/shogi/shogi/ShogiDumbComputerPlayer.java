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
    private ShogiGameState state; //Declaration of ShogiGameState
    private ShogiPiece[][] board; //Declaration of the board
    private LegalMoves getLegalMoves = new LegalMoves(1); //Sets LegalMoves for dumb AI
    int row, col, newRow, newCol; //Declares the old row, old col, new row, and new col
    ShogiPiece piece; //Declares the piece moved

    private boolean pieceSelection = false;

    public ShogiDumbComputerPlayer(String name) { super(name); }

    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof ShogiGameState) {
            this.state = (ShogiGameState) info;

            if(state.getPlayerTurn() == 1){
                sleep(1000); //Sleeps for 1000 millisecond before making move
                dumbAI(); //Makes random move
                /*sleep(750);
                piece.setSelected(false);
                game.sendAction(new ShogiMoveAction(this, piece, newRow, newCol, newRow, newCol));*/
            }

            Log.i("Computer Turn", "Made Move");
        }
    }

    public void dumbAI(){
        ShogiPiece[][] newBoard = state.getCurrentBoard(); //Gets current board
        int[][] possibleMoves; //Declaration of possible moves

        /*
         * This will continue to check random locations on the board
         * until a legal move can be make with a piece that belongs to the AI
         */
        while(true){
            row = randInt(0, 8); //Randomly gets a row on the board
            col = randInt(0, 8); //Randomly gets a col on the board

            /*
             * Checks if there is a piece in the randomly chosen location
             * and if the piece belong to the AI, it makes the move
             */
            if(newBoard[row][col] != null && !newBoard[row][col].getPlayer()){
                piece = newBoard[row][col];

                //Gets the possible moves for the piece selected
                possibleMoves = getLegalMoves.moves(state.getCurrentBoard(), piece.getPiece(), piece.getRow(), piece.getCol());

                //Selects pawns at a smaller frequency than other pieces
                if(piece.getPiece().equals("Pawn") && new Random().nextDouble() < 0.15){ break; }

                //Selects the king at a smaller frequency than other pieces
                if(piece.getPiece().equals("King") && new Random().nextDouble() < 0.15){ break; }

                //Breaks if there are legal moves for the piece
                if(possibleMoves[0] != null){ break; }
            }
        }

        int a = 0;
        for(a = 0; a < possibleMoves.length; a++){
            if(possibleMoves[a] == null){ break; }
        }

        int randMove = (a == 0) ? 0 : randInt(0, a-1);
        newRow = possibleMoves[randMove][0];
        newCol = possibleMoves[randMove][1];

        piece.setSelected(pieceSelection);
        newBoard[row][col].setSelected(pieceSelection);

        game.sendAction(new ShogiMoveAction(this, newBoard[row][col], newRow, newCol, row, col));
    }

    public int randInt(int min, int max){ return new Random().nextInt((max - min) + 1) + min; }
}