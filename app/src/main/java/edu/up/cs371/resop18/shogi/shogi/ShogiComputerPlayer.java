package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GameComputerPlayer;
import edu.up.cs371.resop18.shogi.game.infoMsg.GameInfo;

/**
 * @author Ryan Fredrickson
 */

public class ShogiComputerPlayer extends GameComputerPlayer {
    public ShogiComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        ShogiGameState gameState = (ShogiGameState)info;
        //game.sendAction(new ShogiMovePiece(this));
    }
}