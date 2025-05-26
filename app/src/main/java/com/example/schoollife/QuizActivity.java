package com.example.schoollife;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private Button option1Button, option2Button, option3Button, option4Button, nextButton;
    private ArrayList<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean answered = false;
    private String quizType;
    private ConstraintLayout backToHomeLayout;
    private TextView quizText;

    // Store original button colors for each button individually
    private ColorStateList defaultButtonColor1, defaultButtonColor2, defaultButtonColor3, defaultButtonColor4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get quiz type from intent
        quizType = getIntent().getStringExtra("QUIZ_TYPE");
        if (quizType == null) {
            quizType = "Physics"; // Default to Physics if no quiz type specified
        }

        // Initialize UI components
        initializeUI();

        // Get questions for the selected quiz type
        loadQuestions();

        // Display first question
        displayQuestion();
    }

    private void initializeUI() {
        questionText = findViewById(R.id.question_text);
        option1Button = findViewById(R.id.option1_button);
        option2Button = findViewById(R.id.option2_button);
        option3Button = findViewById(R.id.option3_button);
        option4Button = findViewById(R.id.option4_button);
        nextButton = findViewById(R.id.next_button);
        backToHomeLayout = findViewById(R.id.backToHomeLayout);
        quizText = findViewById(R.id.quizText);

        // Store each button's original color individually
        defaultButtonColor1 = option1Button.getBackgroundTintList();
        defaultButtonColor2 = option2Button.getBackgroundTintList();
        defaultButtonColor3 = option3Button.getBackgroundTintList();
        defaultButtonColor4 = option4Button.getBackgroundTintList();

        // If any button doesn't have a tint, create a consistent default
        if (defaultButtonColor1 == null || defaultButtonColor2 == null ||
                defaultButtonColor3 == null || defaultButtonColor4 == null) {
            ColorStateList defaultTint = ColorStateList.valueOf(Color.parseColor("#E0E0E0"));
            defaultButtonColor1 = defaultTint;
            defaultButtonColor2 = defaultTint;
            defaultButtonColor3 = defaultTint;
            defaultButtonColor4 = defaultTint;

            // Apply the consistent color to all buttons
            option1Button.setBackgroundTintList(defaultTint);
            option2Button.setBackgroundTintList(defaultTint);
            option3Button.setBackgroundTintList(defaultTint);
            option4Button.setBackgroundTintList(defaultTint);
        }

        // Set quiz title
        quizText.setText(quizType + " Quiz");

        // Set up click listeners
        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
            }
        });

        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(4);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    // If the question hasn't been answered yet, show a message
                    // or you could simply require an answer before proceeding
                    return;
                }

                // Move to next question or finish quiz
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    resetButtonStates();
                    displayQuestion();
                } else {
                    finishQuiz();
                }
            }
        });

        backToHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, QuizListActivity.class);
                startActivity(intent);
                finish(); // Go back to previous activity
            }
        });
    }

    private void loadQuestions() {
        QuizManager quizManager = QuizManager.getInstance();
        questionList = quizManager.getQuestions(quizType);

        // If for some reason the question list is empty, go back to quiz list
        if (questionList == null || questionList.isEmpty()) {
            finish();
        }
    }

    private void displayQuestion() {
        answered = false;
        nextButton.setText("Next");

        if (currentQuestionIndex == questionList.size() - 1) {
            nextButton.setText("Finish");
        }

        Question currentQuestion = questionList.get(currentQuestionIndex);

        // Set question text and options
        questionText.setText(currentQuestion.getQuestion());
        option1Button.setText(currentQuestion.getOption1());
        option2Button.setText(currentQuestion.getOption2());
        option3Button.setText(currentQuestion.getOption3());
        option4Button.setText(currentQuestion.getOption4());
    }

    private void checkAnswer(int selectedOption) {
        if (answered) {
            return; // Prevent multiple answers for the same question
        }

        answered = true;
        Question currentQuestion = questionList.get(currentQuestionIndex);

        // Highlight selected button
        Button selectedButton;
        switch (selectedOption) {
            case 1:
                selectedButton = option1Button;
                break;
            case 2:
                selectedButton = option2Button;
                break;
            case 3:
                selectedButton = option3Button;
                break;
            case 4:
                selectedButton = option4Button;
                break;
            default:
                return; // Invalid option
        }

        // Check if the answer is correct
        if (selectedOption == currentQuestion.getCorrectAnswer()) {
            // Correct answer - green color
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50"))); // GREEN
            score++;
        } else {
            // Wrong answer - red color
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F44336"))); // RED

            // Highlight the correct answer in green
            Button correctButton;
            int correctAnswer = currentQuestion.getCorrectAnswer();
            switch (correctAnswer) {
                case 1:
                    correctButton = option1Button;
                    break;
                case 2:
                    correctButton = option2Button;
                    break;
                case 3:
                    correctButton = option3Button;
                    break;
                case 4:
                    correctButton = option4Button;
                    break;
                default:
                    return; // Invalid correct answer
            }

            // Only highlight the correct button if it's not the one selected
            if (correctAnswer != selectedOption) {
                correctButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50"))); // GREEN
            }
        }

        // Disable all option buttons
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);
    }

    private void resetButtonStates() {
        // Reset each button to its original color
        option1Button.setBackgroundTintList(defaultButtonColor1);
        option2Button.setBackgroundTintList(defaultButtonColor2);
        option3Button.setBackgroundTintList(defaultButtonColor3);
        option4Button.setBackgroundTintList(defaultButtonColor4);

        // Enable all option buttons
        option1Button.setEnabled(true);
        option2Button.setEnabled(true);
        option3Button.setEnabled(true);
        option4Button.setEnabled(true);

        answered = false;
    }

    private void finishQuiz() {
        // Navigate to result activity
        Intent intent = new Intent(QuizActivity.this, com.example.schoollife.QuizResultActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", questionList.size());
        intent.putExtra("QUIZ_TYPE", quizType);
        startActivity(intent);
        finish(); // Close this activity
    }
}