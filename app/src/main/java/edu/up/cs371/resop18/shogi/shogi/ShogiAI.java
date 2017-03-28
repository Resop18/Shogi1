package edu.up.cs371.resop18.shogi.shogi;

public class ShogiAI {
    private ShogiGameState gameState;
    private ShogiPiece[][] bestChild = new ShogiPiece[10][9];

    protected int oldRow, oldCol;
    protected int newRow, newCol;

    public ShogiAI(ShogiGameState gState, int MAX_DEPTH){
        gameState = gState;
        ShogiPiece[][] gameBoard = gameState.pieces;
        double bestVal = eval(gameBoard, true, 0, MAX_DEPTH);
    }

    private ShogiPiece[][] newGameState(ShogiPiece[][] board, int[] move){
        ShogiPiece[][] newBoard = new ShogiPiece[10][9];

        for(int i = 0; i < newBoard.length; i++){
            for(int j = 0; j < newBoard[i].length; j++){
                if(board[i][j] != null){
                    newBoard[i][j] = new ShogiPiece(board[i][j].getRow(), board[i][j].getCol(), board[i][j].getPiece());
                    newBoard[i][j].setPlayer(board[i][j].getPlayer());
                    newBoard[i][j].promotePiece(board[i][j].getPromoted());
                }
            }
        }

        int row = move[0];
        int col = move[1];

        int oldRow = move[2];
        int oldCol = move[3];

        if(board[oldRow][oldCol] != null){
            if(board[row][col] == null || board[row][col].getPlayer() != board[oldRow][oldCol].getPlayer()){
                newBoard[row][col] = new ShogiPiece(row, col, board[oldRow][oldCol].getPiece());
                newBoard[row][col].setPlayer(board[oldRow][oldCol].getPlayer());
                newBoard[row][col].promotePiece(board[oldRow][oldCol].getPromoted());
                newBoard[oldRow][oldCol] = null;
            }
        }
        return newBoard;
    }

    private int[][][] actList(ShogiPiece[][] board){
        LegalMoves m = new LegalMoves(0);
        int[][][] list = new int[15][20][4];
        int[][] possibleMoves;
        for(int a = 0; a < list.length; a++){
            for (ShogiPiece[] aBoard : board) {
                for (ShogiPiece anABoard : aBoard){
                    if(anABoard != null){
                        if(!anABoard.getPlayer()){
                            possibleMoves = m.moves(board, anABoard.getPiece(), anABoard.getRow(), anABoard.getCol(), anABoard.getPlayer());
                            for(int i = 0; i < 20; i++){
                                if(possibleMoves[i] != null){
                                    list[a][i][0] = possibleMoves[i][0];
                                    list[a][i][1] = possibleMoves[i][1];

                                    list[a][i][2] = anABoard.getRow();
                                    list[a][i][3] = anABoard.getCol();
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    private ShogiPiece[][][] childList(ShogiPiece[][] board, int[][][] actList){
        ShogiPiece[][] localBoard = new ShogiPiece[10][9];
        ShogiPiece[][][] list = new ShogiPiece[actList.length][10][9];

        for(int i = 0; i < actList.length; i++) {
            for(int j = 0; j < actList[i].length; j++) {
                for(ShogiPiece[][] aList : list){
                    localBoard = newGameState(board, actList[i][j]);
                    for(int k = 0; k < localBoard.length; k++){
                        for(int l = 0; l < localBoard[k].length; l++){
                            if(localBoard[k][l] != null){
                                aList[k][l] = new ShogiPiece(localBoard[k][l].getRow(), localBoard[k][l].getCol(), localBoard[k][l].getPiece());
                                aList[k][l].setPlayer(localBoard[k][l].getPlayer());
                                aList[k][l].promotePiece(localBoard[k][l].getPromoted());
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    private double eval(ShogiPiece[][] board, boolean MAX, int depth, int MAX_DEPTH){
        double val;
        int[][][] actList = actList(board);
        ShogiPiece[][][] tempChildList = childList(board, actList);
        ShogiPiece[][][] childList = new ShogiPiece[actList.length][10][9];

        for(int i = 0; i < actList.length; i++){
            for(int j = 0; j < tempChildList[i].length; j++){
                for(int k = 0; k < tempChildList[i][j].length; k++){
                    if(tempChildList[i][j][k] != null){
                        childList[i][j][k] = new ShogiPiece(tempChildList[i][j][k].getRow(), tempChildList[i][j][k].getCol(), tempChildList[i][j][k].getPiece());
                        childList[i][j][k].setPlayer(tempChildList[i][j][k].getPlayer());
                        childList[i][j][k].promotePiece(tempChildList[i][j][k].getPromoted());
                    }
                }
            }
        }

        if(depth > MAX_DEPTH){
            return 0.5 + Math.random()*Math.random();
        }

        double bestVal = MAX ? -10 : +10;
        for (ShogiPiece[][] aChildList : childList) {
            val = eval(aChildList, !MAX, depth + 1, MAX_DEPTH);
            if(MAX && val > bestVal){
                bestVal = val;
                for(int i = 0; i < aChildList.length; i++){
                    for(int j = 0; j < aChildList[i].length; j++){
                        if(aChildList[i][j] != null){
                            bestChild[i][j] = new ShogiPiece(aChildList[i][j].getRow(), aChildList[i][j].getCol(), aChildList[i][j].getPiece());
                            bestChild[i][j].setPlayer(aChildList[i][j].getPlayer());
                            bestChild[i][j].promotePiece(aChildList[i][j].getPromoted());
                        }
                    }
                }
            }else if(!MAX && -val < bestVal){
                bestVal = -val;
                for(int i = 0; i < aChildList.length; i++){
                    for(int j = 0; j < aChildList[i].length; j++){
                        if(aChildList[i][j] != null){
                            bestChild[i][j] = new ShogiPiece(aChildList[i][j].getRow(), aChildList[i][j].getCol(), aChildList[i][j].getPiece());
                            bestChild[i][j].setPlayer(aChildList[i][j].getPlayer());
                            bestChild[i][j].promotePiece(aChildList[i][j].getPromoted());
                        }
                    }
                }
            }
        }

        return bestVal;
    }

    public ShogiPiece[][] getBestChild(){ return bestChild; }
}