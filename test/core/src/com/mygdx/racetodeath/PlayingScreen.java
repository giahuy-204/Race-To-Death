package com.mygdx.racetodeath;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

class PlayingScreen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;

    private TextureRegion[] backgrounds;
    private float backgroundHeight; //height of background in World units

    private TextureRegion playerCarTextureRegion, enemyCarTextureRegion, playerbulletTextureRegion, enemybulletTextureRegion;

    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;

    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;

    private Cars playerCar;
    private Cars enemyCar;
    private LinkedList<Bullet> playerBulletList;
    private LinkedList<Bullet> enemyBulletList;

    PlayingScreen() {

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        textureAtlas = new TextureAtlas("images.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("screen00");
        backgrounds[1] = textureAtlas.findRegion("screen01");
        backgrounds[2] = textureAtlas.findRegion("screen02");
        backgrounds[3] = textureAtlas.findRegion("screen03");

        backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        playerCarTextureRegion = textureAtlas.findRegion("maincar");
        enemyCarTextureRegion = textureAtlas.findRegion("policecar");

        playerbulletTextureRegion = textureAtlas.findRegion("playerbullet");
        enemybulletTextureRegion = textureAtlas.findRegion("enemybullet");

        playerCar = new PlayerCar(2, 10, 20,
                WORLD_WIDTH/2, WORLD_HEIGHT/4,
                1, 4, 55, 0.5f,
                playerCarTextureRegion, playerbulletTextureRegion);
        enemyCar = new EnemyCar(4, 10, 20,
                WORLD_WIDTH/2, WORLD_HEIGHT*3/4,
                1, 4, 50, 0.8f,
                enemyCarTextureRegion, enemybulletTextureRegion);


        playerBulletList = new LinkedList<>();
        enemyBulletList = new LinkedList<>();


        batch = new SpriteBatch();
    }

    @Override
    public void render(float deltaTime) {
        batch.begin();

        playerCar.update(deltaTime);
        enemyCar.update(deltaTime);

        renderBackground(deltaTime);

        enemyCar.draw(batch);

        playerCar.draw(batch);

        //lasers
        if (playerCar.canFireBullet()) {
            Bullet[] bullets = playerCar.fireBullet();
            for (Bullet bullet: bullets) {
                playerBulletList.add(bullet);
            }
        }

        if (enemyCar.canFireBullet()) {
            Bullet[] bullets = enemyCar.fireBullet();
            for (Bullet bullet: bullets) {
                enemyBulletList.add(bullet);
            }
        }

        ListIterator<Bullet> iterator = playerBulletList.listIterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.draw(batch);
            bullet.yPosition += bullet.movementSpeed*deltaTime;
            if (bullet.yPosition > WORLD_HEIGHT) {
                iterator.remove();
            }
        }

        iterator = enemyBulletList.listIterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.draw(batch);
            bullet.yPosition -= bullet.movementSpeed*deltaTime;
            if (bullet.yPosition + bullet.height < 0) {
                iterator.remove();
            }
        }

        batch.end();
    }

    private void renderBackground(float deltaTime) {

        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer],
                    WORLD_WIDTH, backgroundHeight);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
