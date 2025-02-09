package com.example.schoollife.QuestMode;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoollife.QuestMode.Quest;
import com.example.schoollife.QuestMode.QuestManager;
import com.example.schoollife.R;

import java.util.List;

public class QuestActivity extends AppCompatActivity {
    private QuestManager questManager;
    private TextView questionText;
    private Button[] answerButtons = new Button[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        questManager = new QuestManager();
        questManager.addQuest(new Quest("Какого цвета небо?", new String[]{"Синий", "Зеленый", "Красный", "Желтый"}, 0));
        questManager.addQuest(new Quest("Сколько будет 2+2?", new String[]{"3", "4", "5", "6"}, 1));
        questManager.addQuest(new Quest("Какая планета третья от Солнца?", new String[]{"Марс", "Венера", "Земля", "Юпитер"}, 2));
        questManager.addQuest(new Quest("Какое животное символизирует мудрость?", new String[]{"Лев", "Сова", "Змея", "Кот"}, 1));

        questionText = findViewById(R.id.question_text);
        answerButtons[0] = findViewById(R.id.answer_1);
        answerButtons[1] = findViewById(R.id.answer_2);
        answerButtons[2] = findViewById(R.id.answer_3);
        answerButtons[3] = findViewById(R.id.answer_4);

        loadNextQuest();
    }

    private void loadNextQuest() {
        if (!questManager.getQuests().isEmpty()) {
            Quest currentQuest = questManager.getQuests().get(0);
            questionText.setText(currentQuest.getQuestion());
            String[] answers = currentQuest.getAnswers();
            for (int i = 0; i < 4; i++) {
                answerButtons[i].setText(answers[i]);
                int finalI = i;
                answerButtons[i].setOnClickListener(v -> checkAnswer(finalI, currentQuest.getCorrectAnswerIndex()));
            }
        }
    }

    private void checkAnswer(int selectedIndex, int correctIndex) {
        if (selectedIndex == correctIndex) {
            questManager.getQuests().remove(0);
            loadNextQuest();
        }
    }
}