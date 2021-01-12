package com.macode.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class BirdObject extends BaseObject {
    private ArrayList<Bitmap> arrayBms = new ArrayList<>();

    public BirdObject() {

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.getBm(), this.x, this.y, null);
    }

    public ArrayList<Bitmap> getArrayBms() {
        return arrayBms;
    }

    public void setArrayBms(ArrayList<Bitmap> arrayBms) {
        this.arrayBms = arrayBms;
        // Scaling bitmaps to the height and width of bird
        for (int i = 0; i < arrayBms.size(); i++) {
            this.arrayBms.set(i, Bitmap.createScaledBitmap(this.arrayBms.get(i), this.width, this.height, true));
        }
    }

    // Retrieves the first Bitmap
    @Override
    public Bitmap getBm() {
        return this.getArrayBms().get(0);
    }
}
