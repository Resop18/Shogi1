package edu.up.cs371.resop18.shogi.shogi;

public class ShogiAI {
    public ShogiGameState gameState;
    private ShogiPiece[][] bestChild = new ShogiPiece[10][9];

    protected int numMoves = 20;
    protected int oldRow, oldCol;
    protected int newRow, newCol;
    private int count = 0;

    public ShogiAI(ShogiGameState gState, int MAX_DEPTH){
        gameState = gState;
        ShogiPiece[][] gameBoard = gameState.pieces;

        double start = (double)System.currentTimeMillis();
        double bestVal = eval(gameBoard, -1000.0, 1000.0, true, 0, MAX_DEPTH);
        double end = (double)System.currentTimeMillis();
        timeMinutes(start, end);

        //System.out.println(""+bestVal);
    }

    private void timeMinutes(double start, double end){
        double seconds = (end-start)/1000;
        int minutes = 0;
        while(seconds >= 60){
            seconds -= 60;
            minutes++;
        }
        System.out.println("It took "+minutes+" minutes "+seconds+" seconds");
    }

    public void printTime(double start, double end){ System.out.println("It took " + (end-start)/1000 + " seconds."); }

    public void printBoard(ShogiPiece[][] board){
        count += 1;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] == null){
                    System.out.print("null ");
                }else{
                    System.out.print(board[i][j].getPiece()+" ");
                }
            }
            System.out.println();
        }
        System.out.println("Count = " + count);
        System.out.println();
    }

    public ShogiPiece[][] getBestChild(){ return bestChild; }

    public double max(double a, double b){ return (a > b) ? a : b; }

    public double min(double a, double b){ return (a < b) ? a : b; }

    public ShogiPiece[][] newGameState(ShogiPiece[][] board, int[] move){
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
        LegalMoves m = new LegalMoves(1);
        int[][][] list = new int[numMoves][20][4];
        //int[][] possibleMoves;
        for(int a = 0; a < list.length; a++){
            for (ShogiPiece[] aBoard : board) {
                for (ShogiPiece anABoard : aBoard){
                    if(anABoard != null){
                        if(!anABoard.getPlayer()){
                            int[][] possibleMoves = m.moves(board, anABoard.getPiece(), anABoard.getRow(), anABoard.getCol());
                            for(int i = 0; i < 20; i++){
                                if(possibleMoves[i] == null){ break; }
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

        System.out.println("Length of actList:" + list.length);
        return list;
    }

    private ShogiPiece[][][] childList(ShogiPiece[][] board, int[][][] actList){
        ShogiPiece[][][] list = new ShogiPiece[actList.length][10][9];

        //System.out.println("Creating new childList");

        //This section is what takes the longest
        double start = (double)System.currentTimeMillis();
        for(int i = 0; i < actList.length; i++) {
            if(actList[i] == null){ break; }
            for(int j = 0; j < actList[i].length; j++) {
                if(actList[i][j] == null){ break; }
                ShogiPiece[][] localBoard = newGameState(board, actList[i][j]);
                for(ShogiPiece[][] aList : list){
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
        double end = (double)System.currentTimeMillis();
        System.out.println("Length of childList: " + list.length);
        //printTime(start, end);

        return list;
    }

    public double eval(ShogiPiece[][] board, double alpha, double beta, boolean MAX, int depth, int MAX_DEPTH){
        double val;
        int[][][] actList = actList(board);
        ShogiPiece[][][] childList = childList(board, actList); //was tempChildList
        //ShogiPiece[][][] childList = new ShogiPiece[actList.length][10][9];


        if(depth > MAX_DEPTH){
            return 0.5 + Math.random()*Math.random();
        }

        /*for(int i = 0; i < tempChildList.length; i++){
            for(int j = 0; j < tempChildList[i].length; j++){
                for(int k = 0; k < tempChildList[i][j].length; k++){
                   if(tempChildList[i][j][k] != null){
                        childList[i][j][k] = new ShogiPiece(tempChildList[i][j][k].getRow(), tempChildList[i][j][k].getCol(), tempChildList[i][j][k].getPiece());
                        childList[i][j][k].setPlayer(tempChildList[i][j][k].getPlayer());
                        childList[i][j][k].promotePiece(tempChildList[i][j][k].getPromoted());
                    }
                }
            }
        }*/

        double bestVal = MAX ? -10 : +10;
        for (ShogiPiece[][] aChildList : childList) {
            val = eval(aChildList, alpha, beta, !MAX, depth + 1, MAX_DEPTH);
            if(MAX && -val > bestVal){
                bestVal = max(-val, bestVal);
                alpha = max(alpha, bestVal);
                /*for(int i = 0; i < aChildList.length; i++){
                    for(int j = 0; j < aChildList[i].length; j++){
                        bestChild[i][j] = null;
                        if(aChildList[i][j] != null){
                            bestChild[i][j] = new ShogiPiece(aChildList[i][j].getRow(), aChildList[i][j].getCol(), aChildList[i][j].getPiece());
                            bestChild[i][j].setPlayer(aChildList[i][j].getPlayer());
                            bestChild[i][j].promotePiece(aChildList[i][j].getPromoted());
                        }
                    }
                }*/
                bestChild = aChildList;
                if(beta <= alpha){ break; }
            }else if(!MAX && val < bestVal){
                bestVal = min(val, bestVal);
                beta = min(beta, bestVal);
                /*for(int i = 0; i < aChildList.length; i++){
                    for(int j = 0; j < aChildList[i].length; j++){
                        bestChild[i][j] = null;
                        if(aChildList[i][j] != null){
                            bestChild[i][j] = new ShogiPiece(aChildList[i][j].getRow(), aChildList[i][j].getCol(), aChildList[i][j].getPiece());
                            bestChild[i][j].setPlayer(aChildList[i][j].getPlayer());
                            bestChild[i][j].promotePiece(aChildList[i][j].getPromoted());
                        }
                    }
                }*/
                bestChild = aChildList;
                if(beta <= alpha){ break; }
            }
        }

        if(depth > 0){
            if(MAX){
                //System.out.println("" + alpha);
                return alpha;
            }else{
                //System.out.println("" + beta);
                return beta;
            }
        }else if(depth == 0) {
            System.out.println("Best Child:");

            printBoard(bestChild);
        }
        return bestVal;
    }
}