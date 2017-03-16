package edu.up.cs371.resop18.shogi.shogi;

public class LegalMoves {
    private boolean player;

    public LegalMoves(int p){
        if(p == 0) {
            this.player = true;
        }else if(p == 1){
            this.player = false;
        }
    }

    public int[][] moves(ShogiPiece[][] board, String pieceName, int currRow, int currCol, boolean pl){
        int[][] moves = new int[16][];

        int row = 0;
        int col = 0;
        int i = 0;

        if(pieceName.equals("Pawn")){
            if(currRow != 0 || currRow != 8){
                if(pl){
                    if(board[currRow-1][currCol] == null){
                        moves[0] = new int[]{currRow-1, currCol};
                    }else if(board[currRow-1][currCol] != null && board[currRow-1][currCol].getPlayer() != player){
                        moves[0] = new int[]{currRow-1, currCol};
                    }
                }else{
                    if(board[currRow+1][currCol] == null) {
                        moves[0] = new int[]{currRow+1, currCol};
                    }else if(board[currRow+1][currCol] != null){
                        if(row+1 != 8 || row+1 != 9){
                            moves[0] = new int[]{currRow+1, currCol};
                        }
                        if(board[currRow+1][currCol].getPlayer() != player){
                            moves[0] = new int[]{currRow+1, currCol};
                        }
                    }
                }
            }
        }else if(pieceName.equals("Rook")){
            i = 0;

            row = currRow;
            col = currCol - 1;
            while(col >= 0){
                if(board[row][col] != null){
                    if(board[row][col].getPlayer() != player){
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[]{row, col};
                col -= 1;
                i++;
            }

            row = currRow;
            col = currCol + 1;
            while(col < 9){
                if(board[row][col] != null){
                    if(board[row][col].getPlayer() != player){
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[]{row, col};
                col += 1;
                i++;
            }

            row = currRow - 1;
            col = currCol;
            while(row >= 0){
                if(board[row][col] != null){
                    if(board[row][col].getPlayer() != player){
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[] {row, col};
                row -= 1;
                i++;
            }

            row = currRow + 1;
            while(row < 9){
                if(board[row][col] != null){
                    if(board[row][col].getPlayer() != player){
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[] {row, col};
                row += 1;
                i++;
            }
        }else if(pieceName.equals("Lance")){
            col = currCol;
            if(pl){
                i = 0;
                row = currRow - 1;
                while(row >= 0){
                    if(board[row][col] != null){
                        if(board[row][col].getPlayer() != player){
                            moves[i] = new int[]{row, col};
                            i++;
                        }
                        break;
                    }
                    moves[i] = new int[] {row, col};
                    row -= 1;
                    i++;
                }
            }else{
                i = 0;
                col = currCol + 1;
                while(col < 9){
                    if(board[row][col] != null){
                        if(board[row][col].getPlayer() != player){
                            moves[i] = new int[]{row, col};
                            i++;
                        }
                        break;
                    }
                    moves[i] = new int[] {row, col};
                    col += 1;
                    i++;
                }
            }
        }else if(pieceName.equals("Bishop")){
            i = 0;

            row = currRow - 1;
            col = currCol - 1;
            while(row >= 0 && col >= 0){
                if(board[row][col] != null){
                    if(board[row][col].getPlayer() != player){
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[] {row, col};
                row -= 1;
                col -= 1;
                i++;
            }

            row = currRow + 1;
            col = currCol - 1;
            while(row < 9 && col >= 0){
                if(board[row][col] != null){
                    if(board[row][col].getPlayer() != player){
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[] {row, col};
                row += 1;
                col -= 1;
                i++;
            }

            row = currRow - 1;
            col = currCol + 1;
            while(row >= 0 && col < 9){
                if(board[row][col] != null){
                    if(board[row][col].getPlayer() != player){
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[] {row, col};
                row -= 1;
                col += 1;
                i++;
            }

            row = currRow + 1;
            col = currCol + 1;
            while(row < 9 && col < 9){
                if(board[row][col] != null){
                    if(board[row][col].getPlayer() != player){
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[] {row, col};
                row += 1;
                col += 1;
                i++;
            }
        }else if(pieceName.equals("Knight")){
            if(pl){
                if(board[currRow-2][currCol-1] != null){
                    if(player != board[currRow-2][currCol-1].getPlayer()){
                        moves[0] = new int[] {currRow-2, currCol-1};
                    }
                }else{
                    moves[0] = new int[] {currRow-2, currCol-1};
                }

                if(board[currRow-2][currCol+1] != null){
                    if(player != board[currRow-2][currCol+1].getPlayer()){
                        moves[1] = new int[] {currRow-2, currCol+1};
                    }
                }else{
                    moves[1] = new int[] {currRow-2, currCol+1};
                }
            }else{
                if(board[currRow+2][currCol-1] != null){
                    if(player != board[currRow+2][currCol-1].getPlayer()){
                        moves[0] = new int[] {currRow+2, currCol-1};
                    }
                }else{
                    moves[0] = new int[] {currRow+2, currCol-1};
                }

                if(board[currRow+2][currCol+1] != null){
                    if(player != board[currRow+2][currCol+1].getPlayer()){
                        moves[1] = new int[] {currRow+2, currCol+1};
                    }
                }else{
                    moves[1] = new int[] {currRow+2, currCol+1};
                }
            }
        }else{
            moves[0] = new int[]{currRow, currCol};
        }

        return moves;
    }
}