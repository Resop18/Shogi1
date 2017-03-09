package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Ryan Fredrickson
 * @author Chase Des Laurier
 */

public class ShogiMoveAction extends GameAction {
    private shogiPiece movedPiece = null;
    private shogiPiece capturedPiece = null;

    int row, col;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ShogiMoveAction(GamePlayer player) {
        super(player);
    }

    public void setMovedPiece(shogiPiece piece){ this.movedPiece = piece; }

    public void movePiece(int newRow, int newCol){
        this.row = newRow;
        this.col = newCol;
    }
}
