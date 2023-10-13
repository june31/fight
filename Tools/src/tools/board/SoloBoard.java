package tools.board;

import tools.misc.Copyable;

public interface SoloBoard extends Copyable<SoloBoard> {

	public static final int CONTINUE = 0; 
	public static final int END = -1; 
	public static final int INIT = -2;
	public static final int FAIL = -3;
	
	int process(int command);
}
