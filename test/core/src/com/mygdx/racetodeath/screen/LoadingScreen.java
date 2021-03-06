package com.mygdx.racetodeath.screen;

import com.badlogic.gdx.Screen;
import com.mygdx.racetodeath.RaceToDeath;

public class LoadingScreen implements Screen {

    private RaceToDeath parent;

    public LoadingScreen(RaceToDeath raceToDeath){
        parent = raceToDeath;
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