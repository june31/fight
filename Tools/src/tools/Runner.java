package tools;

public abstract class Runner<V> extends Thread {

	// Default configuration
	protected Runnable actionOnHighScore = null;
	protected boolean reportHighScore = true;
	
	public int id;
	public ParallelEngine<V> engine;
	public Leaderboard<V> board;
	
	public abstract void run();
	
	void init(ParallelEngine<V> engine, int id, Leaderboard<V> board) {
		this.id = id;
		this.engine = engine;
		this.board = board;
	}
	
	public void score(int score, V o) {
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
