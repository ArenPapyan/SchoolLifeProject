package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuestResultActivity extends AppCompatActivity {

    private TextView resultTitleText;
    private TextView resultScoreText;
    private TextView resultMessageText;
    private Button homeButton;
    private Button restartButton;

    private static final String TAG = "QuestResultActivity";
    private QuestScoreManager scoreManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_result);

        // Initialize score manager
        scoreManager = new QuestScoreManager(this);

        // Initialize UI components
        resultScoreText = findViewById(R.id.result_score_text);
        resultMessageText = findViewById(R.id.result_message_text);
        homeButton = findViewById(R.id.home_button);
        restartButton = findViewById(R.id.restart_button);

        // Get data from intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int totalTasks = intent.getIntExtra("TOTAL_TASKS", 0);
        String questType = intent.getStringExtra("QUEST_TYPE");

        // DEBUG: Log everything we received
        Log.d(TAG, "==================== QUEST RESULT DEBUG ====================");
        Log.d(TAG, "Received SCORE: " + score);
        Log.d(TAG, "Received TOTAL_TASKS: " + totalTasks);
        Log.d(TAG, "Received QUEST_TYPE: '" + questType + "'");

        // Validate inputs
        if (questType == null) {
            Log.e(TAG, "ERROR: QUEST_TYPE is NULL!");
            questType = "MILITARY"; // fallback
        }
        if (totalTasks == 0) {
            Log.e(TAG, "ERROR: TOTAL_TASKS is 0!");
            totalTasks = 1; // prevent division by zero
        }

        // Calculate percentage
        int percentage = (score * 100) / totalTasks;
        Log.d(TAG, "Score calculation: (" + score + " * 100) / " + totalTasks + " = " + percentage + "%");

        // Get current rank for this performance
        String currentRank = getRankByTypeAndScore(questType, percentage);
        Log.d(TAG, "Current performance rank: " + currentRank);

        // Get previous best BEFORE saving new score
        int previousBestPercentage = scoreManager.getBestScorePercentage(questType);
        String previousBestRank = getRankByTypeAndScore(questType, previousBestPercentage);
        Log.d(TAG, "Previous best: " + previousBestPercentage + "% (" + previousBestRank + ")");

        // Check if this is actually better
        boolean shouldBeNewBest = percentage > previousBestPercentage;
        Log.d(TAG, "Should be new best: " + shouldBeNewBest + " (" + percentage + "% vs " + previousBestPercentage + "%)");

        // Save the score
        boolean isNewBest = scoreManager.saveQuestScore(questType, score, totalTasks);
        Log.d(TAG, "ScoreManager says is new best: " + isNewBest);

        // Get current best AFTER saving
        int currentBestPercentage = scoreManager.getBestScorePercentage(questType);
        String currentBestRank = getRankByTypeAndScore(questType, currentBestPercentage);
        Log.d(TAG, "Current best after saving: " + currentBestPercentage + "% (" + currentBestRank + ")");

        Log.d(TAG, "==================== END DEBUG ====================");

        // Display results
        displayResults(questType, currentRank, currentBestRank, isNewBest);

        // Set button click listeners
        homeButton.setOnClickListener(v -> goToHomePage());
        String finalQuestType = questType;
        restartButton.setOnClickListener(v -> restartQuest(finalQuestType));
    }

    private void displayResults(String questType, String currentRank, String bestRank, boolean isNewBest) {
        try {
            Log.d(TAG, "Displaying - Current: " + currentRank + ", Best: " + bestRank + ", IsNewBest: " + isNewBest);

            // Display the achieved rank (what you actually got this time)
            if (isNewBest) {
                resultScoreText.setText(currentRank + " 🏆 (NEW BEST!)");
            } else {
                resultScoreText.setText("This game: " + currentRank);
            }

            // Display message
            String message = getQuestMessage(questType, currentRank, bestRank, isNewBest);
            resultMessageText.setText(message);

        } catch (Exception e) {
            Log.e(TAG, "Error displaying results: " + e.getMessage());
            Toast.makeText(this, "Error displaying results", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRankByTypeAndScore(String questType, int percentage) {
        String[] ranks;

        // Select ranks array based on quest type
        switch (questType) {
            case "MILITARY":
                ranks = new String[]{"Private", "Corporal", "Junior Sergeant", "Sergeant", "Senior Sergeant",
                        "Warrant Officer", "Non-commissioned Officer", "Lieutenant", "Senior Lieutenant"};
                break;
            case "FOOTBALL":
                ranks = new String[]{"Bench Player", "Substitute", "Regular Player", "Key Player", "Star Player",
                        "Team Captain", "Club Legend"};
                break;
            case "HIKING":
                ranks = new String[]{"Beginner", "Novice Hiker", "Intermediate", "Advanced", "Expert",
                        "Trail Master", "Mountaineer"};
                break;
            case "SCHOOL":
                ranks = new String[]{"Struggling Student", "Average Student", "Good Student", "Honor Student",
                        "Top Student", "Class Representative", "Valedictorian"};
                break;
            case "LOVE":
                ranks = new String[]{"Single", "Dating", "Exclusive", "Committed", "Engaged",
                        "Married", "Soul Mates"};
                break;
            default:
                Log.w(TAG, "Unknown quest type: " + questType + ", using MILITARY ranks");
                ranks = new String[]{"Private", "Corporal", "Junior Sergeant", "Sergeant", "Senior Sergeant",
                        "Warrant Officer", "Non-commissioned Officer", "Lieutenant", "Senior Lieutenant"};
                break;
        }

        // Calculate the rank index based on percentage (0-100)
        int rankIndex = Math.min((int)(percentage * (ranks.length - 1) / 100.0), ranks.length - 1);
        rankIndex = Math.max(0, rankIndex); // Ensure it's not negative

        Log.d(TAG, "Rank calculation: " + percentage + "% -> index " + rankIndex + " -> " + ranks[rankIndex]);

        return ranks[rankIndex];
    }

    private String getQuestMessage(String questType, String currentRank, String bestRank, boolean isNewBest) {
        String baseMessage;

        switch (questType) {
            case "MILITARY":
                baseMessage = "You completed the military quest and earned the rank " + currentRank + ".";
                break;
            case "FOOTBALL":
                baseMessage = "You achieved the status of " + currentRank + " in the football team.";
                break;
            case "HIKING":
                baseMessage = "You earned the rank of " + currentRank + " in the hiking quest.";
                break;
            case "SCHOOL":
                baseMessage = "You achieved the status of " + currentRank + " in the school quest.";
                break;
            case "LOVE":
                baseMessage = "You achieved the status of " + currentRank + " in the love quest.";
                break;
            default:
                baseMessage = "Congratulations on achieving the status of " + currentRank + ".";
                break;
        }

        // Add best rank information
        if (isNewBest) {
            baseMessage += "\n\n🎉 NEW PERSONAL BEST! 🎉";
        } else if (!isDefaultRank(questType, bestRank)) {
            baseMessage += "\n\nYour all-time best: " + bestRank;
        }

        return baseMessage;
    }

    private boolean isDefaultRank(String questType, String rank) {
        switch (questType) {
            case "MILITARY": return "Private".equals(rank);
            case "FOOTBALL": return "Bench Player".equals(rank);
            case "HIKING": return "Beginner".equals(rank);
            case "SCHOOL": return "Struggling Student".equals(rank);
            case "LOVE": return "Single".equals(rank);
            default: return "Private".equals(rank);
        }
    }

    private void goToHomePage() {
        Intent intent = new Intent(this, QuestListActivity.class);
        startActivity(intent);
        finish();
    }

    private void restartQuest(String questType) {
        Intent intent = new Intent(this, QuestModeActivity.class);
        intent.putExtra("QUEST_TYPE", questType);
        startActivity(intent);
        finish();
    }
}