package edu.up.cs371.resop18.shogi.shogi;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;

import edu.up.cs371.resop18.shogi.R;
import edu.up.cs371.resop18.shogi.game.GameMainActivity;
import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.LocalGame;
import edu.up.cs371.resop18.shogi.game.config.GameConfig;
import edu.up.cs371.resop18.shogi.game.config.GamePlayerType;

/*
 * @author Javier Resop
 */

public class ShogiMainActivity extends GameMainActivity implements Serializable
{
    private static final long serialVersionUID = 42978563847L;
    private static final int PORT_NUMBER = 3452;
    //kludge-y but couldn't figure out how to stop the music player when recreating the activity
    //for resetting the activity
    public static MediaPlayer bgm = new MediaPlayer();

    @Override
    public GameConfig createDefaultConfig() {
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.setAudioStreamType(AudioManager.STREAM_MUSIC);
        bgm.setVolume(.2f, .2f);
        bgm.setLooping(true);
        bgm.start();

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // a human player player type (player type 0)
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new ShogiHumanPlayer(name);
            }});

        // a computer player type (player type 1)
        playerTypes.add(new GamePlayerType("Dumb Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new ShogiDumbComputerPlayer(name);
            }});

        // a computer player type (player type 2)
        playerTypes.add(new GamePlayerType("Smart Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new ShogiHardComputerPlayer(name);
            }});

        // Create a game configuration class for Counter:
        // - player types as given above
        // - from 1 to 2 players
        // - name of game is "Counter Game"
        // - port number as defined above
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "Shogi", PORT_NUMBER);

        //defaultConfig.setUserModifiable(false);

        // Add the default players to the configuration
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 1); // player 2: a computer player

        // Set the default remote-player setup:
        // - player name: "Remote Player"
        // - IP code: (empty string)
        // - default player type: human player
        defaultConfig.setRemoteData("Remote Player", "", 0);

        // return the configuration
        return defaultConfig;
    }

    /**
     * External Citation
     *      Date: 29 April 2017
     *      Problem: Creating a settings menu for different music
     *      Resource: CS Upperclassman
     *      Solution: Helped guide me through the process
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // respond to menu item selection
        int itemId = item.getItemId();
        if(item.getItemId() > 0) {
            if (itemId == R.id.bgm1) {
                if (bgm != null) {
                    bgm.stop();
                    bgm = MediaPlayer.create(this, R.raw.bgm);
                }
            } else if (itemId == R.id.bgm2) {
                if (bgm != null) {
                    bgm.stop();
                    bgm = MediaPlayer.create(this, R.raw.bgm2);
                }

            } else if (itemId == R.id.bgm3) {
                if (bgm != null) {
                    bgm.stop();
                    bgm = MediaPlayer.create(this, R.raw.bgm3);
                }
            }
            bgm.setVolume(.2f, .2f);
            bgm.setLooping(true);
            bgm.start();
            return true;
        }
        return false;
    }


    @Override
    public LocalGame createLocalGame() {
        return new ShogiLocalGame();
    }
}