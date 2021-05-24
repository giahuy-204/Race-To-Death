package com.mygdx.racetodeath.object;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerCar extends Cars {
    public PlayerCar(float movementSpeed, float width, float height,
                     float xCentre, float yCentre, float bulletWidth,
                     float bulletHeight, float bulletMovementSpeed,
                     float timeBetweenShots, TextureRegion carTextureRegion, TextureRegion bulletTextureRegion) {
        super(movementSpeed, width, height, xCentre, yCentre,
                bulletWidth, bulletHeight, bulletMovementSpeed, timeBetweenShots,
                carTextureRegion, bulletTextureRegion);
    }

    @Override
    public Bullet[] fireBullet() {
        Bullet[] bullets = new Bullet[1];
        bullets[0] = new Bullet(boundingBox.x + boundingBox.width*0.5f,boundingBox.y + boundingBox.height*0.85f,
                bulletWidth, bulletHeight, bulletMovementSpeed, bulletTextureRegion);

        timeSinceLastShot = 0;

        return bullets;
    }
}
