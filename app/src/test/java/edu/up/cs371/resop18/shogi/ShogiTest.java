package edu.up.cs371.resop18.shogi;

import org.junit.Test;

import edu.up.cs371.resop18.shogi.shogi.ShogiDumbAI;
import edu.up.cs371.resop18.shogi.shogi.ShogiDumbComputerPlayer;
import edu.up.cs371.resop18.shogi.shogi.ShogiGameState;
import edu.up.cs371.resop18.shogi.shogi.ShogiLocalGame;
import edu.up.cs371.resop18.shogi.shogi.ShogiPiece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ShogiTest {
    @Test
    public void testShogiPieceName() throws Exception {
        ShogiGameState gameState = new ShogiGameState();
        ShogiPiece[][] pieces = gameState.getCurrentBoard();

        assertEquals(pieces[1][0].getPiece(), "Lance");
        assertEquals(pieces[3][0].getPiece(), "Pawn");
    }

    @Test
    public void testShogiPieceRow() throws Exception {
        ShogiGameState gameState = new ShogiGameState();
        ShogiPiece[][] pieces = gameState.getCurrentBoard();

        assertEquals(pieces[1][0].getRow(), 1);
        assertEquals(pieces[3][0].getRow(), 3);
    }

    @Test
    public void testDeepCopy() throws Exception{
        ShogiGameState gameState = new ShogiGameState();
        ShogiGameState gState = new ShogiGameState(gameState);

        assertFalse((gameState == gState));
    }

    @Test
    public void testLegalMove() throws Exception {
        ShogiGameState gameState = new ShogiGameState();
        ShogiPiece[][] pieces = gameState.getCurrentBoard();

        assertTrue(pieces[3][0].legalMove(pieces, 4, 0));
        assertFalse(pieces[3][0].legalMove(pieces, 4, 8));
    }

    @Test
    public void testPromote() throws Exception {
        ShogiGameState gameState = new ShogiGameState();
        ShogiPiece[][] pieces = gameState.getCurrentBoard();

        pieces[1][0].promotePiece(true);

        assertTrue(pieces[1][0].getPromoted());
    }

    @Test
    public void testCanMove() throws Exception {
        ShogiGameState gameState = new ShogiGameState();
        ShogiLocalGame shogiLocalGame = new ShogiLocalGame();

        shogiLocalGame.gameState.setPlayerTurn(1);

        assertTrue(shogiLocalGame.canMove(1));
    }

	@Test
	public void testCanCapture() throws Exception{
		ShogiGameState gameState = new ShogiGameState();
        ShogiDumbComputerPlayer player = new ShogiDumbComputerPlayer("CPU");
        ShogiPiece[][] board = gameState.getCurrentBoard();

        board[4][0] = new ShogiPiece(4, 0, "Pawn");

        ShogiDumbAI ai = new ShogiDumbAI(gameState, player.getGame());
        assertTrue(ai.canCapture(gameState.getCurrentBoard(), 3, 0));
	}
}