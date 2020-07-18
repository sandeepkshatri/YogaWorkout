package com.arrchie.insta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.arrchie.insta.Fragment.HomeFragment;

public class challengeActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        linearLayout=findViewById(R.id.challenge_home);
        linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(challengeActivity.this, MainActivity.class);
                // startActivity(new Intent(HomeFragment.this, challengeActivity.class));
                startActivity(intent);
            }
        });

        linearLayout1=findViewById(R.id.challenge_steps);
        linearLayout1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(challengeActivity.this, StepsActivity.class);
                // startActivity(new Intent(HomeFragment.this, challengeActivity.class));
                startActivity(intent);
            }
        });
        linearLayout2=findViewById(R.id.challenge_gym);
        linearLayout2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(challengeActivity.this, GymMainActivity.class);
                // startActivity(new Intent(HomeFragment.this, GymMainActivity.class));
                startActivity(intent);
            }
        });
        linearLayout3=findViewById(R.id.challenge_yoga);
        linearLayout3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(challengeActivity.this, YogaMainActivity.class);
                // startActivity(new Intent(HomeFragment.this, YogaMainActivity.class));
                startActivity(intent);
            }
        });
    }



}