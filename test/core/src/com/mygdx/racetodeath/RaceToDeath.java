package com.mygdx.racetodeath;

import com.badlogic.gdx.Game;

import java.util.Random;

public class RaceToDeath extends Game {

	PlayingScreen playingScreen;

	public static Random random = new Random();

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
		super.resize(width, height);
		playingScreen.resize(width, height);
	}
}
