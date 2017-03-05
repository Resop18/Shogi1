package edu.up.cs371.resop18.shogi.shogi;

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

    public int[][][] actList(shogiPiece[][] board){
        legalMoves m = new legalMoves();
        int[][][] actList = new int[20][16][];
        for(int a = 0; a < actList.length; a++){
            for (shogiPiece[] aBoard : board) {
                for (shogiPiece anABoard : aBoard) {
                    actList[a] = m.moves(board, anABoard);
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
        for (shogiPiece[][] aChildList : childList) {
            val = eval(aChildList, !MAX, depth + 1, smartAI);
            System.out.println("hi");
            if (MAX && val > bestVal) {
                bestVal = val;
                bestChild = aChildList;
            } else if (!MAX && val < bestVal) {
                bestVal = val;
                bestChild = aChildList;
            }
        }

        return bestVal;
    }
}
