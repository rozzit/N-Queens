package com.example.n_queens;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BoardSquare extends AppCompatButton implements View.OnClickListener
{
    private int row, col;
    private static int spinAnimationStartAngleDeg = 0, spinAnimationEndAngleDeg = 720;
    private int backgroundColor;
    public BoardSquare(Context context, int row, int col, int backgroundColor)
    {
        super(context);
        this.row = row;
        this.col = col;
        setBackgroundColor(backgroundColor);
        setOnClickListener(this);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size); // make it square
    }


    @Override
    public void onClick(View view)
    {
        AppCompatButton thisRef = this;

        // setBackgroundColor(Color.rgb(0, 0, 255));

            /*
        RotateAnimation spinAnimation = new RotateAnimation(spinAnimationStartAngleDeg, spinAnimationEndAngleDeg,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        spinAnimation.setDuration(2000);
        spinAnimation
        */

        AnimatorSet animations = new AnimatorSet();

        animations.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                setEnabled(false); // it's turned back on at the end of the method, its so the user can't keep clicking and resetting the animation
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                setEnabled(true);
            }
        });

        ObjectAnimator spinAnimation = ObjectAnimator.ofFloat(view, "rotationX", spinAnimationStartAngleDeg, spinAnimationEndAngleDeg);
        spinAnimation.setDuration(1000);
        spinAnimation.setInterpolator(new AccelerateDecelerateInterpolator());


        ValueAnimator colorAnimation = ValueAnimator.ofArgb(this.getBackgroundColor(), Color.BLUE);
        colorAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        colorAnimation.setDuration(1000); // milliseconds

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                thisRef.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });



        animations.play(spinAnimation).with(colorAnimation);
        animations.start();

        Toast.makeText(getContext(), "Row = " + row + "   Col = " + col, Toast.LENGTH_SHORT).show();
    }

    // this may look redundant, but I haven't found a way to access the background color of an AppCompatButton,
    // only how to set it.
    public int getBackgroundColor()
    {
        return this.backgroundColor;
    }
    @Override
    public void setBackgroundColor(int color)
    {
        super.setBackgroundColor(color);
        backgroundColor = color;
    }
}




