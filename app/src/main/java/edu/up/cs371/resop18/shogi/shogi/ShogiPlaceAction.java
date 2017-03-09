package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Ryan Fredrickson
 * @author Chase Des Laurier
 */

public class ShogiPlaceAction extends GameAction {
    private int row, col;
    private shogiPiece movedPiece;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ShogiPlaceAction(GamePlayer player, shogiPiece piece, int initRow, int initCol) {
        super(player);
        this.movedPiece = piece;
        this.row = initRow;
        this.col = initCol;
    }

    public shogiPiece getPlacedPiece(){ return this.movedPiece; }
    public int getRow(){ return this.row; }
    public int getCol(){ return this.col; }
}
