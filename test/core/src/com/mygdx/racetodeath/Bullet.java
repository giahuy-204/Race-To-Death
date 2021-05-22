package com.mygdx.racetodeath;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class Bullet {

    float xPosition, yPosition;
    float width, height;

    float movementSpeed;

    TextureRegion textureRegion;


    public Bullet(float xPosition, float yPosition, float width, float height, float movementSpeed, TextureRegion textureRegion) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch) {
        batch.draw(textureRegion, xPosition - width/2 , yPosition, width, height);
    }
}