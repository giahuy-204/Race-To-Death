package com.mygdx.racetodeath.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.racetodeath.object.Bullet;
import com.mygdx.racetodeath.object.EnemyCar;
import com.mygdx.racetodeath.object.Explosion;
import com.mygdx.racetodeath.object.PlayerCar;
import com.mygdx.racetodeath.RaceToDeath;


import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public class PlayingScreen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Texture explosionTexture;

    private TextureRegion[] backgrounds;
    private float backgroundHeight; //height of background in World units

    private TextureRegion playerCarTextureRegion, enemyCarTextureRegion, playerbulletTextureRegion, enemybulletTextureRegion;

    private float[] backgroundOffsets = {0, 0, 0, 0};
    private float backgroundMaxScrollingSpeed;
    private float timeBetweenEnemySpawns = 1f; //3s
    private float enemySpawnTimer = 0; //time start spawn

    private final float WORLD_WIDTH = 72;
    private final float WORLD_HEIGHT = 128;
    private final float TOUCH_MOVEMENT_THRESHOLD = 0.1f;

    private PlayerCar playerCar;
    private LinkedList<EnemyCar> enemyCarList;
    private LinkedList<Bullet> playerBulletList;
    private LinkedList<Bullet> enemyBulletList;
    private LinkedList<Explosion> explosionList;

    private int score = 0;

    public Sound shooting;

    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;

    private RaceToDeath parent;

    public PlayingScreen(RaceToDeath raceToDeath) {
        parent = raceToDeath;

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

        explosionTexture = new Texture("exp2_0.png");

        playerCar = new PlayerCar(45, 10, 20,
                WORLD_WIDTH/2, WORLD_HEIGHT/4,
                1, 4, 75, 0.5f,
                playerCarTextureRegion, playerbulletTextureRegion);

        enemyCarList = new LinkedList<>();

        playerBulletList = new LinkedList<>();
        enemyBulletList = new LinkedList<>();

        explosionList = new LinkedList<>();

        batch = new SpriteBatch();

        prepareHUD();

        shooting = Gdx.audio.newSound(Gdx.files.internal("Shooting-Sound.wav"));
    }


    private void prepareHUD() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Trench-Thin-100.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(240,52,52, 1);
        fontParameter.borderColor = new Color(240,52,52, 1);

        font = fontGenerator.generateFont(fontParameter);

        font.getData().setScale(0.08f);


        hudVerticalMargin = font.getCapHeight()/2;
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 7 ;

    }

    @Override
    public void render(float deltaTime) {

        batch.begin();

        renderBackground(deltaTime);

        detectInput(deltaTime);
        playerCar.update(deltaTime);

        spawnEnemyCars(deltaTime);

        ListIterator<EnemyCar> enemyCarListIterator = enemyCarList.listIterator();
        while (enemyCarListIterator.hasNext()) {
            EnemyCar enemyCar = enemyCarListIterator.next();
            moveEnemy(enemyCar, deltaTime);

            enemyCar.update(deltaTime);

            enemyCar.draw(batch);
        }
        playerCar.draw(batch);

        renderBullets(deltaTime);

        detectCollisions();

        updateAndRenderExplosions(deltaTime);

        updateAndRenderHUD();

        batch.end();

//        renderGameOver();

    }

    private void updateAndRenderHUD() {
        font.draw(batch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Lives", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);

        font.draw(batch, String.format(Locale.getDefault(), "%04d", score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%03d", playerCar.lives), hudRightX, hudRow2Y, hudSectionWidth, Align.left, false);

    }

    private void renderGameOver() {
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (playerCar.lives < 1) {
            parent.changeScreen(RaceToDeath.ENDGAME);
        }
    }

    private void spawnEnemyCars(float deltaTime) {
        enemySpawnTimer += deltaTime;

        if (enemySpawnTimer > timeBetweenEnemySpawns) {
            enemyCarList.add(new EnemyCar(35, 10, 20,
                    RaceToDeath.random.nextFloat()*(WORLD_WIDTH-10)+5, WORLD_HEIGHT - 5,
                    1, 4, 70, 0.8f,
                    enemyCarTextureRegion, enemybulletTextureRegion));
            enemySpawnTimer -= timeBetweenEnemySpawns * 2;
        }
    }

    private void detectInput(float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;

        leftLimit = -playerCar.boundingBox.x ;
        downLimit = -playerCar.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerCar.boundingBox.x - playerCar.boundingBox.width;
        upLimit = (float) WORLD_HEIGHT/2 - playerCar.boundingBox.y - playerCar.boundingBox.height;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && rightLimit > 0) {
            playerCar.translate( Math.min(playerCar.movementSpeed*deltaTime, rightLimit), 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && upLimit > 0) {
            playerCar.translate(  0f, Math.min(playerCar.movementSpeed*deltaTime, upLimit));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && leftLimit < 0) {
            playerCar.translate( Math.max(-playerCar.movementSpeed*deltaTime, leftLimit), 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && downLimit < 0) {
            playerCar.translate( 0f, Math.max(-playerCar.movementSpeed*deltaTime, downLimit));
        }

        if (Gdx.input.isTouched()) {

            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);

            Vector2 playerCarCentre = new Vector2(
                    playerCar.boundingBox.x/2 + playerCar.boundingBox.width/2,
                    playerCar.boundingBox.y/2 + playerCar.boundingBox.height/2);

            float touchDistance = touchPoint.dst(playerCarCentre);

            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD) {
                float xTouchDifference = touchPoint.x - playerCarCentre.x;
                float yTouchDifference = touchPoint.y - playerCarCentre.y;

                float xMove = xTouchDifference / touchDistance * playerCar.movementSpeed * deltaTime;
                float yMove = yTouchDifference / touchDistance * playerCar.movementSpeed * deltaTime;

                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove, leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove, downLimit);

                playerCar.translate(xMove, yMove);

            }

        }

    }

    private void moveEnemy(EnemyCar enemyCar, float deltaTime) {

        float leftLimit, rightLimit, upLimit, downLimit;

        leftLimit = -enemyCar.boundingBox.x ;
        downLimit = (float) WORLD_HEIGHT/2-enemyCar.boundingBox.y;
        rightLimit = WORLD_WIDTH - enemyCar.boundingBox.x - enemyCar.boundingBox.width;
        upLimit = WORLD_HEIGHT - enemyCar.boundingBox.y - enemyCar.boundingBox.height;

        float xMove = enemyCar.getDirectionVector().x * enemyCar.movementSpeed * deltaTime;
        float yMove = enemyCar.getDirectionVector().y * enemyCar.movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove, leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove, downLimit);

        enemyCar.translate(xMove, yMove);

    }

    private void detectCollisions() {

        ListIterator<Bullet> bulletListIterator = playerBulletList.listIterator();
        while (bulletListIterator.hasNext()) {
            Bullet bullet = bulletListIterator.next();
            ListIterator<EnemyCar> enemyCarListIterator = enemyCarList.listIterator();
            while (enemyCarListIterator.hasNext()) {
                EnemyCar enemyCar = enemyCarListIterator.next();

                if (enemyCar.intersects(bullet.boundingBox)) {
                    if (enemyCar.hitAndCheckDestroyed(bullet)) {
                        enemyCarListIterator.remove();
                        explosionList.add(
                                new Explosion(explosionTexture,
                                        new Rectangle(enemyCar.boundingBox),
                                        0.7f));
                        score += 1;
                    }
                    bulletListIterator.remove();
                    break;
                }
            }
        }

        bulletListIterator = enemyBulletList.listIterator();
        while (bulletListIterator.hasNext()) {
            Bullet bullet = bulletListIterator.next();
            if (playerCar.intersects(bullet.boundingBox)) {
                if (playerCar.hitAndCheckDestroyed(bullet)) {

                    explosionList.add(
                            new Explosion(explosionTexture,
                                    new Rectangle(playerCar.boundingBox),
                                    1.6f));
                    playerCar.lives--;
                }
                bulletListIterator.remove();
            }
        }

    }

    private void updateAndRenderExplosions(float deltaTime) {
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if (explosion.isFinished()) {
                explosionListIterator.remove();
            }
            else {
                explosion.draw(batch);
            }
        }

    }

    private void renderBullets(float deltaTime) {
        //lasers
        if (playerCar.canFireBullet()) {
            Bullet[] bullets = playerCar.fireBullet();
            for (Bullet bullet: bullets) {
                playerBulletList.add(bullet);
                shooting.play();
                shooting.loop(0.5f);
//                shooting.wait(10);
            }
        }

        ListIterator<EnemyCar> enemyCarListIterator = enemyCarList.listIterator();
        while (enemyCarListIterator.hasNext()) {
            EnemyCar enemyCar = enemyCarListIterator.next();
            if (enemyCar.canFireBullet()) {
                Bullet[] bullets = enemyCar.fireBullet();
                for (Bullet bullet : bullets) {
                    enemyBulletList.add(bullet);
                }
            }
        }
        ListIterator<Bullet> iterator = playerBulletList.listIterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.draw(batch);
            bullet.boundingBox.y += bullet.movementSpeed*deltaTime;
            if (bullet.boundingBox.y > WORLD_HEIGHT) {
                iterator.remove();
            }
        }

        iterator = enemyBulletList.listIterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.draw(batch);
            bullet.boundingBox.y -= bullet.movementSpeed*deltaTime;
            if (bullet.boundingBox.y + bullet.boundingBox.height < 0) {
                iterator.remove();
            }
        }
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
