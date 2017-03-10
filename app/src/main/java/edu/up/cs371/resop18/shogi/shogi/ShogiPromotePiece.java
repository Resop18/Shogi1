package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Javier Resop
 */

public class ShogiPromotePiece extends GameAction {

    ShogiPiece promotedPiece;
    ShogiPiece[][] allPieces;
    ShogiGameState board;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ShogiPromotePiece(GamePlayer player, ShogiPiece pieces[][], ShogiPiece oldPiece, ShogiGameState gameBoard) {
        super(player);

        this.allPieces = pieces;
        this.promotedPiece = oldPiece;
        this.board = gameBoard;
    }

    public ShogiPiece[][] promotePiece(){
        if(board.getCurrentBoard() == null){
            return null;
        }
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 9; col++){
                if(allPieces[row][col] == promotedPiece){
                    promotedPiece.promotePiece(true);
                    allPieces[row][col]=promotedPiece;
                    return allPieces;
                }
            }
        }
        return null;
    }
}
