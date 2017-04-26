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
    }

    public int randInt(int min, int max){ return new Random().nextInt((max - min) + 1) + min; }

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

                //Selects pawns at a smaller frequency than other pieces
                if(piece.getPiece().equals("Pawn") && new Random().nextDouble() < 0.15){ break; }

                //Selects the king at a smaller frequency than other pieces
                if(piece.getPiece().equals("King") && new Random().nextDouble() < 0.15){ break; }

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

        game.sendAction(new ShogiMoveAction(player, newBoard[row][col], newRow, newCol, row, col));
    }

    public void smartAI(GamePlayer player){
        ShogiPiece[][] newBoard = state.getCurrentBoard(); //Gets current board
        int[][] possibleMoves; //Declaration of possible moves
    }

    public boolean canCapture(ShogiPiece[][] board){
        for(int i = 1; i < board.length - 1; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] == null){ continue; }
                int[][] moves = getLegalMoves.moves(board, board[i][j].getPiece(), board[i][j].getRow(), board[i][j].getCol());
                for(int a = 0; a < moves.length; a++){
                    if(moves[i] == null){ continue; }
                    int newRow = moves[a][0];
                    int newCol = moves[a][1];
                    if(board[newRow][newCol].getPlayer()){ return true; }
                }
            }
        }
        return false;
    }
}
