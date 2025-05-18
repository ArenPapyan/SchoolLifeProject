package com.example.schoollife;

import android.content.Context;
import android.content.SharedPreferences;

public class QuizScoreManager {
    private static final String PREF_NAME = "quiz_scores";
    private static final String BEST_SCORE_PREFIX = "best_score_";
    private static final String BEST_PERCENTAGE_PREFIX = "best_percentage_";

    private SharedPreferences preferences;

    public QuizScoreManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Saves a new quiz score if it's better than the existing best score
     * @return true if this was a new best score, false otherwise
     */
    public boolean saveQuizScore(String quizType, int score, int totalQuestions) {
        int percentage = calculatePercentage(score, totalQuestions);
        int currentBestPercentage = getBestPercentage(quizType);

        // If new score is better than the current best, save it
        if (percentage > currentBestPercentage) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(BEST_SCORE_PREFIX + quizType, score);
            editor.putInt(BEST_PERCENTAGE_PREFIX + quizType, percentage);
            editor.apply();
            return true;
        }
        return false;
    }

    public int getBestScore(String quizType) {
        return preferences.getInt(BEST_SCORE_PREFIX + quizType, 0);
    }

    public int getBestPercentage(String quizType) {
        return preferences.getInt(BEST_PERCENTAGE_PREFIX + quizType, 0);
    }

    private int calculatePercentage(int score, int totalQuestions) {
        return totalQuestions > 0 ? (score * 100) / totalQuestions : 0;
    }
}