package com.macode.flappybird.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.macode.flappybird.Constants;
import com.macode.flappybird.objects.PipeObject;
import com.macode.flappybird.R;
import com.macode.flappybird.objects.BirdObject;

import java.util.ArrayList;

public class GameViewActivity extends View {
    private BirdObject bird;
    private Handler handler;
    private Runnable runnable;
    private ArrayList<PipeObject> arrayPipes;
    private int sumPipe, distance;
    private int score, bestScore = 0;
    private Context context;
    private int soundJump;
    private float volume;
    private boolean loadedSound;
    private SoundPool soundPool;

    public GameViewActivity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            bestScore = sharedPreferences.getInt("bestScore", 0);
        }
        score = 0;
        bestScore = 0;
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


        if (21 <= Build.VERSION.SDK_INT) {
            // Controls the sound system
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    // Specifies the context in which the stream is used, in the case a game
                    // Why the sound is playing and that the sound is used for
                    .setUsage(AudioAttributes.USAGE_GAME)
                    // Specifies what the source is playing
                    // Sonification is used to accompany a user action
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            // Manages and plays audio resources for application
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            this.soundPool = builder.build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        // Called when a sound has completed loading
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadedSound = true;
            }
        });
        soundJump = this.soundPool.load(context, R.raw.jump_02, 1);
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
                // Keeps track of your best score
                if (score > bestScore) {
                    bestScore = score;
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("bestScore", bestScore);
                    editor.apply();
                }
                MainActivity.textScore.setText("" + score);
            }

            // Setting the loop for the pipes
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

    // Gives the bird the flying effect and sound
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bird.setDrop(-15);
            if (loadedSound) {
                int streamId = this.soundPool.play(this.soundJump, (float)0.5, (float)0.5, 1, 0, 1f);
            }
        }
        return true;
    }

    // Resets the game
    public void reset() {
        MainActivity.textScore.setVisibility(VISIBLE);
        MainActivity.textScore.setText("0");
        score = 0;
        initPipe();
        initBird();
    }
}
