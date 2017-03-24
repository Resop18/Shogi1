package edu.up.cs371.resop18.shogi;

import org.junit.Test;

import edu.up.cs371.resop18.shogi.shogi.ShogiGameState;
import edu.up.cs371.resop18.shogi.shogi.ShogiLocalGame;
import edu.up.cs371.resop18.shogi.shogi.ShogiPiece;

import static org.junit.Assert.*;

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

        assertEquals(pieces[0][0].getPiece(), "Lance");
        assertEquals(pieces[2][0].getPiece(), "Pawn");
    }

    @Test
    public void testShogiPieceRow() throws Exception {
        ShogiGameState gameState = new ShogiGameState();
        ShogiPiece[][] pieces = gameState.getCurrentBoard();

        assertEquals(pieces[0][0].getRow(), 0);
        assertEquals(pieces[2][0].getRow(), 2);
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

        assertTrue(pieces[2][0].legalMove(pieces, 3, 0));
        assertFalse(pieces[2][0].legalMove(pieces, 4, 8));
    }

    @Test
    public void testPromote() throws Exception {
        ShogiGameState gameState = new ShogiGameState();
        ShogiPiece[][] pieces = gameState.getCurrentBoard();

        pieces[0][0].promotePiece(true);

        assertTrue(pieces[0][0].getPromoted());
    }

    @Test
    public void testCanMove() throws Exception {
        //ShogiGameState gameState = new ShogiGameState();
        ShogiLocalGame shogiLocalGame = new ShogiLocalGame();

        //shogiLocalGame.gameState.setPlayerTurn(1);

        //assertTrue(shogiLocalGame.canMove(1));
    }
}