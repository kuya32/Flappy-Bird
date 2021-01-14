package com.macode.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static TextView textScore, textBestScore, textScoreOver;
    public static RelativeLayout relativeLayoutGameOver;
    public static Button retryButton;
    private GameViewActivity gameViewActivity;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hides status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Sets width and height of phone/virtual machine
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_main);
        textScore = findViewById(R.id.textScore);
        textBestScore = findViewById(R.id.gameOverBestScoreText);
        textScoreOver = findViewById(R.id.gameOverScoreText);
        relativeLayoutGameOver = findViewById(R.id.relativeLayoutGameOver);
        retryButton = findViewById(R.id.retryButton);
        gameViewActivity = findViewById(R.id.relativeLayoutGameView);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutGameOver.setVisibility(View.INVISIBLE);
                gameViewActivity.reset();
//                Intent goToMainActivity = new Intent(MainActivity.this, MainActivity.class);
//                MainActivity.this.startActivity(goToMainActivity);
            }
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.sillychipsong);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
}