package edu.up.cs371.resop18.shogi.shogi;

import android.util.Log;

import edu.up.cs371.resop18.shogi.game.GameComputerPlayer;
import edu.up.cs371.resop18.shogi.game.infoMsg.GameInfo;

/**
 * @author Ryan Fredrickson
 */

public class ShogiHardComputerPlayer extends GameComputerPlayer {
    private ShogiGameState state;

    public ShogiHardComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof ShogiGameState){
            this.state = (ShogiGameState)info;
            ShogiAI ai = new ShogiAI(this.state, 0);

            Log.i("Computer Turn", "Made Move");
        }
    }
}
