package com.example.n_queens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class PlayGameActivity extends AppCompatActivity
{
    private static final double borderProportion = 0.05;
    private int rows, cols;
    private static final int[] checkerboardColors = new int[]{Color.rgb(0, 0, 0), Color.rgb(255, 255, 255)};
    private static final Paint[] checkerboardColorsPaint = makePaintsFromColors();
    private BoardSquare[][] boardSquares;
    private int boardWidthPixels, boardHeightPixels, squareWidthPixels,squareHeightPixels, boardTopLeftCornerX, boardTopLeftCornerY;


    public GradientDrawable createBorder(String backgroundColor,
                                         String borderColor,
                                         Float borderRadius,
                                         boolean dashed, int width){

        //use a GradientDrawable with only one color set, to make it a solid color
        GradientDrawable border = new GradientDrawable();
        border.setShape(GradientDrawable.RECTANGLE);

        if (backgroundColor != null) {
            border.setColor(Color.parseColor(backgroundColor));  // background color
        }

        if (borderRadius != null) {
            border.setCornerRadius(borderRadius);
        }

        if (dashed){
            border.setStroke(width, Color.parseColor(borderColor), 100,40);
        }else{
            border.setStroke(width, Color.parseColor(borderColor));
        }

        return border;
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.background));
        Bundle b = getIntent().getExtras();
        rows = b.getInt("rows");
        cols = b.getInt("cols");


        final TableLayout layout = new TableLayout(this);
        layout.setId(ViewCompat.generateViewId());
        Button button = new Button(this);
        TableRow tableRow = new TableRow(this);
        tableRow.addView(button);
        tableRow.addView(new BoardSquare(this));
        layout.addView(tableRow);
        setContentView(layout);

    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setSlideInAnimation(Gravity.RIGHT);

        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.background));
        Bundle b = getIntent().getExtras();
        rows = b.getInt("rows");
        cols = b.getInt("cols");

        final TableLayout tableLayout = new TableLayout(this);
        tableLayout.setGravity(Gravity.CENTER_VERTICAL);
        // TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL); //TableLayout.LayoutParams.MATCH_PARENT);
        tableLayout.setId(ViewCompat.generateViewId());
        ViewTreeObserver vto = tableLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tableLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int layoutMeasuredWidth  = tableLayout.getMeasuredWidth();
                int layoutMeasuredHeight = tableLayout.getMeasuredHeight();
                Toast.makeText(getBaseContext(), "ViewHeight = " + layoutMeasuredHeight, Toast.LENGTH_SHORT).show();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                double viewWidthInches = (double) layoutMeasuredWidth / displayMetrics.xdpi;
                double viewHeightInches = (double) layoutMeasuredHeight / displayMetrics.ydpi;

                if(viewWidthInches < viewHeightInches)
                {
                    boardWidthPixels = (int) Math.round(layoutMeasuredWidth * (1 - 2*borderProportion));
                    boardHeightPixels = (int)((double)boardWidthPixels / displayMetrics.xdpi * displayMetrics.ydpi);

                    boardTopLeftCornerX = (int)(borderProportion * layoutMeasuredWidth);
                    boardTopLeftCornerY = (layoutMeasuredHeight - boardHeightPixels) / 2;
                }
                else {
                    boardHeightPixels = (int) Math.round(layoutMeasuredHeight * (1 - 2*borderProportion));
                    boardWidthPixels = (int)((double) boardHeightPixels / displayMetrics.ydpi * displayMetrics.xdpi);

                    boardTopLeftCornerY = (int)(borderProportion * layoutMeasuredHeight);
                    boardTopLeftCornerX = (layoutMeasuredWidth - boardWidthPixels) / 2;
                }
                squareWidthPixels = boardWidthPixels / cols;
                squareHeightPixels = boardHeightPixels / rows;
            }
        });

        TableRow.LayoutParams boardSquareLayoutParams = new TableRow.LayoutParams();
        boardSquareLayoutParams.width = 0;
        boardSquareLayoutParams.weight = (float)(1.0f - 2*borderProportion) / cols;

        boardSquares = new BoardSquare[rows][cols];
        for(int r =  0; r < rows; r++)
        {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            for(int c = 0; c < cols; c++)
            {
                int squareTopCornerX = boardTopLeftCornerX + squareWidthPixels*c;
                int squareTopCornerY = boardTopLeftCornerY + squareHeightPixels*r;
                Rect squareRect = new Rect();
                squareRect.left = squareTopCornerX;
                squareRect.top = squareTopCornerY;
                squareRect.right = squareTopCornerX + squareWidthPixels;
                squareRect.bottom = squareTopCornerY + squareHeightPixels;
                boardSquares[r][c] = new BoardSquare(this, r, c);
                boardSquares[r][c].setBackgroundColor(checkerboardColors[(r + c) % 2]);
                boardSquares[r][c].setOnClickListener(boardSquares[r][c].MakeOnClickListener());
                boardSquares[r][c].setLayoutParams(boardSquareLayoutParams);
                tableRow.addView(boardSquares[r][c]);
            }
            tableLayout.addView(tableRow);
        }
        setContentView(tableLayout);
    }




    private static Paint[] makePaintsFromColors()
    {
        Paint[] paints = new Paint[checkerboardColors.length];
        for(int i = 0; i < PlayGameActivity.checkerboardColors.length; i++)
        {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(PlayGameActivity.checkerboardColors[i]);
            paints[i] = paint;
        }
        return paints;
    }

    public void setSlideInAnimation(int gravitySlideInFrom)
    {
        if(Build.VERSION.SDK_INT>20)
        {
            Slide slide = new Slide();
            slide.setSlideEdge(gravitySlideInFrom);
            slide.setDuration(getResources().getInteger(R.integer.changeGameActivityAnimationDurationMilliseconds));
            // slide.setInterpolator(new AccelerateDecelerateInterpolator());
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }
}