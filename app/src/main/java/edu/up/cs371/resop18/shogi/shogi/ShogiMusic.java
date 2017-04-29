package edu.up.cs371.resop18.shogi.shogi;

import android.media.AudioManager;
import android.media.MediaPlayer;

import edu.up.cs371.resop18.shogi.R;
import edu.up.cs371.resop18.shogi.game.GameMainActivity;

/**
 * Created by Javier on 29-Apr-17.
 */

//The BackGround Music
public class ShogiMusic implements Runnable {
	private MediaPlayer bgm = new MediaPlayer();

	//Creates a MediaPlayer using the GameMainActivity
	public ShogiMusic(GameMainActivity gma){
		bgm = MediaPlayer.create(gma, R.raw.bgm);
		bgm.setAudioStreamType(AudioManager.STREAM_MUSIC);
		bgm.setVolume(.2f, .2f);
		bgm.setLooping(true);
	}

	@Override
	public void run(){
		bgm.start();
	}
}
