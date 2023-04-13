package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

import javax.xml.transform.Result;

public class QuestionActivity extends AppCompatActivity {

    //string arrays that contain the question type, question body, all 3 answers, and the correct answer
    //this is iterated through to display each question throughout the quiz
    String[] questionTitle = {
            "Math", "Math", "Math", "Math", "Math",
            "English", "English", "English",
            "Science", "Science"};
    String[] questionBody = {
            "What is 2 + 2", "What is 6 * 5", "What is 100 / 5", "What is 1098 * 0", "What is 698 - 105",
            "Which is the correct usage of to, two, too: I went ___ the shops", "What tense is this sentence in: I used to play the drums in the school band", "What is the correct usage of their, they're and there: I like disneyland, I wish I could go ___",
            "What is the atomic number of Oxygen?", "What is the symbol for Gold"};
    String[] questionAnswer1 = {
            "4", "100", "20", "100", "505",
            "too", "present", "there",
            "16", "Ag"};
    String[] questionAnswer2 = {
            "6", "30", "50", "1098", "593",
            "to", "future", "their",
            "8", "G"};
    String[] questionAnswer3 = {
            "2", "60", "25", "0", "421",
            "two", "past", "they're",
            "3", "Gold"};
    String[] questionAnswers = {
            "answer1", "answer2", "answer1", "answer3", "answer2",
            "answer2", "answer3", "answer1",
            "answer2", "answer1"};

    //Define all objects used in view
    TextView textQuestionTitle, textQuestionBody, textQuestionCount, textError;
    ProgressBar progressBarQuestion;
    Button buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonSubmitAnswer;

    //button colour values
    private final int colourDefault = 0xFF6C6C6C;
    private final int colourIncorrect = 0xFFF44336;
    private final int colourCorrect = 0xFF4CAF50;
    private final int colourSelected = 0xFF2B2B2B;

    //assigning strings used to determine answers
    private String answerSelected;
    private String answerCorrect;

    private boolean selectedAnswer1, selectedAnswer2, selectedAnswer3;
    private boolean answerSubmitted = false;

    //stores question count, the current question number, and amount of answers gotten correct
    private final int intQuestionCount = 10;
    private int intQuestionCurrent = 1;
    private int intQuestionCorrect = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Bundle extras = getIntent().getExtras();
        String playerName = extras.getString("PlayerName");

        //Assign objects to id
        textQuestionTitle = findViewById(R.id.textQuestionTitle);
        textQuestionBody = findViewById(R.id.textQuestionBody);
        textQuestionCount = findViewById(R.id.textQuestionCount);
        textError = findViewById(R.id.textError);
        progressBarQuestion = findViewById(R.id.progressBarQuestion);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonSubmitAnswer = findViewById(R.id.buttonSubmitAnswer);

        //initial question setup for the first question
        textQuestionCount.setText(intQuestionCurrent + "/" + intQuestionCount);
        textQuestionTitle.setText(questionTitle[intQuestionCurrent-1]);
        textQuestionBody.setText(questionBody[intQuestionCurrent-1]);
        buttonAnswer1.setText(questionAnswer1[intQuestionCurrent-1]);
        buttonAnswer2.setText(questionAnswer2[intQuestionCurrent-1]);
        buttonAnswer3.setText(questionAnswer3[intQuestionCurrent-1]);
        answerCorrect = questionAnswers[intQuestionCurrent-1];
        progressBarQuestion.setProgress(intQuestionCurrent);

