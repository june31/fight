package summer24;

public class Common {
	public static final int HURDLES = 1;
	public static final int BOW = 2;
	public static final int ROLLER = 4;
	public static final int DIVE = 8;
	
	public static final int R = 0;
	public static final int D = 1;
	public static final int L = 2;
	public static final int U = 3;
	
	public static final int ACTIVE = 1;
	public static final int RESET = 2;
	public static final int UNKNOWN = 3;
	
	public static final int MAX_DEPTH = 8;
	
	public static int myIndex;
	public static int turn = 0;
	
	public static final Hurdle[] hurdles = new Hurdle[3 * MAX_DEPTH];
	public static final Bow[] bows = new Bow[3 * MAX_DEPTH];
	public static final Roller[] rollers = new Roller[3];
	public static final Dive[] dives = new Dive[3 * MAX_DEPTH];
	
	public static final int[][] scores = new int[3][];
	
	public static final int[] scoreH = new int[3];
	public static final int[] scoreB = new int[3];
	public static final int[] scoreD = new int[3];
	
	public static int bestH[] = new int[3];
	public static int bestB[] = new int[3];
	public static int bestD[] = new int[3];
	public static int moveH[] = new int[3];
	public static int moveB[] = new int[3];
	public static int moveD[] = new int[3];
	public static int mR1[] = new int[3];
	public static int mR2[] = new int[3];
	public static int mR3[] = new int[3];
	
	public static String dir(int d) {
		return d == L ? "LEFT" : d == U ? "UP" : d == R ? "RIGHT" : "DOWN";
	}
}
