package summer9;

import java.util.ArrayList;
import java.util.List;

import tools.tuple.Pos;

public class Common {
	
	public static final String INPUT = "56 111 18 51 106 51 51 95 97 111 18 120 51 59 52 51 51 58 77 88 55 111 75 88 71 79";
	public static int MIN_X = Integer.MAX_VALUE;
	public static int MAX_X = Integer.MIN_VALUE;
	public static int MIN_Y = Integer.MAX_VALUE;
	public static int MAX_Y = Integer.MIN_VALUE;
	public static final int SQUARE_SIZE = 8;
	public static int X;
	public static int Y;
	public static int turn = 0;

	public static final int[][] board = new int[X][Y];
	
	public static int dx, dy;
	public static Pos me;
	public static List<Pos> baddies = new ArrayList<>();
}
