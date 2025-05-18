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

    /**
     * Saves quest score and returns whether it's a new best score
     *
     * @param questType type of quest
     * @param score current score
     * @param totalTasks total number of tasks in quest
     * @return true if this is a new best score, false otherwise
     */
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

                editor.putInt(bestScoreValueKey, score);
                editor.putInt(bestTotalKey, totalTasks);
            }

            editor.apply();
            return isNewBest;

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error saving quest score: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the best score for a quest type
     *
     * @param questType type of quest
     * @return best score percentage or 0 if not available
     */
    public int getBestScorePercentage(String questType) {
        if (questType == null || questType.isEmpty()) {
            return 0;
        }

        String bestScoreKey = questType + "_BEST_PERCENTAGE";
        return preferences.getInt(bestScoreKey, 0);
    }

    /**
     * Get current score details for a quest type
     *
     * @param questType type of quest
     * @return array of [score, total, percentage] or [0,0,0] if not available
     */
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


    public void resetScores(String questType) {
        if (questType == null || questType.isEmpty()) {
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(questType + "_BEST_PERCENTAGE");
        editor.remove(questType + "_BEST_SCORE");
        editor.remove(questType + "_BEST_TOTAL");
        editor.remove(questType + "_CURRENT_SCORE");
        editor.remove(questType + "_CURRENT_TOTAL");
        editor.remove(questType + "_CURRENT_PERCENTAGE");
        editor.apply();

        Log.d(LOG_TAG, "Reset all scores for " + questType);
    }
}