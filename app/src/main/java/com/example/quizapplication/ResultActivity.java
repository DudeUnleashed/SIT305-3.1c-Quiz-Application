package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    //Define all objects used in view
    TextView textCongratsName, textScoreLabel, textScoreValue;
    Button buttonNewQuiz, buttonFinishQuiz;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Assign objects to id
        textCongratsName = findViewById(R.id.textCongratsName);
        textScoreLabel = findViewById(R.id.textScoreLabel);
        textScoreValue = findViewById(R.id.textScoreValue);

        buttonNewQuiz = findViewById(R.id.buttonNewQuiz);
        buttonFinishQuiz = findViewById(R.id.buttonFinishQuiz);

        Bundle extras = getIntent().getExtras();
        String playerName = extras.getString("PlayerName");
        int playerScore = extras.getInt("PlayerScore");

        textCongratsName.setText("Congratulations " + playerName);
        textScoreValue.setText(playerScore + "/10");

        buttonNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRestartQuiz = new Intent(ResultActivity.this, MainActivity.class);
                intentRestartQuiz.putExtra("PlayerName", playerName);
                startActivity(intentRestartQuiz);
            }
        });

        buttonFinishQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}