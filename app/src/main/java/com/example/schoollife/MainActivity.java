package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_SHIPS = 4;
    private static final int[] SHIP_SIZES = {5, 4, 3, 2};

    private static final int MODE_VS_BOT = 0;
    private static final int MODE_VS_FRIEND = 1;

    private int gameMode = MODE_VS_BOT;
    private int currentShipIndex = 0;
    private boolean isHorizontal = true;
    private boolean isPlacementPhase = true;
    private boolean isPlayer1Turn = true;
    private boolean player1SetupComplete = false;
    private boolean player2SetupComplete = false;
    private int selectedRow = -1;
    private int selectedCol = -1;

    private static final int CELL_EMPTY = 0;
    private static final int CELL_PREVIEW = -1;
    private static final int CELL_HIT = 5;
    private static final int CELL_MISS = 6;

    private int[][] playerBoard = new int[10][10];
    private int[][] opponentBoard = new int[10][10];
    private int[][] player1Board = new int[10][10];
    private int[][] player2Board = new int[10][10];
    private int[][] player1AttackBoard = new int[10][10];
    private int[][] player2AttackBoard = new int[10][10];

    private List<int[]> targetQueue = new ArrayList<>();
    private int[] lastHit = null;
    private boolean huntMode = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_ship);

        showGameModeSelection();
    }

    private void showGameModeSelection() {
        new AlertDialog.Builder(this)
                .setTitle("Select Game Mode")
                .setMessage("Choose your opponent:")
                .setPositiveButton("VS Bot", (dialog, which) -> {
                    gameMode = MODE_VS_BOT;
                    initializeGame();
                })
                .setNegativeButton("VS Friend", (dialog, which) -> {
                    gameMode = MODE_VS_FRIEND;
                    initializeGame();
                })
                .setCancelable(false)
                .show();
    }

    private void initializeGame() {
        setupGrids();
        setupButtons();
        setupBackNavigation();
        updateShipCounter();
        updateStatusText();
        findViewById(R.id.computer_board).setVisibility(View.VISIBLE);
    }

    private void setupGrids() {
        GridLayout playerBoardView = findViewById(R.id.player_board);
        initializeBoard(playerBoardView, true);

        GridLayout computerBoardView = findViewById(R.id.computer_board);
        initializeBoard(computerBoardView, false);
        computerBoardView.setVisibility(View.VISIBLE);
    }

    private void setupButtons() {
        Button toggleButton = findViewById(R.id.toggle_orientation);
        toggleButton.setOnClickListener(v -> {
            isHorizontal = !isHorizontal;
            Toast.makeText(this, isHorizontal ? "Horizontal orientation" : "Vertical orientation", Toast.LENGTH_SHORT).show();
            if(selectedRow >= 0 && selectedCol >= 0) {
                showShipPreview(selectedRow, selectedCol);
            }
        });
        toggleButton.setVisibility(View.VISIBLE);

        Button startGameButton = findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(v -> {
            if (gameMode == MODE_VS_BOT && currentShipIndex >= NUM_SHIPS) {
                startGame();
            } else if (gameMode == MODE_VS_FRIEND && player1SetupComplete && player2SetupComplete) {
                startFriendGame();
            } else {
                Toast.makeText(this, "Complete ship placement first", Toast.LENGTH_SHORT).show();
            }
        });
        startGameButton.setVisibility(View.VISIBLE);

        Button placeShipButton = findViewById(R.id.place_ship_button);
        placeShipButton.setVisibility(View.VISIBLE);
        placeShipButton.setEnabled(true);
        placeShipButton.setOnClickListener(v -> onPlaceShipClicked(v));
    }

    private void setupBackNavigation() {
        LinearLayout backNavigation = findViewById(R.id.back_navigation);
        backNavigation.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MiniGamesListActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void onPlaceShipClicked(View view) {
        if (selectedRow >= 0 && selectedCol >= 0) {
            if (currentShipIndex < NUM_SHIPS) {
                boolean placed = placeShip(selectedRow, selectedCol, isHorizontal);

                if (placed) {
                    currentShipIndex++;

                    if (gameMode == MODE_VS_BOT) {
                        handleBotModeShipPlacement();
                    } else {
                        handleFriendModeShipPlacement();
                    }

                    selectedRow = -1;
                    selectedCol = -1;
                    clearAllPreviews();
                }
            }
        } else {
            Toast.makeText(this, "Please select a position first", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleBotModeShipPlacement() {
        if (currentShipIndex >= NUM_SHIPS) {
            updateStatusText();
            updateShipCounter();
            Toast.makeText(this, "All ships placed! Click 'Start Game'", Toast.LENGTH_SHORT).show();
            findViewById(R.id.start_game_button).setEnabled(true);
        } else {
            updateStatusText();
            updateShipCounter();
        }
    }

    private void handleFriendModeShipPlacement() {
        if (currentShipIndex >= NUM_SHIPS) {
            if (!player1SetupComplete) {
                player1SetupComplete = true;
                copyBoardToPlayer1();
                showPlayer2Setup();
            } else if (!player2SetupComplete) {
                player2SetupComplete = true;
                copyBoardToPlayer2();
                Toast.makeText(this, "Both players ready! Click 'Start Game'", Toast.LENGTH_SHORT).show();
                findViewById(R.id.start_game_button).setEnabled(true);
            }
        } else {
            updateStatusText();
            updateShipCounter();
        }
    }

    private void copyBoardToPlayer1() {
        for (int i = 0; i < 10; i++) {
            System.arraycopy(playerBoard[i], 0, player1Board[i], 0, 10);
        }
    }

    private void copyBoardToPlayer2() {
        for (int i = 0; i < 10; i++) {
            System.arraycopy(playerBoard[i], 0, player2Board[i], 0, 10);
        }
    }

    private void showPlayer2Setup() {
        new AlertDialog.Builder(this)
                .setTitle("Player 2 Setup")
                .setMessage("Player 1 setup complete! Hand device to Player 2 for ship placement.")
                .setPositiveButton("Continue", (dialog, which) -> {
                    currentShipIndex = 0;
                    isPlayer1Turn = false;
                    clearPlayerBoard();
                    updateStatusText();
                    updateShipCounter();
                })
                .setCancelable(false)
                .show();
    }

    private void clearPlayerBoard() {
        playerBoard = new int[10][10];
        GridLayout playerBoardView = findViewById(R.id.player_board);
        for (int i = 0; i < 100; i++) {
            Button cell = (Button) playerBoardView.getChildAt(i);
            cell.setBackgroundColor(getResources().getColor(R.color.cell_empty));
        }
    }

    private void updateStatusText() {
        TextView statusText = findViewById(R.id.status_text);
        if (isPlacementPhase) {
            if (currentShipIndex < NUM_SHIPS) {
                String playerName = (gameMode == MODE_VS_FRIEND && !isPlayer1Turn) ? "Player 2: " : "";
                statusText.setText(playerName + "Place your ship (" + SHIP_SIZES[currentShipIndex] + " cells)");
            } else {
                statusText.setText("All ships placed! Click 'Start Game'");
            }
        } else {
            if (gameMode == MODE_VS_FRIEND) {
                statusText.setText((isPlayer1Turn ? "Player 1" : "Player 2") + "'s turn - Attack!");
            } else {
                statusText.setText("Attack the enemy board!");
            }
        }
    }

    private void updateShipCounter() {
        TextView shipCounter = findViewById(R.id.ship_counter);
        if (currentShipIndex >= NUM_SHIPS) {
            shipCounter.setText("All ships placed!");
            return;
        }

        StringBuilder sb = new StringBuilder("Ships: ");
        for (int i = 0; i < NUM_SHIPS; i++) {
            if (i > 0) sb.append(", ");
            sb.append(SHIP_SIZES[i]).append("-cell");
            sb.append(i < currentShipIndex ? " (0)" : " (1)");
        }
        shipCounter.setText(sb.toString());
    }

    private void initializeBoard(GridLayout grid, boolean isPlayerBoard) {
        grid.setRowCount(10);
        grid.setColumnCount(10);
        grid.removeAllViews();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button cell = new Button(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.rowSpec = GridLayout.spec(row, 1f);
                params.columnSpec = GridLayout.spec(col, 1f);
                params.setMargins(1, 1, 1, 1);
                cell.setLayoutParams(params);

                cell.setPadding(0, 0, 0, 0);
                cell.setMinimumWidth(0);
                cell.setMinimumHeight(0);
                cell.setBackgroundColor(getResources().getColor(R.color.cell_empty));

                final int finalRow = row;
                final int finalCol = col;

                cell.setOnClickListener(v -> {
                    if (isPlayerBoard && isPlacementPhase) {
                        selectedRow = finalRow;
                        selectedCol = finalCol;
                        showShipPreview(finalRow, finalCol);
                    } else if (!isPlayerBoard && !isPlacementPhase) {
                        if (gameMode == MODE_VS_BOT) {
                            attackEnemyAt(finalRow, finalCol, (Button)v);
                        } else {
                            attackFriendAt(finalRow, finalCol, (Button)v);
                        }
                    }
                });

                grid.addView(cell);
            }
        }
    }

    private void showShipPreview(int row, int col) {
        clearAllPreviews();

        if (isValidPlacement(row, col, isHorizontal)) {
            int shipLength = SHIP_SIZES[currentShipIndex];
            GridLayout playerBoardView = findViewById(R.id.player_board);

            for (int i = 0; i < shipLength; i++) {
                int cellRow = isHorizontal ? row : row + i;
                int cellCol = isHorizontal ? col + i : col;

                if (cellRow < 10 && cellCol < 10) {
                    if (playerBoard[cellRow][cellCol] == CELL_EMPTY) {
                        playerBoard[cellRow][cellCol] = CELL_PREVIEW;
                        Button cell = (Button) playerBoardView.getChildAt(cellRow * 10 + cellCol);
                        cell.setBackgroundColor(getResources().getColor(R.color.ship_preview));
                    }
                }
            }
        }
    }

    private void clearAllPreviews() {
        GridLayout playerBoardView = findViewById(R.id.player_board);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (playerBoard[row][col] == CELL_PREVIEW) {
                    playerBoard[row][col] = CELL_EMPTY;
                    Button cell = (Button) playerBoardView.getChildAt(row * 10 + col);
                    cell.setBackgroundColor(getResources().getColor(R.color.cell_empty));
                }
            }
        }
    }

    private boolean placeShip(int row, int col, boolean horizontal) {
        if (currentShipIndex >= NUM_SHIPS) {
            return false;
        }

        if (isValidPlacement(row, col, horizontal)) {
            int shipLength = SHIP_SIZES[currentShipIndex];
            GridLayout playerBoardView = findViewById(R.id.player_board);

            clearAllPreviews();

            for (int i = 0; i < shipLength; i++) {
                int cellRow = horizontal ? row : row + i;
                int cellCol = horizontal ? col + i : col;

                Button cell = (Button) playerBoardView.getChildAt(cellRow * 10 + cellCol);
                cell.setBackgroundColor(getResources().getColor(R.color.ship_placed));
                playerBoard[cellRow][cellCol] = currentShipIndex + 1;
            }
            return true;
        } else {
            Toast.makeText(this, "Invalid placement position", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isValidPlacement(int row, int col, boolean horizontal) {
        if (currentShipIndex >= NUM_SHIPS) {
            return false;
        }

        int shipLength = SHIP_SIZES[currentShipIndex];

        if (horizontal) {
            if (col + shipLength > 10) return false;
        } else {
            if (row + shipLength > 10) return false;
        }

        for (int i = 0; i < shipLength; i++) {
            int checkRow = horizontal ? row : row + i;
            int checkCol = horizontal ? col + i : col;

            if (checkRow >= 10 || checkCol >= 10 ||
                    (playerBoard[checkRow][checkCol] != CELL_EMPTY &&
                            playerBoard[checkRow][checkCol] != CELL_PREVIEW)) {
                return false;
            }
        }
        return true;
    }

    private void startGame() {
        isPlacementPhase = false;
        updateStatusText();

        GridLayout computerBoardView = findViewById(R.id.computer_board);
        computerBoardView.setVisibility(View.VISIBLE);

        findViewById(R.id.toggle_orientation).setEnabled(false);
        findViewById(R.id.place_ship_button).setEnabled(false);
        findViewById(R.id.start_game_button).setEnabled(false);

        placeComputerShips();
        Toast.makeText(this, "Game started! Attack the enemy board!", Toast.LENGTH_LONG).show();
    }

    private void startFriendGame() {
        isPlacementPhase = false;
        isPlayer1Turn = true;
        updateStatusText();

        findViewById(R.id.toggle_orientation).setEnabled(false);
        findViewById(R.id.place_ship_button).setEnabled(false);
        findViewById(R.id.start_game_button).setEnabled(false);

        for (int i = 0; i < 10; i++) {
            System.arraycopy(player2Board[i], 0, opponentBoard[i], 0, 10);
        }

        loadPlayerBoard(player1Board);
        loadOpponentBoard(player1AttackBoard);

        Toast.makeText(this, "Game started! Player 1 attacks first!", Toast.LENGTH_LONG).show();
    }

    private void loadOpponentBoard(int[][] attackBoard) {
        GridLayout computerBoardView = findViewById(R.id.computer_board);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button cell = (Button) computerBoardView.getChildAt(row * 10 + col);
                int cellValue = attackBoard[row][col];

                if (cellValue == CELL_HIT) {
                    cell.setBackgroundColor(getResources().getColor(R.color.hit));
                } else if (cellValue == CELL_MISS) {
                    cell.setBackgroundColor(getResources().getColor(R.color.miss));
                } else {
                    cell.setBackgroundColor(getResources().getColor(R.color.cell_empty));
                }
            }
        }
    }

    private void placeComputerShips() {
        Random random = new Random();

        for (int shipIndex = 0; shipIndex < NUM_SHIPS; shipIndex++) {
            int shipLength = SHIP_SIZES[shipIndex];
            boolean placed = false;

            while (!placed) {
                int row = random.nextInt(10);
                int col = random.nextInt(10);
                boolean horizontal = random.nextBoolean();

                if (isValidComputerPlacement(row, col, horizontal, shipLength)) {
                    for (int i = 0; i < shipLength; i++) {
                        int cellRow = horizontal ? row : row + i;
                        int cellCol = horizontal ? col + i : col;
                        opponentBoard[cellRow][cellCol] = shipIndex + 1;
                    }
                    placed = true;
                }
            }
        }
    }

    private boolean isValidComputerPlacement(int row, int col, boolean horizontal, int shipLength) {
        if (horizontal) {
            if (col + shipLength > 10) return false;
        } else {
            if (row + shipLength > 10) return false;
        }

        for (int i = 0; i < shipLength; i++) {
            int checkRow = horizontal ? row : row + i;
            int checkCol = horizontal ? col + i : col;

            if (checkRow >= 10 || checkCol >= 10 || opponentBoard[checkRow][checkCol] != 0) {
                return false;
            }
        }
        return true;
    }

    private void attackEnemyAt(int row, int col, Button cell) {
        if (opponentBoard[row][col] >= 5) {
            Toast.makeText(this, "Already attacked this position", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isHit = opponentBoard[row][col] > 0;

        if (isHit) {
            opponentBoard[row][col] = CELL_HIT;
            cell.setBackgroundColor(getResources().getColor(R.color.hit));
            Toast.makeText(this, "Hit!", Toast.LENGTH_SHORT).show();

            if (areAllOpponentShipsSunk()) {
                gameOver(true);
                return;
            }
        } else {
            opponentBoard[row][col] = CELL_MISS;
            cell.setBackgroundColor(getResources().getColor(R.color.miss));
            Toast.makeText(this, "Miss!", Toast.LENGTH_SHORT).show();
        }

        smartComputerAttack();
    }

    private void attackFriendAt(int row, int col, Button cell) {
        int[][] currentAttackBoard = isPlayer1Turn ? player1AttackBoard : player2AttackBoard;

        if (currentAttackBoard[row][col] >= 5) {
            Toast.makeText(this, "Already attacked this position", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isHit = opponentBoard[row][col] > 0 && opponentBoard[row][col] < 5;

        if (isHit) {
            opponentBoard[row][col] = CELL_HIT;
            currentAttackBoard[row][col] = CELL_HIT;
            cell.setBackgroundColor(getResources().getColor(R.color.hit));
            Toast.makeText(this, "Hit!", Toast.LENGTH_SHORT).show();

            if (areAllOpponentShipsSunk()) {
                gameOver(isPlayer1Turn);
                return;
            }
        } else {
            opponentBoard[row][col] = CELL_MISS;
            currentAttackBoard[row][col] = CELL_MISS;
            cell.setBackgroundColor(getResources().getColor(R.color.miss));
            Toast.makeText(this, "Miss!", Toast.LENGTH_SHORT).show();
        }

        switchTurns();
    }

    private void switchTurns() {
        new AlertDialog.Builder(this)
                .setTitle("Turn Switch")
                .setMessage("Pass device to " + (isPlayer1Turn ? "Player 2" : "Player 1"))
                .setPositiveButton("Continue", (dialog, which) -> {
                    isPlayer1Turn = !isPlayer1Turn;

                    if (isPlayer1Turn) {
                        loadPlayerBoard(player1Board);
                        for (int i = 0; i < 10; i++) {
                            System.arraycopy(player2Board[i], 0, opponentBoard[i], 0, 10);
                        }
                        loadOpponentBoard(player1AttackBoard);
                    } else {
                        loadPlayerBoard(player2Board);
                        for (int i = 0; i < 10; i++) {
                            System.arraycopy(player1Board[i], 0, opponentBoard[i], 0, 10);
                        }
                        loadOpponentBoard(player2AttackBoard);
                    }

                    updateStatusText();
                })
                .setCancelable(false)
                .show();
    }

    private void loadPlayerBoard(int[][] board) {
        GridLayout playerBoardView = findViewById(R.id.player_board);
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button cell = (Button) playerBoardView.getChildAt(row * 10 + col);
                int cellValue = board[row][col];

                if (cellValue == CELL_EMPTY) {
                    cell.setBackgroundColor(getResources().getColor(R.color.cell_empty));
                } else if (cellValue == CELL_HIT) {
                    cell.setBackgroundColor(getResources().getColor(R.color.hit));
                } else if (cellValue == CELL_MISS) {
                    cell.setBackgroundColor(getResources().getColor(R.color.miss));
                } else if (cellValue > 0 && cellValue < 5) {
                    cell.setBackgroundColor(getResources().getColor(R.color.ship_placed));
                }
            }
        }
    }

    private boolean areAllOpponentShipsSunk() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (opponentBoard[row][col] > 0 && opponentBoard[row][col] < 5) {
                    return false;
                }
            }
        }
        return true;
    }

    private void smartComputerAttack() {
        int[] target = getSmartTarget();
        int row = target[0];
        int col = target[1];

        boolean isHit = playerBoard[row][col] > 0 && playerBoard[row][col] < 5;
        GridLayout playerBoardView = findViewById(R.id.player_board);
        Button cell = (Button) playerBoardView.getChildAt(row * 10 + col);

        if (isHit) {
            playerBoard[row][col] = CELL_HIT;
            cell.setBackgroundColor(getResources().getColor(R.color.hit));

            lastHit = new int[]{row, col};
            huntMode = true;
            addAdjacentTargets(row, col);

            if (areAllPlayerShipsSunk()) {
                gameOver(false);
            }
        } else {
            playerBoard[row][col] = CELL_MISS;
            cell.setBackgroundColor(getResources().getColor(R.color.miss));
        }
    }

    private int[] getSmartTarget() {
        if (!targetQueue.isEmpty()) {
            return targetQueue.remove(0);
        }

        int[][] probability = calculateProbabilities();
        int maxProb = 0;
        List<int[]> bestTargets = new ArrayList<>();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (playerBoard[row][col] < 5) {
                    if (probability[row][col] > maxProb) {
                        maxProb = probability[row][col];
                        bestTargets.clear();
                        bestTargets.add(new int[]{row, col});
                    } else if (probability[row][col] == maxProb) {
                        bestTargets.add(new int[]{row, col});
                    }
                }
            }
        }

        return bestTargets.get(random.nextInt(bestTargets.size()));
    }

    private int[][] calculateProbabilities() {
        int[][] prob = new int[10][10];

        for (int shipIndex = 0; shipIndex < NUM_SHIPS; shipIndex++) {
            int shipSize = SHIP_SIZES[shipIndex];
            if (isShipSunk(shipIndex + 1)) continue;

            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    if (canPlaceShipForProbability(row, col, shipSize, true)) {
                        for (int i = 0; i < shipSize; i++) {
                            prob[row][col + i]++;
                        }
                    }
                    if (canPlaceShipForProbability(row, col, shipSize, false)) {
                        for (int i = 0; i < shipSize; i++) {
                            prob[row + i][col]++;
                        }
                    }
                }
            }
        }

        return prob;
    }

    private boolean canPlaceShipForProbability(int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > 10) return false;
            for (int i = 0; i < size; i++) {
                int cellValue = playerBoard[row][col + i];
                if (cellValue == CELL_MISS) return false;
            }
        } else {
            if (row + size > 10) return false;
            for (int i = 0; i < size; i++) {
                int cellValue = playerBoard[row + i][col];
                if (cellValue == CELL_MISS) return false;
            }
        }
        return true;
    }

    private boolean isShipSunk(int shipId) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (playerBoard[row][col] == shipId) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addAdjacentTargets(int row, int col) {
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10) {
                if (playerBoard[newRow][newCol] < 5) {
                    boolean adjacentToHit = false;
                    for (int[] checkDir : directions) {
                        int checkRow = newRow + checkDir[0];
                        int checkCol = newCol + checkDir[1];
                        if (checkRow >= 0 && checkRow < 10 && checkCol >= 0 && checkCol < 10) {
                            if (playerBoard[checkRow][checkCol] == CELL_HIT) {
                                adjacentToHit = true;
                                break;
                            }
                        }
                    }

                    int[] target = new int[]{newRow, newCol};
                    boolean alreadyInQueue = false;
                    for (int[] existingTarget : targetQueue) {
                        if (existingTarget[0] == newRow && existingTarget[1] == newCol) {
                            alreadyInQueue = true;
                            break;
                        }
                    }

                    if (!alreadyInQueue) {
                        if (adjacentToHit) {
                            targetQueue.add(0, target);
                        } else {
                            targetQueue.add(target);
                        }
                    }
                }
            }
        }
    }

    private boolean areAllPlayerShipsSunk() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (playerBoard[row][col] > 0 && playerBoard[row][col] < 5) {
                    return false;
                }
            }
        }
        return true;
    }

    private void gameOver(boolean playerWon) {
        TextView statusText = findViewById(R.id.status_text);
        String winner = "";

        if (gameMode == MODE_VS_BOT) {
            winner = playerWon ? "You won!" : "Computer won!";
        } else {
            winner = playerWon ? "Player 1 won!" : "Player 2 won!";
        }

        statusText.setText(winner);
        disableAllBoardInteractions();

        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage(winner + (playerWon ? " Congratulations!" : " Better luck next time!"))
                .setPositiveButton("Play Again", (dialog, which) -> resetGame())
                .setNegativeButton("Exit", (dialog, which) -> {
                    Intent intent = new Intent(MainActivity.this, MiniGamesListActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }
    private void disableAllBoardInteractions() {
        GridLayout playerBoardView = findViewById(R.id.player_board);
        GridLayout computerBoardView = findViewById(R.id.computer_board);

        for (int i = 0; i < 100; i++) {
            playerBoardView.getChildAt(i).setClickable(false);
            computerBoardView.getChildAt(i).setClickable(false);
        }
    }

    private void resetGame() {
        // Reset all game state
        currentShipIndex = 0;
        isHorizontal = true;
        isPlacementPhase = true;
        isPlayer1Turn = true;
        player1SetupComplete = false;
        player2SetupComplete = false;
        selectedRow = -1;
        selectedCol = -1;

        // Reset AI state
        targetQueue.clear();
        lastHit = null;
        huntMode = false;

        // Clear all boards
        playerBoard = new int[10][10];
        opponentBoard = new int[10][10];
        player1Board = new int[10][10];
        player2Board = new int[10][10];

        recreate();
    }
}