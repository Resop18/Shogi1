package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.GameComputerPlayer;
import edu.up.cs371.resop18.shogi.game.infoMsg.GameInfo;

/**
 * @author Ryan Fredrickson
 */

public class ShogiComputerPlayer extends GameComputerPlayer {
    private ShogiGameState state;

    public ShogiComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof ShogiGameState){
            this.state = (ShogiGameState)info;
            ShogiAI ai = new ShogiAI(state, 0);
            //game.sendAction(new ShogiMoveAction(this, ai.getBestChild()));
        }
    }
}