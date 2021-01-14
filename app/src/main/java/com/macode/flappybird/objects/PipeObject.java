package com.macode.flappybird.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.macode.flappybird.Constants;
import com.macode.flappybird.objects.BaseObject;

import java.util.Random;

public class PipeObject extends BaseObject {
    public static int speed;
    public PipeObject(float x, float y, int width, int height) {
        super(x, y, width, height);
        speed = 10 * Constants.SCREEN_WIDTH / 1080;
    }

    // Speed of the pipes being drawn into the loop
    public void draw(Canvas canvas) {
        this.x -= speed;
        canvas.drawBitmap(this.bm, this.x, this.y, null);
    }

    // Random placements of the pipes drawn on the screen
    public void randomY() {
        Random random = new Random();
        this.y = random.nextInt((0 + this.height / 4) + 1) - this.height / 4;
    }

    @Override
    public void setBm(Bitmap bm) {
        this.bm = Bitmap.createScaledBitmap(bm, width, height, true);
    }
}
