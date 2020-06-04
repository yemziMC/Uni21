package com.example.uni21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_module);
    }
    public void navigateTo(View view){
        Intent intent = new Intent(this, StartingScreenActivity.class);
        startActivity(intent);
    }
}