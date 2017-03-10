package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Ryan Fredrickson
 * @author Chase Des Laurier
 */

public class ShogiPromoteAction extends GameAction {
    private ShogiPiece promotedPiece = null;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ShogiPromoteAction(GamePlayer player, ShogiPiece piece) {
        super(player);
        this.promotedPiece = piece;
        /*if (piece.getPlayer() && piece.getRow() <= 2 && piece.getRow() >= 0) {
            piece.promotePiece(true);
        } else if (!piece.getPlayer() && piece.getRow() >= 6 && piece.getRow() < 9) {
            piece.promotePiece(true);
        }*/
    }

    public ShogiPiece getPromotedPiece(){ return this.promotedPiece; }
}
