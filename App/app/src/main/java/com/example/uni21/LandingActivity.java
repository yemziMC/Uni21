package com.example.uni21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        }
    }

