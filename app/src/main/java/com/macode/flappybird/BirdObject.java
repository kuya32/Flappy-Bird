package com.macode.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class BirdObject extends BaseObject {
    private ArrayList<Bitmap> arrayBms = new ArrayList<>();
    private int count, vFlap, idCurrentBitmap;
    private float drop;

    public BirdObject() {
        this.count = 0;
        this.vFlap = 5;
        this.idCurrentBitmap = 0;
        this.drop = 0;
    }

    public void draw(Canvas canvas) {
        drop();
        canvas.drawBitmap(this.getBm(), this.x, this.y, null);
    }

    private void drop() {
        this.drop += 0.6;
        this.y += this.drop;
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

    @Override
    public Bitmap getBm() {
        count++;
        if (this.count == this.vFlap) {
            for (int i = 0; i < arrayBms.size(); i++) {
                if (i == arrayBms.size() - 1) {
                    this.idCurrentBitmap = 0;
                    break;
                } else if (this.idCurrentBitmap == i) {
                    idCurrentBitmap = i + 1;
                    break;
                }
            }
            count = 0;
        }
        return this.arrayBms.get(idCurrentBitmap);
    }
}
