package edu.up.cs371.resop18.shogi.shogi;

import android.util.Log;

import java.util.Random;

import edu.up.cs371.resop18.shogi.game.GameComputerPlayer;
import edu.up.cs371.resop18.shogi.game.infoMsg.GameInfo;

/**
 * @author Ryan Fredrickson
 */

public class ShogiDumbComputerPlayer extends GameComputerPlayer {
    private ShogiGameState state;
    private ShogiPiece[][] board;
    private LegalMoves m = new LegalMoves(1);
    int row, col, newRow, newCol;
    ShogiPiece piece;

    public ShogiDumbComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        try {
            if (info instanceof ShogiGameState) {
				this.state = (ShogiGameState) info;

				if(state.getPlayerTurn()==1){
					Thread.sleep(300);
					dumbAI();
				}

                //game.sendAction(new ShogiMoveAction(this, ai.piece, ai.newRow, ai.newCol, ai.row, ai.col));
                Log.i("Computer Turn", "Made Move");
            }
        }
        catch (Exception ie) {
        }
    }
    public void dumbAI(){
        ShogiPiece[][] newBoard = state.getCurrentBoard();



        int[][] possibleMoves;

        while(true){
            row = randInt(0, 8);
            col = randInt(0, 8);
            if(newBoard[row][col] != null && !newBoard[row][col].getPlayer()){
                piece = newBoard[row][col];
                possibleMoves = m.moves(state.getCurrentBoard(), piece.getPiece(), piece.getRow(), piece.getCol());
                if(piece.getPiece().equals("Pawn") && new Random().nextDouble() > 0.15){ break; }
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

        game.sendAction(new ShogiMoveAction(this, newBoard[row][col], newRow, newCol, row, col));
    }

    public int randInt(int min, int max){ return new Random().nextInt((max - min) + 1) + min; }
}