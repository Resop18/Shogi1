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
            ShogiPiece[][] newBoard = gameState.getCurrentBoard();
            ShogiPiece[] captured = gameState.getPlayerCaptured();

            ShogiPiece currPiece = ((ShogiMoveAction) action).currPiece;
            int row = ((ShogiMoveAction) action).newRow;
            int col = ((ShogiMoveAction) action).newCol;
            if(newBoard[row][col] != null){
                for(int i = 0; i < captured.length; i++){
                    if(captured[i] == null){
                        captured[i] = new ShogiPiece(10, 10, newBoard[row][col].getPiece());
                    }
                }
            }

            newBoard[row][col] = new ShogiPiece(row, col, newBoard[((ShogiMoveAction) action).oldRow]
                    [((ShogiMoveAction) action).oldCol].getPiece());

            gameState.setCurrentBoard(newBoard);
            currPiece.setPlayer(currPiece.getPlayer());
            if(row < 3 && row >= 0 && newBoard[row][col].getPlayer()){
                newBoard[row][col].promotePiece(true);
            }
            currPiece.setSelected(false);
            gameState.setPlayerTurn(1);
            return true;
        }
        return false;
    }
}
