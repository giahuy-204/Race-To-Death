package com.mygdx.racetodeath;

import com.badlogic.gdx.Game;

public class RaceToDeath extends Game {

	PlayingScreen playingScreen;

	@Override
	public void create() {
		playingScreen = new PlayingScreen();
		setScreen(playingScreen);
	}


	@Override
	public void dispose() {
		playingScreen.dispose();
	}


	@Override
	public void render() {
		super.render();
	}


	@Override
	public void resize(int width, int height) {
		playingScreen.resize(width, height);
	}
}
