package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Define all objects used in view
    TextView textTitle, textEnterName;
    EditText editName;
    Button buttonStart;

    public String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign objects to id
        textTitle = findViewById(R.id.textTitle);
        textEnterName = findViewById(R.id.textEnterName);
        editName = findViewById(R.id.editTextPersonName);
        buttonStart = findViewById(R.id.buttonStart);

        //if returning through the results screen, puts playerName in the editName
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String existingPlayerName = extras.getString("PlayerName");
            editName.setText(existingPlayerName);
        }


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerName = String.valueOf(editName.getText());
                Intent intentToQuestion = new Intent(MainActivity.this, QuestionActivity.class);
                intentToQuestion.putExtra("PlayerName", playerName);
                startActivity(intentToQuestion);
                finish();
            }
        });
    }


}