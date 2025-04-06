package com.example.schoollife;

public class Question {
    private String questionText;
    private String[] answers;
    private int[] results;

    public Question(String questionText, String[] answers, int[] results) {
        this.questionText = questionText;
        this.answers = answers;
        this.results = results;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int[] getResults() {
        return results;
    }
}