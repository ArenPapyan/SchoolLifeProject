package com.example.schoollife;

import android.annotation.SuppressLint;
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

import com.example.schoollife.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button Quest = findViewById(R.id.btn2);
        Button quiz = findViewById(R.id.btnQuiz);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        TextView miniGames = findViewById(R.id.miniGames);
        TextView settings = findViewById(R.id.settings);
        TextView textView = findViewById(R.id.UserDetails);
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        // Optional: Add card click listeners if you want the entire cards to be clickable
        CardView questCard = findViewById(R.id.questCard);
        CardView quizCard = findViewById(R.id.quizCard);

        if (questCard != null) {
            questCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, QuestListActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (quizCard != null) {
            quizCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, QuizListActivity.class);
                    startActivity(intent);
                }
            });
        }

        Quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QuestListActivity.class);
                startActivity(intent);
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QuizListActivity.class);
                startActivity(intent);
            }
        });

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

        // Optional: Add container click listeners for the new bottom navigation if they exist
        View miniGamesContainer = findViewById(R.id.miniGamesContainer);
        View settingsContainer = findViewById(R.id.settingsContainer);

        if (miniGamesContainer != null) {
            miniGamesContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, MiniGamesListActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (settingsContainer != null) {
            settingsContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}