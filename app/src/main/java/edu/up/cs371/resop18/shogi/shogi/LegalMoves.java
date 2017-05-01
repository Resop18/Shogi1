package edu.up.cs371.resop18.shogi.shogi;

import java.io.Serializable;

/**
 * @author Ryan Fredrickson
 * @author Jake Nguyen
 */

public class LegalMoves implements Serializable{
    private static final long serialVersionUID = 42978563847L;
    private boolean player;

    public LegalMoves(int n){
        if (n == 0) {
            this.player = true;
        } else if (n == 1) {
            this.player = false;
        }
    }

    public int[][] moves(ShogiPiece[][] board, String pieceName, int currRow, int currCol){
        int[][] moves = new int[20][];
        int[][] dropMoves = new int[81][];

        int row = 0;
        int col = 0;
        int i = 0;

        if(currRow == 10){
            for(row = 4; row < 10; row++){
                for(col = 0; col < 9; col++){
                    if(board[row][col] == null){
                        dropMoves[i] = new int[]{row,col};
                        i++;
                    }
                }
            }
            return dropMoves;
        }
        if(currRow == 0){
            for(row = 6; row > 0; row--){
                for(col = 0; col < 9; col++){
                    if(board[row][col] == null){
                        dropMoves[i] = new int[]{row,col};
                        i++;
                    }
                }
            }
            return dropMoves;
        }

        if(pieceName.equals("Pawn")){
            if (currRow != 1 || currRow != 9) {
                if (player) {
                    if (currRow - 1 >= 1) {
                        if (board[currRow - 1][currCol] == null) {
                            moves[0] = new int[]{currRow - 1, currCol};
                        } else if (board[currRow - 1][currCol] != null && board[currRow - 1][currCol].getPlayer() != player) {
                            moves[0] = new int[]{currRow - 1, currCol};
                        }
                    }
                } else {
                    if (currRow + 1 < 11) {
                        if (board[currRow + 1][currCol] == null) {
                            moves[0] = new int[]{currRow + 1, currCol};
                        } else if (board[currRow + 1][currCol] != null) {
                            if (row + 1 != 9 || row + 1 != 10) {
                                moves[0] = new int[]{currRow + 1, currCol};
                            }
                            if (board[currRow + 1][currCol].getPlayer() != player) {
                                moves[0] = new int[]{currRow + 1, currCol};
                            }
                        }
                    }
                }
            }
        } else if (pieceName.equals("Rook")) {
            i = 0;

            row = currRow;
            col = currCol - 1;
            while (col >= 0) {
                if (board[row][col] != null) {
                    if (board[row][col].getPlayer() != player) {
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
            while (col < 9) {
                if (board[row][col] != null) {
                    if (board[row][col].getPlayer() != player) {
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
            while (row >= 1) {
                if (board[row][col] != null) {
                    if (board[row][col].getPlayer() != player) {
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[]{row, col};
                row -= 1;
                i++;
            }

            row = currRow + 1;
            while (row < 10) {
                if (board[row][col] != null) {
                    if (board[row][col].getPlayer() != player) {
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[]{row, col};
                row += 1;
                i++;
            }
        } else if (pieceName.equals("Lance")) {
            col = currCol;
            if(player){
                i = 0;
                row = currRow - 1;
                while(row >= 1){
                    if (board[row][col] != null) {
                        if(board[row][col].getPlayer() != player){
                            moves[i] = new int[]{row, col};
                            i++;
                        }
                        break;
                    }
                    moves[i] = new int[]{row, col};
                    row -= 1;
                    i++;
                }
            } else {
                i = 0;
                row = currRow + 1;
                while (row < 10) {
                    if (board[row][col] != null) {
                        if (board[row][col].getPlayer() != player) {
                            moves[i] = new int[]{row, col};
                            i++;
                        }
                        break;
                    }
                    moves[i] = new int[]{row, col};
                    row += 1;
                    i++;
                }
            }
        } else if (pieceName.equals("Bishop")) {
            i = 0;

            row = currRow - 1;
            col = currCol - 1;
            while (row >= 1 && col >= 0) {
                if (board[row][col] != null) {
                    if (board[row][col].getPlayer() != player) {
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[]{row, col};
                row -= 1;
                col -= 1;
                i++;
            }

            row = currRow + 1;
            col = currCol - 1;
            while (row < 10 && col >= 0) {
                if (board[row][col] != null) {
                    if (board[row][col].getPlayer() != player) {
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[]{row, col};
                row += 1;
                col -= 1;
                i++;
            }

            row = currRow - 1;
            col = currCol + 1;
            while (row >= 1 && col < 9) {
                if (board[row][col] != null) {
                    if (board[row][col].getPlayer() != player) {
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[]{row, col};
                row -= 1;
                col += 1;
                i++;
            }

            row = currRow + 1;
            col = currCol + 1;
            while (row < 10 && col < 9) {
                if (board[row][col] != null) {
                    if (board[row][col].getPlayer() != player) {
                        moves[i] = new int[]{row, col};
                        i++;
                    }
                    break;
                }
                moves[i] = new int[]{row, col};
                row += 1;
                col += 1;
                i++;
            }
        } else if (pieceName.equals("Knight")) {
            if (player) {
                if (currRow - 2 > 0) {
                    if (currCol - 1 >= 0) {
                        if (board[currRow - 2][currCol - 1] != null) {
                            if (player != board[currRow - 2][currCol - 1].getPlayer()) {
                                moves[0] = new int[]{currRow - 2, currCol - 1};
                            }
                        } else {
                            moves[0] = new int[]{currRow - 2, currCol - 1};
                        }
                    }

                    if (currCol + 1 < 9) {
                        if(board[currRow - 2][currCol + 1] != null){
                            if (player != board[currRow - 2][currCol + 1].getPlayer()) {
                                moves[1] = new int[]{currRow - 2, currCol + 1};
                            }
                        } else {
                            moves[1] = new int[]{currRow - 2, currCol + 1};
                        }
                    }
                }
            } else {
                if (currRow + 2 < 11) {
                    if (currCol - 1 >= 0) {
                        if (board[currRow + 2][currCol - 1] != null) {
                            if (player != board[currRow + 2][currCol - 1].getPlayer()) {
                                moves[0] = new int[]{currRow + 2, currCol - 1};
                            }
                        } else {
                            moves[0] = new int[]{currRow + 2, currCol - 1};
                        }
                    }

                    if (currCol + 1 < 9) {
                        if (board[currRow + 2][currCol + 1] != null) {
                            if (player != board[currRow + 2][currCol + 1].getPlayer()) {
                                moves[1] = new int[]{currRow + 2, currCol + 1};
                            }
                        } else {
                            moves[1] = new int[]{currRow + 2, currCol + 1};
                        }
                    }
                }
            }
        } else if (pieceName.equals("Gold") || pieceName.equals("King")) {
            i = 0;
            if (player) {
                if (currRow != 1 && currCol != 0) {
                    if (board[currRow - 1][currCol - 1] != null) {
                        if (player != board[currRow - 1][currCol - 1].getPlayer()) {
                            moves[i] = new int[]{currRow - 1, currCol - 1};
                        }
                    } else {
                        moves[i] = new int[]{currRow - 1, currCol - 1};
                    }
                }
                i++;

                if (currRow != 1 && currCol != 8) {
                    if (board[currRow - 1][currCol + 1] != null) {
                        if (player != board[currRow - 1][currCol + 1].getPlayer()) {
                            moves[i] = new int[]{currRow - 1, currCol + 1};
                        }
                    } else {
                        moves[i] = new int[]{currRow - 1, currCol + 1};
                    }
                }
                i++;
            } else {
                if (currRow != 9 && currCol != 0) {
                    if (board[currRow + 1][currCol - 1] != null) {
                        if (player != board[currRow + 1][currCol - 1].getPlayer()) {
                            moves[i] = new int[]{currRow + 1, currCol - 1};
                        }
                    } else {
                        moves[i] = new int[]{currRow + 1, currCol - 1};
                    }
                }
                i++;

                if (currRow != 9 && currCol != 8) {
                    if (board[currRow + 1][currCol + 1] != null) {
                        if (player != board[currRow + 1][currCol + 1].getPlayer()) {
                            moves[i] = new int[]{currRow + 1, currCol + 1};
                        }
                    } else {
                        moves[i] = new int[]{currRow + 1, currCol + 1};
                    }
                }
                i++;
            }

            if (currRow != 1) {
                if (board[currRow - 1][currCol] != null) {
                    if (player != board[currRow - 1][currCol].getPlayer()) {
                        moves[i] = new int[]{currRow - 1, currCol};
                    }
                } else {
                    moves[i] = new int[]{currRow - 1, currCol};
                }
            }
            i++;

            if (!pieceName.equals("Silver")) {
                if (currRow != 9) {
                    if (board[currRow + 1][currCol] != null) {
                        if (player != board[currRow + 1][currCol].getPlayer()) {
                            moves[i] = new int[]{currRow + 1, currCol};
                        }
                    } else {
                        moves[i] = new int[]{currRow + 1, currCol};
                    }
                }
                i++;

                if (currCol != 8) {
                    if (board[currRow][currCol + 1] != null) {
                        if (player != board[currRow][currCol + 1].getPlayer()) {
                            moves[i] = new int[]{currRow, currCol + 1};
                        }
                    } else {
                        moves[i] = new int[]{currRow, currCol + 1};
                    }
                }
                i++;

                if (currCol != 0) {
                    if (board[currRow][currCol - 1] != null) {
                        if (player != board[currRow][currCol - 1].getPlayer()) {
                            moves[i] = new int[]{currRow, currCol - 1};
                        }
                    } else {
                        moves[i] = new int[]{currRow, currCol - 1};
                    }
                }
                i++;
            }

            if (pieceName.equals("King")) {
                if (player) {
                    if (currRow + 1 < 10 && currCol - 1 > 0) {
                        if (board[currRow + 1][currCol - 1] != null) {
                            if (player != board[currRow + 1][currCol - 1].getPlayer()) {
                                moves[i] = new int[]{currRow + 1, currCol - 1};
                            }
                        } else {
                            moves[i] = new int[]{currRow + 1, currCol - 1};
                        }
                        i++;
                    }
                    if (currRow + 1 < 10 && currCol + 1 < 9) {
                        if (board[currRow + 1][currCol + 1] != null) {
                            if (player != board[currRow + 1][currCol + 1].getPlayer()) {
                                moves[i] = new int[]{currRow + 1, currCol + 1};
                            }
                        } else {
                            moves[i] = new int[]{currRow + 1, currCol + 1};
                        }
                        i++;
                    }
                } else {
                    if (currRow != 1 && currCol != 0 && currRow != 8 && currCol != 8) {
                        if (board[currRow - 1][currCol - 1] != null) {
                            if (player != board[currRow - 1][currCol - 1].getPlayer()) {
                                moves[i] = new int[]{currRow - 1, currCol - 1};
                            }
                        } else {
                            moves[i] = new int[]{currRow - 1, currCol - 1};
                        }
                        i++;

                        if (board[currRow - 1][currCol + 1] != null) {
                            if (player != board[currRow - 1][currCol + 1].getPlayer()) {
                                moves[i] = new int[]{currRow - 1, currCol + 1};
                            }
                        } else {
                            moves[i] = new int[]{currRow - 1, currCol + 1};
                        }
                        i++;
                    }
                }
            }
        } else if (pieceName.equals("Silver")) {
            i = 0;
            if(currRow-1 > 0 && currCol-1 >= 0 &&
                    (board[currRow-1][currCol-1] == null || player != board[currRow-1][currCol-1].getPlayer())){
                moves[i] = new int[]{currRow-1, currCol-1};
                i++;
            }

            if(currRow-1 > 1 && currCol+1 < 9 &&
                    (board[currRow-1][currCol+1] == null || player != board[currRow-1][currCol+1].getPlayer())){
                moves[i] = new int[]{currRow-1, currCol+1};
                i++;
            }

            if(currRow+1 < 10 && currCol+1 > 0 &&
                    (board[currRow+1][currCol+1] == null || player != board[currRow+1][currCol+1].getPlayer())){
                moves[i] = new int[]{currRow+1, currCol+1};
                i++;
            }

            if(currRow+1 < 10 && currCol-1 > 0 &&
                    (board[currRow+1][currCol-1] == null || player != board[currRow+1][currCol-1].getPlayer())){
                moves[i] = new int[]{currRow+1, currCol-1};
                i++;
            }

            if(player){
                if (currRow - 1 > 0 &&
                        (board[currRow - 1][currCol] == null || player != board[currRow - 1][currCol].getPlayer())) {
                    moves[i] = new int[]{currRow - 1, currCol};
                    i++;
                }
            }else{
                if (currRow + 1 < 10 &&
                        (board[currRow + 1][currCol] == null || player != board[currRow + 1][currCol].getPlayer())) {
                    moves[i] = new int[]{currRow + 1, currCol};
                    i++;
                }
            }
        } else {
            for (i = 0; i < 20; i++) {
                moves[i] = new int[]{currRow, currCol};
            }
        }

        if (board[currRow][currCol].getPromoted()) {
            if (pieceName.equals("Pawn") || pieceName.equals("Silver") || pieceName.equals("Knight") || pieceName.equals("Lance")) {
                return moves(board, "Gold", currRow, currCol);
            } else if (pieceName.equals("Bishop")) {
                i = 16;
                if(currRow-1 >= 1) {
                    if (board[currRow - 1][currCol] == null || board[currRow - 1][currCol].getPlayer() != player) {
                        moves[i] = new int[]{currRow - 1, currCol};
                        i++;
                    }
                }
                if(currRow+1 < 10){
                    if(board[currRow+1][currCol] == null || board[currRow+1][currCol].getPlayer() != player){
                        moves[i] = new int[]{currRow+1, currCol};
                        i++;
                    }
                }
                if(currCol-1 >= 0) {
                    if (board[currRow][currCol-1] == null || board[currRow][currCol-1].getPlayer() != player) {
                        moves[i] = new int[]{currRow, currCol-1};
                        i++;
                    }
                }
                if(currCol+1 < 9){
                    if(board[currRow][currCol+1] == null || board[currRow][currCol+1].getPlayer() != player){
                        moves[i] = new int[]{currRow, currCol+1};
                    }
                }
                return moves;
            } else if (pieceName.equals("Rook")){
                i = 16;
                if(currRow+1 < 10 && currCol+1 < 9){
                    if(board[currRow+1][currCol+1] == null || board[currRow+1][currCol+1].getPlayer() != player){
                        moves[i] = new int[]{currRow+1, currCol+1};
                        i++;
                    }
                }

                if(currRow+1 < 10 && currCol-1 >= 0){
                    if(board[currRow+1][currCol-1] == null || board[currRow+1][currCol-1].getPlayer() != player){
                        moves[i] = new int[]{currRow+1, currCol-1};
                        i++;
                    }
                }

                if(currRow-1 > 1 && currCol+1 < 9){
                    if(board[currRow-1][currCol+1] == null || board[currRow-1][currCol+1].getPlayer() != player){
                        moves[i] = new int[]{currRow-1, currCol+1};
                        i++;
                    }
                }

                if(currRow-1 > 1 && currCol-1 >= 0){
                    if(board[currRow-1][currCol-1] == null || board[currRow-1][currCol-1].getPlayer() != player){
                        moves[i] = new int[]{currRow-1, currCol-1};
                    }
                }

                return moves;
            }
        }

        return moves;
    }
}