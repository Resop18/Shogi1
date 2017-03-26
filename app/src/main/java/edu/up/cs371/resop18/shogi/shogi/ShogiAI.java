package edu.up.cs371.resop18.shogi.shogi;

import android.util.Log;

public class ShogiAI {
    ShogiGameState gameState;
    private ShogiPiece[][] bestChild;

    public ShogiAI(ShogiGameState gState){
        gameState = gState;
        ShogiPiece[][] gameBoard = gameState.pieces;
        double bestVal = eval(gameBoard, true, 0, false);
    }

    private ShogiPiece[][] newGameState(ShogiPiece[][] board, int[] move){
        LegalMoves m = new LegalMoves(100);
        int[][] movesList;
        for(int j = 0; j < 9; j++) {
            for(int k = 0; k < 9; k++){
                if(board[j][k] != null) {
                    movesList = m.moves(board, board[j][k].getPiece(), board[j][k].getRow(), board[j][k].getCol(), board[j][k].getPlayer());
                    for(int[] b : movesList){
                        if(b != null){
                            if(board[b[0]][b[1]] == null || board[b[0]][b[1]].getPlayer() != board[j][k].getPlayer()){
                                try{
                                    board[b[0]][b[1]] = new ShogiPiece(b[0], b[1], board[j][k].getPiece());
                                    board[b[0]][b[1]].setPlayer(board[j][k].getPlayer());
                                    board[b[0]][b[1]].promotePiece(board[j][k].getPromoted());
                                    board[j][k] = null;
                                }catch(Exception ex){ }
                            }
                        }
                    }
                }
            }
        }
        return board;
    }

    private int[][][] actList(ShogiPiece[][] board){
        LegalMoves m = new LegalMoves(0);
        int[][][] list = new int[40][20][];
        int[][] possibleMoves;
        for(int a = 0; a < list.length; a++){
            for (ShogiPiece[] aBoard : board) {
                for (ShogiPiece anABoard : aBoard){
                    if(anABoard != null){
                        possibleMoves = m.moves(board, anABoard.getPiece(), anABoard.getRow(), anABoard.getCol(), anABoard.getPlayer());
                        if(possibleMoves != null){
                            for(int i = 0; i < 20; i++){
                                try{
                                    list[a][i][0] = possibleMoves[i][0];
                                    list[a][i][1] = possibleMoves[i][1];
                                }catch (Exception e){ }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    private ShogiPiece[][][] childList(ShogiPiece[][] board, int[][][] actList){
        ShogiPiece[][][] list = new ShogiPiece[100][9][9];
        int i;
        for(ShogiPiece[][] aList : list){
            for (int[][] anActList : actList) {
                i = 0;
                for (int[] anAnActList : anActList) {
                    aList = newGameState(board, anAnActList);
                    i++;
                    Log.i("i", ""+i);
                }
            }
        }
        return list;
    }

    private double eval(ShogiPiece[][] board, boolean MAX, int depth, boolean smartAI){
        int MAX_DEPTH = smartAI ? 4 : 0;
        double val;
        int[][][] actList = actList(board);
        ShogiPiece[][][] childList = childList(board, actList);

        if(depth > MAX_DEPTH){
            return 0.5 + Math.random()*Math.random();
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

    public ShogiPiece[][] getBestChild(){ return bestChild; }
}