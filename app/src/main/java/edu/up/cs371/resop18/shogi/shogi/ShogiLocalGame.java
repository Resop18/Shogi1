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
        return playerIdx == gameState.getPlayerTurn();
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        //Shogi Drop Action
        if(action instanceof ShogiDropAction){
            ShogiPiece[][] newBoard = gameState.getCurrentBoard();

            ShogiDropAction act = (ShogiDropAction)action;
            int row = act.newRow;
            int col = act.newCol;
            if(newBoard[row][col] == null){ //Checks to see if move is legal
                newBoard[row][col] = new ShogiPiece(row, col, newBoard[act.oldRow][act.oldCol].getPiece());
            }else{
                return false;
            }
            gameState.setPlayerTurn(1);
            return true;
        }
        //Shogi Move Action
        else if(action instanceof ShogiMoveAction){
            ShogiPiece[][] newBoard = gameState.getCurrentBoard();
            ShogiPiece[] captured = gameState.getPlayerCaptured();

            ShogiPiece currPiece = ((ShogiMoveAction) action).currPiece;
            int row = ((ShogiMoveAction) action).newRow;
            int col = ((ShogiMoveAction) action).newCol;

            //If possible captures piece
            if(newBoard[row][col] != null){
                for(int i = 0; i < captured.length; i++){
                    if(captured[i] == null){
                        captured[i] = new ShogiPiece(10, 10, newBoard[row][col].getPiece());
                    }
                }
            }

            //Create piece in desired place
            newBoard[row][col] = new ShogiPiece(row, col, newBoard[((ShogiMoveAction) action).oldRow]
                    [((ShogiMoveAction) action).oldCol].getPiece());

            gameState.setCurrentBoard(newBoard);
            currPiece.setPlayer(currPiece.getPlayer());

            //Force Promotes Piece if in Applicable Area
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
