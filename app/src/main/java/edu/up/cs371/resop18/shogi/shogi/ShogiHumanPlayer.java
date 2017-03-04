package edu.up.cs371.resop18.shogi.shogi;

import android.view.View;

import edu.up.cs371.resop18.shogi.R;
import edu.up.cs371.resop18.shogi.game.GameHumanPlayer;
import edu.up.cs371.resop18.shogi.game.GameMainActivity;
import edu.up.cs371.resop18.shogi.game.infoMsg.GameInfo;

/**
 * Created by RyanF on 3/4/17.
 */
public class ShogiHumanPlayer extends GameHumanPlayer {
    private GameMainActivity myActivity;
    private ShogiGameState state;

    public ShogiHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.activity_main);
    }

    /*protected void updateDisplay() {
        // set the text in the appropriate widget
        counterValueTextView.setText("" + state.getCounter());
    }*/

    @Override
    public void receiveInfo(GameInfo info) {
        // ignore the message if it's not a CounterState message
        if (!(info instanceof ShogiGameState)) return;

        // update our state; then update the display
        this.state = (ShogiGameState)info;

    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.activity_main);

        // remember the field that we update to display the counter's value
        //this.counterValueTextView = (TextView) activity.findViewById(R.id.counterValueTextView);

        // if we have a game state, "simulate" that we have just received
        // the state from the game so that the GUI values are updated
        if (state != null) {
            receiveInfo(state);
        }
    }

    @Override
    public void sendInfo(GameInfo info) {

    }

    @Override
    public boolean requiresGui() {
        return false;
    }

    @Override
    public boolean supportsGui() {
        return true;
    }
}
