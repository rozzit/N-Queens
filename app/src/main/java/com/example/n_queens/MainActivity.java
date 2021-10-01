package com.example.n_queens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchAIDifficultySelection(View view)
    {
        Intent selectAIDifficultyIntent = new Intent(this, SelectAIDifficultyActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(selectAIDifficultyIntent, options.toBundle());
    }

    public void launchBoardSizeSelection(View view)
    {
        Intent selectBoardSizeIntent = new Intent(this, SelectBoardSizeActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(selectBoardSizeIntent, options.toBundle());
    }
}