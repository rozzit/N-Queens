package com.example.n_queens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class SelectBoardSizeActivity extends AppCompatActivity {
    private Spinner boardSizeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSlideInAnimation(Gravity.LEFT);
        setContentView(R.layout.activity_select_board_size);
        boardSizeSpinner = findViewById(R.id.boardDimensionSpinner);
        //  https://developer.android.com/reference/android/widget/Spinner#attr_android:gravity
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.possibleBoardDimensions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardSizeSpinner.setAdapter(adapter);
    }
    public void doneButtonClicked(View view)
    {
        String selectedDimensions = boardSizeSpinner.getSelectedItem().toString();
        String[] dimensions = selectedDimensions.split(" × ");
        int rows = Integer.parseInt(dimensions[0]);
        int cols = Integer.parseInt(dimensions[1]);
        /*
         * Toast.makeText(this, "rows: " + rows + ", " + "cols: " + cols, Toast.LENGTH_LONG).show();
         */
        Intent playGameIntent = new Intent(this, PlayGameActivity.class);
        Bundle b = new Bundle();
        b.putInt("rows", rows);
        b.putInt("cols", cols);
        playGameIntent.putExtras(b); //Put your id to your next Intent
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(playGameIntent, options.toBundle());
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