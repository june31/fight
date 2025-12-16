package tools.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import tools.collections.pos.Lp;
import tools.tuple.Pos;

public class DFSMap {
    public int[][] map;
    public int rows, cols;
    public int l1, c1, l2, c2;
    public int v1, v2;
    public boolean found;
    public int scanned;
    public int score;
    private boolean endReached;
    public Lp bestPath;
    private int bestScore;
    private Predicate<DFSMap> moveCondition = _ -> v2 != '#';
    private IntUnaryOperator setScoreFunction = _ -> 0;
    private Predicate<DFSMap> endCondition = _ -> false;
    private Consumer<DFSMap> sideEffect = _ -> {};
    private boolean firstEffect = false;

    public DFSMap(int[][] map) {
        this.map = map;
        this.rows = map.length;
        this.cols = map[0].length;
    }

    public DFSMap setMove(Predicate<DFSMap> move) {
        this.moveCondition = move;
        return this;
    }

    public DFSMap setWall(int wall) {
        this.moveCondition = _ -> v2 != wall;
        return this;
    }

    public DFSMap setScore(ToIntFunction<DFSMap> score) {
        this.setScoreFunction = _ -> score.applyAsInt(this);
        return this;
    }

    public DFSMap addScore(ToIntFunction<DFSMap> score) {
    	this.setScoreFunction = s -> s + score.applyAsInt(this);
        return this;
    }

    public DFSMap setEnd(Predicate<DFSMap> end) {
        this.endCondition = end;
        return this;
    }

    public DFSMap setSideEffect(Consumer<DFSMap> effect) {
        this.sideEffect = effect;
        return this;
    }

    public DFSMap disableFirstEffect() {
        this.firstEffect = false;
        return this;
    }

    public boolean isEndReached() {
        return endReached;
    }

    /**
     * Lance le DFS à partir de start, sans repasser deux fois par la même case.
     * Les lambdas utilisent l1,c1 (courant) et l2,c2 (cible).
     * @param start position de départ
     * @return meilleur score trouvé
     */
    public int start(Pos start) {
        found = false;
        scanned = 0;
        score = 0;
        endReached = false;
        bestPath = null;
        bestScore = Integer.MIN_VALUE;
        Set<Pos> visited = new HashSet<>();
        Lp path = new Lp();
        int best = dfsRec(start, 0, visited, path, true);
        score = best == Integer.MIN_VALUE ? 0 : best;
        // Vérifie si la bestPath termine sur une case de fin
        if (bestPath != null && !bestPath.isEmpty()) {
            Pos last = bestPath.get(bestPath.size() - 1);
            l1 = last.l;
            c1 = last.c;
            v1 = map[l1][c1];
            l2 = last.l;
            c2 = last.c;
            v2 = map[l2][c2];
            endReached = endCondition.test(this);
        } else {
            endReached = false;
        }
        return score;
    }

    private int dfsRec(Pos pos, int currentScore, Set<Pos> visited, Lp path, boolean isFirst) {
        l1 = pos.l;
        c1 = pos.c;
        v1 = map[l1][c1];
        if (visited.contains(pos)) return Integer.MIN_VALUE;
        visited.add(pos);
        scanned++;
        path.add(pos);
        if (isFirst && firstEffect) sideEffect.accept(this);
        if (!isFirst) sideEffect.accept(this);
        if (endCondition.test(this)) {
            found = true;
            int finalScore = setScoreFunction.applyAsInt(currentScore);
            if (finalScore > bestScore) {
                bestScore = finalScore;
                bestPath = new Lp(path);
            }
            visited.remove(pos);
            path.remove(path.size() - 1);
            return finalScore;
        }
        int best = Integer.MIN_VALUE;
        for (Pos next : neighbors(pos)) {
            l2 = next.l;
            c2 = next.c;
            v2 = map[l2][c2];
            if (!moveCondition.test(this)) continue;
            int res = dfsRec(next, setScoreFunction.applyAsInt(currentScore), visited, path, false);
            if (res > best) best = res;
        }
        // Si c'est un cul-de-sac, on doit aussi vérifier le score
        if (best == Integer.MIN_VALUE) {
            int deadEndScore = setScoreFunction.applyAsInt(currentScore);
            if (deadEndScore > bestScore) {
                bestScore = deadEndScore;
                bestPath = new Lp(path);
            }
            visited.remove(pos);
            path.remove(path.size() - 1);
            return deadEndScore;
        }
        visited.remove(pos);
        path.remove(path.size() - 1);
        return best;
    }

    private List<Pos> neighbors(Pos p) {
        List<Pos> n = new ArrayList<>();
        int[] dl = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int d = 0; d < 4; d++) {
            int nl = p.l + dl[d], nc = p.c + dc[d];
            if (nl >= 0 && nl < rows && nc >= 0 && nc < cols) {
                n.add(new Pos(nl, nc));
            }
        }
        return n;
    }
}
