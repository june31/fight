package tools.board;

import tools.misc.Copyable;

public abstract class SoloBoard implements Copyable<SoloBoard> {

	public static final int END = -1; 
	public static final int INIT = -2;
	public static final int FAIL = -3;
	
	public abstract int process(int command);

}
