package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class QuizListActivity extends AppCompatActivity {

    private TextView userDetailsText;
    private QuizScoreManager scoreManager;

    // Quiz progress UI elements
    private ProgressBar quiz1Progress, quiz2Progress, quiz3Progress, quiz4Progress, quiz5Progress;
    private TextView quiz1Percentage, quiz2Percentage, quiz3Percentage, quiz4Percentage, quiz5Percentage;

    // Quiz types
    private static final String PHYSICS = "Physics";
    private static final String CHEMISTRY = "Chemistry";
    private static final String MATHS = "Maths";
    private static final String GEOGRAPHY = "Geography";
    private static final String BIOLOGY = "Biology";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

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
        try {
            // Initialize score manager
            scoreManager = new QuizScoreManager(this);

            // Initialize UI components
            initializeUI();

            // Update progress bars with best scores
            updateBestScores();

            // Set up click listeners for quiz cards
            setupQuizCardListeners();

            // Set up bottom navigation
            setupBottomNavigation();

        } catch (Exception e) {
            Log.e("EduThinkPlay", "Error in QuizListActivity: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update scores when returning to this activity
        updateBestScores();
    }

    private void initializeUI() {
        // User details text
        userDetailsText = findViewById(R.id.UserDetails);

        // Quiz progress bars
        quiz1Progress = findViewById(R.id.quiz1Progress);
        quiz2Progress = findViewById(R.id.quiz2Progress);
        quiz3Progress = findViewById(R.id.quiz3Progress);
        quiz4Progress = findViewById(R.id.quiz4Progress);
        quiz5Progress = findViewById(R.id.quiz5Progress);

        // Quiz percentage texts
        quiz1Percentage = findViewById(R.id.quiz1Percentage);
        quiz2Percentage = findViewById(R.id.quiz2Percentage);
        quiz3Percentage = findViewById(R.id.quiz3Percentage);
        quiz4Percentage = findViewById(R.id.quiz4Percentage);
        quiz5Percentage = findViewById(R.id.quiz5Percentage);
    }

    private void updateBestScores() {
        // Update Physics quiz progress
        updateQuizProgress(quiz1Progress, quiz1Percentage, PHYSICS);

        // Update Chemistry quiz progress
        updateQuizProgress(quiz2Progress, quiz2Percentage, CHEMISTRY);

        // Update Maths quiz progress
        updateQuizProgress(quiz3Progress, quiz3Percentage, MATHS);

        // Update Geography quiz progress
        updateQuizProgress(quiz4Progress, quiz4Percentage, GEOGRAPHY);

        // Update Biology quiz progress
        updateQuizProgress(quiz5Progress, quiz5Percentage, BIOLOGY);
    }

    private void updateQuizProgress(ProgressBar progressBar, TextView percentageText, String quizType) {
        int bestPercentage = scoreManager.getBestPercentage(quizType);
        progressBar.setProgress(bestPercentage);
        percentageText.setText(bestPercentage + "%");
    }

    private void setupQuizCardListeners() {
        // Set up each quiz card's click listener
        setupQuizCardListener(R.id.quiz1StartButton, PHYSICS);
        setupQuizCardListener(R.id.quiz2StartButton, CHEMISTRY);
        setupQuizCardListener(R.id.quiz3StartButton, MATHS);
        setupQuizCardListener(R.id.quiz4StartButton, GEOGRAPHY);
        setupQuizCardListener(R.id.quiz5StartButton, BIOLOGY);
    }

    private void setupQuizCardListener(int buttonId, final String quizType) {
        CardView startButton = findViewById(buttonId);
        if (startButton != null){
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(quizType);
            }
        });
    }
    }

    private void startQuiz(String quizType) {
        Intent intent = new Intent(QuizListActivity.this, QuizActivity.class);
        intent.putExtra("QUIZ_TYPE", quizType);
        startActivity(intent);
        finish();
    }

    private void setupBottomNavigation() {
        // Home button is already active in this screen
        CardView HomeContainer = findViewById(R.id.homeContainer);
        HomeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to mini games activity
                Intent intent = new Intent(QuizListActivity.this, HomeActivity.class);
                startActivity(intent);
                // Toast message can be shown for features not yet implemented
            }
        });
        // Mini Games navigation
        CardView miniGamesContainer = findViewById(R.id.miniGamesContainer);
        miniGamesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to mini games activity
                Intent intent = new Intent(QuizListActivity.this, MiniGamesListActivity.class);
                startActivity(intent);
                // Toast message can be shown for features not yet implemented
            }
        });

        // Settings navigation
        CardView settingsContainer = findViewById(R.id.settingsContainer);
        settingsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizListActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}