package com.mygdx.racetodeath.screen;

import com.badlogic.gdx.Screen;
import com.mygdx.racetodeath.RaceToDeath;

public class LoadingScreen implements Screen {

    private RaceToDeath parent; // a field to store our orchestrator

    // our constructor with a Box2DTutorial argument
    public LoadingScreen(RaceToDeath raceToDeath){
        parent = raceToDeath;     // setting the argument to our field.
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        parent.changeScreen(RaceToDeath.MENU);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}