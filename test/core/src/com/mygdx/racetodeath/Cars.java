package com.mygdx.racetodeath;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Cars {

    float movementSpeed;


    Rectangle boundingBox;

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
        this.boundingBox = new Rectangle(xCentre - width / 2,yCentre - height / 2, width, height);
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

    public boolean intersects(Rectangle otherRectangle) {

        return boundingBox.overlaps(otherRectangle);
    }

    public void hit(Bullet bullet) {

    }

    public void translate(float xChange, float yChange) {
        boundingBox.setPosition(boundingBox.x + xChange, boundingBox.y + yChange);
    }

    public void draw(Batch batch) {
        batch.draw(shipTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
