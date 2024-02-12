package tools;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ParallelEngine<V> {

	volatile int bestScore = Integer.MIN_VALUE;
	private int deferredScore = Integer.MIN_VALUE;;
	private int leaderboardSize;
	private List<Runner<V>> runners = new ArrayList<>();
	private long start;
	private long delay;
	private long previousHighScoreTime = 0;

	public boolean silent = false;
	public Leaderboard<V> leaderboard; // Supplementary leaderboard
	
	public ParallelEngine(int boardSize, Supplier<Runner<V>> runner) {
		this(Runtime.getRuntime().availableProcessors(), boardSize, runner);
	}

	public ParallelEngine(int cores, int leadboardSize, Supplier<Runner<V>> runnerSupplier) {
		start = System.currentTimeMillis();
		previousHighScoreTime = start;
		leaderboardSize = leadboardSize;
		leaderboard = new Leaderboard<V>(leaderboardSize);

		for (int i = 0; i < cores; i++) {
			Runner<V> runner = runnerSupplier.get();
			Leaderboard<V> board = new Leaderboard<V>(leadboardSize);
			runner.init(this, i, board);
			runners.add(runner);
		}
	}

	public void setDelayBetweenHighScores(long millis) {
		delay = millis;
	}
	
	public void start() {
		start(0);
	}

	@SuppressWarnings("unchecked")
	public V start(long duration) {
		int cores = runners.size();
		if (!silent) System.out.println("Starting runner on " + cores + " core" + (cores > 1 ? "s..." : "..."));
		for (int i = 0; i < cores; i++) {
			runners.get(i).start();
		}
		if (duration > 0) {
			try {
				Thread.sleep(duration);
				for (int i = 0; i < cores; i++) runners.get(i).interrupt();
			} catch (InterruptedException ex) {}
		}
		for (int i = 0; i < cores; i++) {
			try {
				runners.get(i).join();
			} catch (InterruptedException ex) {}
		}
		return (V) mergeBoards().objects[0];
	}

	void highScore(int threadNb, int score, boolean reportHighScore, Runnable runnable) {
		highScore(threadNb, score, reportHighScore, runnable, false);
	}
	
	private synchronized void highScore(int threadNb, int score, boolean reportHighScore, Runnable runnable, boolean deferred) {
		if (score > bestScore || (deferred && score >= deferredScore)) {
			bestScore = score;
			deferredScore = score;
			if (reportHighScore) {
				long now = System.currentTimeMillis();
				if (now < previousHighScoreTime + delay) {
					new Thread(() -> {
						try {
							Thread.sleep(previousHighScoreTime + delay - now);
						} catch (InterruptedException e) {}
						highScore(threadNb, score, reportHighScore, runnable, true);
					}).start();
					return;
				}
				previousHighScoreTime = now;
				long duration = now - start;
				System.out.println("New best score from thread " + threadNb + " at time="
						+ (duration / 1000) + "." + (duration % 1000) + "s: " + score);
			}
			if (runnable != null) runnable.run();
		}
	}

	@SuppressWarnings("unchecked")
	private Leaderboard<V> mergeBoards() {
		for (Runner<V> r : runners) {
			Leaderboard<V> b = r.board;
			for (int i = 0; i < b.size; i++) leaderboard.add(b.scores[i], (V) b.objects[i]);
		}
		return leaderboard;
	}
}
