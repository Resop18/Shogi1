package edu.up.cs371.resop18.shogi.shogi;

import java.util.ArrayList;

import edu.up.cs371.resop18.shogi.game.GameMainActivity;
import edu.up.cs371.resop18.shogi.game.GamePlayer;
import edu.up.cs371.resop18.shogi.game.LocalGame;
import edu.up.cs371.resop18.shogi.game.config.GameConfig;
import edu.up.cs371.resop18.shogi.game.config.GamePlayerType;

/*
 * @author Javier Resop
 */

public class ShogiMainActivity extends GameMainActivity {
    private static final int PORT_NUMBER = 3452;


    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // a human player player type (player type 0)
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new ShogiHumanPlayer(name);
            }});

        // a computer player type (player type 1)
        playerTypes.add(new GamePlayerType("Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new ShogiComputerPlayer(name);
            }});

        // a computer player type (player type 2)
        /*playerTypes.add(new GamePlayerType("Computer Player (GUI)") {
            public GamePlayer createPlayer(String name) {
                return new CounterComputerPlayer2(name);
            }});*/

        // Create a game configuration class for Counter:
        // - player types as given above
        // - from 1 to 2 players
        // - name of game is "Counter Game"
        // - port number as defined above
        GameConfig defaultConfig = new GameConfig(playerTypes, 1, 2, "Shogi", PORT_NUMBER);

        defaultConfig.setUserModifiable(false);

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

    @Override
    public LocalGame createLocalGame() {
        return new ShogiLocalGame();
    }
}
