package com.example.schoollife;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.schoollife.R;


import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game2048 extends Activity implements GestureDetector.OnGestureListener {

    private static final int GRID_SIZE = 4;
    private static final int MIN_SWIPE_DISTANCE = 120;
    private static final int MIN_SWIPE_VELOCITY = 200;
    private static final String PREFS_NAME = "Game2048Prefs";
    private static final String BEST_SCORE_KEY = "bestScore";

    private int[][] gameBoard;
    private TextView[][] tiles;
    private TextView scoreText;
    private TextView bestScoreText;
    private Button newGameButton;
    private GridLayout gameGrid;
    private GestureDetector gestureDetector;
    private int score;
    private int bestScore;
    private boolean gameWon;
    private boolean gameOver;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        bestScore = sharedPreferences.getInt(BEST_SCORE_KEY, 0);

        initializeViews();
        gestureDetector = new GestureDetector(this, this);
        startNewGame();

        ConstraintLayout backToHomeLayout = findViewById(R.id.backToHomeLayout);
        backToHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game2048.this, MiniGamesListActivity.class);
                startActivity(intent);
                finish(); // Optional: close current activity
            }
        });
    }

    private void initializeViews() {
        scoreText = findViewById(R.id.scoreText);
        bestScoreText = findViewById(R.id.bestScoreText);
        newGameButton = findViewById(R.id.newGameButton);
        gameGrid = findViewById(R.id.gameGrid);

        // Initialize the tile array
        tiles = new TextView[GRID_SIZE][GRID_SIZE];

        // Create tiles programmatically
        gameGrid.setColumnCount(GRID_SIZE);
        gameGrid.setRowCount(GRID_SIZE);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                TextView tile = new TextView(this);
                tile.setWidth(200);
                tile.setHeight(200);
                tile.setGravity(android.view.Gravity.CENTER);
                tile.setTextSize(24);
                tile.setBackgroundResource(R.drawable.tile_background);
                tile.setTextColor(android.graphics.Color.BLACK);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.setMargins(4, 4, 4, 4);
                tile.setLayoutParams(params);

                gameGrid.addView(tile);
                tiles[i][j] = tile;
            }
        }

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });

        // Set touch listener for the entire game grid
        gameGrid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private void startNewGame() {
        gameBoard = new int[GRID_SIZE][GRID_SIZE];
        score = 0;
        gameWon = false;
        gameOver = false;

        // Add two initial tiles
        addRandomTile();
        addRandomTile();

        updateUI();
    }

    private void addRandomTile() {
        List<int[]> emptyCells = new ArrayList<>();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (gameBoard[i][j] == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            Random random = new Random();
            int[] randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
            gameBoard[randomCell[0]][randomCell[1]] = random.nextFloat() < 0.9f ? 2 : 4;
        }
    }

    private void updateUI() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                TextView tile = tiles[i][j];
                int value = gameBoard[i][j];

                if (value == 0) {
                    tile.setText("");
                    tile.setBackgroundColor(0xFFCDC1B4);
                } else {
                    tile.setText(String.valueOf(value));
                    tile.setBackgroundColor(getTileColor(value));
                }
            }
        }

        scoreText.setText("Score: " + score);
        bestScoreText.setText("Best: " + bestScore);

        // Update best score if current score is higher
        if (score > bestScore) {
            bestScore = score;
            saveBestScore();
            bestScoreText.setText("Best: " + bestScore);
        }

        if (!gameWon && isGameWon()) {
            Toast.makeText(this, "You Win! 🎉", Toast.LENGTH_LONG).show();
            gameWon = true;
        }

        if (isGameOver()) {
            Toast.makeText(this, "Game Over! 😔", Toast.LENGTH_LONG).show();
            gameOver = true;
        }
    }

    private void saveBestScore() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(BEST_SCORE_KEY, bestScore);
        editor.apply();
    }
    private int getTileColor(int value) {
        switch (value) {
            case 2: return 0xFFEEE4DA;
            case 4: return 0xFFEDE0C8;
            case 8: return 0xFFF2B179;
            case 16: return 0xFFF59563;
            case 32: return 0xFFF67C5F;
            case 64: return 0xFFF65E3B;
            case 128: return 0xFFEDCF72;
            case 256: return 0xFFEDCC61;
            case 512: return 0xFFEDC850;
            case 1024: return 0xFFEDC53F;
            case 2048: return 0xFFEDC22E;
            default: return 0xFF3C3A32;
        }
    }

    private boolean isGameWon() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (gameBoard[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGameOver() {
        // Check for empty cells
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (gameBoard[i][j] == 0) {
                    return false;
                }
            }
        }

        // Check for possible merges
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int current = gameBoard[i][j];
                if ((i < GRID_SIZE - 1 && gameBoard[i + 1][j] == current) ||
                        (j < GRID_SIZE - 1 && gameBoard[i][j + 1] == current)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean moveLeft() {
        boolean moved = false;

        for (int i = 0; i < GRID_SIZE; i++) {
            int[] row = new int[GRID_SIZE];
            System.arraycopy(gameBoard[i], 0, row, 0, GRID_SIZE);

            // Move tiles left
            int writeIndex = 0;
            for (int j = 0; j < GRID_SIZE; j++) {
                if (row[j] != 0) {
                    gameBoard[i][writeIndex] = row[j];
                    if (writeIndex != j) moved = true;
                    writeIndex++;
                }
            }

            // Fill remaining with zeros
            while (writeIndex < GRID_SIZE) {
                gameBoard[i][writeIndex] = 0;
                writeIndex++;
            }

            // Merge tiles
            for (int j = 0; j < GRID_SIZE - 1; j++) {
                if (gameBoard[i][j] != 0 && gameBoard[i][j] == gameBoard[i][j + 1]) {
                    gameBoard[i][j] *= 2;
                    score += gameBoard[i][j];
                    gameBoard[i][j + 1] = 0;
                    moved = true;

                    // Shift remaining tiles
                    for (int k = j + 1; k < GRID_SIZE - 1; k++) {
                        gameBoard[i][k] = gameBoard[i][k + 1];
                    }
                    gameBoard[i][GRID_SIZE - 1] = 0;
                }
            }
        }

        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;

        for (int i = 0; i < GRID_SIZE; i++) {
            int[] row = new int[GRID_SIZE];
            System.arraycopy(gameBoard[i], 0, row, 0, GRID_SIZE);

            // Move tiles right
            int writeIndex = GRID_SIZE - 1;
            for (int j = GRID_SIZE - 1; j >= 0; j--) {
                if (row[j] != 0) {
                    gameBoard[i][writeIndex] = row[j];
                    if (writeIndex != j) moved = true;
                    writeIndex--;
                }
            }

            // Fill remaining with zeros
            while (writeIndex >= 0) {
                gameBoard[i][writeIndex] = 0;
                writeIndex--;
            }

            // Merge tiles
            for (int j = GRID_SIZE - 1; j > 0; j--) {
                if (gameBoard[i][j] != 0 && gameBoard[i][j] == gameBoard[i][j - 1]) {
                    gameBoard[i][j] *= 2;
                    score += gameBoard[i][j];
                    gameBoard[i][j - 1] = 0;
                    moved = true;

                    // Shift remaining tiles
                    for (int k = j - 1; k > 0; k--) {
                        gameBoard[i][k] = gameBoard[i][k - 1];
                    }
                    gameBoard[i][0] = 0;
                }
            }
        }

        return moved;
    }

    private boolean moveUp() {
        boolean moved = false;

        for (int j = 0; j < GRID_SIZE; j++) {
            int[] column = new int[GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) {
                column[i] = gameBoard[i][j];
            }

            // Move tiles up
            int writeIndex = 0;
            for (int i = 0; i < GRID_SIZE; i++) {
                if (column[i] != 0) {
                    gameBoard[writeIndex][j] = column[i];
                    if (writeIndex != i) moved = true;
                    writeIndex++;
                }
            }

            // Fill remaining with zeros
            while (writeIndex < GRID_SIZE) {
                gameBoard[writeIndex][j] = 0;
                writeIndex++;
            }

            // Merge tiles
            for (int i = 0; i < GRID_SIZE - 1; i++) {
                if (gameBoard[i][j] != 0 && gameBoard[i][j] == gameBoard[i + 1][j]) {
                    gameBoard[i][j] *= 2;
                    score += gameBoard[i][j];
                    gameBoard[i + 1][j] = 0;
                    moved = true;

                    // Shift remaining tiles
                    for (int k = i + 1; k < GRID_SIZE - 1; k++) {
                        gameBoard[k][j] = gameBoard[k + 1][j];
                    }
                    gameBoard[GRID_SIZE - 1][j] = 0;
                }
            }
        }

        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;

        for (int j = 0; j < GRID_SIZE; j++) {
            int[] column = new int[GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) {
                column[i] = gameBoard[i][j];
            }

            // Move tiles down
            int writeIndex = GRID_SIZE - 1;
            for (int i = GRID_SIZE - 1; i >= 0; i--) {
                if (column[i] != 0) {
                    gameBoard[writeIndex][j] = column[i];
                    if (writeIndex != i) moved = true;
                    writeIndex--;
                }
            }

            // Fill remaining with zeros
            while (writeIndex >= 0) {
                gameBoard[writeIndex][j] = 0;
                writeIndex--;
            }

            // Merge tiles
            for (int i = GRID_SIZE - 1; i > 0; i--) {
                if (gameBoard[i][j] != 0 && gameBoard[i][j] == gameBoard[i - 1][j]) {
                    gameBoard[i][j] *= 2;
                    score += gameBoard[i][j];
                    gameBoard[i - 1][j] = 0;
                    moved = true;

                    // Shift remaining tiles
                    for (int k = i - 1; k > 0; k--) {
                        gameBoard[k][j] = gameBoard[k - 1][j];
                    }
                    gameBoard[0][j] = 0;
                }
            }
        }

        return moved;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (gameOver) return false;

        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();

        boolean moved = false;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Horizontal swipe
            if (Math.abs(deltaX) > MIN_SWIPE_DISTANCE && Math.abs(velocityX) > MIN_SWIPE_VELOCITY) {
                if (deltaX > 0) {
                    moved = moveRight();
                } else {
                    moved = moveLeft();
                }
            }
        } else {
            // Vertical swipe
            if (Math.abs(deltaY) > MIN_SWIPE_DISTANCE && Math.abs(velocityY) > MIN_SWIPE_VELOCITY) {
                if (deltaY > 0) {
                    moved = moveDown();
                } else {
                    moved = moveUp();
                }
            }
        }

        if (moved) {
            addRandomTile();
            updateUI();
        }

        return true;
    }

}