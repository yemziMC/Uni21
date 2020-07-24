package com.example.uni21;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class StartingScreenActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String EXTRA_SCORE = "extraScore";


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    public TextView textViewHighscore;

    //Values used to determine if any of the 4 buttons has been pressed
    public boolean clickedCorpFinance = false;
    public boolean clickedRegAffairs = false;
    public boolean clickedFoodBiotech = false;
    public boolean clickedCorpStrategy = false;
    public int selectedCategoryID;

    public int questions_amount;
    public int score;
    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        textViewHighscore = findViewById(R.id.text_view_highscore);

        loadHighscore();
        Intent intent = getIntent();
        score = intent.getIntExtra(resultActivity.EXTRA_SCORE, 0);
        if (score > highscore) {
            updateHighscore(score);
        }

        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }

    //Highlights the Corp Finance button, sets it to true and unsets all other buttons
    public void highlightCorpFinance(View view) {
        clickedCorpFinance = !clickedCorpFinance;
        clickedFoodBiotech = false;
        clickedRegAffairs = false;
        clickedCorpStrategy = false;
        selectedCategoryID = 0;
        Button btn1 = findViewById(R.id.Corporate);
        Button btn2 = findViewById(R.id.reg_affairs);
        Button btn3 = findViewById(R.id.food_bio);
        Button btn4 = findViewById(R.id.coop_strategy);

        if(clickedCorpFinance) {
            btn1.setBackgroundResource(R.drawable.button_pressed_layout);
            btn2.setBackgroundResource(R.drawable.button_layout);
            btn3.setBackgroundResource(R.drawable.button_layout);
            btn4.setBackgroundResource(R.drawable.button_layout);
        }
    }

    //Highlights the Reg Affairs button, sets it to true and unsets all other buttons
    public void highlightRegAffairs(View view) {
        clickedRegAffairs = !clickedRegAffairs;
        clickedFoodBiotech = false;
        clickedCorpFinance = false;
        clickedCorpStrategy = false;
        selectedCategoryID = 1;
        Button btn1 = findViewById(R.id.Corporate);
        Button btn2 = findViewById(R.id.reg_affairs);
        Button btn3 = findViewById(R.id.food_bio);
        Button btn4 = findViewById(R.id.coop_strategy);
        if(clickedRegAffairs) {
            btn1.setBackgroundResource(R.drawable.button_layout);
            btn2.setBackgroundResource(R.drawable.button_pressed_layout);
            btn3.setBackgroundResource(R.drawable.button_layout);
            btn4.setBackgroundResource(R.drawable.button_layout);
        }
    }

    //Highlights the Food Biotech button, sets it to true and unsets all other buttons
    public void highlightFoodBiotech(View view) {
        clickedFoodBiotech = !clickedFoodBiotech;
        clickedRegAffairs = false;
        clickedCorpFinance = false;
        clickedCorpStrategy = false;
        selectedCategoryID = 2;
        Button btn1 = findViewById(R.id.Corporate);
        Button btn2 = findViewById(R.id.reg_affairs);
        Button btn3 = findViewById(R.id.food_bio);
        Button btn4 = findViewById(R.id.coop_strategy);
        if(clickedFoodBiotech) {
            btn1.setBackgroundResource(R.drawable.button_layout);
            btn2.setBackgroundResource(R.drawable.button_layout);
            btn3.setBackgroundResource(R.drawable.button_pressed_layout);
            btn4.setBackgroundResource(R.drawable.button_layout);
        }
    }

    //Highlights the Corp Strategy button, sets it to true and unsets all other buttons
    public void highlightCorpStrategy(View view) {
        clickedCorpStrategy = !clickedCorpStrategy;
        clickedRegAffairs = false;
        clickedCorpFinance = false;
        clickedFoodBiotech = false;
        selectedCategoryID = 3;
        Button btn1 = findViewById(R.id.Corporate);
        Button btn2 = findViewById(R.id.reg_affairs);
        Button btn3 = findViewById(R.id.food_bio);
        Button btn4 = findViewById(R.id.coop_strategy);
        if(clickedCorpStrategy) {
            btn1.setBackgroundResource(R.drawable.button_layout);
            btn2.setBackgroundResource(R.drawable.button_layout);
            btn3.setBackgroundResource(R.drawable.button_layout);
            btn4.setBackgroundResource(R.drawable.button_pressed_layout);
        }
    }

    //Starts the quiz
    //It takes values of the category
    private void startQuiz() {
        //int selectedCategoryName = 1;
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        List<Category> categories = dbHelper.getAllCategories();

        Category selectedCategory = (Category) categories.get(selectedCategoryID);
        int categoryID = selectedCategory.getId();
        String categoryName = selectedCategory.getName();

        Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryID);
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    public void loadHighscore() {
        Intent intent = getIntent();
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        questions_amount = intent.getIntExtra(QuizActivity.QUESTION_AMOUNT, 0);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore + "/" + questions_amount);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Highscore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}