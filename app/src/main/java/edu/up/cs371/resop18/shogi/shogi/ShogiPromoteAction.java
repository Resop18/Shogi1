package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Ryan Fredrickson
 * @author Chase Des Laurier
 */

public class ShogiPromoteAction extends GameAction {
    private shogiPiece promotedPiece = null;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ShogiPromoteAction(GamePlayer player) {
        super(player);
    }

    public void setPromotedPiece(shogiPiece piece){ this.promotedPiece = piece; }

    public void promotePiece(){ this.promotedPiece.promotePiece(true); }
}
