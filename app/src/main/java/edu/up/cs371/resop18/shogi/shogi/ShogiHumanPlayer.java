package edu.up.cs371.resop18.shogi.shogi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.up.cs371.resop18.shogi.R;
import edu.up.cs371.resop18.shogi.game.GameHumanPlayer;
import edu.up.cs371.resop18.shogi.game.GameMainActivity;
import edu.up.cs371.resop18.shogi.game.infoMsg.GameInfo;

/**
 * @author Ryan Fredrickson
 * @author Javier Resop
 */

public class ShogiHumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener{
	private GameMainActivity myActivity;
	private ShogiGameState state;
	private ShogiPiece[][] currPieces;
	private Button optionsButt;
	private Button pressmeButt;
	private Button reset;
	private Vibrator vb;
	private boolean havePieceSelected = false;
	private Integer rowSel, colSel;
	private ShogiGui gui;
	private boolean hasKing = true;
	public static boolean checkOption = false; //for determining if a player is in the options menu


	/**
	 * constructor, obviously
	 *
	 * @param name the name of the human player?
     */
	public ShogiHumanPlayer(String name) { super(name); }


	/**
	 * Unknown what this method does
	 * @return some view
     */
	@Override
	public View getTopView() {
		return myActivity.findViewById(R.id.activity_main);
	}


	/**
	 * used to update the human player's gamestate
	 *
	 * @param info the updated gamestate
     */
	@Override
	public void receiveInfo(GameInfo info) {

		//only update the state if info is a gamestate
		if(info instanceof ShogiGameState){

			if(state != null && state.getCheckAlert()) {


				//display an alert informing the human that the previous move (s)he made
				//was illegal
				AlertDialog.Builder checkAlertBuilder = new AlertDialog.Builder(myActivity);
				checkAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) { } });
				checkAlertBuilder.setMessage("You cannot move there because otherwise you would be in check");

				AlertDialog checkAlertDialog = checkAlertBuilder.create();
				checkAlertDialog.show();

