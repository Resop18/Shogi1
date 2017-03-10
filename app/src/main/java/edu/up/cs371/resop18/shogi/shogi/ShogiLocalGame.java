package edu.up.cs371.resop18.shogi.shogi;

import android.util.Log;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.LocalGame;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Ryan Fredrickson
 * @author Javier Resop
 */
public class ShogiLocalGame extends LocalGame {
    private ShogiGameState gameState;

    public ShogiLocalGame(){
        this.gameState = new ShogiGameState();

    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        Log.i("p","sent the info");
        p.sendInfo(new ShogiGameState(gameState));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        //if(playerIdx==0)
            return true;
       // return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        if(action instanceof ShogiDropAction){


            return true;
        }
        else if(action instanceof ShogiMoveAction){
            ShogiPiece currPiece = ((ShogiMoveAction) action).currPiece;
            int row = ((ShogiMoveAction) action).newRow;
            int col = ((ShogiMoveAction) action).newCol;
            //currPieces[row][col] = new ShogiPiece(row, col, currPieces[i][j].getPiece());
            currPiece.setPlayer(currPiece.getPlayer());
            currPiece.setSelected(false);
            return true;
        }
        return false;
    }
}
