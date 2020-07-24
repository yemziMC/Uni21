package com.example.uni21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class resultActivity extends AppCompatActivity {

    public static final String QUESTION_AMOUNT = "questionAmount";
    public static final String EXTRA_SCORE = "extraScore";
    private Button correctButton;
    private Button wrongButton;
    private Button resultButton;
    private ScrollView correct_scrollview;
    private ScrollView wrong_scrollview;
    private int total_score;
    private int questions_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textViewScore = findViewById(R.id.resultLabel);
        correctButton = findViewById(R.id.correct_button);
        wrongButton = findViewById(R.id.wrong_button);
        correct_scrollview = findViewById(R.id.correct_scrollview);
        wrong_scrollview = findViewById(R.id.wrong_scrollview);

        Intent intent = getIntent();

        total_score = intent.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
        questions_amount = intent.getIntExtra(QuizActivity.QUESTION_AMOUNT, 0);
        ArrayList<String> correct_questions_array = intent.getStringArrayListExtra(QuizActivity.CORRECT_QUESTIONS);
        ArrayList<String> wrong_questions_array = intent.getStringArrayListExtra(QuizActivity.WRONG_QUESTIONS);

        wrong_scrollview.setVisibility(View.GONE);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_listview, correct_questions_array);
        ArrayAdapter adapter1 = new ArrayAdapter<>(this, R.layout.activity_listview, wrong_questions_array);
        ListView listView = findViewById(R.id.correct_list);
        ListView listView1 = findViewById(R.id.wrong_list);

        textViewScore.setText(total_score + "/" + questions_amount);
        listView.setAdapter(adapter);
        listView1.setAdapter(adapter1);

        correctButtonPressed();
        wrongButtonPressed();
    }

    private void correctButtonPressed() {
        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct_scrollview.setVisibility(View.VISIBLE);
                wrong_scrollview.setVisibility(View.GONE);
            }
        });
    }

    private void wrongButtonPressed() {
        wrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct_scrollview.setVisibility(View.GONE);
                wrong_scrollview.setVisibility(View.VISIBLE);
            }
        });
    }

    public void floatTo(View view) {
        Intent intent = new Intent(this, StartingScreenActivity.class);
        intent.putExtra(QUESTION_AMOUNT, questions_amount);
        intent.putExtra(EXTRA_SCORE, total_score);
        startActivity(intent);
    }

}
