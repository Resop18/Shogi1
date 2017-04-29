package edu.up.cs371.resop18.shogi.shogi;

import android.app.AlertDialog;
import android.util.Log;

import java.io.Serializable;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.LocalGame;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Ryan Fredrickson
 * @author Javier Resop
 */

public class ShogiLocalGame extends LocalGame implements Serializable{
	private static final long serialVersionUID = 42978563847L;
    private ShogiGameState gameState;

    private int playerCaptured = 10;
    private int opponentCaptured = 0;

    public ShogiLocalGame(){
        this.gameState = new ShogiGameState();
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        //Log.i("p","sent the info");
        p.sendInfo(new ShogiGameState(gameState));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == gameState.getPlayerTurn();
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
			int count = 0;
			ShogiPiece[] p2Cap = gameState.getOpponentCaptured();
			ShogiPiece[] p1Cap = gameState.getPlayerCaptured();
            ShogiPiece[][] newBoard = gameState.getCurrentBoard();

            ShogiDropAction act = (ShogiDropAction)action;
            int row = act.newRow;
            int col = act.newCol;
			String pieceName = newBoard[act.oldRow][act.oldCol].getPiece();
            if(newBoard[row][col] == null){ //Checks to see if move is legal
                newBoard[row][col] = new ShogiPiece(row, col, pieceName);
				if(gameState.getPlayerTurn()==1) {
					newBoard[row][col].setPlayer(false);
				}
            }else{
                return false;
            }
            for(int i = 0; i < 19; i++) {
				if(gameState.getPlayerTurn() == 0){
					if(p1Cap[i] != null) {
						if (p1Cap[i].getPiece().equals(pieceName)) {
							count++;
						}
					}
				}
				else{
					if(p2Cap[i] != null) {
						if (p2Cap[i].getPiece().equals(pieceName)) {
							count++;
						}
					}
				}
			}
			if(gameState.getPlayerTurn() == 0){
				if(count == 1) {
					for (int i = 0; i < 9; i++) {
						if(newBoard[act.oldRow][i] != null){
							if (newBoard[act.oldRow][i].getPiece().equals(pieceName)) {
								newBoard[act.oldRow][i] = null;
								break;
							}
						}
					}
				}
				for(int i = 0; i < 19; i++){
					if(p1Cap[i] != null) {
						if (p1Cap[i].getPiece().equals(pieceName)) {
							p1Cap[i] = null;
							break;
						}
					}
				}

			}
			else{
				if(count == 1) {
					for(int i = 0; i < 9; i++){
						if(newBoard[act.oldRow][i] != null) {
							if (newBoard[act.oldRow][i].getPiece().equals(pieceName)) {
								newBoard[act.oldRow][i] = null;
								break;
							}
						}
					}
				}
				for(int i = 0; i < 19; i++){
					if(p2Cap[i] != null) {
						if (p2Cap[i].getPiece().equals(pieceName)) {
							p1Cap[i] = null;
							break;
						}
					}
				}

			}

			if(gameState.getPlayerTurn() == 1){gameState.setPlayerTurn(0);}
			else if(gameState.getPlayerTurn() == 0){gameState.setPlayerTurn(1);}
			return true;
		}
		//Shogi Move Action
		else if(action instanceof ShogiMoveAction){
			ShogiMoveAction sma = ((ShogiMoveAction)action);

            ShogiPiece[][] newBoard = gameState.getCurrentBoard();
            //ShogiPiece[] captured = gameState.getPlayerCaptured();

            int row = sma.newRow;
            int col = sma.newCol;

            //If possible captures piece
            if(newBoard[row][col] != null){
                for(int i = 0; i < gameState.getPlayerCaptured().length; i++){
                    if(gameState.getPlayerTurn() == 0){
                        if(gameState.getPlayerCaptured(i) == null){

                            gameState.setP1Captured(new ShogiPiece(i, newBoard[row][col].getPiece()), i);
                            if(newBoard[row][col].getPiece().equals("King")){gameState.setPlayerHasKing(1);}
                            if(newBoard[row][col].getPiece().equals("Pawn")){newBoard[playerCaptured][0] = new ShogiPiece(row, col, "Pawn");}
                            else if(newBoard[row][col].getPiece().equals("Lance")){newBoard[playerCaptured][1] = new ShogiPiece(row, col, "Lance");}
                            else if(newBoard[row][col].getPiece().equals("Knight")){newBoard[playerCaptured][2] = new ShogiPiece(row, col, "Knight");}
                            else if(newBoard[row][col].getPiece().equals("Silver")){newBoard[playerCaptured][3] = new ShogiPiece(row, col, "Silver");}
                            else if(newBoard[row][col].getPiece().equals("Gold")){newBoard[playerCaptured][4] = new ShogiPiece(row, col, "Gold");}
                            else if(newBoard[row][col].getPiece().equals("Rook")){newBoard[playerCaptured][5] = new ShogiPiece(row, col, "Rook");}
                            else if(newBoard[row][col].getPiece().equals("Bishop")){newBoard[playerCaptured][6] = new ShogiPiece(row, col, "Bishop");}
                            newBoard[row][col] = null;
                            break;
                        }
                    }else if(gameState.getPlayerTurn() == 1){
                        if(gameState.getOpponentCaptured(i) == null){

                            gameState.setP2Captured(new ShogiPiece(i, newBoard[row][col].getPiece()), i);
                            if(newBoard[row][col].getPiece().equals("King")){gameState.setPlayerHasKing(0);}
                            if(newBoard[row][col].getPiece().equals("Pawn")){
                                newBoard[opponentCaptured][0] = new ShogiPiece(row, col, "Pawn");
                                newBoard[opponentCaptured][0].setPlayer(false);
                            }
                            else if(newBoard[row][col].getPiece().equals("Lance")){
                                newBoard[opponentCaptured][1] = new ShogiPiece(row, col, "Lance");
                                newBoard[opponentCaptured][1].setPlayer(false);
                            }
                            else if(newBoard[row][col].getPiece().equals("Knight")){
                                newBoard[opponentCaptured][2] = new ShogiPiece(row, col, "Knight");
                                newBoard[opponentCaptured][2].setPlayer(false);
                            }
                            else if(newBoard[row][col].getPiece().equals("Silver")){
                                newBoard[opponentCaptured][3] = new ShogiPiece(row, col, "Silver");
                                newBoard[opponentCaptured][3].setPlayer(false);
                            }
                            else if(newBoard[row][col].getPiece().equals("Gold")){
                                newBoard[opponentCaptured][4] = new ShogiPiece(row, col, "Gold");
                                newBoard[opponentCaptured][4].setPlayer(false);
                            }
                            else if(newBoard[row][col].getPiece().equals("Rook")){
                                newBoard[opponentCaptured][5] = new ShogiPiece(row, col, "Rook");
                                newBoard[opponentCaptured][5].setPlayer(false);
                            }
                            else if(newBoard[row][col].getPiece().equals("Bishop")){
                                newBoard[opponentCaptured][6] = new ShogiPiece(row, col, "Bishop");
                                newBoard[opponentCaptured][6].setPlayer(false);
                            }
                            newBoard[row][col] = null;
                            break;
                        }
                    }
                }
            }

            //Create piece in desired place
            //ShogiPiece currPiece = new ShogiPiece(sma.oldRow, sma.oldCol, sma.currPiece.getPiece());
            newBoard[row][col] = new ShogiPiece(row, col, sma.currPiece.getPiece());
            newBoard[row][col].promotePiece(sma.currPiece.getPromoted());
            newBoard[row][col].setPlayer(sma.currPiece.getPlayer());
            newBoard[row][col].setSelected(sma.currPiece.getSelected());
            newBoard[sma.oldRow][sma.oldCol] = null;


            /*if(!(row == sma.oldRow && col == sma.oldCol)){
                newBoard[sma.oldRow][sma.oldCol] = null;
            }*/

            //Force Promotes Piece if in Applicable Area
            if(row < 4 && row >= 1 && newBoard[row][col].getPlayer()){
                newBoard[row][col].promotePiece(true);
            }

            if(row < 10 && row >= 7 && !newBoard[row][col].getPlayer()){
                newBoard[row][col].promotePiece(true);
            }


            //if the player who moved is in check afterwards, notify the player
            //and don't let the move go through
            if(gameState.determinePlayerInCheck(gameState.getPlayerTurn(), newBoard)) {
                
            }

            gameState.setCurrentBoard(newBoard);


            if(gameState.getPlayerTurn() == 1){ gameState.setPlayerTurn(0); }
            else if(gameState.getPlayerTurn() == 0){ gameState.setPlayerTurn(1); }
            return true;
        }
        return true;
    }
}