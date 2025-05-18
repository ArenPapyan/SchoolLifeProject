package com.example.schoollife;

public class Question {
    // Դաշտեր QuestModeActivity-ի համար
    private String[] questionText;
    private String[] answers;
    private int[] results;

    // Դաշտեր QuizManager-ի համար
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctAnswer; // 1, 2, 3, or 4

    /**
     * Կոնստրուկտոր QuizManager-ի համար
     */
    public Question(String question, String option1, String option2, String option3, String option4, int correctAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;

        // Լրացնել հին API-ի դաշտերը
        this.questionText = new String[]{question};
        this.answers = new String[]{option1, option2, option3, option4};
        this.results = new int[]{correctAnswer};
    }

    /**
     * Կոնստրուկտոր QuestModeActivity-ի համար
     */
    public Question(String questionText, String[] answers, int[] results) {
        this.questionText = new String[]{questionText};
        this.answers = answers;
        this.results = results;

        // Լրացնել QuizManager-ի դաշտերը
        this.question = questionText;
        if (answers.length > 0) this.option1 = answers[0];
        if (answers.length > 1) this.option2 = answers[1];
        if (answers.length > 2) this.option3 = answers[2];
        if (answers.length > 3) this.option4 = answers[3];
        this.correctAnswer = (results != null && results.length > 0) ? results[0] : 1;
    }

    /**
     * Original (full) constructor for compatibility
     */
    public Question(String[] questionText, String[] answers, int[] results,
                    String[] option1, String[] option2, String[] option3,
                    String[] option4, int[] correctAnswer) {
        this.questionText = questionText;
        this.answers = answers;
        this.results = results;

        // Լրացնել նաև QuizManager-ի դաշտերը
        if (questionText != null && questionText.length > 0) this.question = questionText[0];
        if (option1 != null && option1.length > 0) this.option1 = option1[0];
        if (option2 != null && option2.length > 0) this.option2 = option2[0];
        if (option3 != null && option3.length > 0) this.option3 = option3[0];
        if (option4 != null && option4.length > 0) this.option4 = option4[0];
        if (correctAnswer != null && correctAnswer.length > 0) this.correctAnswer = correctAnswer[0];
    }

    // Գեթերներ QuestModeActivity-ի համար
    public String getQuestionText() {
        return questionText != null && questionText.length > 0 ? questionText[0] : "";
    }

    public String[] getAnswers() {
        return answers;
    }

    public int[] getResults() {
        return results;
    }

    // Գեթերներ QuizManager-ի համար
    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}