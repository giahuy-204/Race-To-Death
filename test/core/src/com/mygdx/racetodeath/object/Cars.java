package com.mygdx.racetodeath.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Cars {

    public float movementSpeed;

    public Rectangle boundingBox;

    public float bulletWidth, bulletHeight;
    public float bulletMovementSpeed;
    public float timeBetweenShots;
    public float timeSinceLastShot = 0;

    TextureRegion carTextureRegion, bulletTextureRegion;

    public Cars(float movementSpeed,
                float width, float height,
                float xCentre, float yCentre,
                float bulletWidth, float bulletHeight, float bulletMovementSpeed,
                float timeBetweenShots,
                TextureRegion carTextureRegion,
                TextureRegion bulletTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.boundingBox = new Rectangle(xCentre - width / 2,yCentre - height / 2, width, height);
        this.bulletWidth = bulletWidth;
        this.bulletHeight = bulletHeight;
        this.bulletMovementSpeed = bulletMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;
        this.carTextureRegion = carTextureRegion;
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

    public boolean hitAndCheckDestroyed(Bullet bullet) {
        return true;
    }

    public void translate(float xChange, float yChange) {
        boundingBox.setPosition(boundingBox.x + xChange, boundingBox.y + yChange);
    }

    public void draw(Batch batch) {
        batch.draw(carTextureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
