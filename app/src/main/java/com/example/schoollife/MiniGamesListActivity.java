package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MiniGamesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mini_games_list);
        View homeContainer = findViewById(R.id.homeContainer);
        View settingsContainer = findViewById(R.id.settingsContainer);
        Button game1PlayButton = findViewById(R.id.game1PlayButton);
        Button game2PlayButton = findViewById(R.id.game2PlayButton);


        if (homeContainer != null) {
            homeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MiniGamesListActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        game1PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiniGamesListActivity.this, MiniGamesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        game2PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiniGamesListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (settingsContainer != null) {
            settingsContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MiniGamesListActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}