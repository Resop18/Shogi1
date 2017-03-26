package edu.up.cs371.resop18.shogi.shogi;

public class ShogiAI {
    ShogiGameState gameState;
    private ShogiPiece[][] bestChild;

    public ShogiAI(ShogiGameState gState, boolean smartAI){
        gameState = gState;
        ShogiPiece[][] gameBoard = gameState.pieces;
        double bestVal = eval(gameBoard, true, 0, smartAI);
    }

    private ShogiPiece[][] newGameState(ShogiPiece[][] board, int[] move){
        int row = move[0];
        int col = move[1];

        int oldRow = move[2];
        int oldCol = move[3];

        if(board[oldRow][oldCol] != null){
            if(board[row][col] == null || board[row][col].getPlayer() != board[oldRow][oldCol].getPlayer()){
                try{
                    board[row][col] = new ShogiPiece(row, col, board[oldRow][oldCol].getPiece());
                    board[row][col].setPlayer(board[oldRow][oldCol].getPlayer());
                    board[row][col].promotePiece(board[oldRow][oldCol].getPromoted());
                    board[oldRow][oldCol] = null;
                }catch(Exception e){ }
            }
        }
        return board;
    }

    private int[][][] actList(ShogiPiece[][] board){
        LegalMoves m = new LegalMoves(0);
        int[][][] list = new int[40][20][4];
        int[][] possibleMoves;
        for(int a = 0; a < 40; a++){
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
        ShogiPiece[][][] list = new ShogiPiece[100][10][9];
        for(ShogiPiece[][] aList : list){
            for(int i = 0; i < actList.length; i++) {
                for(int j = 0; j < actList[i].length; j++) {
                    aList = newGameState(board, actList[i][j]);
                }
            }
        }
        return list;
    }

    private double eval(ShogiPiece[][] board, boolean MAX, int depth, boolean smartAI){
        int MAX_DEPTH = smartAI ? 2 : 0;
        double val;
        int[][][] actList = actList(board);
        ShogiPiece[][][] childList = childList(board, actList);

        if(depth > MAX_DEPTH){
            return 0.5 + Math.random()*Math.random();
        }

        double bestVal = MAX ? -10 : +10;
        for (ShogiPiece[][] aChildList : childList) {
            val = eval(aChildList, !MAX, depth + 1, smartAI);
            if(MAX && val > bestVal){
                bestVal = val;
                bestChild = aChildList;
            }else if(!MAX && -val < bestVal){
                bestVal = -val;
                bestChild = aChildList;
            }else{
                bestVal = 0;
                bestChild = aChildList;
            }
        }

        return bestVal;
    }

    public ShogiPiece[][] getBestChild(){ return bestChild; }
}