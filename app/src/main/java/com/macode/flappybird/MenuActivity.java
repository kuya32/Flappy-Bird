package com.macode.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button playButton = MenuActivity.this.findViewById(R.id.playButton);
        playButton.setOnClickListener((view) -> {
            Intent goToMainActivity = new Intent(MenuActivity.this, MainActivity.class);
            MenuActivity.this.startActivity(goToMainActivity);
        });
    }
}