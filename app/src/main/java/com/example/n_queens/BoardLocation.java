package com.example.n_queens;

public class BoardLocation
{
    private int row;
    private int col;
    public BoardLocation(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    public int getRow()
    {
        return row;
    }
    public int getCol()
    {
        return col;
    }
    public String toString()
    {
        return "{row=" + row + ", col=" + col + "}";
    }
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof BoardLocation)
        {
            BoardLocation otherBoardLocation = (BoardLocation) o;
            return row == otherBoardLocation.row && col == otherBoardLocation.col;
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        // implements Elegant Pairing Function algorithm
        return col < row ? row * row + col : col *col + col + row;
    }
}
