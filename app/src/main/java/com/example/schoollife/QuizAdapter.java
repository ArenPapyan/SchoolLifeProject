package com.example.schoollife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoollife.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private final List<Quiz> quizList;
    private final List<Quiz> filteredQuizList;
    private final Context context;

    public QuizAdapter(List<Quiz> quizList, Context context) {
        this.quizList = quizList;
        this.filteredQuizList = new ArrayList<>(quizList);
        this.context = context;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = filteredQuizList.get(position);

        holder.quizTitle.setText(quiz.getTitle());
        holder.quizDescription.setText(quiz.getDescription());
        holder.quizDifficulty.setText(quiz.getDifficulty());
        holder.quizTypeIcon.setImageResource(quiz.getIconResourceId());

        // Set different color for difficulty badge based on level
        switch (quiz.getDifficulty().toLowerCase()) {
            case "beginner":
                holder.quizDifficulty.setBackgroundResource(R.drawable.difficulty_badge_background);
                // You could set a green background here by creating a new drawable
                break;
            case "intermediate":
                holder.quizDifficulty.setBackgroundResource(R.drawable.difficulty_badge_background);
                // You could set an orange background here
                break;
            case "advanced":
                holder.quizDifficulty.setBackgroundResource(R.drawable.difficulty_badge_background);
                // Keep the purple background for advanced
                break;
        }

        // Set button click listener
        holder.startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the quiz activity
                Toast.makeText(context, "Starting " + quiz.getTitle(), Toast.LENGTH_SHORT).show();
                // In a real app:
                // Intent intent = new Intent(context, TakeQuizActivity.class);
                // intent.putExtra("QUIZ_ID", quiz.getId());
                // context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredQuizList.size();
    }

    // Method to filter quizzes based on search text
    public void filter(String text) {
        filteredQuizList.clear();

        if (text.isEmpty()) {
            filteredQuizList.addAll(quizList);
        } else {
            text = text.toLowerCase();
            for (Quiz quiz : quizList) {
                if (quiz.getTitle().toLowerCase().contains(text) ||
                        quiz.getDescription().toLowerCase().contains(text) ||
                        quiz.getDifficulty().toLowerCase().contains(text)) {
                    filteredQuizList.add(quiz);
                }
            }
        }

        notifyDataSetChanged();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView quizTitle, quizDescription, quizDifficulty;
        ImageView quizTypeIcon;
        Button startQuizButton;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);

            quizTitle = itemView.findViewById(R.id.quizTitle);
            quizDescription = itemView.findViewById(R.id.quizDescription);
            quizDifficulty = itemView.findViewById(R.id.quizDifficulty);
            quizTypeIcon = itemView.findViewById(R.id.quizTypeIcon);
            startQuizButton = itemView.findViewById(R.id.startQuizButton);
        }
    }
}