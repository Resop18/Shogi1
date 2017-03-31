package edu.up.cs371.resop18.shogi.shogi;

import android.content.Context;
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
	private Button undoButt;
	private Button optionsButt;
	private Vibrator vb;
	private boolean havePieceSelected = false;
	private int rowSel, colSel;
	private ShogiGui gui;

	public ShogiHumanPlayer(String name) { super(name); }

	@Override
	public View getTopView() {
		return myActivity.findViewById(R.id.activity_main);
	}

	@Override
	public void receiveInfo(GameInfo info) {
		// ignore the message if it's not a CounterState message
		if(info instanceof ShogiGameState){
			// update our state; then update the display
			this.state = (ShogiGameState)info;
			this.currPieces = state.getCurrentBoard();
			if(this.currPieces == null){ Log.i("sad", "why God why"); }
			gui =(ShogiGui)myActivity.findViewById(R.id.ShogiBoard);
			gui.pieces = this.currPieces;
			gui.invalidate();
		}
	}

	@Override
	public void setAsGui(GameMainActivity activity) {
		// remember the activity
		myActivity = activity;

		vb = (Vibrator)myActivity.getSystemService(Context.VIBRATOR_SERVICE);
		// Load the layout resource for our GUI
		activity.setContentView(R.layout.activity_main);

		undoButt = (Button)myActivity.findViewById(R.id.Undo);
		optionsButt = (Button)myActivity.findViewById(R.id.Options);

		undoButt.setOnClickListener(this);
		optionsButt.setOnClickListener(this);
		myActivity.findViewById(R.id.ShogiBoard).setOnTouchListener(this);

		// if we have a game state, "simulate" that we have just received
		// the state from the game so that the GUI values are updated
		if (state != null) {
			receiveInfo(state);
		}
	}

	//@Override
	//public void sendInfo(GameInfo info) { }

	@Override
	public boolean requiresGui() {
		return false;
	}

	@Override
	public boolean supportsGui() {
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.Undo){
			vb.vibrate(new long[]{0,200,100,200,275,425,100,200,100,200,275,425,100,75,25,75,125,75,25,75,125,100,100}, -1);
		}
		if(v.getId() == R.id.Options){
			myActivity.setContentView(R.layout.options);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int row,col;
		col = 0;
		//if(state == null){ this.state = new ShogiGameState(); }
		//this.currPieces=state.getCurrentBoard();

		if(this.currPieces == null){
			Log.i("null", "currPieces array null somehow");
			return false;
		}
		//Don't do anything when dragging or lifting touch
		if(event.getActionMasked() != MotionEvent.ACTION_DOWN) {
			Log.i("event", "is not dwon");
			return false;
		}

		//check if user tapped inside the board lines
		if(event.getY() > ShogiGui.topLeftY + 10 * ShogiGui.spaceDim || event.getX() > ShogiGui.topLeftX + 9 * ShogiGui.spaceDim){
			return false;
		}
		//butt
		if(event.getY() < ShogiGui.topLeftY){
			return false;
		}

		//This determine space that was tapped
		for(row = 0; row <= 9; row++) {
			if(event.getY() <= ShogiGui.topLeftY + (row + 1) * ShogiGui.spaceDim) {
				for (col = 0; col < 9; col++) {
					if(event.getX() < ShogiGui.topLeftX + (col + 1) * ShogiGui.spaceDim){
						if(havePieceSelected){
							//If you tap if a position when a piece is selected it will move the piece there
							if(currPieces[row][col] == null) {
								//if(ShogiGui.pieceIsSelected){
								for(int i = 0; i <= 9; i++){
									for(int j = 0; j < 9; j++){
										if(currPieces[row][col] == null || (currPieces[row][col]!=null && currPieces[row][col].getPlayer() == false)){
											if(currPieces[rowSel][colSel].getSelected()){
												if(i == 9){
													game.sendAction(new ShogiDropAction(this, currPieces[rowSel][colSel], row, col, rowSel, colSel));
												}

												game.sendAction(new ShogiMoveAction(this, currPieces[rowSel][colSel],  row, col, rowSel, colSel));

												break;
											}
										}
									}
									break;
								}
								//currPieces[row][col].setSelected(false);
								ShogiGui.pieceIsSelected = false;
								havePieceSelected = false;
								//redraw board with currPieces updated

								return true;
							}else {
								if (currPieces[row][col].getPlayer()) {
									//This deals with selected and deselecting currPieces
									if (currPieces[row][col].getSelected()) {
										//This deselects a piece if it is selected
										currPieces[row][col].setSelected(false);
										ShogiGui.pieceIsSelected = false;
									} else {
										//This will select the piece if it is not selected
										for (int i = 0; i < 9; i++) {
											for (int j = 0; j < 9; j++) {
												if (currPieces[i][j] != null) {
													if (currPieces[i][j].getSelected()) {
														currPieces[i][j].setSelected(false);
													}
												}
											}
										}

										currPieces[row][col].setSelected(true);
										ShogiGui.pieceIsSelected = true;
									}
								}
							}
						}else {
							Log.i("tap", "got the tap");
							if (this.currPieces[row][col] != null && currPieces[row][col].getPlayer()) {
								this.currPieces[row][col].setSelected(true);
								havePieceSelected = true;
								rowSel = row;
								colSel = col;
							}
							break;
						}
					}
				}
				break;
			}
		}



		//redraw board with currPieces updated
		gui.invalidate();

		return true;
	}
}