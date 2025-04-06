package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.schoollife.HomeActivity;
import com.example.schoollife.Question;
import com.example.schoollife.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class QuestModeActivity extends AppCompatActivity {
    // UI components
    private TextView questionTextView;
    private TextView rankTextView;
    private MaterialButton answerAButton;
    private MaterialButton answerBButton;
    private MaterialButton answerCButton;
    private MaterialButton restartButton;
    private MaterialButton homeButton;
    private CardView questionCardView;
    private TextView resultTextView;

    // Game data
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int currentRankIndex = 0;
    private String[] ranks = {"Private", "Corporal", "Junior Sergeant", "Sergeant", "Senior Sergeant",
            "Senior", "Non-commissioned Officer", "Lieutenant", "Senior Lieutenant"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_quest);

        // Initialize UI components
        questionTextView = findViewById(R.id.questionTextView);
        rankTextView = findViewById(R.id.rankTextView);
        answerAButton = findViewById(R.id.answerAButton);
        answerBButton = findViewById(R.id.answerBButton);
        answerCButton = findViewById(R.id.answerCButton);
        restartButton = findViewById(R.id.restartButton);
        homeButton = findViewById(R.id.homeButton);
        questionCardView = findViewById(R.id.questionCardView);
        resultTextView = findViewById(R.id.resultTextView);

        // Set up button click listeners
        answerAButton.setOnClickListener(v -> processAnswer(0));
        answerBButton.setOnClickListener(v -> processAnswer(1));
        answerCButton.setOnClickListener(v -> processAnswer(2));
        restartButton.setOnClickListener(v -> restartGame());
        homeButton.setOnClickListener(v -> goToHomePage());

        // Initialize game data
        initializeQuestions();
        startGame();
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();

        // 1. Հարց
        questions.add(new Question(
                "You have discovered enemy positions during a combat mission, what will you do?",
                new String[]{
                        "Report to the commander immediately and await instructions",
                        "You will begin reconnaissance without reporting",
                        "You will attack independently, using the element of surprise"
                },
                new int[]{1, 0, -1} // 1: պաշտոնը բարձրացնել, 0: նույնը թողնել, -1: իջեցնել
        ));

        // 2. Հարց
        questions.add(new Question(
                "Your comrade is injured during combat, what will you do?",
                new String[]{
                        "You will stop the combat and immediately try to help him",
                        "You will ensure safety, then provide first aid and report",
                        "You will continue the combat with the intention of providing assistance later"
                },
                new int[]{0, 1, -1}
        ));


        questions.add(new Question(
                "You noticed that one of your unit members has stolen ammunition, what will you do?",
                new String[]{
                        "You will report to the commander",
                        "You will talk to him personally and offer to return it",
                        "You will ignore it, avoiding conflict"
                },
                new int[]{1, 0, -1}
        ));

        questions.add(new Question(

                "During combat duty, your partner asks you to replace him because he is feeling unwell, what will you do?",
                new String[]{
                        "You will replace him and then report the situation to the commander",
                        "You will replace him, but not report",
                        "You will refuse, citing a violation of the regulations"
                },
                new int[]{1, -1, 0}
        ));

        questions.add(new Question(
                "The commander gives you a task that you think is dangerous and poorly planned, what will you do?",
                new String[]{
                        "You will carry out the task without asking questions",
                        "You will refuse to carry out the task",
                        "You will present your concerns and suggest alternative solutions"
                },
                new int[]{0, -1, 1}
        ));

        questions.add(new Question(
                "During military operations, you discover civilians who may be in danger, what will you do?",
                new String[]{
                        "You will report to the command and await instructions",
                        "You will continue the mission, ignoring the civilians",
                        "You will try to evacuate them yourself"
                },
                new int[]{1, -1, 0}
        ));

        questions.add(new Question(
                "You notice that your comrade often drinks alcohol before duty, what will you do?",
                new String[]{
                        "You will talk to him about the problem and offer help",
                        "You will report it to higher command",
                        "You will ignore it as long as it does not interfere with his service"
                },
                new int[]{0, 1, -1}
        ));

        questions.add(new Question(
                "During a combat mission, you receive conflicting orders from different commanders, what will you do?",
                new String[]{
                        "You will carry out the first order you receive",
                        "You will report the situation to the higher command and wait for clarification",
                        "You will choose the order that you think is more correct"
                },
                new int[]{-1, 1, 0}
        ));

        questions.add(new Question(
                "During combat operations, you have discovered a wounded enemy soldier, what will you do?",
                new String[]{
                        "You will report and provide first aid",
                        "Ignore and continue the mission",
                        "You will take a prisoner, but will not provide assistance"
                },
                new int[]{1, -1, 0}
        ));

        questions.add(new Question(
                "Your commander makes a decision that you disagree with, but it doesn't directly endanger anyone, what will you do?",
                new String[]{
                        "You will follow orders, but later express your opinion in a private conversation",
                        "You will openly criticize the decision in front of the other soldiers",
                        "You will refuse to carry out the order"
                },
                new int[]{1, -1, -1}
        ));
    }

    private void startGame() {
        currentQuestionIndex = 0;
        currentRankIndex = 0;
        updateRankDisplay();
        displayQuestion(currentQuestionIndex);

        // Սկզբում թաքցնում ենք արդյունքը և վերսկսման/տուն կոճակները
        resultTextView.setVisibility(View.GONE);
        restartButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);

        // Ցուցադրում ենք հարցը և պատասխանների կոճակները
        questionCardView.setVisibility(View.VISIBLE);
        answerAButton.setVisibility(View.VISIBLE);
        answerBButton.setVisibility(View.VISIBLE);
        answerCButton.setVisibility(View.VISIBLE);
    }

    private void displayQuestion(int questionIndex) {
        if (questionIndex < questions.size()) {
            Question currentQuestion = questions.get(questionIndex);
            questionTextView.setText(currentQuestion.getQuestionText());
            answerAButton.setText(currentQuestion.getAnswers()[0]);
            answerBButton.setText(currentQuestion.getAnswers()[1]);
            answerCButton.setText(currentQuestion.getAnswers()[2]);

            // Անիմացիա հարցի համար
            questionCardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        } else {
            endGame();
        }
    }

    private void processAnswer(int answerIndex) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int rankChange = currentQuestion.getResults()[answerIndex];

        // Փոխում ենք կոչումը
        currentRankIndex += rankChange;

        // Ստուգում ենք սահմանները
        if (currentRankIndex < 0) {
            currentRankIndex = 0;
        } else if (currentRankIndex >= ranks.length) {
            currentRankIndex = ranks.length - 1;
        }

        updateRankDisplay();

        // Ցուցադրում ենք հաջորդ հարցը կամ ավարտում ենք խաղը
        currentQuestionIndex++;

        // Ցույց ենք տալիս Snackbar՝ կոչման փոփոխության մասին
        String message;
        int color;

        if (rankChange > 0) {
            message = "Congratulations. Your rank has been raised.";
            color = getResources().getColor(R.color.success_green);
        } else if (rankChange < 0) {
            message = "Your rank has been lowered.";
            color = getResources().getColor(R.color.error_red);
        } else {
            message = "Your title remained the same.";
            color = getResources().getColor(R.color.neutral_yellow);
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(color);
        snackbar.show();

        // Անիմացիա պատասխանից հետո
        questionCardView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));

        // Կարճ դադարից հետո ցուցադրում ենք հաջորդ հարցը
        questionCardView.postDelayed(() -> displayQuestion(currentQuestionIndex), 500);
    }

    private void updateRankDisplay() {
        rankTextView.setText("\n" + "Current title." + ranks[currentRankIndex]);
    }

    private void endGame() {
        // Թաքցնում ենք հարցը և պատասխանների կոճակները
        questionCardView.setVisibility(View.GONE);
        answerAButton.setVisibility(View.GONE);
        answerBButton.setVisibility(View.GONE);
        answerCButton.setVisibility(View.GONE);

        // Ցուցադրում ենք արդյունքը և վերսկսման/տուն կոճակները
        resultTextView.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
        homeButton.setVisibility(View.VISIBLE);

        // Ձևավորում ենք արդյունքի հաղորդագրությունը
        String resultMessage;
        if (currentRankIndex >= 7) { // Lieutenant or above
            resultMessage = "Congratulations! You have achieved the rank of " + ranks[currentRankIndex] +
                    ". You have demonstrated excellent leadership skills.";

        } else if (currentRankIndex >= 4) { // Sergeant Major and above
            resultMessage = "Good result! You have reached the rank of " + ranks[currentRankIndex] +
                    ". Keep up the good work.";
        } else {
            resultMessage = "Your final rank is " + ranks[currentRankIndex] +
                    ". Try again for better results.";
        }

        resultTextView.setText(resultMessage);
        resultTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    private void restartGame() {
        startGame();
    }

    private void goToHomePage() {
        // Այստեղ պետք է իրականացնել տրամաբանությունը HomeActivity-ին անցնելու համար
        // Օրինակ՝
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}