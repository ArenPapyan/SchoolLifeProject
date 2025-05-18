package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity {

    private TextView resultScoreText, resultMessageText, resultPercentageText;
    private Button homeButton, retryButton;
    private int score;
    private int totalQuestions;
    private String quizType;
    private QuizScoreManager scoreManager;
    private boolean isNewBestScore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        try {
            // Initialize score manager
            scoreManager = new QuizScoreManager(this);

            // Get data from intent
            Intent intent = getIntent();
            if (intent != null) {
                score = intent.getIntExtra("SCORE", 0);
                totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0);
                quizType = intent.getStringExtra("QUIZ_TYPE");

                // Save score and check if it's a new best
                isNewBestScore = scoreManager.saveQuizScore(quizType, score, totalQuestions);

                Log.d("EduThinkPlay", "Intent data received: score=" + score +
                        ", totalQuestions=" + totalQuestions + ", quizType=" + quizType +
                        ", isNewBestScore=" + isNewBestScore);
            } else {
                Log.e("EduThinkPlay", "Intent is null");
                Toast.makeText(this, "Error loading quiz results", Toast.LENGTH_SHORT).show();
            }

            // Initialize UI components
            initializeUI();

            // Display results
            displayResults();

            // Set up button listeners
            setupButtonListeners();

        } catch (Exception e) {
            Log.e("EduThinkPlay", "Error in QuizResultActivity: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeUI() {
        try {
            resultScoreText = findViewById(R.id.result_score_text);
            resultMessageText = findViewById(R.id.result_message_text);
            resultPercentageText = findViewById(R.id.result_percentage_text);
            homeButton = findViewById(R.id.home_button);
            retryButton = findViewById(R.id.retry_button);

            if (resultScoreText == null || resultMessageText == null || resultPercentageText == null ||
                    homeButton == null || retryButton == null) {
                Log.e("EduThinkPlay", "One or more UI elements not found");
                throw new NullPointerException("UI elements not found");
            }

        } catch (Exception e) {
            Log.e("EduThinkPlay", "Error initializing UI: " + e.getMessage());
            throw e;
        }
    }

    private void displayResults() {
        try {
            // Display score
            resultScoreText.setText(score + "/" + totalQuestions);

            // Calculate percentage
            int percentage = totalQuestions > 0 ? (score * 100) / totalQuestions : 0;
            Log.d("EduThinkPlay", "Score percentage: " + percentage + "%");

            // Display percentage
            resultPercentageText.setText("(" + percentage + "%)");

            // Display appropriate message based on score
            String message;
            if (percentage >= 80) {
                message = "Excellent! You've mastered this quiz!";
            } else if (percentage >= 60) {
                message = "Good job! You're doing well!";
            } else if (percentage >= 40) {
                message = "Not bad, but you can improve!";
            } else {
                message = "Keep practicing to improve your score!";
            }

            // Add new best score message if applicable
            if (isNewBestScore) {
                message = "New best score! " + message;
            }

            resultMessageText.setText(message);

        } catch (Exception e) {
            Log.e("EduThinkPlay", "Error displaying results: " + e.getMessage());
            throw e;
        }
    }

    private void setupButtonListeners() {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Go back to home screen
                    Log.d("EduThinkPlay", "Home button clicked");
                    Intent intent = new Intent(QuizResultActivity.this, QuizListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Log.e("EduThinkPlay", "Error on home button click: " + e.getMessage());
                    Toast.makeText(QuizResultActivity.this, "Error returning to home", Toast.LENGTH_SHORT).show();
                }
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Retry the same quiz
                    Log.d("EduThinkPlay", "Retry button clicked, quizType: " + quizType);
                    Intent intent = new Intent(QuizResultActivity.this, QuizActivity.class);
                    intent.putExtra("QUIZ_TYPE", quizType);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Log.e("EduThinkPlay", "Error on retry button click: " + e.getMessage());
                    Toast.makeText(QuizResultActivity.this, "Error restarting quiz", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}