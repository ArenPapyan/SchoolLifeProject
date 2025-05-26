package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MiniGamesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_mini_games_list);
        View homeContainer = findViewById(R.id.homeContainer);
        View settingsContainer = findViewById(R.id.settingsContainer);
        CardView game1PlayButton = findViewById(R.id.game1PlayButton);
        CardView game2PlayButton = findViewById(R.id.game2PlayButton);
        CardView game3PlayButton = findViewById(R.id.game3PlayButton);
        CardView game4PlayButton = findViewById(R.id.game4PlayButton);



        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        TextView textView = findViewById(R.id.UserDetails);
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (user1 != null) {
                String username = user.getDisplayName();
                textView.setText(username);
            }
        }

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
        game3PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiniGamesListActivity.this, SnakeGame.class);
                startActivity(intent);
                finish();
            }
        });
        game4PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiniGamesListActivity.this, Game2048.class);
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