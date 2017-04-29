package edu.up.cs371.resop18.shogi.shogi;

import java.util.Random;

import edu.up.cs371.resop18.shogi.game.Game;
import edu.up.cs371.resop18.shogi.game.GamePlayer;

/**
 * @author Ryan Fredrickson
 */

public class ShogiDumbAI {
    private ShogiPiece piece;
    private ShogiGameState state;
    private LegalMoves getLegalMoves = new LegalMoves(1);
    private Game game;

    public ShogiDumbAI(ShogiGameState currState, Game currGame){
        this.game = currGame;
        this.state = currState;
        sleep(500); //Sleeps for 2 second before making move
    }

    public void sleep(int time){
        try { Thread.sleep(time); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    public int randInt(int min, int max){ return new Random().nextInt((max - min) + 1) + min; }

    /**
     * Will make random check positions on the board until it can find a piece that can move and then make random move
     *
     * @param player
     */
    public void dumbAI(GamePlayer player){
        ShogiPiece[][] newBoard = state.getCurrentBoard(); //Gets current board
        int[][] possibleMoves; //Declaration of possible moves

        int row, col, newRow, newCol;

        /*
         * This will continue to check random locations on the board
         * until a legal move can be make with a piece that belongs to the AI
         */
        while(true){
            row = randInt(1, 9); //Randomly gets a row on the board
            col = randInt(0, 8); //Randomly gets a col on the board

            /*
             * Checks if there is a piece in the randomly chosen location
             * and if the piece belong to the AI, it makes the move
             */
            if(newBoard[row][col] != null && !newBoard[row][col].getPlayer()){
                piece = newBoard[row][col];

                //Gets the possible moves for the piece selected
                possibleMoves = getLegalMoves.moves(state.getCurrentBoard(), piece.getPiece(), piece.getRow(), piece.getCol());

                //Selects the king or pawn at a smaller frequency than other pieces
                //Checks if there is a legal move and if there is, it selects that piece
                if((piece.getPiece().equals("King") || piece.getPiece().equals("Pawn")) && new Random().nextDouble() < 0.15){
                    for(int a = 0; a < possibleMoves.length; a++){
                        if(possibleMoves[a] != null){ break; }
                    }
                }

                //Breaks if there are legal moves for the piece
                if(possibleMoves[0] != null){ break; }
            }
        }

        int a = 0;
        for(a = 0; a < possibleMoves.length; a++){
            if(possibleMoves[a] == null){ break; }
        }

        while(true){
            int randMove = (a == 0) ? 0 : randInt(0, a-1);
            if(possibleMoves[randMove] != null){
                newRow = possibleMoves[randMove][0];
                newCol = possibleMoves[randMove][1];
                break;
            }
        }

        piece.setSelected(false);
        newBoard[row][col].setSelected(false);

        //wait until the human is not in the options menu
        while(ShogiHumanPlayer.checkOption){ sleep(1000); }

        game.sendAction(new ShogiMoveAction(player, newBoard[row][col], newRow, newCol, row, col));
    }

    /**
     * Will dermine if a piece can move and, if not, will make a random move
     *
     * @param player
     */
    public void smartAI(GamePlayer player){
        ShogiPiece[][] board = state.getCurrentBoard(); //Gets current board
        int[][] possibleMoves; //Declaration of possible moves

        piece = null;

        for(int row = 1; row < board.length-1; row++){
            if(piece != null){ break; }
            for(int col = 0; col < board.length; col++){
                if(board[row][col].getPiece().equals("King")){
                    piece = board[row][col];
                    break;
                }
            }
        }

        if(piece != null){
            if(state.determinePlayerInCheck(1, board, piece.getRow(), piece.getCol())){

            }
        }


        piece = null;
        for(int row = 1; row < board.length-1; row++){
            for(int col = 0; col < board[row].length; col++){
                if(board[row][col] == null || board[row][col].getPlayer()){ continue; }
                if(canCapture(board, row, col)){
                    piece = board[row][col];
                    possibleMoves = getLegalMoves.moves(board, piece.getPiece(), piece.getRow(), piece.getCol());
                    for(int i = 0; i < possibleMoves.length; i++){
                        if(possibleMoves[i] == null){ continue; }
                        int newRow = possibleMoves[i][0];
                        int newCol = possibleMoves[i][1];
                        if(board[newRow][newCol] == null){ continue; }
                        if(board[row][col].getPlayer() != board[newRow][newCol].getPlayer()){
                            //wait until the human is not in the options menu
                            while(ShogiHumanPlayer.checkOption) { sleep(1000); }
                            game.sendAction(new ShogiMoveAction(player, board[row][col], newRow, newCol, row, col));
                            return;
                        }
                    }
                }
            }
        }

        int newRow, newCol, row, col;
        if(hasCapturedPieces() && Math.random() < 0.1){
            ShogiPiece[][] oppCaptured = state.getCurrentBoard();
            for(int i = 0; i < oppCaptured.length; i++){
                if(oppCaptured[i] == null){ continue; }
                    piece = oppCaptured[0][i];
                    row = piece.getRow();
                    col = piece.getCol();

                    possibleMoves = getLegalMoves.moves(board, piece.getPiece(), row, col);
                    for(int iterMoves = 0; iterMoves < possibleMoves.length; iterMoves++){
                        if(possibleMoves[iterMoves] == null){ continue; }

                        //wait until the human is not in the options menu
                        while(ShogiHumanPlayer.checkOption){ sleep(1000); }
                        game.sendAction(new ShogiDropAction(player, piece,
                                possibleMoves[iterMoves][0], possibleMoves[iterMoves][1], row, col));
                        return;
                    }
            }
        }
        dumbAI(player);
    }

    /**
     * Check if a piece at a certain location can capture an enemy piece
     *
     * @param board current board
     * @param row row of piece to check
     * @param col col of piece to check
     * @return boolean if piece can capture enemy piece
     */
    public boolean canCapture(ShogiPiece[][] board, int row, int col){
        piece = board[row][col];
        int[][] moves = getLegalMoves.moves(board, piece.getPiece(), piece.getRow(), piece.getCol());
        for(int a = 0; a < moves.length; a++){
            if(moves[a] == null){ continue; }
            int newRow = moves[a][0];
            int newCol = moves[a][1];
            if(board[newRow][newCol] == null){ continue; }
            if(piece.getPlayer() != board[newRow][newCol].getPlayer()){ return true; }
        }
        piece = null;
        return false;
    }

    /**
     *
     * @return boolean if there is a piece that can be captured
     */
    public boolean hasCapturedPieces(){
        ShogiPiece[][] opponentCaptured = state.getCurrentBoard();
        for(int i = 0; i < opponentCaptured[0].length; i++){
            if(opponentCaptured[0][i] != null){ return true; }
        }
        return false;
    }
}