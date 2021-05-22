package com.mygdx.racetodeath;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

abstract class Cars {

    float movementSpeed;

    float xPosition, yPosition;
    float width, height;

    float bulletWidth, bulletHeight;
    float bulletMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    TextureRegion shipTextureRegion, bulletTextureRegion;

    public Cars(float movementSpeed,
                float width, float height,
                float xCentre, float yCentre,
                float bulletWidth, float bulletHeight, float bulletMovementSpeed,
                float timeBetweenShots,
                TextureRegion shipTextureRegion,
                TextureRegion bulletTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.bulletWidth = bulletWidth;
        this.bulletHeight = bulletHeight;
        this.bulletMovementSpeed = bulletMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;
        this.shipTextureRegion = shipTextureRegion;
        this.bulletTextureRegion = bulletTextureRegion;
    }


    public void update(float deltaTime) {
        timeSinceLastShot += deltaTime;
    }

    public boolean canFireBullet() {
        return (timeSinceLastShot - timeBetweenShots >= 0);
    }

    public abstract Bullet[] fireBullet();

    public void draw(Batch batch) {
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
    }
}
