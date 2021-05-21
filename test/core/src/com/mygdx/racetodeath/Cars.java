package com.mygdx.racetodeath;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class Cars {

    float movementSpeed;

    float xPosition, yPosition;
    float width, height;

    TextureRegion shipTexture;

    public Cars(float movementSpeed,
                float width, float height,
                float xCentre, float yCentre,
                TextureRegion shipTexture) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xCentre - width / 2;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.shipTexture = shipTexture;
    }

    public void draw(Batch batch) {
        batch.draw(shipTexture, xPosition, yPosition, width, height);
    }
}
