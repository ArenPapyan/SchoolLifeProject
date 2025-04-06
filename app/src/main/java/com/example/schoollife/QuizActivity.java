package com.example.schoollife;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    // Ներդրված Question դասը
    private static class Question {
        private String questionText;
        private String[] options;
        private int correctAnswerIndex;

        // Կոնստրուկտոր
        public Question(String questionText, String option1, String option2, String option3, String option4, int correctAnswerIndex) {
            this.questionText = questionText;
            this.options = new String[]{option1, option2, option3, option4};
            this.correctAnswerIndex = correctAnswerIndex;
        }

        // Getters
        public String getQuestionText() {
            return questionText;
        }

        public String getOption(int index) {
            if (index >= 0 && index < options.length) {
                return options[index];
            }
            return "";
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }

    private TextView questionTextView;
    private Button option1Button, option2Button, option3Button, option4Button, nextButton;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean hasAnswered = false;
    private Button[] optionButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Կապել UI տարրերը
        questionTextView = findViewById(R.id.question_text);
        option1Button = findViewById(R.id.option1_button);
        option2Button = findViewById(R.id.option2_button);
        option3Button = findViewById(R.id.option3_button);
        option4Button = findViewById(R.id.option4_button);
        nextButton = findViewById(R.id.next_button);

        // Կոճակների զանգված՝ հեշտ մշակման համար
        optionButtons = new Button[]{option1Button, option2Button, option3Button, option4Button};

        // Next կոճակը սկզբում անջատված է
        nextButton.setEnabled(false);

        // Կոճակների լսողները
        for (Button button : optionButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button) v);
                }
            });
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextQuestion();
            }
        });

        // Պատրաստել հարցերի ցանկը
        prepareQuestions();

        // Խառնել հարցերը
        Collections.shuffle(questionList);

        // Ցուցադրել առաջին հարցը
        displayQuestion();
    }

    private void prepareQuestions() {
        questionList = new ArrayList<>();

        // Ավելացնել բոլոր հարցերը ցանկին
        questionList.add(new Question(
                getString(R.string.q1),
                getString(R.string.q1_option1),
                getString(R.string.q1_option2),
                getString(R.string.q1_option3),
                getString(R.string.q1_option4),
                0)); // Ճիշտ պատասխանը option1 է (ինդեքս 0)

        questionList.add(new Question(
                getString(R.string.q2),
                getString(R.string.q2_option1),
                getString(R.string.q2_option2),
                getString(R.string.q2_option3),
                getString(R.string.q2_option4),
                1)); // Ճիշտ պատասխանը option2 է (ինդեքս 1)

        questionList.add(new Question(
                getString(R.string.q3),
                getString(R.string.q3_option1),
                getString(R.string.q3_option2),
                getString(R.string.q3_option3),
                getString(R.string.q3_option4),
                2)); // Ճիշտ պատասխանը option3 է (ինդեքս 2)

        questionList.add(new Question(
                getString(R.string.q4),
                getString(R.string.q4_option1),
                getString(R.string.q4_option2),
                getString(R.string.q4_option3),
                getString(R.string.q4_option4),
                0));

        questionList.add(new Question(
                getString(R.string.q5),
                getString(R.string.q5_option1),
                getString(R.string.q5_option2),
                getString(R.string.q5_option3),
                getString(R.string.q5_option4),
                3));

        questionList.add(new Question(
                getString(R.string.q6),
                getString(R.string.q6_option1),
                getString(R.string.q6_option2),
                getString(R.string.q6_option3),
                getString(R.string.q6_option4),
                1));

        questionList.add(new Question(
                getString(R.string.q7),
                getString(R.string.q7_option1),
                getString(R.string.q7_option2),
                getString(R.string.q7_option3),
                getString(R.string.q7_option4),
                2));

        questionList.add(new Question(
                getString(R.string.q8),
                getString(R.string.q8_option1),
                getString(R.string.q8_option2),
                getString(R.string.q8_option3),
                getString(R.string.q8_option4),
                0));

        questionList.add(new Question(
                getString(R.string.q9),
                getString(R.string.q9_option1),
                getString(R.string.q9_option2),
                getString(R.string.q9_option3),
                getString(R.string.q9_option4),
                3));

        questionList.add(new Question(
                getString(R.string.q10),
                getString(R.string.q10_option1),
                getString(R.string.q10_option2),
                getString(R.string.q10_option3),
                getString(R.string.q10_option4),
                1));

        questionList.add(new Question(
                getString(R.string.q11),
                getString(R.string.q11_option1),
                getString(R.string.q11_option2),
                getString(R.string.q11_option3),
                getString(R.string.q11_option4),
                2));

        questionList.add(new Question(
                getString(R.string.q12),
                getString(R.string.q12_option1),
                getString(R.string.q12_option2),
                getString(R.string.q12_option3),
                getString(R.string.q12_option4),
                0));

        questionList.add(new Question(
                getString(R.string.q13),
                getString(R.string.q13_option1),
                getString(R.string.q13_option2),
                getString(R.string.q13_option3),
                getString(R.string.q13_option4),
                3));

        questionList.add(new Question(
                getString(R.string.q14),
                getString(R.string.q14_option1),
                getString(R.string.q14_option2),
                getString(R.string.q14_option3),
                getString(R.string.q14_option4),
                1));

        questionList.add(new Question(
                getString(R.string.q15),
                getString(R.string.q15_option1),
                getString(R.string.q15_option2),
                getString(R.string.q15_option3),
                getString(R.string.q15_option4),
                2));
    }

    private void displayQuestion() {
        resetButtonColors();
        hasAnswered = false;
        nextButton.setEnabled(false);

        Question question = questionList.get(currentQuestionIndex);

        // Թարմացնել հարցի տեքստը
        questionTextView.setText(question.getQuestionText());

        // Թարմացնել տարբերակները
        option1Button.setText(question.getOption(0));
        option2Button.setText(question.getOption(1));
        option3Button.setText(question.getOption(2));
        option4Button.setText(question.getOption(3));

        // Միացնել բոլոր տարբերակների կոճակները
        for (Button button : optionButtons) {
            button.setEnabled(true);
        }
    }

    private void checkAnswer(Button selectedButton) {
        if (hasAnswered) {
            return; // Արդեն պատասխանել ենք այս հարցին
        }

        hasAnswered = true;
        Question currentQuestion = questionList.get(currentQuestionIndex);
        int correctAnswerIndex = currentQuestion.getCorrectAnswerIndex();

        // Ստուգել պատասխանը
        boolean isCorrect = false;
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i] == selectedButton) {
                isCorrect = (i == correctAnswerIndex);
                break;
            }
        }

        // Գունավորել կոճակները
        if (isCorrect) {
            selectedButton.setBackgroundColor(Color.rgb(76, 175, 80)); // Կանաչ
            score++;
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            selectedButton.setBackgroundColor(Color.rgb(244, 67, 54)); // Կարմիր
            optionButtons[correctAnswerIndex].setBackgroundColor(Color.rgb(76, 175, 80)); // Ցույց տալ ճիշտ պատասխանը
            Toast.makeText(this, getString(R.string.incorrect_toast) + " " +
                    currentQuestion.getOption(correctAnswerIndex), Toast.LENGTH_SHORT).show();
        }

        // Անջատել բոլոր տարբերակների կոճակները
        for (Button button : optionButtons) {
            button.setEnabled(false);
        }

        // Միացնել Next կոճակը
        nextButton.setEnabled(true);
    }

    private void resetButtonColors() {
        for (Button button : optionButtons) {
            button.setBackgroundColor(Color.rgb(63, 81, 181)); // Սկզբնական գույն (կապույտ)
        }
    }

    private void goToNextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex < questionList.size()) {
            displayQuestion();
        } else {
            showResults();
        }
    }

    private void showResults() {
        int totalQuestions = questionList.size();
        int percentage = (score * 100) / totalQuestions;

        String resultMessage = getString(R.string.results_message) + "\n\n" +
                getString(R.string.correct_answers) + ": " + score + " / " + totalQuestions + "\n" +
                getString(R.string.percentage) + ": " + percentage + "%";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.results_title)
                .setMessage(resultMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.restart_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetQuiz();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.home_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Վերադառնալ Home էկրանին
                        // Եթե HomeActivity ունեք. Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        // startActivity(intent);
                        finish(); // Փակել ընթացիկ ակտիվիտին
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        Collections.shuffle(questionList);
        displayQuestion();
    }
}
