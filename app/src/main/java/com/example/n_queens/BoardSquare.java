package com.example.n_queens;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BoardSquare extends AppCompatButton
{
    private int row, col;
    public BoardSquare(Context context, int row, int col)
    {
        super(context);
        this.row = row;
        this.col = col;
    }

    public OnClickListener MakeOnClickListener()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Row = " + row + "   Col = " + col, Toast.LENGTH_SHORT).show();
                setBackgroundColor(Color.rgb(0, 0, 255));
            }
        };
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size); // make it square
    }


}




