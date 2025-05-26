package com.example.schoollife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGameView extends SurfaceView implements Runnable {

    // Callback interface for communicating with MainActivity
    public interface GameCallbacks {
        void onScoreChanged(int score);
        void onGameOver(int finalScore);
        void onGameStart();
    }

    private GameCallbacks gameCallbacks;

    // Game thread
    private Thread gameThread = null;
    private boolean playing = false;
    private boolean paused = true;

    // Canvas and Paint for drawing
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    // Game area in blocks
    private final int NUM_BLOCKS_WIDE = 25;  // Reduced for smaller game area
    private int numBlocksHigh;

    // Size of each block in pixels
    private int blockSize;

    // Actual game area dimensions
    private int gameAreaWidth;
    private int gameAreaHeight;

    // Snake
    private ArrayList<Point> snake = new ArrayList<>();

    // Apple
    private Point apple = new Point();

    // Direction of snake movement
    public enum Heading {UP, RIGHT, DOWN, LEFT}
    private Heading heading = Heading.RIGHT;

    // Game score
    private int score;

    // FPS and timing
    private long nextFrameTime;
    private final long FPS = 10;
    private final long MILLIS_PER_SECOND = 1000;

    // Random for apple placement
    private Random random = new Random();

    public SnakeGameView(Context context) {
        super(context);
        init(context);
    }

    public SnakeGameView(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SnakeGameView(Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Initialize drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        paint.setAntiAlias(true);

        // Start new game
        newGame();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Now we know the actual size of our view
        gameAreaWidth = w;
        gameAreaHeight = h;

        // Calculate block size based on the smaller dimension to ensure squares
        blockSize = Math.min(gameAreaWidth / NUM_BLOCKS_WIDE, gameAreaHeight / 15); // minimum 15 blocks high

        // Recalculate the number of blocks that fit
        numBlocksHigh = gameAreaHeight / blockSize;

        // Ensure minimum playable area
        if (numBlocksHigh < 10) {
            numBlocksHigh = 10;
        }

        // Center the game area if there's extra space
        int usedWidth = NUM_BLOCKS_WIDE * blockSize;
        int usedHeight = numBlocksHigh * blockSize;
    }

    @Override
    public void run() {
        while (playing) {
            // Check if it's time for a frame update
            if (updateRequired()) {
                update();
                draw();
            }
        }
    }

    public void pause() {
        playing = false;
        try {
            if (gameThread != null) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newGame() {
        // Reset snake with initial length of 3 segments
        snake.clear();

        // Make sure we have valid dimensions
        if (numBlocksHigh <= 0) {
            numBlocksHigh = 15; // default value
        }

        int startX = NUM_BLOCKS_WIDE / 2;
        int startY = numBlocksHigh / 2;

        // Add 3 initial segments
        snake.add(new Point(startX, startY));     // Head
        snake.add(new Point(startX - 1, startY)); // Body
        snake.add(new Point(startX - 2, startY)); // Tail

        // Reset apple
        spawnApple();

        // Reset score and direction
        score = 0;
        heading = Heading.RIGHT;

        // Setup timing
        nextFrameTime = System.currentTimeMillis();

        // Unpause game
        paused = false;

        // Notify callback about game start
        if (gameCallbacks != null) {
            gameCallbacks.onGameStart();
            gameCallbacks.onScoreChanged(score);
        }
    }

    public void spawnApple() {
        boolean validPosition = false;
        int attempts = 0;
        final int maxAttempts = 100;

        while (!validPosition && attempts < maxAttempts) {
            apple.x = random.nextInt(NUM_BLOCKS_WIDE - 2) + 1; // Keep 1 block margin from sides
            apple.y = random.nextInt(Math.max(1, numBlocksHigh - 2)) + 1;   // Keep 1 block margin from top/bottom

            // Make sure apple doesn't spawn on snake
            validPosition = true;
            for (Point segment : snake) {
                if (segment.x == apple.x && segment.y == apple.y) {
                    validPosition = false;
                    break;
                }
            }
            attempts++;
        }

        // Fallback if no valid position found
        if (!validPosition) {
            apple.x = NUM_BLOCKS_WIDE - 2;
            apple.y = numBlocksHigh - 2;
        }
    }

    private void eatApple() {
        // Add to score
        score++;

        // Notify callback about score change
        if (gameCallbacks != null) {
            gameCallbacks.onScoreChanged(score);
        }

        // Grow snake by adding a new segment at the tail
        Point tail = snake.get(snake.size() - 1);
        snake.add(new Point(tail.x, tail.y));

        // Spawn new apple
        spawnApple();
    }

    private void moveSnake() {
        // Move body - each segment moves to where the segment in front was
        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        // Move head based on direction
        switch (heading) {
            case UP:
                snake.get(0).y--;
                break;
            case RIGHT:
                snake.get(0).x++;
                break;
            case DOWN:
                snake.get(0).y++;
                break;
            case LEFT:
                snake.get(0).x--;
                break;
        }
    }

    private boolean detectDeath() {
        boolean dead = false;

        // Check if snake hit walls
        if (snake.get(0).x < 0 || snake.get(0).x >= NUM_BLOCKS_WIDE ||
                snake.get(0).y < 0 || snake.get(0).y >= numBlocksHigh) {
            dead = true;
        }

        // Check if snake hit itself
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                dead = true;
                break;
            }
        }

        return dead;
    }

    public void update() {
        // Don't update if paused
        if (paused) return;

        // Move snake
        moveSnake();

        // Check if apple eaten
        if (snake.get(0).x == apple.x && snake.get(0).y == apple.y) {
            eatApple();
        }

        // Check for death
        if (detectDeath()) {
            paused = true;
            // Notify callback about game over
            if (gameCallbacks != null) {
                gameCallbacks.onGameOver(score);
            }
        }
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Fill screen with game background
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Calculate centering offsets if game area is smaller than view
            int offsetX = Math.max(0, (getWidth() - (NUM_BLOCKS_WIDE * blockSize)) / 2);
            int offsetY = Math.max(0, (getHeight() - (numBlocksHigh * blockSize)) / 2);

            // Draw game boundary
            paint.setColor(Color.argb(100, 255, 255, 255));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            canvas.drawRect(offsetX - 2, offsetY - 2,
                    offsetX + (NUM_BLOCKS_WIDE * blockSize) + 2,
                    offsetY + (numBlocksHigh * blockSize) + 2, paint);
            paint.setStyle(Paint.Style.FILL);

            // Draw apple
            paint.setColor(Color.argb(255, 255, 69, 0));
            canvas.drawRect(offsetX + apple.x * blockSize + 2,
                    offsetY + apple.y * blockSize + 2,
                    offsetX + (apple.x * blockSize) + blockSize - 2,
                    offsetY + (apple.y * blockSize) + blockSize - 2,
                    paint);

            // Draw snake
            for (int i = 0; i < snake.size(); i++) {
                Point segment = snake.get(i);

                // Make head different color
                if (i == 0) {
                    paint.setColor(Color.argb(255, 144, 238, 144)); // Light green head
                } else {
                    paint.setColor(Color.argb(255, 34, 139, 34));   // Forest green body
                }

                canvas.drawRect(offsetX + segment.x * blockSize + 1,
                        offsetY + segment.y * blockSize + 1,
                        offsetX + (segment.x * blockSize) + blockSize - 1,
                        offsetY + (segment.y * blockSize) + blockSize - 1,
                        paint);
            }

            // Draw game over message (only when paused and not using UI overlay)
            if (paused && gameCallbacks == null) {
                paint.setColor(Color.argb(200, 0, 0, 0));
                canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(60);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", getWidth() / 2f, getHeight() / 2f - 50, paint);
                paint.setTextSize(40);
                canvas.drawText("Tap to play again", getWidth() / 2f, getHeight() / 2f + 50, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired() {
        // Run at specific FPS
        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (paused && gameCallbacks == null) {
                    newGame();
                    return true;
                }

                // Get touch coordinates
                float x = motionEvent.getX();
                float y = motionEvent.getY();

                // Determine swipe direction based on touch position
                float centerX = getWidth() / 2f;
                float centerY = getHeight() / 2f;

                float deltaX = x - centerX;
                float deltaY = y - centerY;

                // Fixed direction logic
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // Horizontal swipe
                    if (deltaX > 0 && heading != Heading.LEFT) {
                        heading = Heading.RIGHT;
                    } else if (deltaX < 0 && heading != Heading.RIGHT) {
                        heading = Heading.LEFT;
                    }
                } else {
                    // Vertical swipe
                    if (deltaY < 0 && heading != Heading.DOWN) {
                        heading = Heading.UP;
                    } else if (deltaY > 0 && heading != Heading.UP) {
                        heading = Heading.DOWN;
                    }
                }
                break;
        }
        return true;
    }

    // Methods for MainActivity integration

    public void setGameCallbacks(GameCallbacks callbacks) {
        this.gameCallbacks = callbacks;
    }

    public void changeDirection(Heading newHeading) {
        // Prevent snake from reversing into itself
        switch (newHeading) {
            case UP:
                if (heading != Heading.DOWN) heading = newHeading;
                break;
            case DOWN:
                if (heading != Heading.UP) heading = newHeading;
                break;
            case LEFT:
                if (heading != Heading.RIGHT) heading = newHeading;
                break;
            case RIGHT:
                if (heading != Heading.LEFT) heading = newHeading;
                break;
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void pauseGame() {
        paused = true;
    }

    public void resumeGame() {
        paused = false;
    }

    public int getScore() {
        return score;
    }
}