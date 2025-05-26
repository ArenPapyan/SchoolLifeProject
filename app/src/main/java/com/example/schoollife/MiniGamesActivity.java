package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniGamesActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private int roundCount = 0;
    private ConstraintLayout BTH;
    private TextView statusText;
    private boolean isVsBot = false;
    private boolean gameEnded = false;
    private Handler botHandler = new Handler();
    private Random random = new Random();
    private boolean playerIsX = true; // Player's symbol assignment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_games);

        BTH = findViewById(R.id.backToHomeLayout);
        statusText = findViewById(R.id.statusText);

        BTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiniGamesActivity.this, MiniGamesListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initializeButtons();
        showGameModeDialog();

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> restartGame());

        Button changeModeButton = findViewById(R.id.changeModeButton);
        changeModeButton.setOnClickListener(v -> changeGameMode());
    }

    private void initializeButtons() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + (i * 3 + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new ButtonClickListener(i, j));
            }
        }
    }

    private void showGameModeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Game Mode");
        builder.setMessage("Select how you want to play:");

        builder.setPositiveButton("vs Friend", (dialog, which) -> {
            isVsBot = false;
            playerIsX = true;
            startNewGame();
        });

        builder.setNegativeButton("vs Bot", (dialog, which) -> {
            isVsBot = true;
            // Random assignment: player can be X or O
            playerIsX = random.nextBoolean();
            startNewGame();
        });

        builder.setCancelable(false);
        builder.show();
    }

    private void updateStatusText() {
        if (gameEnded) return;

        if (isVsBot) {
            boolean isPlayerTurn = (playerIsX && playerXTurn) || (!playerIsX && !playerXTurn);
            if (isPlayerTurn) {
                String playerSymbol = playerIsX ? "X" : "O";
                statusText.setText("Your Turn (" + playerSymbol + ")");
            } else {
                String botSymbol = playerIsX ? "O" : "X";
                statusText.setText("Bot's Turn (" + botSymbol + ")");
            }
        } else {
            if (playerXTurn) {
                statusText.setText("Player X's Turn");
            } else {
                statusText.setText("Player O's Turn");
            }
        }
    }

    private void startNewGame() {
        roundCount = 0;
        gameEnded = false;
        playerXTurn = true; // X always starts first

        // Clear the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        // Remove any pending bot moves
        botHandler.removeCallbacksAndMessages(null);

        updateStatusText();

        // If bot is X (starts first), make bot move
        if (isVsBot && !playerIsX) {
            botHandler.postDelayed(this::makeBotMove, 1000);
        }
    }

    private void restartGame() {
        startNewGame();
    }

    private void changeGameMode() {
        showGameModeDialog();
    }

    private class ButtonClickListener implements View.OnClickListener {
        private final int row;
        private final int col;

        ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            // Ignore if game ended or button already clicked
            if (gameEnded || !((Button) v).getText().toString().equals("")) {
                return;
            }

            // If it's bot mode, check if it's actually player's turn
            if (isVsBot) {
                boolean isPlayerTurn = (playerIsX && playerXTurn) || (!playerIsX && !playerXTurn);
                if (!isPlayerTurn) {
                    return; // Not player's turn
                }
            }

            makeMove(row, col, (Button) v);
        }
    }

    private void makeMove(int row, int col, Button button) {
        if (playerXTurn) {
            button.setText("X");
        } else {
            button.setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            gameEnded = true;
            String winner;
            if (isVsBot) {
                // Determine winner based on who made the winning move
                boolean playerWon = (playerIsX && playerXTurn) || (!playerIsX && !playerXTurn);
                winner = playerWon ? "You win!" : "Bot wins!";
            } else {
                winner = playerXTurn ? "Player X wins!" : "Player O wins!";
            }
            showToast(winner);
            statusText.setText(winner);
            return;
        } else if (roundCount == 9) {
            gameEnded = true;
            showToast("It's a draw!");
            statusText.setText("It's a draw!");
            return;
        }

        // Switch turns
        playerXTurn = !playerXTurn;
        updateStatusText();

        // If it's bot mode and now it's bot's turn, make bot move
        if (isVsBot && !gameEnded) {
            boolean isBotTurn = (!playerIsX && playerXTurn) || (playerIsX && !playerXTurn);
            if (isBotTurn) {
                botHandler.postDelayed(this::makeBotMove, 1000);
            }
        }
    }

    private void makeBotMove() {
        if (gameEnded) return;

        String botSymbol = playerIsX ? "O" : "X";
        String playerSymbol = playerIsX ? "X" : "O";

        // Try to win first
        int[] winMove = findWinningMove(botSymbol);
        if (winMove != null) {
            makeMove(winMove[0], winMove[1], buttons[winMove[0]][winMove[1]]);
            return;
        }

        // Block player from winning
        int[] blockMove = findWinningMove(playerSymbol);
        if (blockMove != null) {
            makeMove(blockMove[0], blockMove[1], buttons[blockMove[0]][blockMove[1]]);
            return;
        }

        // Take center if available
        if (buttons[1][1].getText().toString().equals("")) {
            makeMove(1, 1, buttons[1][1]);
            return;
        }

        // Take a corner
        int[][] corners = {{0,0}, {0,2}, {2,0}, {2,2}};
        List<int[]> availableCorners = new ArrayList<>();
        for (int[] corner : corners) {
            if (buttons[corner[0]][corner[1]].getText().toString().equals("")) {
                availableCorners.add(corner);
            }
        }
        if (!availableCorners.isEmpty()) {
            int[] corner = availableCorners.get(random.nextInt(availableCorners.size()));
            makeMove(corner[0], corner[1], buttons[corner[0]][corner[1]]);
            return;
        }

        // Take any available spot
        List<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    availableMoves.add(new int[]{i, j});
                }
            }
        }
        if (!availableMoves.isEmpty()) {
            int[] move = availableMoves.get(random.nextInt(availableMoves.size()));
            makeMove(move[0], move[1], buttons[move[0]][move[1]]);
        }
    }

    private int[] findWinningMove(String player) {
        // Check all possible moves to see if any results in a win
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    // Temporarily make the move
                    buttons[i][j].setText(player);
                    boolean isWin = checkForWin();
                    buttons[i][j].setText(""); // Undo the move

                    if (isWin) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    private boolean checkForWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(buttons[i][1].getText().toString()) &&
                    buttons[i][0].getText().toString().equals(buttons[i][2].getText().toString()) &&
                    !buttons[i][0].getText().toString().equals("")) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().toString().equals(buttons[1][i].getText().toString()) &&
                    buttons[0][i].getText().toString().equals(buttons[2][i].getText().toString()) &&
                    !buttons[0][i].getText().toString().equals("")) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[0][0].getText().toString().equals(buttons[2][2].getText().toString()) &&
                !buttons[0][0].getText().toString().equals("")) {
            return true;
        }

        if (buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString()) &&
                buttons[0][2].getText().toString().equals(buttons[2][0].getText().toString()) &&
                !buttons[0][2].getText().toString().equals("")) {
            return true;
        }

        return false;
    }

    private void resetGame() {
        // This method is kept for compatibility but now just calls changeGameMode
        changeGameMode();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up handler to prevent memory leaks
        if (botHandler != null) {
            botHandler.removeCallbacksAndMessages(null);
        }
    }
}