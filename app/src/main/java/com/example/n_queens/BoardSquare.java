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
    private BoardLocation boardLocation;
    private static int spinAnimationStartAngleDeg = 0, spinAnimationEndAngleDeg = 540;
    private static int spimAnimationDurationMilliseconds = 1000;
    private int backgroundColor;
    public BoardSquare(Context context, int row, int col, int backgroundColor)
    {
        super(context);
        boardLocation = new BoardLocation(row, col);
        setBackgroundColor(backgroundColor);
        setOnClickListener(this);
    }
    public BoardLocation getLocation()
    {
        return boardLocation;
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height);
        setMeasuredDimension(size, size); // make it square
    }

    @Override
    public void onClick(View view)
    {
        Toast.makeText(getContext(), "Row = " + boardLocation.getRow() + "   Col = " + boardLocation.getCol(), Toast.LENGTH_SHORT).show();
        PlayGameActivity currentActivity = (PlayGameActivity) this.getContext();
        currentActivity.flipAllReachableSquaresFrom(this.boardLocation);
    }

    public AnimatorSet changeColorAndRotateAnimation()
    {

        AnimatorSet animations = new AnimatorSet();
        ObjectAnimator spinAnimation = ObjectAnimator.ofFloat(this, "rotationX", spinAnimationStartAngleDeg, spinAnimationEndAngleDeg);
        spinAnimation.setDuration(spimAnimationDurationMilliseconds);
        spinAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        ValueAnimator colorAnimation = ValueAnimator.ofArgb(this.getBackgroundColor(), Color.BLUE);
        colorAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        colorAnimation.setDuration(spimAnimationDurationMilliseconds);

        AppCompatButton _this = this; // need a reference to 'this' in the following listener
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                _this.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        animations.play(spinAnimation).with(colorAnimation);
        return animations;
        // animations.start();
    }

    // This may look redundant, but I haven't found a way to access the background color of an AppCompatButton,
    // only how to set it. This is a workaround.
    @Override
    public void setBackgroundColor(int color)
    {
        super.setBackgroundColor(color);
        backgroundColor = color;
    }
    public int getBackgroundColor()
    {
        return this.backgroundColor;
    }

}




