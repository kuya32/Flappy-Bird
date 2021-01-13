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
    private ArrayList<PipeObject> arrayPipes;
    private int sumPipe, distance;
    private int score, bestScore;
    private boolean reset;
    public GameViewActivity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        score = 0;
        bestScore = 0;
        reset = false;
        initBird();
        initPipe();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Update draw method
                invalidate();
            }
        };
    }

    private void initBird() {
        bird = new BirdObject();
        bird.setWidth(100 * Constants.SCREEN_WIDTH / 1080);
        bird.setHeight(100 * Constants.SCREEN_HEIGHT / 1920);
        bird.setX(100 * Constants.SCREEN_WIDTH / 1080);
        bird.setY(Constants.SCREEN_HEIGHT / 2 - bird.getHeight() / 2);
        ArrayList<Bitmap> arrayBms = new ArrayList<>();
        arrayBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird1));
        arrayBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird2));
        bird.setArrayBms(arrayBms);
    }

    private void initPipe() {
        sumPipe = 6;
        distance = 375 * Constants.SCREEN_HEIGHT / 1920;
        arrayPipes = new ArrayList<>();
        for (int i = 0; i < sumPipe; i++) {
            if (i < sumPipe / 2) {
                this.arrayPipes.add(new PipeObject(Constants.SCREEN_WIDTH + i * ((Constants.SCREEN_WIDTH + 200 * Constants.SCREEN_WIDTH / 1080) / (sumPipe / 2)), 0, 200 * Constants.SCREEN_WIDTH / 1080, Constants.SCREEN_HEIGHT / 2));
                this.arrayPipes.get(this.arrayPipes.size() - 1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe2));
                this.arrayPipes.get(this.arrayPipes.size() - 1).randomY();
            } else {
                this.arrayPipes.add(new PipeObject(this.arrayPipes.get(i - sumPipe / 2).getX(), this.arrayPipes.get(i - sumPipe / 2).getY() + this.arrayPipes.get(i - sumPipe / 2).getHeight() + this.distance, 200 * Constants.SCREEN_WIDTH / 1080,  Constants.SCREEN_HEIGHT / 2));
                this.arrayPipes.get(this.arrayPipes.size() - 1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe1));
            }
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        bird.draw(canvas);
        for (int i = 0; i < sumPipe; i++) {
            // Collision System: The game will stop and present end game score if the body box of the bird collides with the pipes
            if (bird.getRect().intersect(arrayPipes.get(i).getRect()) || bird.getY() - bird.getHeight() < 0 || bird.getY() > Constants.SCREEN_HEIGHT) {
                PipeObject.speed = 0;
                MainActivity.textScoreOver.setText(MainActivity.textScore.getText());
                MainActivity.textBestScore.setText("Best: " + bestScore);
                MainActivity.textScore.setVisibility(INVISIBLE);
                MainActivity.relativeLayoutGameOver.setVisibility(VISIBLE);
            }
            // Scoring System: Score increases by one every time the front of the bird passes by half the width of each pipe
            if (this.bird.getX() + this.bird.getWidth() > arrayPipes.get(i).getX() + arrayPipes.get(i).getWidth() / 2 && this.bird.getX() + this.bird.getWidth() <= arrayPipes.get(i).getX() + arrayPipes.get(i).getWidth() / 2 + PipeObject.speed && i < sumPipe / 2) {
                score++;
                MainActivity.textScore.setText("" + score);
            }
            if (this.arrayPipes.get(i).getX() < -arrayPipes.get(i).getWidth()) {
                this.arrayPipes.get(i).setX(Constants.SCREEN_WIDTH);
                if (i < sumPipe / 2) {
                    arrayPipes.get(i).randomY();
                } else {
                    arrayPipes.get(i).setY(this.arrayPipes.get(i - sumPipe / 2).getY() + this.arrayPipes.get(i - sumPipe / 2).getHeight() + this.distance);
                }
            }
            this.arrayPipes.get(i).draw(canvas);
        }
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

    public void reset() {
        MainActivity.textScore.setText("0");
        score = 0;
        initPipe();
        initBird();
    }
}
