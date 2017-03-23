package edu.up.cs371.resop18.shogi.shogi;

import android.content.Context;
import android.os.StrictMode;
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

    public ShogiHumanPlayer(String name) {
        super(name);
    }

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
            if(this.currPieces==null){Log.i("sad", "why God why");}
            myActivity.findViewById(R.id.ShogiBoard).invalidate();
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        // remember the activity
        myActivity = activity;

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        vb = (Vibrator)myActivity.getSystemService(Context.VIBRATOR_SERVICE);
        // Load the layout resource for our GUI
        activity.setContentView(R.layout.activity_main);

        undoButt = (Button)myActivity.findViewById(R.id.Undo);
        optionsButt = (Button)myActivity.findViewById(R.id.Options);

        undoButt.setOnClickListener(this);
        optionsButt.setOnClickListener(this);
        myActivity.findViewById(R.id.activity_main).setOnTouchListener(this);

        // if we have a game state, "simulate" that we have just received
        // the state from the game so that the GUI values are updated
        if (state != null) {
            receiveInfo(state);
        }
    }

    @Override
    public void sendInfo(GameInfo info) { }

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
        if(state == null){ this.state = new ShogiGameState(); }
        this.currPieces=state.getCurrentBoard();

        if(this.currPieces == null){
            Log.i("null", "currPieces array null somehow");
            return false;
        }
        //Don't do anything when dragging or lifting touch
        if(event.getActionMasked() != MotionEvent.ACTION_DOWN) {
            Log.i("event", "is not up");
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
                        Log.i("tap", "got the tap");
                        break;
                    }
                }
                break;
            }
        }

        //If you tap if a position when a piece is selected it will move the piece there
        if(currPieces[row][col] == null) {
            if(ShogiGui.pieceIsSelected){
                for(int i = 0; i <= 9; i++){
                    for(int j = 0; j < 9; j++){
                        if(currPieces[i][j] != null){
                            if(currPieces[i][j].getSelected()){
                                if(i == 9){
                                    game.sendAction(new ShogiDropAction(this, currPieces[i][j], row, col, i, j));
                                }
                                //game.sendAction(new ShogiMoveAction(this, currPieces[i][j], row, col, i, j));
                                currPieces[i][j] = null;
                            }
                        }
                    }
                }
                //currPieces[row][col].setSelected(false);
                ShogiGui.pieceIsSelected = false;
            }else {
                return false;
            }
        }else{
            //This deals with selected and deselecting currPieces
            if(currPieces[row][col].getSelected()){
                //This deselects a piece if it is selected
                currPieces[row][col].setSelected(false);
                ShogiGui.pieceIsSelected = false;
            }else{
                //This will select the piece if it is not selected
                for(int i = 0; i < 9; i++){
                    for(int j = 0; j < 9; j++){
                        if(currPieces[i][j] != null){
                            if(currPieces[i][j].getSelected()){
                                currPieces[i][j].setSelected(false);
                            }
                        }
                    }
                }

                currPieces[row][col].setSelected(true);
                ShogiGui.pieceIsSelected = true;
            }
        }

        //redraw board with currPieces updated
        myActivity.findViewById(R.id.activity_main).invalidate();

        return true;
    }

  /*  public class ShogiGui extends SurfaceView implements View.OnTouchListener{
        ShogiPiece currPieces[][];

        public static final float ShogiGui.spaceDim = 150; //150 is height/width of rows & cols
        public static final float backBoardTopLeftX = 20; //20 is good
        public static final float backBoardTopLeftY = 125; //100 is good
        public static final float ShogiGui.topLeftX = backBoardTopLeftX + ShogiGui.spaceDim / 2; //95 is good
        public static final float ShogiGui.topLeftY = backBoardTopLeftY + ShogiGui.spaceDim; //350 is good
        private boolean pieceIsSelected = false;
        private Bitmap background; //the bamboo background; made global so it wont have to be redrawn every onDraw
        private Bitmap board; // make  a board

        private int i, j; //for iterating and managing the currPieces array
        private int row, col; //for iterating and managing currPieces

        public ShogiGui(Context context, AttributeSet attrs) {
            super(context, attrs);
            setWillNotDraw(false);

            //set up bamboo background
            background = BitmapFactory.decodeResource(getResources(), R.drawable.bam222);

            board = BitmapFactory.decodeResource(getResources(), R.drawable.shougi_board);
            board = Bitmap.createScaledBitmap(board, 1450, 1400, false); //1450 1400

            currPieces = new ShogiGameState().currPieces;
        }

        @Override
        public void onDraw(Canvas canvas) {
            //Gets screen size
        *//*WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;*//*

            //Instantiate touch listener
            this.setOnTouchListener(this);

            //creates the various paints used in the board
            Paint shogiboard = new Paint();
            Paint opponent = new Paint();
            Paint player = new Paint();
            Paint captured = new Paint();
            Paint square = new Paint();
            Paint text = new Paint();

            //creates the colors used
            shogiboard.setColor(0xFFB69B4C);
            opponent.setColor(0xFFFF0F0F);
            player.setColor(0xFF85bff8);
            captured.setColor(0xFFC7AC5D);
            square.setColor(0xFF000000);
            text.setColor(0xFF000000);

            //misc settings for paints
            square.setStyle(Paint.Style.STROKE);
            text.setTextSize(48f);
            //Paint text2 = text;
            square.setStrokeWidth(5f);

            //background for the main display
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(background, 0, 0, null);

            //the board and player sides
            canvas.drawRect(230.5f, 100f, 1500f,250f, captured);
            canvas.drawCircle(230.5f, 275f, 180f, opponent);
            canvas.drawText("Opponent", 130f, 200f, text);
            canvas.drawRect(50f, 1650f, 1319f,1800f, captured);
            canvas.drawCircle(1319f, 1625f, 180f,player);
            text.setUnderlineText(true);
            canvas.drawText("Player",1250f,1725f,text);
            //canvas.drawRect(50f, 250f, 1500f, 1650f, shogiboard);
            //canvas.drawRect(50f, 250f, 1500f, 1650f, square);
            canvas.drawCircle(1210, 1710, 20, text);

            canvas.drawBitmap(board, 50f, 250f, null);


            //draw vertical lines; start xy is top point, end xy is bottom point
            for(i = 0; i < 10; i++) {
                canvas.drawLine(topLeftX + i * ShogiGui.spaceDim, ShogiGui.topLeftY, ShogiGui.topLeftX + i * ShogiGui.spaceDim, ShogiGui.topLeftY + 9 * ShogiGui.spaceDim, square);
                canvas.drawLine(topLeftX, ShogiGui.topLeftY + i * ShogiGui.spaceDim, ShogiGui.topLeftX + 9 * ShogiGui.spaceDim, ShogiGui.topLeftY+ i * ShogiGui.spaceDim, square);
            }


            //This draws the currPieces from the array
            for(i = 0; i < 9; i++) {
                for(j = 0; j < 9; j++){
                    if(currPieces[i][j] != null){
                        currPieces[i][j].drawShogiPiece(canvas);

                        if(currPieces[i][j].getSelected())
                            currPieces[i][j].drawMoves(canvas);
                    }
                }
            }
        }

        //This checks if a piece has been selected and/or moved
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            //Don't do anything when dragging or lifting touch
            if(event.getActionMasked() != MotionEvent.ACTION_DOWN) {
                return false;
            }

            //check if user tapped inside the board lines
            if(event.getY() > ShogiGui.topLeftY + 9 * ShogiGui.spaceDim || event.getX() > ShogiGui.topLeftX + 9 * ShogiGui.spaceDim){
                return false;
            }
            if(event.getY() < ShogiGui.topLeftY){
                return false;
            }

            //This determine space that was tapped
            for(row = 0; row < 9; row++) {
                if(event.getY() < ShogiGui.topLeftY + (row + 1) * ShogiGui.spaceDim) {
                    for (col = 0; col < 9; col++) {
                        if(event.getX() < ShogiGui.topLeftX + (col + 1) * ShogiGui.spaceDim){
                            break;
                        }
                    }
                    break;
                }
            }


            //If you tap if a position when a piece is selected it will move the piece there
            if(currPieces[row][col] == null) {
                if(pieceIsSelected){
                    for(int i = 0; i < 9; i++){
                        for(int j = 0; j < 9; j++){
                            if(currPieces[i][j] != null){
                                if(currPieces[i][j].getSelected()){
                                    currPieces[row][col] = new ShogiPiece(row, col, currPieces[i][j].getPiece());
                                    if(!currPieces[i][j].getPlayer()){
                                        currPieces[row][col].setPlayer(false);
                                    }
                                    currPieces[i][j] = null;
                                }
                            }
                        }
                    }
                    currPieces[row][col].setSelected(false);
                    pieceIsSelected = false;
                }else {
                    return false;
                }
            }else{
                //This deals with selected and deselecting currPieces
                if(currPieces[row][col].getSelected()){
                    //This deselects a piece if it is selected
                    currPieces[row][col].setSelected(false);
                    pieceIsSelected = false;
                }else{
                    //This will select the piece if it is not selected
                    for(int i = 0; i < 9; i++){
                        for(int j = 0; j < 9; j++){
                            if(currPieces[i][j] != null){
                                if(currPieces[i][j].getSelected()){
                                    currPieces[i][j].setSelected(false);
                                }
                            }
                        }
                    }

                    currPieces[row][col].setSelected(true);
                    pieceIsSelected = true;
                }
            }

            //redraw board with currPieces updated
            //this.invalidate();

            GameAction action = null;
            //action = new ShogiPromotePiece(player, currPieces, currPieces[row][col], state);
            game.sendAction(action);

            this.invalidate();
            return true;
        }
    }*/
}
