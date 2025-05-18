// MainActivity.java
package com.example.schoollife;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.WindowManager;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.schoollife.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int BOARD_SIZE = 8;
    private static final int SHIPS_COUNT = 4;
    private static final int[] SHIP_SIZES = {2, 3, 4, 5}; // Four ships with different sizes

    private Button[][] playerCells;
    private Button[][] computerCells;
    private boolean[][] playerShips;
    private boolean[][] computerShips;
    private boolean[][] playerHits;
    private boolean[][] computerHits;
    private boolean setupPhase = true;
    private int currentShipIndex = 0; // Index for ship placement
    private int playerShipCellsPlaced = 0; // Total ship cells placed
    private int totalShipCells = 0; // Total cells all ships occupy
    private int playerHitsCount = 0;
    private int computerHitsCount = 0;
    private boolean isHorizontalPlacement = true; // Ship orientation
    private Random random = new Random();
    private TextView statusText;

    // New variables for improved ship placement
    private boolean isPlacingShip = false;
    private int tempShipStartRow = -1;
    private int tempShipStartCol = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide navigation bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_battle_ship);

        // Calculate total ship cells
        for (int size : SHIP_SIZES) {
            totalShipCells += size;
        }

        statusText = findViewById(R.id.status_text);
        statusText.setText("Ընտրեք նավի սկզբնակետը, չափսը՝ " + SHIP_SIZES[0] +
                (isHorizontalPlacement ? " (Հորիզոնական)" : " (Ուղղահայաց)"));

        // Back to MiniGamesList button
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MiniGamesListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize game boards
        playerCells = new Button[BOARD_SIZE][BOARD_SIZE];
        computerCells = new Button[BOARD_SIZE][BOARD_SIZE];
        playerShips = new boolean[BOARD_SIZE][BOARD_SIZE];
        computerShips = new boolean[BOARD_SIZE][BOARD_SIZE];
        playerHits = new boolean[BOARD_SIZE][BOARD_SIZE];
        computerHits = new boolean[BOARD_SIZE][BOARD_SIZE];

        // Create player board
        GridLayout playerBoard = findViewById(R.id.player_board);
        setupBoard(playerBoard, playerCells, true);

        // Create computer board
        GridLayout computerBoard = findViewById(R.id.computer_board);
        setupBoard(computerBoard, computerCells, false);

        // Orientation toggle button
        Button toggleButton = findViewById(R.id.toggle_orientation);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setupPhase && currentShipIndex < SHIPS_COUNT) {
                    isHorizontalPlacement = !isHorizontalPlacement;

                    if (isPlacingShip) {
                        // Update preview when orientation is changed during placement
                        clearTempShipPreview();
                        showShipPlacementPreview(tempShipStartRow, tempShipStartCol);
                    }

                    updateStatusText();
                }
            }
        });

        // Start Game button
        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentShipIndex == SHIPS_COUNT) {
                    setupPhase = false;
                    placeComputerShips();
                    statusText.setText("Խաղը սկսված է: Ձեր հերթն է");
                    startButton.setVisibility(View.GONE);
                    toggleButton.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this,
                            "Խնդրում ենք նախ տեղադրել բոլոր նավերը",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateStatusText() {
        if (!isPlacingShip) {
            statusText.setText("Ընտրեք նավի սկզբնակետը, չափսը՝ " + SHIP_SIZES[currentShipIndex] +
                    (isHorizontalPlacement ? " (Հորիզոնական)" : " (Ուղղահայաց)"));
        } else {
            statusText.setText("Սեղմեք կրկին՝ հաստատելու համար, չափսը՝ " + SHIP_SIZES[currentShipIndex] +
                    (isHorizontalPlacement ? " (Հորիզոնական)" : " (Ուղղահայաց)"));
        }
    }

    private void setupBoard(GridLayout board, Button[][] cells, final boolean isPlayerBoard) {
        board.setColumnCount(BOARD_SIZE);
        board.setRowCount(BOARD_SIZE);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                final Button cell = new Button(this);
                final int row = i;
                final int col = j;

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.columnSpec = GridLayout.spec(j, 1f);
                params.rowSpec = GridLayout.spec(i, 1f);
                params.setMargins(2, 2, 2, 2);
                cell.setLayoutParams(params);

                cell.setBackgroundColor(ContextCompat.getColor(this, R.color.water));

                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isPlayerBoard && setupPhase) {
                            // Improved player ship placement
                            if (currentShipIndex < SHIPS_COUNT) {
                                handlePlayerShipPlacement(row, col);
                            }
                        } else if (!isPlayerBoard && !setupPhase && !computerHits[row][col]) {
                            // Player's attack on computer board
                            computerHits[row][col] = true;
                            if (computerShips[row][col]) {
                                cell.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.hit));
                                playerHitsCount++;
                                checkWinCondition();
                            } else {
                                cell.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.miss));
                                computerTurn();
                            }
                        }
                    }
                });

                cells[i][j] = cell;
                board.addView(cell);
            }
        }
    }

    private void handlePlayerShipPlacement(int row, int col) {
        if (!isPlacingShip) {
            // Start ship placement - first click
            if (playerShips[row][col]) {
                Toast.makeText(this, "Այստեղ արդեն նավ կա", Toast.LENGTH_SHORT).show();
                return;
            }

            tempShipStartRow = row;
            tempShipStartCol = col;
            isPlacingShip = true;

            // Show preview of ship placement
            showShipPlacementPreview(row, col);
            updateStatusText();
        } else {
            // Confirm ship placement - second click
            // If it's the same cell, confirm placement, otherwise treat as new starting point
            if (row == tempShipStartRow && col == tempShipStartCol) {
                // Same cell - confirm placement
                if (canPlaceShip(tempShipStartRow, tempShipStartCol)) {
                    placePlayerShip(tempShipStartRow, tempShipStartCol);
                    isPlacingShip = false;
                    tempShipStartRow = -1;
                    tempShipStartCol = -1;
                } else {
                    Toast.makeText(this, "Չի կարելի նավ տեղադրել այստեղ", Toast.LENGTH_SHORT).show();
                    clearTempShipPreview();
                    isPlacingShip = false;
                }
            } else {
                // Different cell - cancel previous and start new
                clearTempShipPreview();

                if (playerShips[row][col]) {
                    Toast.makeText(this, "Այստեղ արդեն նավ կա", Toast.LENGTH_SHORT).show();
                    isPlacingShip = false;
                    return;
                }

                tempShipStartRow = row;
                tempShipStartCol = col;
                showShipPlacementPreview(row, col);
            }

            updateStatusText();
        }
    }

    private void showShipPlacementPreview(int startRow, int startCol) {
        int currentShipSize = SHIP_SIZES[currentShipIndex];

        if (!canPlaceShip(startRow, startCol)) {
            Toast.makeText(this, "Չի կարելի նավ տեղադրել այստեղ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show preview coloring
        if (isHorizontalPlacement) {
            for (int c = startCol; c < startCol + currentShipSize && c < BOARD_SIZE; c++) {
                if (!playerShips[startRow][c]) {
                    playerCells[startRow][c].setBackgroundColor(
                            ContextCompat.getColor(this, R.color.shipPreview));
                }
            }
        } else {
            for (int r = startRow; r < startRow + currentShipSize && r < BOARD_SIZE; r++) {
                if (!playerShips[r][startCol]) {
                    playerCells[r][startCol].setBackgroundColor(
                            ContextCompat.getColor(this, R.color.shipPreview));
                }
            }
        }
    }

    private void clearTempShipPreview() {
        // Reset coloring of cells that aren't actual ships
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!playerShips[i][j]) {
                    playerCells[i][j].setBackgroundColor(
                            ContextCompat.getColor(MainActivity.this, R.color.water));
                }
            }
        }
    }

    private boolean canPlaceShip(int startRow, int startCol) {
        int currentShipSize = SHIP_SIZES[currentShipIndex];

        if (isHorizontalPlacement) {
            // Check if ship fits horizontally
            if (startCol + currentShipSize > BOARD_SIZE) {
                return false;
            }

            // Check if cells are free
            for (int c = startCol; c < startCol + currentShipSize; c++) {
                if (playerShips[startRow][c]) {
                    return false;
                }
            }

            // Check adjacent cells (no touching ships)
            for (int r = Math.max(0, startRow - 1); r <= Math.min(BOARD_SIZE - 1, startRow + 1); r++) {
                for (int c = Math.max(0, startCol - 1); c <= Math.min(BOARD_SIZE - 1, startCol + currentShipSize); c++) {
                    if (r == startRow && c >= startCol && c < startCol + currentShipSize) {
                        continue; // Skip the ship cells themselves
                    }
                    if (playerShips[r][c]) {
                        return false;
                    }
                }
            }
        } else {
            // Check if ship fits vertically
            if (startRow + currentShipSize > BOARD_SIZE) {
                return false;
            }

            // Check if cells are free
            for (int r = startRow; r < startRow + currentShipSize; r++) {
                if (playerShips[r][startCol]) {
                    return false;
                }
            }

            // Check adjacent cells (no touching ships)
            for (int r = Math.max(0, startRow - 1); r <= Math.min(BOARD_SIZE - 1, startRow + currentShipSize); r++) {
                for (int c = Math.max(0, startCol - 1); c <= Math.min(BOARD_SIZE - 1, startCol + 1); c++) {
                    if (c == startCol && r >= startRow && r < startRow + currentShipSize) {
                        continue; // Skip the ship cells themselves
                    }
                    if (playerShips[r][c]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placePlayerShip(int startRow, int startCol) {
        int currentShipSize = SHIP_SIZES[currentShipIndex];

        // Place the ship
        if (isHorizontalPlacement) {
            for (int c = startCol; c < startCol + currentShipSize; c++) {
                playerShips[startRow][c] = true;
                playerCells[startRow][c].setBackgroundColor(ContextCompat.getColor(this, R.color.ship));
            }
        } else {
            for (int r = startRow; r < startRow + currentShipSize; r++) {
                playerShips[r][startCol] = true;
                playerCells[r][startCol].setBackgroundColor(ContextCompat.getColor(this, R.color.ship));
            }
        }

        playerShipCellsPlaced += currentShipSize;
        currentShipIndex++;

        if (currentShipIndex < SHIPS_COUNT) {
            updateStatusText();
        } else {
            statusText.setText("Բոլոր նավերը տեղադրված են! Սեղմեք «Սկսել խաղը»");
        }
    }

    private void placeComputerShips() {
        for (int i = 0; i < SHIPS_COUNT; i++) {
            int shipSize = SHIP_SIZES[i];
            boolean placed = false;

            while (!placed) {
                int row = random.nextInt(BOARD_SIZE);
                int col = random.nextInt(BOARD_SIZE);
                boolean horizontal = random.nextBoolean();

                if (canPlaceComputerShip(row, col, shipSize, horizontal)) {
                    placeComputerShip(row, col, shipSize, horizontal);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceComputerShip(int startRow, int startCol, int size, boolean horizontal) {
        if (horizontal) {
            // Check if ship fits horizontally
            if (startCol + size > BOARD_SIZE) {
                return false;
            }

            // Check if cells are free
            for (int c = startCol; c < startCol + size; c++) {
                if (computerShips[startRow][c]) {
                    return false;
                }
            }

            // Check adjacent cells (no touching ships)
            for (int r = Math.max(0, startRow - 1); r <= Math.min(BOARD_SIZE - 1, startRow + 1); r++) {
                for (int c = Math.max(0, startCol - 1); c <= Math.min(BOARD_SIZE - 1, startCol + size); c++) {
                    if (r == startRow && c >= startCol && c < startCol + size) {
                        continue; // Skip the ship cells themselves
                    }
                    if (computerShips[r][c]) {
                        return false;
                    }
                }
            }
        } else {
            // Check if ship fits vertically
            if (startRow + size > BOARD_SIZE) {
                return false;
            }

            // Check if cells are free
            for (int r = startRow; r < startRow + size; r++) {
                if (computerShips[r][startCol]) {
                    return false;
                }
            }

            // Check adjacent cells (no touching ships)
            for (int r = Math.max(0, startRow - 1); r <= Math.min(BOARD_SIZE - 1, startRow + size); r++) {
                for (int c = Math.max(0, startCol - 1); c <= Math.min(BOARD_SIZE - 1, startCol + 1); c++) {
                    if (c == startCol && r >= startRow && r < startRow + size) {
                        continue; // Skip the ship cells themselves
                    }
                    if (computerShips[r][c]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placeComputerShip(int startRow, int startCol, int size, boolean horizontal) {
        if (horizontal) {
            for (int c = startCol; c < startCol + size; c++) {
                computerShips[startRow][c] = true;
            }
        } else {
            for (int r = startRow; r < startRow + size; r++) {
                computerShips[r][startCol] = true;
            }
        }
    }

    private void computerTurn() {
        statusText.setText("Համակարգչի հերթն է...");

        // Improved AI for computer's turn
        int row, col;
        boolean madeSmartMove = false;

        // First check if there are any hits that should be followed up
        if (!madeSmartMove) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (playerHits[i][j] && playerShips[i][j]) {
                        // Found a hit, try adjacent cells
                        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                        for (int[] dir : directions) {
                            int newRow = i + dir[0];
                            int newCol = j + dir[1];
                            if (isValidCell(newRow, newCol) && !playerHits[newRow][newCol]) {
                                row = newRow;
                                col = newCol;
                                madeSmartMove = true;
                                playerHits[row][col] = true;

                                if (playerShips[row][col]) {
                                    playerCells[row][col].setBackgroundColor(
                                            ContextCompat.getColor(this, R.color.hit));
                                    computerHitsCount++;
                                    checkWinCondition();

                                    if (!setupPhase) { // If game is still going
                                        statusText.setText("Համակարգիչը խփեց! Համակարգչի հերթն է կրկին");
                                        // Computer fires again if hit
                                        computerTurn();
                                    }
                                } else {
                                    playerCells[row][col].setBackgroundColor(
                                            ContextCompat.getColor(this, R.color.miss));
                                    statusText.setText("Համակարգիչը վրիպեց! Ձեր հերթն է");
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }

        // If no smart move was made, choose randomly
        if (!madeSmartMove) {
            do {
                row = random.nextInt(BOARD_SIZE);
                col = random.nextInt(BOARD_SIZE);
            } while (playerHits[row][col]);

            playerHits[row][col] = true;

            if (playerShips[row][col]) {
                playerCells[row][col].setBackgroundColor(ContextCompat.getColor(this, R.color.hit));
                computerHitsCount++;
                checkWinCondition();

                if (!setupPhase) { // If game is still going
                    statusText.setText("Համակարգիչը խփեց! Համակարգչի հերթն է կրկին");
                    // Computer fires again if hit
                    computerTurn();
                }
            } else {
                playerCells[row][col].setBackgroundColor(ContextCompat.getColor(this, R.color.miss));
                statusText.setText("Համակարգիչը վրիպեց! Ձեր հերթն է");
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    private void checkWinCondition() {
        if (playerHitsCount == totalShipCells) {
            statusText.setText("Դուք հաղթեցիք!");
            endGame();
        } else if (computerHitsCount == totalShipCells) {
            statusText.setText("Համակարգիչը հաղթեց!");
            endGame();
        }
    }

    private void endGame() {
        setupPhase = true; // Game over mode

        // Show all hidden ships
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (computerShips[i][j] && !computerHits[i][j]) {
                    computerCells[i][j].setBackgroundColor(ContextCompat.getColor(this, R.color.ship));
                }
            }
        }
    }
}