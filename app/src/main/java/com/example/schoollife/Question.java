package com.example.schoollife;

public class Question {
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