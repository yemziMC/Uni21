package com.example.uni21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
    }

    public void navigateTo(View view){
        Intent intent = new Intent(this, SelectModuleActivity.class);
        startActivity(intent);
        Button btn1 = findViewById(R.id.imageButton1);
        btn1.setBackgroundResource(R.drawable.button_pressed_layout);
        }
    }

