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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class QuestListActivity extends AppCompatActivity {

    // UI Elements
    private TextView userDetailsText;
    private CardView headerLayout;
    private CardView miniGamesContainer, homeContainer, settingsContainer;
    private CardView quest1StartButton, quest2StartButton, quest3StartButton,
            quest4StartButton, quest5StartButton;

    // Best Score TextViews
    private TextView quest1BestScore, quest2BestScore, quest3BestScore, quest4BestScore, quest5BestScore;

    // Progress bars and percentage TextViews
    private ProgressBar quest1Progress, quest2Progress, quest3Progress, quest4Progress, quest5Progress;
    private TextView quest1Percentage, quest2Percentage, quest3Percentage, quest4Percentage, quest5Percentage;

    // Add QuestScoreManager
    private QuestScoreManager scoreManager;

    // Quest type mapping
    private String[] questTypes = {"MILITARY", "FOOTBALL", "HIKING", "SCHOOL", "LOVE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);

        // Initialize score manager
        scoreManager = new QuestScoreManager(this);

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
        return getSharedPreferences("QuestAppPrefs", MODE_PRIVATE)
                .getString("username", null);
    }

    /**
     * Load the best ranks for all quests using QuestScoreManager
     * and update the corresponding TextViews
     */
    private void loadBestScores() {
        // Load and display best rank for each quest
        updateBestRankDisplay(quest1BestScore, scoreManager.getBestRank(questTypes[0])); // MILITARY
        updateBestRankDisplay(quest2BestScore, scoreManager.getBestRank(questTypes[1])); // FOOTBALL
        updateBestRankDisplay(quest3BestScore, scoreManager.getBestRank(questTypes[2])); // HIKING
        updateBestRankDisplay(quest4BestScore, scoreManager.getBestRank(questTypes[3])); // SCHOOL
        updateBestRankDisplay(quest5BestScore, scoreManager.getBestRank(questTypes[4])); // LOVE
    }

    /**
     * Update the display of a best rank TextView
     * @param textView The TextView to update
     * @param rank The rank to display
     */
    private void updateBestRankDisplay(TextView textView, String rank) {
        if (rank != null && !rank.isEmpty() &&
                !rank.equals("Private") && !rank.equals("Bench Player") &&
                !rank.equals("Beginner") && !rank.equals("Struggling Student") &&
                !rank.equals("Single")) {
            textView.setText("Best rank: " + rank);
        } else {
            textView.setText("Best rank: Not achieved");
        }
    }

    private void startQuestActivity(String questTitle, int questId) {
        // Navigate to specific quest details/start screen
        Intent intent = new Intent(this, QuestModeActivity.class);
        intent.putExtra("QUEST_TITLE", questTitle);
        intent.putExtra("QUEST_ID", questId);

        // Add quest type for the score manager
        String questType = questTypes[questId - 1]; // questId is 1-based, array is 0-based
        intent.putExtra("QUEST_TYPE", questType);

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
                Intent intent = new Intent(QuestListActivity.this, MiniGamesListActivity.class);
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

    // Method to update quest progress (if you still need this functionality)
    public void updateQuestProgress(int questId, int progressPercentage) {
        switch (questId) {
            case 1:
                if (quest1Progress != null) quest1Progress.setProgress(progressPercentage);
                if (quest1Percentage != null) quest1Percentage.setText(progressPercentage + "%");
                break;
            case 2:
                if (quest2Progress != null) quest2Progress.setProgress(progressPercentage);
                if (quest2Percentage != null) quest2Percentage.setText(progressPercentage + "%");
                break;
            case 3:
                if (quest3Progress != null) quest3Progress.setProgress(progressPercentage);
                if (quest3Percentage != null) quest3Percentage.setText(progressPercentage + "%");
                break;
            case 4:
                if (quest4Progress != null) quest4Progress.setProgress(progressPercentage);
                if (quest4Percentage != null) quest4Percentage.setText(progressPercentage + "%");
                break;
            case 5:
                if (quest5Progress != null) quest5Progress.setProgress(progressPercentage);
                if (quest5Percentage != null) quest5Percentage.setText(progressPercentage + "%");
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
     * This method is called when returning to this activity
     * to refresh the best ranks display
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Reload best ranks each time this activity becomes visible
        loadBestScores();
    }

    /**
     * Method to get the quest type by quest ID
     * @param questId The quest ID (1-5)
     * @return The quest type string
     */
    public String getQuestTypeById(int questId) {
        if (questId >= 1 && questId <= 5) {
            return questTypes[questId - 1];
        }
        return questTypes[0]; // Default to MILITARY
    }

    /**
     * Method to refresh a specific quest's best rank display
     * @param questId The quest ID to refresh
     */
    public void refreshQuestBestRank(int questId) {
        String questType = getQuestTypeById(questId);
        String bestRank = scoreManager.getBestRank(questType);

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
            updateBestRankDisplay(textView, bestRank);
        }
    }

}