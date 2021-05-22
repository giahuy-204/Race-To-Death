package com.mygdx.racetodeath;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class EnemyCar extends Cars {
    public EnemyCar(float movementSpeed, float width, float height,
                     float xCentre, float yCentre, float bulletWidth,
                     float bulletHeight, float bulletMovementSpeed,
                     float timeBetweenShots, TextureRegion shipTextureRegion, TextureRegion bulletTextureRegion) {
        super(movementSpeed, width, height, xCentre, yCentre,
                bulletWidth, bulletHeight, bulletMovementSpeed, timeBetweenShots,
                shipTextureRegion, bulletTextureRegion);
    }

    @Override
    public Bullet[] fireBullet() {
        Bullet[] bullets = new Bullet[1];
        bullets[0] = new Bullet(xPosition + width*0.5f,yPosition - bulletHeight ,
                bulletWidth, bulletHeight,
                bulletMovementSpeed, bulletTextureRegion);

        timeSinceLastShot = 0;

        return bullets;
    }
}
