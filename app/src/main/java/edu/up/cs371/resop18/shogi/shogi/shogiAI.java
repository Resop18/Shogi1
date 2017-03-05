package edu.up.cs371.resop18.shogi.shogi;

public class shogiAI {
    shogiPiece[][] bestChild;

    public shogiAI(){
        shogiPiece[][] gameBoard = new ShogiGameState().Pieces;
        double bestVal = eval(gameBoard, true, 0, true);

        System.out.println(bestVal);

        for(int i = 0; i < bestChild.length; i++){
            for(int j = 0; j < bestChild[i].length; j++){
                if(bestChild[i][j] != null){
                    System.out.println(bestChild[i][j].getPiece());
                }
            }
        }
    }

    public int[][][] actList(shogiPiece[][] board){
        legalMoves m = new legalMoves();
        int[][][] actList = new int[20][16][];
        for(int a = 0; a < actList.length; a++){
            for(int i = 0; i < board.length; i++){
                for(int j = 0; j < board[i].length; j++){
                        actList[a] = m.moves(board, board[i][j]);
                    }
            }
        }
        return actList;
    }

    public shogiPiece[][][] childList(shogiPiece[][] board, int[][][] actList){
        shogiPiece[][][] list = new shogiPiece[100][100][];

        return list;
    }

    public double eval(shogiPiece[][] board, boolean MAX, int depth, boolean smartAI){
        int MAX_DEPTH = smartAI ? 4 : 1;
        double val;
        int[][][] actList = actList(board);
        shogiPiece[][][] childList = childList(board, actList);

        if(depth > MAX_DEPTH){
            return 1.0 + Math.random();
        }

        double bestVal = MAX ? -10 : +10;
        for(int i = 0; i < childList.length; i++){
            val = eval(childList[i], !MAX, depth+1, smartAI);
            System.out.println("hi");
            if(MAX && val > bestVal){
                bestVal = val;
                bestChild = childList[i];
            }else if(!MAX && val < bestVal){
                bestVal = val;
                bestChild = childList[i];
            }
        }

        return bestVal;
    }

    public static void main(String [] args){
        new shogiAI();
    }
}
