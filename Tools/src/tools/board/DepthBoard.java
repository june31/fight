package tools.board;

import tools.misc.Copyable;

public abstract class DepthBoard<B> implements Copyable<B> {

	public static final int CONTINUE = 0; 
	public static final int END = -1; 
	public static final int INIT = -2;
	public static final int FAIL = -3;
	
	public int depth;
	
	public abstract int[] getCommands();
	public abstract int process(int command);
	public boolean endCondition() { return false; }
}
