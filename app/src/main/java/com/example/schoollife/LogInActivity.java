package com.example.schoollife;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.schoollife.R;

public class LogInActivity extends AppCompatActivity {
    EditText emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    Button loginButton;

    CheckBox checkBox;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        checkBox = findViewById(R.id.checkBox);
        usernameEditText = findViewById(R.id.usernameEditText1);
        passwordEditText = findViewById(R.id.passwordEditText1);
        loginButton = findViewById(R.id.LogInButton);

        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
        startActivity(intent);

       // checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         //   @Override
           // public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             //   if (isChecked) {
               //     passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                //} else {
                  //  passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                //}
                //passwordEditText.setSelection(passwordEditText.getText().length());
            //}
        //});
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Երբ checkbox-ը նշված է, ցույց տալ գաղտնաբառը
                    passwordEditText.setTransformationMethod(null);
                } else {
                    // Երբ checkbox-ը նշված չէ, թաքցնել գաղտնաբառը
                    passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                }
                // Պահպանել կուրսորի դիրքը
                int position = passwordEditText.getSelectionStart();
                passwordEditText.setSelection(position);
            }
        });
    }
}