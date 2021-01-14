package com.macode.flappybird.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.macode.flappybird.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Transitions from main menu view to game view
        Button playButton = MenuActivity.this.findViewById(R.id.playButton);
        playButton.setOnClickListener((view) -> {
            Intent goToMainActivity = new Intent(MenuActivity.this, MainActivity.class);
            MenuActivity.this.startActivity(goToMainActivity);
        });
    }
}