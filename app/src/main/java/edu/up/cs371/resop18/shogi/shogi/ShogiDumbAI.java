package edu.up.cs371.resop18.shogi.shogi;

/**
 * @author Ryan Fredrickson
 */

import java.util.Random;

public class ShogiDumbAI {
    private ShogiPiece[][] board;
    private LegalMoves m = new LegalMoves(1);
    int row, col, newRow, newCol;
    ShogiPiece piece;

    public ShogiDumbAI(ShogiGameState gState){
        this.board = gState.pieces;
    }

    public int randInt(int min, int max){ return new Random().nextInt((max - min) + 1) + min; }

    public ShogiPiece[][] dumbAI(){
        ShogiPiece[][] newBoard = new ShogiPiece[10][9];

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] != null){
                    newBoard[i][j] = new ShogiPiece(i, j, board[i][j].getPiece());
                    newBoard[i][j].setPlayer(board[i][j].getPlayer());
                    newBoard[i][j].promotePiece(board[i][j].getPromoted());
                }
            }
        }

        int[][] possibleMoves;

        while(true){
            row = randInt(0, 8);
            col = randInt(0, 8);
            if(newBoard[row][col] != null && !newBoard[row][col].getPlayer()){
                piece = newBoard[row][col];
                possibleMoves = m.moves(board, piece.getPiece(), piece.getRow(), piece.getCol());
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

        newBoard[newRow][newCol] = new ShogiPiece(newRow, newCol, newBoard[row][col].getPiece());
        newBoard[newRow][newCol].setPlayer(newBoard[row][col].getPlayer());
        newBoard[newRow][newCol].promotePiece(newBoard[row][col].getPromoted());
        newBoard[row][col] = null;

        return newBoard;
    }
}