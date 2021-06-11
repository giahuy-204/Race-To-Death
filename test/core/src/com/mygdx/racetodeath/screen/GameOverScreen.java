package com.mygdx.racetodeath.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.racetodeath.RaceToDeath;


public class GameOverScreen implements Screen {

    private RaceToDeath parent;
    private Stage stage;
    private TextureAtlas atlas2;
    private TextureRegion background2;
    private Skin skin;
    private Preferences preferences;
    private int highScore;

    GameOverScreen(Preferences preferences) {
        // Store reference to LibGDX Preferences
        this.preferences = preferences;
    }

    public GameOverScreen(RaceToDeath raceToDeath){
        parent = raceToDeath;     // setting the argument to our field.
        stage = new Stage(new StretchViewport(500,1000));

        atlas2 = new TextureAtlas("images/images.atlas");
        background2 = atlas2.findRegion("mainmenuscreen");
        skin = new Skin(Gdx.files.internal("skins/glassy-ui.json"));
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

//        highScore = preferences.getInteger("max", 0);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        table.setBackground(new TiledDrawable(background2));

        TextButton menuButton = new TextButton("Main menu", skin);
        TextButton exit2 = new TextButton("Exit", skin);

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(RaceToDeath.MENU);
            }
        });

        exit2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(menuButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(exit2).fillX().uniformX();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
//        System.out.println(highScore);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
