package edu.up.cs371.resop18.shogi.shogi;

public class LegalMoves {
    private boolean player;

    public LegalMoves(int n){
        if(n == 0){
            this.player = true;
        }else if(n == 1){
            this.player = false;
        }
    }

    public int[][] moves(ShogiPiece[][] board, String pieceName, int currRow, int currCol){
        int[][] moves = new int[20][];

        int row = 0;
        int col = 0;
        int i = 0;

        if(pieceName.equals("Pawn")){
            if(currRow != 0 || currRow != 8){
                if(player){
                    if(currRow - 1 >= 0){
                        if(board[currRow-1][currCol] == null){
                            moves[0] = new int[]{currRow-1, currCol};
                        }else if(board[currRow-1][currCol] != null && board[currRow-1][currCol].getPlayer() != player){
                            moves[0] = new int[]{currRow-1, currCol};
                        }
                    }
                }else{
                    if(currRow + 1 < 9){
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
            if(player){
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
                row = currRow + 1;
                while(col < 9){
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
            if(player){
                if(currRow - 2 >= 0){
                    if(currCol - 1 >= 0){
                        if(board[currRow-2][currCol-1] != null){
                            if(player != board[currRow-2][currCol-1].getPlayer()){
                                moves[0] = new int[] {currRow-2, currCol-1};
                            }
                        }else{
                            moves[0] = new int[] {currRow-2, currCol-1};
                        }
                    }

                    if(currCol + 1 < 9){
                        if(board[currRow-2][currCol+1] != null){
                            if(player != board[currRow-2][currCol+1].getPlayer()){
                                moves[1] = new int[] {currRow-2, currCol+1};
                            }
                        }else{
                            moves[1] = new int[] {currRow-2, currCol+1};
                        }
                    }
                }
            }else{
                if(currRow + 2 < 9){
                    if(currCol - 1 >= 0){
                        if(board[currRow+2][currCol-1] != null){
                            if(player != board[currRow+2][currCol-1].getPlayer()){
                                moves[0] = new int[] {currRow+2, currCol-1};
                            }
                        }else{
                            moves[0] = new int[] {currRow+2, currCol-1};
                        }
                    }

                    if(currCol + 1 < 9){
                        if(board[currRow+2][currCol+1] != null){
                            if(player != board[currRow+2][currCol+1].getPlayer()){
                                moves[1] = new int[] {currRow+2, currCol+1};
                            }
                        }else{
                            moves[1] = new int[] {currRow+2, currCol+1};
                        }
                    }
                }
            }
        }else if(pieceName.equals("Gold") || pieceName.equals("King")){
            i = 0;
            if(player){
                if(currRow != 0 && currCol != 0){
                    if(board[currRow-1][currCol-1] != null){
                        if(player != board[currRow-1][currCol-1].getPlayer()){
                            moves[i] = new int[] {currRow-1, currCol-1};
                        }
                    }else{
                        moves[i] = new int[] {currRow-1, currCol-1};
                    }
                }
                i++;

                if(currRow != 0 && currCol != 8){
                    if(board[currRow-1][currCol+1] != null){
                        if(player != board[currRow-1][currCol+1].getPlayer()){
                            moves[i] = new int[] {currRow-1, currCol+1};
                        }
                    }else{
                        moves[i] = new int[] {currRow-1, currCol+1};
                    }
                }
                i++;
            }else{
                if(currRow != 8 && currCol != 0){
                    if(board[currRow+1][currCol-1] != null){
                        if(player != board[currRow+1][currCol-1].getPlayer()){
                            moves[i] = new int[] {currRow+1, currCol-1};
                        }
                    }else{
                        moves[i] = new int[] {currRow+1, currCol-1};
                    }
                }
                i++;

                if(currRow != 8 && currCol != 8){
                    if(board[currRow+1][currCol+1] != null){
                        if(player != board[currRow+1][currCol+1].getPlayer()){
                            moves[i] = new int[] {currRow+1, currCol+1};
                        }
                    }else{
                        moves[i] = new int[] {currRow+1, currCol+1};
                    }
                }
                i++;
            }

            if(currRow != 0){
                if(board[currRow-1][currCol] != null){
                    if(player != board[currRow-1][currCol].getPlayer()){
                        moves[i] = new int[] {currRow-1, currCol};
                    }
                }else{
                    moves[i] = new int[] {currRow-1, currCol};
                }
            }
            i++;

            if(!pieceName.equals("Silver")) {
                if (currRow != 8) {
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

            if(pieceName.equals("King")){
                if(player){
                    if(currRow+1 < 9 && currCol-1 > 0){
                        if(board[currRow+1][currCol-1] != null){
                            if(player != board[currRow+1][currCol-1].getPlayer()){
                                moves[i] = new int[] {currRow+1, currCol-1};
                            }
                        }else{
                            moves[i] = new int[] {currRow+1, currCol-1};
                        }
                        i++;
                    }
                    if(currRow+1 < 9 && currCol+1 < 9){
                        if(board[currRow+1][currCol+1] != null){
                            if(player != board[currRow+1][currCol+1].getPlayer()){
                                moves[i] = new int[] {currRow+1, currCol+1};
                            }
                        }else{
                            moves[i] = new int[] {currRow+1, currCol+1};
                        }
                        i++;
                    }
                }else{
                    if(currRow != 0 && currCol != 0 && currRow != 8 && currCol != 8){
                        if(board[currRow-1][currCol-1] != null){
                            if(player != board[currRow-1][currCol-1].getPlayer()){
                                moves[i] = new int[] {currRow-1, currCol-1};
                            }
                        }else{
                            moves[i] = new int[] {currRow-1, currCol-1};
                        }
                        i++;

                        if(board[currRow-1][currCol+1] != null){
                            if(player != board[currRow-1][currCol+1].getPlayer()){
                                moves[i] = new int[] {currRow-1, currCol+1};
                            }
                        }else{
                            moves[i] = new int[] {currRow-1, currCol+1};
                        }
                        i++;
                    }
                }
            }
        }
        else if(pieceName.equals("Silver")){
            if(currRow-1 < 0 ){

                if(currCol - 1 < 0){ //check the top left
                    if(board[currRow+1][currCol+1] == null || board[currRow+1][currCol+1].getPlayer()!=player ){
                        moves[0] = new int[]{currRow+1, currCol+1}; //check to draw a dot diaacnos
                    }
                }else if(currCol +1 >= 9){ //check the top right
                    if(board[currRow+1][currCol-1] == null || board[currRow+1][currCol-1].getPlayer()!=player ){
                        moves[0] = new int[]{currRow+1, currCol-1}; //check to draw a dot diaacnos
                    }
                }else{ //checktopcentre
                    if(board[currRow+1][currCol-1] == null || board[currRow+1][currCol-1].getPlayer()!=player ){ //leftdot
                        moves[0] = new int[]{currRow+1, currCol-1};
                    }
                    if(board[currRow+1][currCol+1] == null || board[currRow+1][currCol+1].getPlayer()!=player){ //rightdot
                        moves[1] = new int[]{currRow+1, currCol+1};
                    }
                }

            }else if(currRow+1 >= 8){

                if(currCol - 1 <= 0){
                    if(board[currRow-1][currCol] == null || board[currRow-1][currCol].getPlayer()!=player)
                        moves[0] = new int[]{currRow-1, currCol};
                    if(board[currRow-1][currCol+1] == null || board[currRow-1][currCol+1].getPlayer()!=player)
                        moves[1] = new int[]{currRow-1, currCol+1};
                }else if(currCol+1 >= 9){
                    if(board[currRow-1][currCol] == null || board[currRow-1][currCol].getPlayer()!=player)
                        moves[0] = new int[]{currRow-1, currCol};
                    if(board[currRow-1][currCol-1] == null || board[currRow-1][currCol-1].getPlayer()!=player)
                        moves[1] = new int[]{currRow-1, currCol-1};
                }else{
                    if(board[currRow-1][currCol] == null || board[currRow-1][currCol].getPlayer()!=player)
                        moves[0] = new int[]{currRow-1, currCol};
                    if(board[currRow-1][currCol-1] == null || board[currRow-1][currCol-1].getPlayer()!=player)
                        moves[1] = new int[]{currRow-1, currCol-1};
                    if(board[currRow-1][currCol+1] == null || board[currRow-1][currCol+1].getPlayer()!=player)
                        moves[2] = new int[]{currRow-1, currCol+1};
                }

            }else if(currCol+1 >= 9){

                if(board[currRow-1][currCol] == null || board[currRow-1][currCol].getPlayer()!=player)
                    moves[0] = new int[]{currRow-1, currCol};
                if(board[currRow-1][currCol-1] == null || board[currRow-1][currCol-1].getPlayer()!=player)
                    moves[1] = new int[]{currRow-1, currCol-1};
                if(board[currRow+1][currCol-1] == null || board[currRow+1][currCol-1].getPlayer()!=player)
                    moves[2] = new int[]{currRow+1, currCol-1};

            }else if(currRow-1 < 0 ){

                if(board[currRow-1][currCol] == null || board[currRow-1][currCol].getPlayer()!=player){
                    moves[0] = new int[]{currRow-1, currCol};
                }
                if(board[currRow-1][currCol+1] == null || board[currRow-1][currCol+1].getPlayer()!=player){
                    moves[1] = new int[]{currRow-1, currCol+1};
                }
                if(board[currRow+1][currCol+1] == null || board[currRow+1][currCol+1].getPlayer()!=player){
                    moves[2] = new int[]{currRow+1, currCol+1};
                }

            }else{
                if(board[currRow-1][currCol] == null || board[currRow-1][currCol].getPlayer()!= player){
                    i = 0;
                    moves[i] = new int[]{currRow-1, currCol};
                    i++;
                    if(currCol-1 >= 0){
                        if(board[currRow-1][currCol-1] == null || board[currRow-1][currCol-1].getPlayer()!=player){
                            moves[i] = new int[]{currRow-1, currCol-1};
                            i++;
                        }

                        if(board[currRow+1][currCol-1] == null || board[currRow+1][currCol-1].getPlayer()!=player){
                            moves[i] = new int[]{currRow+1, currCol-1};
                            i++;
                        }
                    }

                    if(board[currRow-1][currCol+1] == null || board[currRow-1][currCol+1].getPlayer()!=player){
                        moves[i] = new int[]{currRow-1, currCol+1};
                        i++;
                    }

                    if(board[currRow+1][currCol+1] == null || board[currRow+1][currCol+1].getPlayer()!=player){
                        moves[i] = new int[]{currRow+1, currCol+1};
                        i++;
                    }
                }
            }
        }else{
            for(i = 0; i < 20; i++){
                moves[i] = new int[]{currRow, currCol};
            }
        }

        if(board[currRow][currCol].getPromoted()){
            if(pieceName.equals("Pawn") || pieceName.equals("Silver") || pieceName.equals("Knight") || pieceName.equals("Lance")){
                moves = moves(board, "Gold", currRow, currCol);
            }else if(pieceName.equals("Bishop")){
                int[][] newMoves = new int[][]{{currRow+1, currCol}, {currRow-1, currCol}, {currRow, currCol+1}, {currRow, currCol-1}};
                int[][] array1and2 = new int[moves.length + newMoves.length][];
                System.arraycopy(moves, 0, array1and2, 0, moves.length);
                System.arraycopy(newMoves, 0, array1and2, moves.length, newMoves.length);
                moves = array1and2;
            }else if(pieceName.equals("Rook")){
                int[][] newMoves = new int[][]{{currRow+1, currCol+1}, {currRow-1, currCol-1}, {currRow-1, currCol+1}, {currRow+1, currCol-1}};
                int[][] array1and2 = new int[moves.length + newMoves.length][];
                System.arraycopy(moves, 0, array1and2, 0, moves.length);
                System.arraycopy(newMoves, 0, array1and2, moves.length, newMoves.length);
                moves = array1and2;
            }
        }

        return moves;
    }
}