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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_result);

        // Initialize UI components
        resultScoreText = findViewById(R.id.result_score_text);
        resultMessageText = findViewById(R.id.result_message_text);
        homeButton = findViewById(R.id.home_button);
        restartButton = findViewById(R.id.restart_button); // Add this line to initialize restartButton

        // Get data from intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0);
        int totalTasks = intent.getIntExtra("TOTAL_TASKS", 0);
        String questType = intent.getStringExtra("QUEST_TYPE");

        Log.d(TAG, "Received data - score: " + score + ", totalTasks: " + totalTasks + ", questType: " + questType);

        displayResults(score, questType);

        // Set button click listeners
        homeButton.setOnClickListener(v -> goToHomePage());
        restartButton.setOnClickListener(v -> restartQuest(questType));
    }

    private void displayResults(int score, String questType) {
        try {
            // Get the appropriate rank based on quest type and score
            String rank = getRankByTypeAndScore(questType, score);

            Log.d(TAG, "Final rank: " + rank);

            // Display the achieved rank
            resultScoreText.setText(rank);

            // Display appropriate message based on quest type
            String message = getQuestMessage(questType, rank);
            resultMessageText.setText(message);

        } catch (Exception e) {
            Log.e(TAG, "Error displaying results: " + e.getMessage());
            Toast.makeText(this, "Սխալ տեղի ունեցավ արդյունքները ցուցադրելիս", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRankByTypeAndScore(String questType, int score) {
        // Get the appropriate rank based on quest type and score percentage
        String[] ranks;

        // Select ranks array based on quest type
        if ("MILITARY".equals(questType)) {
            ranks = new String[]{"Private", "Corporal", "Junior Sergeant", "Sergeant", "Senior Sergeant",
                    "Warrant Officer", "Non-commissioned Officer", "Lieutenant", "Senior Lieutenant"};
        } else if ("FOOTBALL".equals(questType)) {
            ranks = new String[]{"Bench Player", "Substitute", "Regular Player", "Key Player", "Star Player",
                    "Team Captain", "Club Legend"};
        } else if ("HIKING".equals(questType)) {
            ranks = new String[]{"Beginner", "Novice Hiker", "Intermediate", "Advanced", "Expert",
                    "Trail Master", "Mountaineer"};
        } else if ("SCHOOL".equals(questType)) {
            ranks = new String[]{"Struggling Student", "Average Student", "Good Student", "Honor Student",
                    "Top Student", "Class Representative", "Valedictorian"};
        } else if ("LOVE".equals(questType)) {
            ranks = new String[]{"Single", "Dating", "Exclusive", "Committed", "Engaged",
                    "Married", "Soul Mates"};
        } else {
            // Default to military ranks if quest type is unknown
            ranks = new String[]{"Private", "Corporal", "Junior Sergeant", "Sergeant", "Senior Sergeant",
                    "Warrant Officer", "Non-commissioned Officer", "Lieutenant", "Senior Lieutenant"};
        }

        // Calculate the rank index based on score (0-100)
        int rankIndex = Math.min((int)(score * (ranks.length - 1) / 100.0), ranks.length - 1);

        return ranks[rankIndex];
    }

    private String getQuestMessage(String questType, String rank) {
        // Return an appropriate message based on quest type and achieved rank
        if ("MILITARY".equals(questType)) {
            return "You have successfully completed the military quest and earned the rank " + rank + ". Continue your triumphant march!";
        } else if ("FOOTBALL".equals(questType)) {
            return "You have achieved the status of " + rank + " in the football team. Continue training and achieve new successes!";
        } else if ("HIKING".equals(questType)) {
            return "You have earned the rank of " + rank + " in the hiking quest. New adventures await you!";
        } else if ("SCHOOL".equals(questType)) {
            return "You have achieved the status of " + rank + " in the school quest. Continue your studies and reach the heights!";
        } else if ("LOVE".equals(questType)) {
            return "You have achieved the status of " + rank + " in the love quest. Love conquers all!";
        } else {
            return "Congratulations on achieving the status of " + rank + ". Keep moving forward!";
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