package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Ryan Fredrickson
 * @author Chase Des Laurier
 */

public class ShogiMoveAction extends GameAction {

    private ShogiPiece capturedPiece;
    public int newRow, newCol, oldRow, oldCol;
    public ShogiPiece currPiece;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     * @param piece the piece to be moved
     * @param newR the new row to which shogiPiece Piece will be moved
     * @param newC the new column to which shogiPiece Piece will be moved
     */
    public ShogiMoveAction(GamePlayer player, ShogiPiece piece, int newR, int newC, int oldR, int oldC) {
        super(player);
        this.currPiece = piece;
        this.newRow = newR;
        this.newCol = newC;
        this.oldRow = oldR;
        this.oldCol = oldC;
    }

}
