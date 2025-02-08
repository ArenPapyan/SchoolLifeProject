package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoollife.R;

class SignUpActivity extends AppCompatActivity {

    EditText emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    Button signupButton;

    TextView  LogInLink;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        checkBox = findViewById(R.id.checkBox);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signupButton = findViewById(R.id.signupButton);
        LogInLink = findViewById(R.id.LogInLink);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignUpActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (username.equals("admin") && password.equals("1234")) {
                    Toast.makeText(SignUpActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });
        LogInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

    }



    public void onSignUpClicked(View view) {

    }
}
