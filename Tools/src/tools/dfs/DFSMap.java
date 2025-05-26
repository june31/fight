package tools.dfs;

import tools.tuple.Pos;
import tools.collections.pos.Lp;
import java.util.*;
import java.util.function.*;

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
    private BooleanSupplier moveCondition = () -> v2 != '#';
    private IntSupplier scoreFunction = () -> 0;
    private BooleanSupplier endCondition = () -> false;
    private IntBinaryOperator addScoreFunction = (oldScore, value) -> oldScore + value;
    private java.util.function.Consumer<DFSMap> sideEffect = d -> {};
    private boolean firstEffect = false;

    public DFSMap(int[][] map) {
        this.map = map;
        this.rows = map.length;
        this.cols = map[0].length;
    }

    public DFSMap setMove(BooleanSupplier move) {
        this.moveCondition = move;
        return this;
    }

    public DFSMap setWall(int wall) {
        this.moveCondition = () -> v2 != wall;
        return this;
    }

    public DFSMap setWall(IntPredicate wall) {
        this.moveCondition = () -> !wall.test(v2);
        return this;
    }

    public DFSMap setScore(IntSupplier score) {
        this.scoreFunction = score;
        this.addScoreFunction = (oldScore, value) -> score.getAsInt();
        return this;
    }

    public DFSMap addScore(IntSupplier score) {
        this.scoreFunction = score;
        this.addScoreFunction = (oldScore, value) -> oldScore + score.getAsInt();
        return this;
    }

    public DFSMap setEnd(BooleanSupplier end) {
        this.endCondition = end;
        return this;
    }

    public DFSMap setSideEffect(java.util.function.Consumer<DFSMap> effect) {
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
        score = bestScore == Integer.MIN_VALUE ? 0 : bestScore;
        // Vérifie si la bestPath termine sur une case de fin
        if (bestPath != null && !bestPath.isEmpty()) {
            Pos last = bestPath.get(bestPath.size() - 1);
            l1 = last.l;
            c1 = last.c;
            v1 = map[l1][c1];
            l2 = last.l;
            c2 = last.c;
            v2 = map[l2][c2];
            endReached = endCondition.getAsBoolean();
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
        int localScore = currentScore;
        if (endCondition.getAsBoolean()) {
            found = true;
            int finalScore = addScoreFunction.applyAsInt(localScore, scoreFunction.getAsInt());
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
            if (!moveCondition.getAsBoolean()) continue;
            int res = dfsRec(next, addScoreFunction.applyAsInt(localScore, scoreFunction.getAsInt()), visited, path, false);
            if (res > best) {
                best = res;
            }
        }
        // Si c'est un cul-de-sac, on doit aussi vérifier le score
        if (best == Integer.MIN_VALUE) {
            int deadEndScore = addScoreFunction.applyAsInt(localScore, scoreFunction.getAsInt());
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
