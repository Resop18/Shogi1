package edu.up.cs371.resop18.shogi.shogi;

import android.util.Log;

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
            //ShogiDumbAI ai = new ShogiDumbAI(state);
            //ai.dumbAI();
            //game.sendAction(new ShogiMoveAction(this, ai.dumbAI()));
            //game.sendAction(new ShogiMoveAction(this, ai.piece, ai.newRow, ai.newCol, ai.row, ai.col));
            Log.i("Computer Turn", "Made Move");
        }
    }
}