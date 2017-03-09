package edu.up.cs371.resop18.shogi.shogi;

import android.util.Log;

public class legalMoves {
    private boolean player = false;

    public legalMoves(boolean player){
        this.player = player;
    }

    public int[][] moves(shogiPiece[][] board, shogiPiece piece){
        int[][] moves = new int[16][];
        
        int row = 0;
        int col = 0;
        int i = 0;
        
        for(row = 0; row < board.length; row++){
            for(col = 0; col < board[row].length; col++){
                if(board[row][col] == piece){
                    break;
                }
            }
        }
        row = row - 1;
        col = col - 1;

        if(piece == null){
            return moves;
        }

        if(piece.getPiece().equals("Pawn")){
            if(row != 0){
                Log.i("Pawn Row", ""+row);
                Log.i("Pawn Col", ""+col);
                if(piece.getPlayer()){
                    if(board[row-1][col] == null){
                        moves[0] = new int[]{row-1, col};
                    }else if(board[row-1][col] != null && board[row-1][col].getPlayer() != player){
                        moves[0] = new int[]{row-1, col};
                    }
                }else{
                    if(board[row+1][col] == null && (row+1 != 8 || row+1 != 9)) {
                        moves[0] = new int[]{row+1, col};
                    }else if(board[row+1][col] != null && board[row+1][col].getPlayer() != player){
                        if(row+1 != 8 || row+1 != 9){
                            moves[0] = new int[]{row+1, col};
                        }
                    }
                }
            }
        }else if(piece.getPiece().equals("Rook")){
            i = 0;

            row = piece.getRow();
            col = piece.getCol() - 1;
            while(col >= 0){
                col -= 1;
                moves[i] = new int[]{row, col};
                if(board[row][col] != null){
                    break;
                }
                i++;
            }

            col = piece.getCol() + 1;
            while(col < 9){
                col += 1;
                moves[i] = new int[]{row, col};
                if(board[row][col] != null){
                    break;
                }
                i++;
            }

            row = piece.getRow() - 1;
            col = piece.getCol();
            while(row >= 0){
                row -= 1;
                moves[i] = new int[] {row, col};
                if(board[row][col] != null){
                    break;
                }
                i++;
            }

            row = piece.getRow() + 1;
            while(row < 9){
                row += 1;
                moves[i] = new int[] {row, col};
                if(board[row][col] != null){
                    break;
                }
                i++;
            }
        }else if(piece.getPiece().equals("Lance")){
            row = piece.getRow();
            if(piece.getPlayer()){
                i = 0;
                col = piece.getCol() - 1;
                while(col >= 0){
                    col -= 1;
                    moves[i] = new int[] {row, col};
                    if(board[row][col] != null){
                        break;
                    }
                    i++;
                }
            }else{
                i = 0;
                col = piece.getCol() + 1;
                while(col < 9){
                    col += 1;
                    moves[i] = new int[] {row, col};
                    if(board[row][col] != null){
                        break;
                    }
                    i++;
                }
            }
        }else{
            moves[0] = new int[]{row, col};
        }

        return moves; 
    }
}