package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.LocalGame;
import edu.up.cs371.resop18.shogi.game.actionMsg.GameAction;

/**
 * @author Ryan Fredrickson
 * @author Javier Resop
 */
public class ShogiLocalGame extends LocalGame {
    private ShogiGameState gameState;

    public ShogiLocalGame(){
        this.gameState = new ShogiGameState();
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new ShogiGameState(gameState));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() { return null; }

    @Override
    protected boolean makeMove(GameAction action) {
        ShogiMoveAction move = (ShogiMoveAction)action;

        return false;
    }
}
