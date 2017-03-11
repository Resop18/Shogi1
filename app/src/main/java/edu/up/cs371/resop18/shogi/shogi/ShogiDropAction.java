package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Javier Resop
 */

public class ShogiDropAction extends GameAction {
    private ShogiPiece capturedPiece;
    public int newRow, newCol, oldRow, oldCol;
    public ShogiPiece currPiece;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ShogiDropAction(GamePlayer player, ShogiPiece piece, int newR, int newC, int oldR, int oldC) {
        super(player);
        this.currPiece = piece;
        this.newRow = newR;
        this.newCol = newC;
        this.oldRow = oldR;
        this.oldCol = oldC;
    }
}
