package com.mygdx.racetodeath;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.racetodeath.screen.GameOverScreen;
import com.mygdx.racetodeath.screen.LoadingScreen;
import com.mygdx.racetodeath.screen.MenuScreen;
import com.mygdx.racetodeath.screen.PlayingScreen;
import com.mygdx.racetodeath.screen.SettingScreen;




import java.util.Random;

public class RaceToDeath extends Game {

	public LoadingScreen loadingScreen;
	public SettingScreen settingScreen;
	public MenuScreen menuScreen;
	public PlayingScreen playingScreen;
	public GameOverScreen gameOverScreen;
	private AppPreferences preferences;

	public Music loading, playing;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;

	public static Random random = new Random();

	@Override
	public void create() {
		preferences = new AppPreferences();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);

		loading = Gdx.audio.newMusic(Gdx.files.internal("mainmenu.ogg"));
		playing = Gdx.audio.newMusic(Gdx.files.internal("background.ogg"));


	}

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				loading.setVolume(0.5f);
				loading.setLooping(true);
				loading.play();
				break;
			case PREFERENCES:
				if(settingScreen == null) settingScreen = new SettingScreen(this);
				this.setScreen(settingScreen);
				break;
			case APPLICATION:
				if(playingScreen == null) playingScreen = new PlayingScreen(this);
				this.setScreen(playingScreen);
				loading.stop();
				playing.setVolume(0.5f);
				playing.setLooping(true);
				playing.play();
				break;
			case ENDGAME:
				if(gameOverScreen == null) gameOverScreen = new GameOverScreen(this);
				this.setScreen(gameOverScreen);
				break;
		}
	}






	@Override
	public void dispose() {
		loadingScreen.dispose();
	}


	@Override
	public void render() {
		super.render();
	}

	public AppPreferences getPreferences(){
		return this.preferences;
	}


	@Override
	public void resize(int width, int height) {
//		super.resize(width, height);
//		loadingScreen.resize(width, height);
	}
}
