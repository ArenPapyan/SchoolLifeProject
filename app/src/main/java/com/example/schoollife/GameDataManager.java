package com.example.schoollife;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameDataManager {
    private static GameDataManager instance;
    private DatabaseReference userRef;
    private FirebaseUser currentUser;

    public void loadQuizScore(String quizName, OnDataLoadedListener listener) {
        Log.d("GameDataManager", "Loading score for: " + quizName);

        if (userRef != null) {
            Log.d("GameDataManager", "UserRef exists, loading data...");
            userRef.child("quizzes").child(quizName).child("bestScore")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Integer score = dataSnapshot.getValue(Integer.class);
                            Log.d("GameDataManager", "Data loaded: " + score);
                            listener.onDataLoaded(score != null ? score : 0);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("GameDataManager", "Error loading data: " + databaseError.getMessage());
                            listener.onDataLoaded(0);
                        }
                    });
        } else {
            Log.e("GameDataManager", "UserRef is null!");
        }
    }
    private GameDataManager() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(currentUser.getUid());
        }
    }

    public static GameDataManager getInstance() {
        if (instance == null) {
            instance = new GameDataManager();
        }
        return instance;
    }

    // Update user reference when account changes
    public void updateUser() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(currentUser.getUid());
        }
    }

    // Save minigame score
    public void saveMinigameScore(String minigameName, int score) {
        if (userRef != null) {
            userRef.child("minigames").child(minigameName).child("bestScore").setValue(score);
        }
    }

    // Save quiz score
    public void saveQuizScore(String quizName, int score) {
        if (userRef != null) {
            userRef.child("quizzes").child(quizName).child("bestScore").setValue(score);
        }
    }

    // Save quest rank
    public void saveQuestRank(String questName, String rank) {
        if (userRef != null) {
            userRef.child("quests").child(questName).child("bestRank").setValue(rank);
        }
    }

    // Save general game progress
    public void saveGameProgress(String dataType, Object data) {
        if (userRef != null) {
            userRef.child("gameProgress").child(dataType).setValue(data);
        }
    }

    // Load minigame score
    public void loadMinigameScore(String minigameName, OnDataLoadedListener listener) {
        if (userRef != null) {
            userRef.child("minigames").child(minigameName).child("bestScore")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Integer score = dataSnapshot.getValue(Integer.class);
                            listener.onDataLoaded(score != null ? score : 0);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onDataLoaded(0); // Default value on error
                        }
                    });
        }
    }




    // Load quest rank
    public void loadQuestRank(String questName, OnQuestDataLoadedListener listener) {
        if (userRef != null) {
            userRef.child("quests").child(questName).child("bestRank")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String rank = dataSnapshot.getValue(String.class);
                            listener.onDataLoaded(rank != null ? rank : "Beginner");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onDataLoaded("Beginner"); // Default rank on error
                        }
                    });
        }
    }

    // Load any game data
    public void loadGameData(String dataPath, OnGenericDataLoadedListener listener) {
        if (userRef != null) {
            userRef.child(dataPath).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listener.onDataLoaded(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onDataLoaded(null);
                }
            });
        }
    }

    // Interface for data loading callbacks
    public interface OnDataLoadedListener {
        void onDataLoaded(int data);
    }

    public interface OnQuestDataLoadedListener {
        void onDataLoaded(String rank);
    }

    public interface OnGenericDataLoadedListener {
        void onDataLoaded(DataSnapshot dataSnapshot);
    }
}