        //Handles onclick functions of answer1
        buttonAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerSelected = "answer1";
                //resets colour of other buttons, and sets current button to the selected colour type
                buttonAnswer1.setBackgroundTintList(ColorStateList.valueOf(colourSelected));
                buttonAnswer2.setBackgroundTintList(ColorStateList.valueOf(colourDefault));
                buttonAnswer3.setBackgroundTintList(ColorStateList.valueOf(colourDefault));
                //sets boolean values correct for selecting this answer
                selectedAnswer1 = true;
                selectedAnswer2 = false;
                selectedAnswer3 = false;
                //little text box that also states what answer has been selected
                textError.setText("You have selected answer 1");
            }
        });

        //Handles onclick functions of answer2
        buttonAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerSelected = "answer2";
                //resets colour of other buttons, and sets current button to the selected colour type
                buttonAnswer1.setBackgroundTintList(ColorStateList.valueOf(colourDefault));
                buttonAnswer2.setBackgroundTintList(ColorStateList.valueOf(colourSelected));
                buttonAnswer3.setBackgroundTintList(ColorStateList.valueOf(colourDefault));
                //sets boolean values correct for selecting this answer
                selectedAnswer1 = false;
                selectedAnswer2 = true;
                selectedAnswer3 = false;
                //little text box that also states what answer has been selected
                textError.setText("You have selected answer 2");
            }
        });

        //Handles onclick functions of answer3
        buttonAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerSelected = "answer3";
                //resets colour of other buttons, and sets current button to the selected colour type
                buttonAnswer1.setBackgroundTintList(ColorStateList.valueOf(colourDefault));
                buttonAnswer2.setBackgroundTintList(ColorStateList.valueOf(colourDefault));
                buttonAnswer3.setBackgroundTintList(ColorStateList.valueOf(colourSelected));
                //sets boolean values correct for selecting this answer
                selectedAnswer1 = false;
                selectedAnswer2 = false;
                selectedAnswer3 = true;
                //little text box that also states what answer has been selected
                textError.setText("You have selected answer 3");
            }
        });

        //Handles onclick functions of the submit and next question buttons
        buttonSubmitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if button has already been pressed in this instance, reset all values and change to next question
                if (answerSubmitted){
                    //if we have used all questions, send to result screen instead
                    if (intQuestionCurrent == intQuestionCount){
                        Intent intentToResult = new Intent(QuestionActivity.this, ResultActivity.class);
                        intentToResult.putExtra("PlayerScore", intQuestionCorrect);
                        intentToResult.putExtra("PlayerName", playerName);
                        startActivity(intentToResult);
                        finish();
                    }else {
                        //resets all value to base
                        answerSubmitted = false;
                        buttonAnswer1.setBackgroundTintList(ColorStateList.valueOf(colourDefault));
                        buttonAnswer2.setBackgroundTintList(ColorStateList.valueOf(colourDefault));
                        buttonAnswer3.setBackgroundTintList(ColorStateList.valueOf(colourDefault));
                        selectedAnswer1 = false;
                        selectedAnswer2 = false;
                        selectedAnswer3 = false;
                        answerSelected = "";
                        textError.setText("");
                        buttonSubmitAnswer.setText("Submit Answer");
                        //begins changing values for next question
                        intQuestionCurrent++;
                        progressBarQuestion.setProgress(intQuestionCurrent);
                        textQuestionCount.setText(intQuestionCurrent + "/" + intQuestionCount);
                        //takes the next value from each of the arrays of questions and answers
                        textQuestionTitle.setText(questionTitle[intQuestionCurrent - 1]);
                        textQuestionBody.setText(questionBody[intQuestionCurrent - 1]);
                        buttonAnswer1.setText(questionAnswer1[intQuestionCurrent - 1]);
                        buttonAnswer2.setText(questionAnswer2[intQuestionCurrent - 1]);
                        buttonAnswer3.setText(questionAnswer3[intQuestionCurrent - 1]);
                        answerCorrect = questionAnswers[intQuestionCurrent - 1];
                    }
                }else
                //makes sure an answer was selected before allowing it to continue
                if (!selectedAnswer1 && !selectedAnswer2 && !selectedAnswer3){
                    textError.setText("You have not selected an answer");
                }else
                //if answer is correct, change colour of the right button to green
                if (Objects.equals(answerSelected, answerCorrect)){
                    textError.setText("Congratulations you got the question correct");
                    answerSubmitted = true;
                    buttonSubmitAnswer.setText("Next Question");
                    //tracks amount of correct answers
                    intQuestionCorrect++;
                    //sets correct answer to green
                    if (selectedAnswer1){
                        buttonAnswer1.setBackgroundTintList(ColorStateList.valueOf(colourCorrect));
                    }
                    if (selectedAnswer2){
                        buttonAnswer2.setBackgroundTintList(ColorStateList.valueOf(colourCorrect));
                    }
                    if (selectedAnswer3){
                        buttonAnswer3.setBackgroundTintList(ColorStateList.valueOf(colourCorrect));
                    }
                }else
                //if the answer is incorrect, change the selected answer to red, and the correct answer to green
                {
                    textError.setText("You have gotten the question wrong");
                    answerSubmitted = true;
                    buttonSubmitAnswer.setText("Next Question");
                    //sets the selected answer to red
                    if (selectedAnswer1){
                        buttonAnswer1.setBackgroundTintList(ColorStateList.valueOf(colourIncorrect));
                    }
                    if (selectedAnswer2){
                        buttonAnswer2.setBackgroundTintList(ColorStateList.valueOf(colourIncorrect));
                    }
                    if (selectedAnswer3){
                        buttonAnswer3.setBackgroundTintList(ColorStateList.valueOf(colourIncorrect));
                    }
                    //sets the correct answer to green
                    if (Objects.equals(answerCorrect, "answer1")){
                        buttonAnswer1.setBackgroundTintList(ColorStateList.valueOf(colourCorrect));
                    }
                    if (Objects.equals(answerCorrect, "answer2")){
                        buttonAnswer2.setBackgroundTintList(ColorStateList.valueOf(colourCorrect));
                    }
                    if (Objects.equals(answerCorrect, "answer3")){
                        buttonAnswer3.setBackgroundTintList(ColorStateList.valueOf(colourCorrect));
                    }
                }
            }

        });
    }
}