package tools;

public abstract class Runner<T> extends Thread {

	// Default configuration
	protected Runnable actionOnHighScore = null;
	protected boolean reportHighScore = true;
	
	public int id;
	public ParallelEngine<T> engine;
	public Leaderboard<T> board;
	
	public abstract void run();
	
	void init(ParallelEngine<T> engine, int id, Leaderboard<T> board) {
		this.id = id;
		this.engine = engine;
		this.board = board;
	}
	
	public void score(int score, T o) {
		board.add(score, o);
		if (engine != null && score > engine.bestScore) {
			engine.highScore(id, score, reportHighScore, actionOnHighScore);
		}
	}
	
	@Override
	public String toString() {
		return "" + id;
	}
	
	
}
