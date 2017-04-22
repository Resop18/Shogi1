package edu.up.cs371.resop18.shogi.shogi;

public class ShogiAI {
    public ShogiGameState gameState;
    private ShogiPiece[][] bestChild = new ShogiPiece[10][9];

    protected int numMoves = 30;
    protected int oldRow, oldCol;
    protected int newRow, newCol;
    private int count = 0;

    /**
     * ShogiAI Constructor which takes in the Game State and Max Depth desired in the minimax evaluation
     *
     * @param gState the current Game State
     * @param MAX_DEPTH
     */
    public ShogiAI(ShogiGameState gState, int MAX_DEPTH){
        gameState = gState;
        ShogiPiece[][] gameBoard = gameState.pieces; //Gets the current game board

        int depth = 0;

        double start = (double)System.currentTimeMillis();
        double bestVal = eval(gameBoard, -1000.0, 1000.0, true, depth, MAX_DEPTH);
        double end = (double)System.currentTimeMillis();
        timeMinutes(start, end, depth);
    }

    /**
     * Calculates and displays how long an action took.
     *
     * @param start
     * @param end
     * @param depth
     */
    private void timeMinutes(double start, double end, int depth){
        double seconds = (end-start)/1000;
        int minutes = 0;
        while(seconds >= 60){
            seconds -= 60;
            minutes++;
        }
        System.out.println("It took "+minutes+" minutes "+seconds+" seconds for depth "+depth+".");
    }

    /**
     * Calculates and displays how long an action took.
     *
     * @param start
     * @param end
     */
    public void printTime(double start, double end){ System.out.println("It took " + (end-start)/1000 + " seconds."); }

    /**
     * Takes a parameter of a board and prints out the board
     *
     * @param board
     */
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

    /**
     * Returns best child board
     *
     * @return returns the best child
     */
    public ShogiPiece[][] getBestChild(){ return bestChild; }

    /**
     * Gives the max of two values
     *
     * @param a
     * @param b
     * @return returns the max value between a and b
     */
    public double max(double a, double b){ return (a > b) ? a : b; }


    /**
     * Give the min of two values
     *
     * @param a
     * @param b
     * @return returns the min value between a and b
     */
    public double min(double a, double b){ return (a < b) ? a : b; }

