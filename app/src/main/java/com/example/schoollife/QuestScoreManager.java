package com.example.schoollife;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class QuestScoreManager {

    private static final String PREFERENCES_NAME = "QuestScores";
    private static final String LOG_TAG = "EduThinkPlay";
    private SharedPreferences preferences;

    public QuestScoreManager(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public boolean saveQuestScore(String questType, int score, int totalTasks) {
        try {
            if (questType == null || questType.isEmpty()) {
                Log.e(LOG_TAG, "Invalid questType provided");
                return false;
            }

            // Calculate percentage
            int percentage = totalTasks > 0 ? (score * 100) / totalTasks : 0;

            // Get previous best score percentage
            String bestScoreKey = questType + "_BEST_PERCENTAGE";
            int previousBestPercentage = preferences.getInt(bestScoreKey, 0);

            boolean isNewBest = percentage > previousBestPercentage;

            // Save the current score details
            String currentScoreKey = questType + "_CURRENT_SCORE";
            String currentTotalKey = questType + "_CURRENT_TOTAL";
            String currentPercentageKey = questType + "_CURRENT_PERCENTAGE";

            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(currentScoreKey, score);
            editor.putInt(currentTotalKey, totalTasks);
            editor.putInt(currentPercentageKey, percentage);

            // If this is a new best score, save it too
            if (isNewBest) {
                Log.d(LOG_TAG, "New best score for " + questType + ": " + percentage + "% (previous: " + previousBestPercentage + "%)");
                editor.putInt(bestScoreKey, percentage);

                String bestScoreValueKey = questType + "_BEST_SCORE";
                String bestTotalKey = questType + "_BEST_TOTAL";
                String bestRankKey = questType + "_BEST_RANK";

                editor.putInt(bestScoreValueKey, score);
                editor.putInt(bestTotalKey, totalTasks);

                // Save the best rank achieved
                String bestRank = getRankByTypeAndScore(questType, percentage);
                editor.putString(bestRankKey, bestRank);
            }

            editor.apply();
            return isNewBest;

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error saving quest score: " + e.getMessage());
            return false;
        }
    }

    public int getBestScorePercentage(String questType) {
        if (questType == null || questType.isEmpty()) {
            return 0;
        }

        String bestScoreKey = questType + "_BEST_PERCENTAGE";
        return preferences.getInt(bestScoreKey, 0);
    }

    public String getBestRank(String questType) {
        if (questType == null || questType.isEmpty()) {
            return getDefaultRank(questType);
        }

        String bestRankKey = questType + "_BEST_RANK";
        String bestRank = preferences.getString(bestRankKey, "");

        if (bestRank.isEmpty()) {
            // If no best rank saved, calculate from best percentage
            int bestPercentage = getBestScorePercentage(questType);
            if (bestPercentage > 0) {
                return getRankByTypeAndScore(questType, bestPercentage);
            } else {
                return getDefaultRank(questType);
            }
        }

        return bestRank;
    }

    public int[] getCurrentScoreDetails(String questType) {
        if (questType == null || questType.isEmpty()) {
            return new int[]{0, 0, 0};
        }

        String currentScoreKey = questType + "_CURRENT_SCORE";
        String currentTotalKey = questType + "_CURRENT_TOTAL";
        String currentPercentageKey = questType + "_CURRENT_PERCENTAGE";

        int score = preferences.getInt(currentScoreKey, 0);
        int total = preferences.getInt(currentTotalKey, 0);
        int percentage = preferences.getInt(currentPercentageKey, 0);

        return new int[]{score, total, percentage};
    }

    private String getRankByTypeAndScore(String questType, int percentage) {
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

        // FIXED: Ensure percentage is within bounds and handle edge cases properly
        percentage = Math.max(0, Math.min(100, percentage)); // Clamp between 0-100

        // Calculate the rank index based on percentage (0-100)
        // The issue was here - we need to ensure proper distribution across ranks
        int rankIndex;
        if (percentage == 0) {
            rankIndex = 0; // Lowest rank for 0%
        } else if (percentage >= 100) {
            rankIndex = ranks.length - 1; // Highest rank for 100%
        } else {
            // For percentages 1-99, distribute across ranks 0 to (length-1)
            // Using ceiling to ensure we don't get stuck at rank 0 for low percentages
            rankIndex = (int) Math.ceil((double) percentage * (ranks.length - 1) / 100.0);
            rankIndex = Math.max(0, Math.min(ranks.length - 1, rankIndex));
        }

        Log.d(LOG_TAG, "Rank calculation: " + percentage + "% -> index " + rankIndex + " -> " + ranks[rankIndex]);

        return ranks[rankIndex];
    }

    // Add this method to help with debugging - you can call it to test rank calculations
    public void testRankCalculations(String questType) {
        Log.d(LOG_TAG, "=== Testing rank calculations for " + questType + " ===");
        for (int i = 0; i <= 100; i += 10) {
            String rank = getRankByTypeAndScore(questType, i);
            Log.d(LOG_TAG, i + "% -> " + rank);
        }
        Log.d(LOG_TAG, "=== End test ===");
    }

    private String getDefaultRank(String questType) {
        if ("MILITARY".equals(questType)) {
            return "Private";
        } else if ("FOOTBALL".equals(questType)) {
            return "Bench Player";
        } else if ("HIKING".equals(questType)) {
            return "Beginner";
        } else if ("SCHOOL".equals(questType)) {
            return "Struggling Student";
        } else if ("LOVE".equals(questType)) {
            return "Single";
        } else {
            return "Private";
        }
    }

    public void resetScores(String questType) {
        if (questType == null || questType.isEmpty()) {
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(questType + "_BEST_PERCENTAGE");
        editor.remove(questType + "_BEST_SCORE");
        editor.remove(questType + "_BEST_TOTAL");
        editor.remove(questType + "_BEST_RANK");
        editor.remove(questType + "_CURRENT_SCORE");
        editor.remove(questType + "_CURRENT_TOTAL");
        editor.remove(questType + "_CURRENT_PERCENTAGE");
        editor.apply();

        Log.d(LOG_TAG, "Reset all scores for " + questType);
    }
}