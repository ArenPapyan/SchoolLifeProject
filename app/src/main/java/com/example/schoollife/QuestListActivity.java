package com.example.schoollife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.schoollife.HomeActivity;
import com.example.schoollife.MiniGamesActivity;
import com.example.schoollife.QuizListActivity;
import com.example.schoollife.R;
import com.example.schoollife.SettingsActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class QuestListActivity extends AppCompatActivity {

    // UI Elements
    private TextView userDetailsText;
    private CardView headerLayout;
    private MaterialCardView miniGamesContainer, homeContainer, settingsContainer;
    private MaterialButton quest1StartButton, quest2StartButton, quest3StartButton,
            quest4StartButton, quest5StartButton;

    // Best Score TextViews
    private TextView quest1BestScore, quest2BestScore, quest3BestScore, quest4BestScore, quest5BestScore;

    // Progress bars and percentage TextViews
    private ProgressBar quest1Progress, quest2Progress, quest3Progress, quest4Progress, quest5Progress;
    private TextView quest1Percentage, quest2Percentage, quest3Percentage, quest4Percentage, quest5Percentage;

    // SharedPreferences key for storing best scores
    private static final String PREFS_NAME = "QuestAppPrefs";
    private static final String BEST_SCORE_KEY_PREFIX = "best_score_quest_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);

        // Initialize UI elements
        initializeViews();

        // Set up listeners
        setupClickListeners();

        // Set up bottom navigation
        setupBottomNavigation();

        // Load user data (if available)
        loadUserData();

        // Load and display best scores
        loadBestScores();
    }

    private void initializeViews() {
        // Header
        headerLayout = findViewById(R.id.headerLayout);
        userDetailsText = findViewById(R.id.UserDetails);

        // Bottom navigation
        miniGamesContainer = findViewById(R.id.miniGamesContainer);
        homeContainer = findViewById(R.id.homeContainer);
        settingsContainer = findViewById(R.id.settingsContainer);

        // Quest buttons
        quest1StartButton = findViewById(R.id.quest1StartButton);
        quest2StartButton = findViewById(R.id.quest2StartButton);
        quest3StartButton = findViewById(R.id.quest3StartButton);
        quest4StartButton = findViewById(R.id.quest4StartButton);
        quest5StartButton = findViewById(R.id.quest5StartButton);

        // Initialize best score TextViews
        quest1BestScore = findViewById(R.id.quest1BestScore);
        quest2BestScore = findViewById(R.id.quest2BestScore);
        quest3BestScore = findViewById(R.id.quest3BestScore);
        quest4BestScore = findViewById(R.id.quest4BestScore);
        quest5BestScore = findViewById(R.id.quest5BestScore);
    }

    private void setupClickListeners() {
        quest1StartButton.setOnClickListener(v -> startQuestActivity("Military Position", 1));
        quest2StartButton.setOnClickListener(v -> startQuestActivity("Football Player", 2));
        quest3StartButton.setOnClickListener(v -> startQuestActivity("Mountain Challenge", 3));
        quest4StartButton.setOnClickListener(v -> startQuestActivity("School Student", 4));
        quest5StartButton.setOnClickListener(v -> startQuestActivity("Love Story", 5));
    }

    private void loadUserData() {
        // You would typically load this from SharedPreferences, database, or API
        String username = getUsernameFromPreferences();
        if (username != null && !username.isEmpty()) {
            userDetailsText.setText(username);
        } else {
            userDetailsText.setText("Guest User");
        }
    }

    private String getUsernameFromPreferences() {
        // Example implementation
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .getString("username", null);
    }

    /**
     * Load the best scores for all quests from SharedPreferences
     * and update the corresponding TextViews
     */
    private void loadBestScores() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Load and display best score for each quest
        updateBestScoreDisplay(quest1BestScore, prefs.getInt(BEST_SCORE_KEY_PREFIX + 1, 0));
        updateBestScoreDisplay(quest2BestScore, prefs.getInt(BEST_SCORE_KEY_PREFIX + 2, 0));
        updateBestScoreDisplay(quest3BestScore, prefs.getInt(BEST_SCORE_KEY_PREFIX + 3, 0));
        updateBestScoreDisplay(quest4BestScore, prefs.getInt(BEST_SCORE_KEY_PREFIX + 4, 0));
        updateBestScoreDisplay(quest5BestScore, prefs.getInt(BEST_SCORE_KEY_PREFIX + 5, 0));
    }

    /**
     * Update the display of a best score TextView
     * @param textView The TextView to update
     * @param score The score to display
     */
    private void updateBestScoreDisplay(TextView textView, int score) {
        if (score > 0) {
            textView.setText("Best score: " + score);
        } else {
            textView.setText("Best score: Rank 0");
        }
    }

    /**
     * Save a new best score for a quest if it's higher than the existing best score
     * @param questId The ID of the quest
     * @param score The new score to save if it's a high score
     * @return true if the score was saved as a new high score, false otherwise
     */
    public boolean saveHighScore(int questId, int score) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String key = BEST_SCORE_KEY_PREFIX + questId;
        int currentBest = prefs.getInt(key, 0);

        // Only save if the new score is higher
        if (score > currentBest) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(key, score);
            editor.apply();

            // Update the display
            updateQuestBestScore(questId, score);
            return true;
        }
        return false;
    }

    /**
     * Update the best score display for a specific quest
     * @param questId The ID of the quest
     * @param score The best score to display
     */
    public void updateQuestBestScore(int questId, int score) {
        TextView textView = null;

        switch (questId) {
            case 1:
                textView = quest1BestScore;
                break;
            case 2:
                textView = quest2BestScore;
                break;
            case 3:
                textView = quest3BestScore;
                break;
            case 4:
                textView = quest4BestScore;
                break;
            case 5:
                textView = quest5BestScore;
                break;
        }

        if (textView != null) {
            updateBestScoreDisplay(textView, score);
        }
    }

    private void startQuestActivity(String questTitle, int questId) {
        // Navigate to specific quest details/start screen
        Intent intent = new Intent(this, QuestModeActivity.class);
        intent.putExtra("QUEST_TITLE", questTitle);
        intent.putExtra("QUEST_ID", questId);
        startActivity(intent);
    }

    private void setupBottomNavigation() {
        // Home button navigation
        homeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestListActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Mini Games navigation
        miniGamesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestListActivity.this, MiniGamesActivity.class);
                startActivity(intent);
            }
        });

        // Settings navigation
        settingsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestListActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to update quest progress
    public void updateQuestProgress(int questId, int progressPercentage) {
        switch (questId) {
            case 1:
                quest1Progress.setProgress(progressPercentage);
                quest1Percentage.setText(progressPercentage + "%");
                break;
            case 2:
                quest2Progress.setProgress(progressPercentage);
                quest2Percentage.setText(progressPercentage + "%");
                break;
            case 3:
                quest3Progress.setProgress(progressPercentage);
                quest3Percentage.setText(progressPercentage + "%");
                break;
            case 4:
                quest4Progress.setProgress(progressPercentage);
                quest4Percentage.setText(progressPercentage + "%");
                break;
            case 5:
                quest5Progress.setProgress(progressPercentage);
                quest5Percentage.setText(progressPercentage + "%");
                break;
        }
    }

    // If you have any back button handling for this screen:
    @Override
    public void onBackPressed() {
        // You might want custom behavior or just default
        super.onBackPressed();
    }

    /**
     * This method should be called from QuestModeActivity when a quest is completed
     * to update the best score if necessary
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Reload best scores each time this activity becomes visible
        loadBestScores();
    }
}