    /**
     * Takes in a state of the board and a move.
     * Then, it will return a new state of the board based on the move.
     *
     * @param board
     * @param move
     * @return returns new board based on move
     */
    public ShogiPiece[][] newGameState(ShogiPiece[][] board, int[] move){
        ShogiPiece[][] newBoard = new ShogiPiece[10][9];

        //Deep copy the board into newBoard
        for(int i = 0; i < newBoard.length; i++){
            for(int j = 0; j < newBoard[i].length; j++){
                if(board[i][j] != null){
                    newBoard[i][j] = new ShogiPiece(board[i][j].getRow(), board[i][j].getCol(), board[i][j].getPiece());
                    newBoard[i][j].setPlayer(board[i][j].getPlayer());
                    newBoard[i][j].promotePiece(board[i][j].getPromoted());
                }
            }
        }

        //Setting the new row and col
        int row = move[0];
        int col = move[1];

        //Setting the old row and col
        int oldRow = move[2];
        int oldCol = move[3];

        //Performing the move action
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

    /**
     * Uses the board to create all possible legal moves for the current state
     *
     * @param board
     * @return returns all the legal moves for the current board
     */
    public int[][][] actList(ShogiPiece[][] board, boolean max){
        LegalMoves m;
        int maxPlayer = max ? 1 : 0;
        m = new LegalMoves(maxPlayer); //Sets the LegalMoves to look for the AI's moves

        int[][][] list = new int[numMoves][20][4]; //Create array for moves
        for(int a = 0; a < list.length; a++){
            for (ShogiPiece[] aBoard : board) {
                for (ShogiPiece anABoard : aBoard){
                    if(max) {
                        if (anABoard != null) {
                            if (!anABoard.getPlayer()) {
                                //Gets all moves for current piece
                                int[][] possibleMoves = m.moves(board, anABoard.getPiece(), anABoard.getRow(), anABoard.getCol());

                                //Adds all moves for piece to list of legal moves
                                for (int i = 0; i < 20; i++) {
                                    if (possibleMoves[i] == null) {
                                        continue;
                                    }
                                    list[a][i][0] = possibleMoves[i][0]; //Add new row to move
                                    list[a][i][1] = possibleMoves[i][1]; //Add new col to move

                                    list[a][i][2] = anABoard.getRow(); //Add current row to move
                                    list[a][i][3] = anABoard.getCol(); //Add current col to move
                                }
                            }
                        }
                    } else{
                        if (anABoard != null) {
                            if (anABoard.getPlayer()) {
                                //Gets all moves for current piece
                                int[][] possibleMoves = m.moves(board, anABoard.getPiece(), anABoard.getRow(), anABoard.getCol());

                                //Adds all moves for piece to list of legal moves
                                for (int i = 0; i < 20; i++) {
                                    if (possibleMoves[i] == null) {
                                        continue;
                                    }
                                    list[a][i][0] = possibleMoves[i][0]; //Add new row to move
                                    list[a][i][1] = possibleMoves[i][1]; //Add new col to move

                                    list[a][i][2] = anABoard.getRow(); //Add current row to move
                                    list[a][i][3] = anABoard.getCol(); //Add current col to move
                                }
                            }
                        }
                    }
                }
            }
        }

        //System.out.println("Length of actList:" + list.length);
        return list;
    }

    /**
     * Using the board and actList, childList will create all possible next states of the board
     * and return them as a 3D array
     *
     * @param board
     * @param actList
     * @return returns all next possible states of the board based on actList
     */
    private ShogiPiece[][][] childList(ShogiPiece[][] board, int[][][] actList){
        ShogiPiece[][][] list = new ShogiPiece[actList.length][10][9];

        //System.out.println("Creating new childList");

        //This section is what takes the longest
        double start = (double)System.currentTimeMillis();

        //Goes through the length of actList to get moves
        for(int a = 0; a < actList.length; a++){
        for(int i = 0; i < actList[a].length; i++) {
            if(actList[a][i] == null){ break; } //If there are no moves it will stop stop
            for(int j = 0; j < actList[a][i].length; j++) {
                if(actList[a][i][j] < 0){ break; } //If there are no moves it will stop stop

                //Creates to state of the board based on the current board and the move at actList[i][j]
                ShogiPiece[][] localBoard = newGameState(board, actList[a][i]);

                //Deep copies the localBoard into 'list', which is a list of all next states of the board
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
        }}
        double end = (double)System.currentTimeMillis();
        //printTime(start, end);
        //System.out.println("Length of childList: " + list.length);

        return list;
    }

    /**
     * This evaluated the current state of the board and branches out using recursion
     * to determine which moves is the best to take
     *
     * @param board
     * @param alpha
     * @param beta
     * @param MAX
     * @param depth
     * @param MAX_DEPTH
     * @return
     */
    public double eval(ShogiPiece[][] board, double alpha, double beta, boolean MAX, int depth, int MAX_DEPTH){
        double val; //Temporary holder for evaluation of child states
        int[][][] actList = actList(board, MAX); //Creates actList
        ShogiPiece[][][] childList = childList(board, actList); //Creates childList

        if(depth > MAX_DEPTH){
            return 0.5 + Math.random()*Math.random(); //Temporary heuristics until AI is known to work
        }

        double bestVal = MAX ? -10 : +10; //temp max and min value for bestVal

        //Iterates through child next possible states of the board for best child
        for (ShogiPiece[][] aChildList : childList) {
            //Recursion call for next board states
            val = eval(aChildList, alpha, beta, !MAX, depth + 1, MAX_DEPTH);

            /*
             * Checks who's move it is and compares the evaluation of the child to the current bestVal
             * which ever child state is the best becomes assigned to bestChild
             */
            if(MAX && -val > bestVal){
                bestVal = max(-val, bestVal);
                alpha = max(alpha, bestVal);
                bestChild = aChildList;
                if(beta <= alpha){ break; } //Alpha-beta pruning
            }else if(!MAX && val < bestVal){
                bestVal = min(val, bestVal);
                beta = min(beta, bestVal);
                bestChild = aChildList;
                if(beta <= alpha){ break; } //Alpha-beta pruning
            }
        }

        //Returns alpha, beta, or bestVal depending on who is the maximizing player
        /*if(depth > 0){
            return MAX ? alpha : beta;
        }else */if(depth == 0) {
            System.out.println("Best Child:");
            printBoard(bestChild);
        }
        return bestVal; //returns bestVal
    }
}