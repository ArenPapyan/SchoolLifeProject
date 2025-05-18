package com.example.schoollife;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logoImageView);

        // Սկեյլ անիմացիա՝ փոքրից մեծ
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.5f, 1.5f, // X չափս
                0.5f, 1.5f, // Y չափս
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(2000); // 2 վայրկյան
        scaleAnimation.setFillAfter(true); // թողնում է վերջի վիճակը

        logo.startAnimation(scaleAnimation);

        // Երբ անիմացիան ավարտվում է, բացում ենք հաջորդ activity
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, LogInActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}
