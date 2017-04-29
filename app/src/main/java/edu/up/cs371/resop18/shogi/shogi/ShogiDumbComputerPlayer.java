package edu.up.cs371.resop18.shogi.shogi;

import edu.up.cs371.resop18.shogi.game.Game;
import edu.up.cs371.resop18.shogi.game.GameComputerPlayer;
import edu.up.cs371.resop18.shogi.game.infoMsg.GameInfo;

/**
 * @author Ryan Fredrickson
 * @author Javier Resop
 */

public class ShogiDumbComputerPlayer extends GameComputerPlayer {
    private ShogiGameState state; //Declaration of ShogiGameState

    public ShogiDumbComputerPlayer(String name) { super(name); }

    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof ShogiGameState) {
            this.state = (ShogiGameState) info;

            if(state.getPlayerTurn() == 1){
                //Dump AI, which randomly makes moves
                ShogiDumbAI ai = new ShogiDumbAI(state, game);
                ai.dumbAI(this);
            }
        }
    }

    public Game getGame(){
        return game;
    }
}