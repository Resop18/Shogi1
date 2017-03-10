package edu.up.cs371.resop18.shogi.shogi;

/**
 * @author Ryan Fredrickson
 */

public class ShogiAI {
    private ShogiPiece[][] bestChild;

    public ShogiAI(){
        ShogiPiece[][] gameBoard = new ShogiGameState().pieces;
        double bestVal = eval(gameBoard, true, 0, true);

        System.out.println(bestVal);

        for (ShogiPiece[] aBestChild : bestChild) {
            for (ShogiPiece anABestChild : aBestChild) {
                if (anABestChild != null) {
                    System.out.println(anABestChild.getPiece());
                }
            }
        }
    }

    private ShogiPiece[][] newGameState(ShogiPiece[][] board, int[] move){
        LegalMoves m = new LegalMoves(new ShogiGameState().isPlayersTurn);
        int[][] movesList = new int[100][];
        for(int i = 0; i < movesList.length; i++) {
            for(int j = 0; j < board.length; j++) {
                for(int k = 0; k < board[j].length; k++){
                    if(board[j][k] != null) {
                        movesList = m.moves(board, board[j][k].getPiece(), board[j][k].getRow(), board[j][k].getCol(), board[j][k].getPlayer());
                        for(int[] b : movesList){
                            if(board[b[0]][b[1]] == null){
                                board[b[0]][b[1]] = new ShogiPiece(b[0], b[1], board[j][k].getPiece());
                            }
                        }
                    }
                }
            }
        }

        return board;
    }

    private int[][][] actList(ShogiPiece[][] board){
        LegalMoves m = new LegalMoves(new ShogiGameState().isPlayersTurn);
        int[][][] actList = new int[40][16][];
        for(int a = 0; a < actList.length; a++){
            for (ShogiPiece[] aBoard : board) {
                for (ShogiPiece anABoard : aBoard) {
                    actList[a] = m.moves(board, anABoard.getPiece(), anABoard.getRow(), anABoard.getCol(), anABoard.getPlayer());
                }
            }
        }
        return actList;
    }

    private ShogiPiece[][][] childList(ShogiPiece[][] board, int[][][] actList){
        ShogiPiece[][][] list = new ShogiPiece[100][9][9];
        for(ShogiPiece[][] aList : list){
            for (int[][] anActList : actList) {
                for (int[] anAnActList : anActList) {
                    aList = newGameState(board, anAnActList);
                }
            }

        }

        return list;
    }

    private double eval(ShogiPiece[][] board, boolean MAX, int depth, boolean smartAI){
        int MAX_DEPTH = smartAI ? 4 : 1;
        double val;
        int[][][] actList = actList(board);
        ShogiPiece[][][] childList = childList(board, actList);

        if(depth > MAX_DEPTH){
            return 0.5 + Math.random();
        }

        double bestVal = MAX ? -10 : +10;
        for (ShogiPiece[][] aChildList : childList) {
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
