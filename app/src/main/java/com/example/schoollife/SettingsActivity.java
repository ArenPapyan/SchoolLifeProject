package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button LogOut = findViewById(R.id.LogOut);
        ImageView Home = findViewById(R.id.home);
        //TextView helpText = findViewById(R.id.help_text);
        //TextView aboutText = findViewById(R.id.about_text);

        // Help click listener
        // helpText.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //       Intent intent = new Intent(SettingsActivity.this, HelpActivity.class);
        //         startActivity(intent);
        //     }
        //  });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //aboutText.setOnClickListener(new View.OnClickListener() {
        //  @Override
        // public void onClick(View v) {
        //     Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
        //   startActivity(intent);
        //   }
        //  });
    }
}