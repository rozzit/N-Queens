package com.example.n_queens;
import java.util.*;

public class Queen
{
    private BoardLocation location; // the location on the board where it was placed
    private Player player;          // the player who placed it
    public Queen(BoardLocation location, Player player)
    {
        this.location = location;
        this.player = player;
    }
    public BoardLocation getLocation()
    {
        return location;
    }
    public Player getPlayer()
    {
        return player;
    }
    /*
       To use this effectively, it should be created at runtime ONCE per game
       for each location and stored in a lookup table.
     */
    public static HashSet<BoardLocation> reachableLocationsFrom(BoardLocation location, Board board)
    {
        HashSet<BoardLocation> reachableLocations = new HashSet<>();

        // all horizontally reachable positions
        for(int col = 0; col < board.getSideLength(); col++)
        {
            reachableLocations.add(new BoardLocation(location.getRow(), col));
        }
        // all vertically reachable positions
        for(int row = 0; row < board.getSideLength(); row++)
        {
            reachableLocations.add(new BoardLocation(row, location.getCol()));
        }
        // down-right diagonal
        for(int col = location.getCol() + 1, row = location.getRow() + 1; !board.isOutOfBounds(col, row); col++, row++)
        {
            reachableLocations.add(new BoardLocation(row, col));
        }
        // up-right diagonal
        for(int col = location.getCol() + 1, row = location.getRow() - 1; !board.isOutOfBounds(col, row); col++, row--)
        {
            reachableLocations.add(new BoardLocation(row, col));
        }
        // down-left diagonal
        for(int col = location.getCol() - 1, row = location.getRow() + 1; !board.isOutOfBounds(col, row); col--, row++)
        {
            reachableLocations.add(new BoardLocation(row, col));
        }
        // up-left diagonal
        for(int col = location.getCol() - 1, row = location.getRow() - 1; !board.isOutOfBounds(col, row); col--, row--)
        {
            reachableLocations.add(new BoardLocation(row, col));
        }
        return reachableLocations;
    }

	/*
	    // test for the function 'reachableLocationsFrom'
	   public static void main(String[] args)
	   {
	    BoardLocation loc = new BoardLocation(4, 4);
	    Board brd = new Board(8);
	    ArrayList<BoardLocation> list = reachableLocationsFrom(loc, brd);
	    System.out.println(list.toString());
	   }
	 */
}
