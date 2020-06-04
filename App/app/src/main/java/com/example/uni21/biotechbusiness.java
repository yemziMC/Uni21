package com.example.uni21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class biotechbusiness extends AppCompatActivity {
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biotechbusiness);
    }

    public void highlight(View view) {
        Button btn = ((Button) findViewById(R.id.Corporate));
        btn.setBackgroundResource(R.drawable.button_pressed_layout);
    }

    public void navigateTo(View view){
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }
}
