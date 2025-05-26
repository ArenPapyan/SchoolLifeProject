package com.example.schoollife;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.schoollife.R;


public class SnakeGame extends Activity {

    private SnakeGameView gameView;
    private TextView tvScore;
    private TextView tvHighScore;
    private CardView gameOverCard;
    private TextView tvFinalScore;
    private CardView controlCard;
    private Button btnPause;
    private int highScore = 0;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "SnakeGamePrefs";
    private static final String HIGH_SCORE_KEY = "high_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Make fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set layout
        setContentView(R.layout.activity_snake_game);

        // Initialize SharedPreferences and load high score
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        highScore = sharedPreferences.getInt(HIGH_SCORE_KEY, 0);

        // Create and add game view programmatically
        FrameLayout gameContainer = findViewById(R.id.gameContainer);
        gameView = new SnakeGameView(this);
        gameContainer.addView(gameView);

        // Initialize other views
        initializeViews();

        // Setup control buttons
        setupControls();

        // Set callbacks for game view to update UI
        gameView.setGameCallbacks(new SnakeGameView.GameCallbacks() {
            @Override
            public void onScoreChanged(int score) {
                runOnUiThread(() -> tvScore.setText("Score: " + score));
            }

            @Override
            public void onGameOver(int finalScore) {
                runOnUiThread(() -> {
                    // Update high score if needed
                    if (finalScore > highScore) {
                        highScore = finalScore;
                        tvHighScore.setText("Best: " + highScore);
                        // Save the new high score
                        saveHighScore();
                    }

                    tvFinalScore.setText("Final Score: " + finalScore);
                    showGameOverDialog();
                });
            }

            @Override
            public void onGameStart() {
                runOnUiThread(() -> {
                    hideGameOverDialog();
                    tvHighScore.setText("Best: " + highScore);
                });
            }
        });

        // Initialize high score display
        tvHighScore.setText("Best: " + highScore);
    }

    private void initializeViews() {
        tvScore = findViewById(R.id.tvScore);
        tvHighScore = findViewById(R.id.tvHighScore);
        gameOverCard = findViewById(R.id.gameOverCard);
        tvFinalScore = findViewById(R.id.tvFinalScore);
        controlCard = findViewById(R.id.controlCard);
        btnPause = findViewById(R.id.btnPause);
    }

    private void setupControls() {
        Button btnLeft = findViewById(R.id.btnLeft);
        Button btnRight = findViewById(R.id.btnRight);
        Button btnUp = findViewById(R.id.btnUp);
        Button btnDown = findViewById(R.id.btnDown);
        Button btnRestart = findViewById(R.id.btnRestart);
        Button btnRestartGameOver = findViewById(R.id.btnRestartGameOver);

        // Back to MiniGamesList button
        ConstraintLayout backToHomeLayout = findViewById(R.id.backToHomeLayout);
        backToHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pause the game before leaving
                if (gameView != null) {
                    gameView.pause();
                }
                Intent intent = new Intent(SnakeGame.this, MiniGamesListActivity.class);
                startActivity(intent);
                finish(); // Optional: close current activity
            }
        });

        // Direction controls
        btnLeft.setOnClickListener(v -> gameView.changeDirection(SnakeGameView.Heading.LEFT));
        btnRight.setOnClickListener(v -> gameView.changeDirection(SnakeGameView.Heading.RIGHT));
        btnUp.setOnClickListener(v -> gameView.changeDirection(SnakeGameView.Heading.UP));
        btnDown.setOnClickListener(v -> gameView.changeDirection(SnakeGameView.Heading.DOWN));

        // Pause/Resume button
        btnPause.setOnClickListener(v -> {
            if (gameView.isPaused()) {
                gameView.resumeGame();
                btnPause.setText("Pause");
            } else {
                gameView.pauseGame();
                btnPause.setText("Resume");
            }
        });

        // Restart button in control panel
        btnRestart.setOnClickListener(v -> {
            gameView.newGame();
            hideGameOverDialog();
            btnPause.setText("Pause");
        });

        // Restart button in game over dialog
        btnRestartGameOver.setOnClickListener(v -> {
            gameView.newGame();
            hideGameOverDialog();
            btnPause.setText("Pause");
        });

        // Toggle control panel visibility with long press on game area
        gameView.setOnLongClickListener(v -> {
            toggleControlPanelVisibility();
            return true;
        });
    }

    private void saveHighScore() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HIGH_SCORE_KEY, highScore);
        editor.apply(); // Use apply() for asynchronous saving
    }

    private void showGameOverDialog() {
        gameOverCard.setVisibility(View.VISIBLE);
        gameOverCard.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(300)
                .start();
    }

    private void hideGameOverDialog() {
        gameOverCard.animate()
                .alpha(0.0f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(300)
                .withEndAction(() -> gameOverCard.setVisibility(View.GONE))
                .start();
    }

    private void toggleControlPanelVisibility() {
        if (controlCard.getVisibility() == View.VISIBLE) {
            // Hide with animation
            controlCard.animate()
                    .translationY(controlCard.getHeight())
                    .alpha(0.0f)
                    .setDuration(300)
                    .withEndAction(() -> controlCard.setVisibility(View.GONE))
                    .start();
        } else {
            // Show with animation
            controlCard.setVisibility(View.VISIBLE);
            controlCard.setTranslationY(controlCard.getHeight());
            controlCard.setAlpha(0.0f);
            controlCard.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setDuration(300)
                    .start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameView != null) {
            gameView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameView != null) {
            gameView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameView != null) {
            gameView.pause();
        }
    }

    @Override
    public void onBackPressed() {
        // Pause the game when back button is pressed
        if (gameView != null) {
            gameView.pause();
        }
        super.onBackPressed();
    }
}