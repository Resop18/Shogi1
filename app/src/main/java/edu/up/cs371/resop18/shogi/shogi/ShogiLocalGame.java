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
        return playerIdx==gameState.getPlayerTurn();
    }

    @Override
    protected String checkIfGameOver() {
        if(!gameState.getPlayer1HasKing(0)){
            return playerNames[1] +" has won!";
        }
        if(!gameState.getPlayer1HasKing(1)){
            return playerNames[0] +" has won!";
        }
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
            if(gameState.getPlayerTurn() == 1){gameState.setPlayerTurn(0);}
            else if(gameState.getPlayerTurn() == 0){gameState.setPlayerTurn(1);}
            return true;
        }
        //Shogi Move Action
        else if(action instanceof ShogiMoveAction){
			ShogiMoveAction sma = ((ShogiMoveAction)action);

           /* if(!gameState.getPlayerTurn()){
                Log.i("Turn", "CPU");
            }

            if(sma.currPiece == null && !gameState.getPlayerTurn()){
                Log.i("Updated Board", "Updating Board");
                gameState.setCurrentBoard(sma.board);
                Log.i("Setting New Board", "New Board Set");
                gameState.setPlayerTurn(true);
                Log.i("Change Player Turn", "Changed");
                return true;
            }else if(sma.currPiece == null){
                return false;
            }
*/
            ShogiPiece[][] newBoard = gameState.getCurrentBoard();
            ShogiPiece[] captured = gameState.getPlayerCaptured();

            int row = sma.newRow;
            int col = sma.newCol;


			//Log.i("currentPiece", currPiece.getPiece());

            //If possible captures piece
            if(newBoard[row][col] != null){
                for(int i = 0; i < captured.length; i++){
                    if(captured[i] == null){

                        captured[i] = new ShogiPiece(i, newBoard[row][col].getPiece());
                        if(newBoard[row][col].getPiece().equals("King")){gameState.setPlayerHasKing(1);}
                        newBoard[row][col] = null;
                        break;
                    }
                }
            }
            ShogiPiece currPiece = new ShogiPiece(sma.oldRow, sma.oldCol, sma.currPiece.getPiece());
            //Create piece in desired place
            newBoard[row][col] = currPiece;
            newBoard[row][col].promotePiece(sma.currPiece.getPromoted());
            newBoard[row][col].setPlayer(sma.currPiece.getPlayer());
            newBoard[sma.oldRow][sma.oldCol] = null;

            gameState.setCurrentBoard(newBoard);
            currPiece.setPlayer(currPiece.getPlayer());

            //Force Promotes Piece if in Applicable Area
            if(row < 3 && row >= 0 && newBoard[row][col].getPlayer()){
                newBoard[row][col].promotePiece(true);
            }

            currPiece.setSelected(false);
            if(gameState.getPlayerTurn() == 1){gameState.setPlayerTurn(0);}
            else if(gameState.getPlayerTurn() == 0){gameState.setPlayerTurn(1);}
            return true;
        }
        return true;
    }
}
