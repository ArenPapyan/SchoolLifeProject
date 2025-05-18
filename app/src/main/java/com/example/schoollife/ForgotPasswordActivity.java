package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText resetEmailEditText;
    private Button resetPasswordButton;
    private TextView backToLoginLink;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Firebase Authentication միացում
        mAuth = FirebaseAuth.getInstance();

        // View-ների կապակցում
        resetEmailEditText = findViewById(R.id.resetEmailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        backToLoginLink = findViewById(R.id.backToLoginLink);

        // Reset Password կոճակի սեղմման իրադարձություն
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        // Մուտքի էջ վերադառնալու հղումի սեղմման իրադարձություն
        backToLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Վերադառնալ մուտքի էջ
                Intent intent = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void resetPassword() {
        String email = resetEmailEditText.getText().toString().trim();

        // Ստուգել՝ արդյոք էլ. հասցեն դատարկ է
        if (email.isEmpty()) {
            resetEmailEditText.setError("Email is required");
            return;
        }

        // Գաղտնաբառի վերականգնման էլ. նամակ ուղարկելու հարցում
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Հաջողության դեպքում՝ տեղեկացնել օգտատիրոջը
                            Toast.makeText(ForgotPasswordActivity.this,
                                    "Password reset link sent to your email",
                                    Toast.LENGTH_LONG).show();
                            resetEmailEditText.setText("");
                        } else {
                            // Սխալի դեպքում՝ ցույց տալ համապատասխան հաղորդագրություն
                            Toast.makeText(ForgotPasswordActivity.this,
                                    "Failed to send reset link. Please check your email and try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}