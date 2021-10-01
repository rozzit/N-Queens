package com.example.n_queens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.n_queens.R;

public class SelectAIDifficultyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSlideInAnimation(Gravity.LEFT);
        setContentView(R.layout.activity_select_ai_difficulty);
    }

    public void launchBoardSizeSelection(View view)
    {
        Intent selectBoardSizeIntent = new Intent(this, SelectBoardSizeActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(selectBoardSizeIntent, options.toBundle());
    }

    public void setSlideInAnimation(int gravitySlideInFrom)
    {
        if(Build.VERSION.SDK_INT>20)
        {
            Slide slide = new Slide();
            slide.setSlideEdge(gravitySlideInFrom);
            slide.setDuration(getResources().getInteger(R.integer.changeMenuActivityAnimationDurationMilliseconds));
            slide.setInterpolator(new AccelerateDecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }

}