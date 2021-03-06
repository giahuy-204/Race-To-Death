package com.mygdx.racetodeath.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.racetodeath.RaceToDeath;

public class EnemyCar extends Cars {

    Vector2 directionVector;
    public float timeSinceLastDirectionChange = 0;
    public float directionChangeFrequency = 0.75f;

    public EnemyCar(float movementSpeed, float width, float height,
                     float xCentre, float yCentre, float bulletWidth,
                     float bulletHeight, float bulletMovementSpeed,
                     float timeBetweenShots, TextureRegion carTextureRegion, TextureRegion bulletTextureRegion) {
        super(movementSpeed, width, height, xCentre, yCentre,
                bulletWidth, bulletHeight, bulletMovementSpeed, timeBetweenShots,
                carTextureRegion, bulletTextureRegion);

        directionVector = new Vector2(0, -1);
    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }

    public void randomizeDirectionVector() {
        double bearing = RaceToDeath.random.nextDouble()*6.283185;
        directionVector.x = (float) Math.sin(bearing);
        directionVector.y = (float) Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;
        if (timeSinceLastDirectionChange > directionChangeFrequency) {
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
    }

    @Override
    public Bullet[] fireBullet() {
        Bullet[] bullets = new Bullet[1];
        bullets[0] = new Bullet(boundingBox.x + boundingBox.width*0.5f,boundingBox.y - bulletHeight ,
                bulletWidth, bulletHeight,
                bulletMovementSpeed, bulletTextureRegion);

        timeSinceLastShot = 0;

        return bullets;
    }

    public void draw(Batch batch) {
        batch.draw(carTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
