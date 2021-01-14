package com.macode.flappybird.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.macode.flappybird.objects.BaseObject;

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

    // Function that handles birds gravity
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
        // Switches between the two bird images to create the flapping animation of the bird
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

        // Angles the bird bitmap depending on when the user clicks to make the bird jump
        if (this.drop < 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(-25);
            return Bitmap.createBitmap(arrayBms.get(idCurrentBitmap), 0, 0, arrayBms.get(idCurrentBitmap).getWidth(), arrayBms.get(idCurrentBitmap).getHeight(), matrix, true);
        } else if (drop >= 0) {
            Matrix matrix = new Matrix();
            if (drop < 70) {
                matrix.postRotate(-25 + (drop*2));
            } else {
                matrix.postRotate(45);
            }
            return Bitmap.createBitmap(arrayBms.get(idCurrentBitmap), 0, 0, arrayBms.get(idCurrentBitmap).getWidth(), arrayBms.get(idCurrentBitmap).getHeight(), matrix, true);
        }
        return this.arrayBms.get(idCurrentBitmap);
    }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }
}
