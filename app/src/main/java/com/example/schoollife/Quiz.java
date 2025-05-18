package com.example.schoollife;

public class Quiz {
    private String title;
    private String description;
    private String difficulty;
    private int iconResourceId;

    public Quiz(String title, String description, String difficulty, int iconResourceId) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.iconResourceId = iconResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }
}