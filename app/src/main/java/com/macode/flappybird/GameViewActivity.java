package com.macode.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameViewActivity extends View {
    private BirdObject bird;
    private Handler handler;
    private Runnable runnable;

    public GameViewActivity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bird = new BirdObject();
        bird.setWidth(100 * Constants.SCREEN_WIDTH / 1080);
        bird.setHeight(100 * Constants.SCREEN_HEIGHT / 1920);
        bird.setX(100 * Constants.SCREEN_WIDTH / 1080);
        bird.setY(Constants.SCREEN_HEIGHT / 2 - bird.getHeight() / 2);
        ArrayList<Bitmap> arrayBms = new ArrayList<>();
        arrayBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird1));
        arrayBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird2));
        bird.setArrayBms(arrayBms);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Update draw method
                invalidate();
            }
        };
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        bird.draw(canvas);
        // Update every 0.01 seconds
        handler.postDelayed(runnable, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bird.setDrop(-15);
        }
        return true;
    }
}
