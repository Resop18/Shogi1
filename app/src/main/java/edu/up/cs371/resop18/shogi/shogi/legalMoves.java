package edu.up.cs371.resop18.shogi.shogi;

import android.util.Log;

public class legalMoves {
    private boolean player = false;

    public legalMoves(boolean player){
        this.player = player;
    }

    public int[][] moves(shogiPiece[][] board, String pieceName, int currRow, int currCol, boolean pl){
        int[][] moves = new int[16][];
        
        int row = 0;
        int col = 0;
        int i = 0;

        if(pieceName.equals("Pawn")){
            if(currRow != 0){
                Log.i("Pawn Row", ""+row);
                Log.i("Pawn Col", ""+col);
                if(pl){
                    if(board[row-1][col] == null){
                        moves[0] = new int[]{currRow-1, currCol};
                    }else if(board[currRow-1][currCol] != null && board[currRow-1][currCol].getPlayer() != player){
                        moves[0] = new int[]{currRow-1, currCol};
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
        }else if(pieceName.equals("Rook")){
            i = 0;

            row = currRow;
            col = currCol - 1;
            while(col >= 0){
                col -= 1;
                moves[i] = new int[]{row, col};
                if(board[row][col] != null){
                    break;
                }
                i++;
            }

            col = currCol + 1;
            while(col < 9){
                col += 1;
                moves[i] = new int[]{row, col};
                if(board[row][col] != null){
                    break;
                }
                i++;
            }

            row = currRow - 1;
            col = currCol;
            while(row >= 0){
                row -= 1;
                moves[i] = new int[] {row, col};
                if(board[row][col] != null){
                    break;
                }
                i++;
            }

            row = currRow + 1;
            while(row < 9){
                row += 1;
                moves[i] = new int[] {row, col};
                if(board[row][col] != null){
                    break;
                }
                i++;
            }
        }else if(pieceName.equals("Lance")){
            row = currRow;
            if(pl){
                i = 0;
                col = currCol - 1;
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
                col = currCol + 1;
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
            moves[0] = new int[]{currRow, currCol};
        }

        return moves; 
    }
}