				//reset the game state so that it will not display another
				//check alert unless human player is in check
				state.setCheckAlert(false);
			}

			// update our state; then update the display
			this.state = (ShogiGameState)info;
			this.currPieces = state.getCurrentBoard();
			gui = (ShogiGui)myActivity.findViewById(R.id.ShogiBoard);
			gui.pieces = this.currPieces;
			gui.invalidate();
		}
	}


	/**
	 * unknown what this method does
	 *
	 * @param activity the main activity
     */
	@Override
	public void setAsGui(GameMainActivity activity) {
		// remember the activity
		myActivity = (ShogiMainActivity)activity;

		vb = (Vibrator)myActivity.getSystemService(Context.VIBRATOR_SERVICE);
		// Load the layout resource for our GUI
		activity.setContentView(R.layout.activity_main);

		optionsButt = (Button)myActivity.findViewById(R.id.Options);

		optionsButt.setOnClickListener(this);
		myActivity.findViewById(R.id.ShogiBoard).setOnTouchListener(this);

		// if we have a game state, "simulate" that we have just received
		// the state from the game so that the GUI values are updated
		if (state != null) {
			receiveInfo(state);
		}
	}


	/**
	 * detects when a button was clicked
	 *
	 * @param v the view (button) that was clicked
     */
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.Options){
			checkOption = true;
			myActivity.setContentView(R.layout.options);
			pressmeButt = (Button) myActivity.findViewById(R.id.EasterEgg);
			reset = (Button)myActivity.findViewById(R.id.Reset);
			pressmeButt.setOnClickListener(this);
			reset.setOnClickListener(this);
		}
		else if(v.getId() == R.id.EasterEgg)
		{
			checkOption = false;
			myActivity.setContentView(R.layout.activity_main);
			optionsButt = (Button)myActivity.findViewById(R.id.Options);
			optionsButt.setOnClickListener(this);
			myActivity.findViewById(R.id.ShogiBoard).setOnTouchListener(this);

			//reset the position of the pieces using the most recent state
			if (state != null) {
				receiveInfo(state);
			}
			havePieceSelected = false;
		}
		else if(v.getId() == R.id.Reset){
			ShogiMainActivity.bgm.stop();
			checkOption=false;
			myActivity.recreate();
		}
	}


	/**
	 * this handles a touch on the board so that a move can be made
	 *
	 * @param v the view that was touched, i.e. the human player
	 * @param event
	 *
     * @return true if the listener has "consumed" the event, false otherwise
	 *
     */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int row, col; //used for storing the location of the space that the user tapped


		//dont do anything when it's not the player's turn
		//if(state.getPlayerTurn() != 0){ return false; }


		//dont do anything if we dont have the current piece placements
		if(this.currPieces == null){
			return false;
		}


		//Don't do anything when dragging or lifting touch
		if(event.getAction() != MotionEvent.ACTION_UP) {
			return true;
		}


		//get the row and column of the tapped space
		row = (int)((event.getY() - ShogiGui.topLeftY)/(ShogiGui.spaceDim));
		col = (int)((event.getX() - ShogiGui.topLeftX)/(ShogiGui.spaceDim));
		System.out.print("col: " + col +" row: " + row);


		//dont do anything if the user tapped outside the board
		if(row >= 11 || col >= 9){
			return false;
		}
		else if(row < 0 || col < 0){
			return false;
		}


		//when a piece on the board is currently selected
		if(havePieceSelected) {

			//when a piece is currently selected and
			//the tapped space contains one of the player's own pieces
			//old comment: when the user tapped a space that has one of his/her own pieces
			if (state.getPlayerTurn() == 0) {
				if (currPieces[row][col] != null && currPieces[row][col].getPlayer()) {

					//when the player taps the piece that is already selected, deselect it
					//old comment: This deals with selected and deselecting currPieces
					if (currPieces[row][col].getSelected()) {
						currPieces[row][col].setSelected(false);
						gui.pieceIsSelected = false;
						havePieceSelected = false;
					}


					//when the player taps a piece of his/hers that is not
					//the selected piece, deselect the currently selected
					//piece and select the other tapped piece
					else {

						//find and deselect the currently selected piece
						for (int i = 1; i < 11; i++) {
							for (int j = 0; j < 9; j++) {
								if (currPieces[i][j] != null) {
									if (currPieces[i][j].getSelected()) {
										currPieces[i][j].setSelected(false);
									}
								}
							}
						}


						//select the newly tapped piece
						currPieces[row][col].setSelected(true);
						gui.pieceIsSelected = true;
						rowSel = row;
						colSel = col;
					}
				}


				//if a piece is selected and the tapped space does not contain one of
				//the human player's pieces, then check if the tapped space is a legal
				//move for the currently selected piece. If it is, move the piece
				else if (currPieces[rowSel][colSel].legalMove(currPieces, row, col)) {

					if (rowSel == 10) {
						game.sendAction(new ShogiDropAction(this, currPieces[rowSel][colSel], row, col, rowSel, colSel));
					} else {
						game.sendAction(new ShogiMoveAction(this, currPieces[rowSel][colSel], row, col, rowSel, colSel));
					}

					vb.vibrate(100);

					//reset
					havePieceSelected = false;
					rowSel = -1;
					colSel = -1;
				}


				//if a piece is selected and the tapped space is not a legal move,
				//then leave everything as it is
				else return true;

			}
			else{
				if (currPieces[row][col] != null && !currPieces[row][col].getPlayer()) {

					//when the player taps the piece that is already selected, deselect it
					//old comment: This deals with selected and deselecting currPieces
					if (currPieces[row][col].getSelected()) {
						currPieces[row][col].setSelected(false);
						gui.pieceIsSelected = false;
						havePieceSelected = false;
					}


					//when the player taps a piece of his/hers that is not
					//the selected piece, deselect the currently selected
					//piece and select the other tapped piece
					else {

						//find and deselect the currently selected piece
						for (int i = 0; i < 10; i++) {
							for (int j = 0; j < 9; j++) {
								if (currPieces[i][j] != null) {
									if (currPieces[i][j].getSelected()) {
										currPieces[i][j].setSelected(false);
									}
								}
							}
						}


						//select the newly tapped piece
						currPieces[row][col].setSelected(true);
						gui.pieceIsSelected = true;
						rowSel = row;
						colSel = col;
					}
				}


				//if a piece is selected and the tapped space does not contain one of
				//the human player's pieces, then check if the tapped space is a legal
				//move for the currently selected piece. If it is, move the piece
				else if (currPieces[rowSel][colSel].legalMove(currPieces, row, col)) {

					if (rowSel == 0) {
						game.sendAction(new ShogiDropAction(this, currPieces[rowSel][colSel], row, col, rowSel, colSel));
					} else {
						game.sendAction(new ShogiMoveAction(this, currPieces[rowSel][colSel], row, col, rowSel, colSel));
					}

					vb.vibrate(100);

					//reset
					havePieceSelected = false;
					rowSel = -1;
					colSel = -1;
				}


				//if a piece is selected and the tapped space is not a legal move,
				//then leave everything as it is
				else return true;

			}
		}
		//when no piece is currently selected
		else {

			//when the tapped space is not empty and contains a piece
			//that belongs to the human player
			if(state.getPlayerTurn() == 0){
				if(currPieces[row][col] != null && currPieces[row][col].getPlayer()) {
					this.currPieces[row][col].setSelected(true);
					havePieceSelected = true;
					rowSel = row;
					colSel = col;
				}
			}
			else{
				if(currPieces[row][col] != null && !currPieces[row][col].getPlayer()) {
					this.currPieces[row][col].setSelected(true);
					havePieceSelected = true;
					rowSel = row;
					colSel = col;
				}
			}
		}

		//redraw board with currPieces updated
		gui.invalidate();


		//done
		return true;
	}

	public boolean getHasKing(){
		return hasKing;
	}

	public void setHasKing(boolean hasKing){
		this.hasKing = hasKing;
	}
}