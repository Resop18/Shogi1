package edu.up.cs371.resop18.shogi.shogi;

/**
 * @author Ryan Fredrickson
 */

public class shogiAI {
    private shogiPiece[][] bestChild;

    public shogiAI(){
        shogiPiece[][] gameBoard = new ShogiGameState().Pieces;
        double bestVal = eval(gameBoard, true, 0, true);

        System.out.println(bestVal);

        for (shogiPiece[] aBestChild : bestChild) {
            for (shogiPiece anABestChild : aBestChild) {
                if (anABestChild != null) {
                    System.out.println(anABestChild.getPiece());
                }
            }
        }
    }

    private shogiPiece[][] newGameState(shogiPiece[][] board, int[] move){
        legalMoves m = new legalMoves(new ShogiGameState().isPlayersTurn);
        int[][] movesList = new int[100][];
        for(int i = 0; i < movesList.length; i++) {
            for(int j = 0; j < board.length; j++) {
                for(int k = 0; k < board[j].length; k++){
                    if(board[j][k] != null) {
                        movesList = m.moves(board, board[j][k]);
                        for(int[] b : movesList){
                            if(board[b[0]][b[1]] == null){
                                board[b[0]][b[1]] = new shogiPiece(b[0], b[1], board[j][k].getPiece());
                            }
                        }
                    }
                }
            }
        }

        return board;
    }

    private int[][][] actList(shogiPiece[][] board){
        legalMoves m = new legalMoves(new ShogiGameState().isPlayersTurn);
        int[][][] actList = new int[40][16][];
        for(int a = 0; a < actList.length; a++){
            for (shogiPiece[] aBoard : board) {
                for (shogiPiece anABoard : aBoard) {
                    actList[a] = m.moves(board, anABoard);
                }
            }
        }
        return actList;
    }

    private shogiPiece[][][] childList(shogiPiece[][] board, int[][][] actList){
        shogiPiece[][][] list = new shogiPiece[100][9][9];
        for(shogiPiece[][] aList : list){
            for (int[][] anActList : actList) {
                for (int[] anAnActList : anActList) {
                    aList = newGameState(board, anAnActList);
                }
            }

        }

        return list;
    }

    private double eval(shogiPiece[][] board, boolean MAX, int depth, boolean smartAI){
        int MAX_DEPTH = smartAI ? 4 : 1;
        double val;
        int[][][] actList = actList(board);
        shogiPiece[][][] childList = childList(board, actList);

        if(depth > MAX_DEPTH){
            return 0.5 + Math.random();
        }

        double bestVal = MAX ? -10 : +10;
        for (shogiPiece[][] aChildList : childList) {
            val = eval(aChildList, !MAX, depth + 1, smartAI);
            System.out.println("hi");
            if(MAX && val > bestVal){
                bestVal = val;
                bestChild = aChildList;
            }else if(!MAX && val < bestVal){
                bestVal = val;
                bestChild = aChildList;
            }else{
                bestVal = 0;
                bestChild = aChildList;
            }
        }

        return bestVal;
    }
}
