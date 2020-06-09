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

import com.example.uni21.QuizDbHelper;
import com.example.uni21.Category;
import com.example.uni21.Question;
import com.example.uni21.QuizActivity;
import com.example.uni21.QuizDbHelper;
import com.example.uni21.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class StartingScreenActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;
    private Spinner spinnerCategory;
    private Spinner spinnerDifficulty;

    //Values used to determine if any of the 4 buttons has been pressed
    public boolean clickedCorpFinance = false;
    public boolean clickedRegAffairs = false;
    public boolean clickedFoodBiotech = false;
    public boolean clickedCorpStrategy = false;


    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        textViewHighscore = findViewById(R.id.text_view_highscore);
        spinnerCategory = findViewById(R.id.spinner_category);
        //spinnerDifficulty = findViewById(R.id.spinner_difficulty);

        loadCategories();
        //loadDifficultyLevels();
        loadHighscore();

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
        Button btn1 = ((Button) findViewById(R.id.Corporate));
        Button btn2 = ((Button) findViewById(R.id.reg_affairs));
        Button btn3 = ((Button) findViewById(R.id.food_bio));
        Button btn4 = ((Button) findViewById(R.id.coop_strategy));

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
        Button btn1 = ((Button) findViewById(R.id.Corporate));
        Button btn2 = ((Button) findViewById(R.id.reg_affairs));
        Button btn3 = ((Button) findViewById(R.id.food_bio));
        Button btn4 = ((Button) findViewById(R.id.coop_strategy));
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
        Button btn1 = ((Button) findViewById(R.id.Corporate));
        Button btn2 = ((Button) findViewById(R.id.reg_affairs));
        Button btn3 = ((Button) findViewById(R.id.food_bio));
        Button btn4 = ((Button) findViewById(R.id.coop_strategy));
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
        Button btn1 = ((Button) findViewById(R.id.Corporate));
        Button btn2 = ((Button) findViewById(R.id.reg_affairs));
        Button btn3 = ((Button) findViewById(R.id.food_bio));
        Button btn4 = ((Button) findViewById(R.id.coop_strategy));
        if(clickedCorpStrategy) {
            btn1.setBackgroundResource(R.drawable.button_layout);
            btn2.setBackgroundResource(R.drawable.button_layout);
            btn3.setBackgroundResource(R.drawable.button_layout);
            btn4.setBackgroundResource(R.drawable.button_pressed_layout);
        }
    }

    //Starts the quiz
    //It takes values of the category and difficulty
    private void startQuiz() {
        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        int categoryID = selectedCategory.getId();
        String categoryName = selectedCategory.getName();
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryID);
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }

    private void loadCategories() {
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        List<Category> categories = dbHelper.getAllCategories();

        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);
    }

    private void loadDifficultyLevels() {
        String[] difficultyLevels = Question.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
    }

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);
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