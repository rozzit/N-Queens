package com.example.n_queens;

import java.util.*;

public class Board
{
    private final int sideLength;

    public Board(int sideLength)
    {
        if(sideLength <= 0)
            throw new IllegalArgumentException("Board side length must be > 0, not '" + sideLength + "'");
        this.sideLength = sideLength;
    }
    public int getSideLength()
    {
        return sideLength;
    }
    public boolean isOutOfBounds(int x, int y)
    {
        return x < 0 || x >= sideLength || y < 0 || y >= sideLength;
    }
}
