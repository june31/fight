package tooltests.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tools.bfs.BFSGraph;
import tools.board.SoloBoard;
import tools.dfs.DFSSolo;
import tools.scanner.Scan;
import tools.structures.graph.Graph;
import tools.structures.graph.node.Node;

// https://www.codingame.com/ide/puzzle/there-is-no-spoon-episode-2
class CGP_DFSSolo_ThereIsNoSpoon2 {
	public static void main(String[] args) {
		Scan.setDebugMode(false);
		int[][] map = Scan.readMapCL();
		Board finalBoard = new DFSSolo<>(() -> new Board(map)).process(); 
		for (String command: finalBoard.stringCommands) System.out.println(command);
	}
}

class Board extends Graph implements SoloBoard {

	private static boolean initialized = false;

	private final int[] dirl = { -1, 0, 1, 0 };  
	private final int[] dirc = { 0, 1, 0, -1 };  

	private Circle[][] nodeMap;
	private int ln;
	private int cn;
	private List<Circle> nodeList = new ArrayList<>();
	private int rollback = -1;

	public List<String> stringCommands = new ArrayList<>();

	public Board(int[][] map) {
		ln = map.length;
		cn = map[0].length;
		nodeMap = new Circle[ln][cn];
		int id = 0;
		for (int l = 0; l < ln; l++) {
			for (int c = 0; c < cn; c++) {
				int w = map[l][c];
				if (w == '.') continue;
				Circle n = new Circle(id++, w - '0', l, c);
				nodeMap[l][c] = n;
				nodeList.add(n);
				addNode(n); // for BFSGraph
			}
		}
		initNeighbors();

		if (!initialized) {
			try {
				for (Circle n: nodeList) {
					initialProcessNode(n);
				}
			} catch (InvalidBoardException ex) {
				// Should not happen
				throw new Error(ex);
			}
			initialized = true;
		}
	}

	private void initNeighbors() {
		for (Circle n: nodeList) {
			// Up
			int l = n.l;
			int c = n.c;
			while (l > 0) {
				if (nodeMap[--l][c] != null) {
					n.neighbors[0] = nodeMap[l][c];
					break;
				}
			}
			// Right
			l = n.l;
			while (c < cn - 1) {
				if (nodeMap[l][++c] != null) {
					n.neighbors[1] = nodeMap[l][c];
					break;
				}
			}
			// Down
			c = n.c;
			while (l < ln - 1) {
				if (nodeMap[++l][c] != null) {
					n.neighbors[2] = nodeMap[l][c];
					break;
				}
			}
			// Left
			l = n.l;
			while (c > 0) {
				if (nodeMap[l][--c] != null) {
					n.neighbors[3] = nodeMap[l][c];
					break;
				}
			}
			// Use N0 instead of null
			for (int i = 0; i < 4; i++) {
				if (n.neighbors[i] == null) n.neighbors[i] = Circle.N0;
			}
		}
	}

	@Override
	public void copyTo(SoloBoard sb) {
		Board b = (Board) sb;
		for (int x = 0; x < nodeList.size(); x++) {
			Circle n = nodeList.get(x);
			Circle m = b.nodeList.get(x);
			for (int i = 0; i < 4; i++) {
				m.val[i] = n.val[i];
				m.max[i] = n.max[i];
			}
			m.active = n.active;
			m.remain = n.remain;
		}
		b.stringCommands.clear();
		b.stringCommands.addAll(stringCommands);
	}

	@Override
	public int process(int command) {
		try {
			// Command from parent board
			if (command >= 0) {
				apply(command);
			}
			
			// If son failed, forbid link
			if (rollback >= 0) {
				forbidLink();
			}
			
			// Detect all possible rules
			boolean change;
			do {
				change = false;
				for (Circle n: nodeList) {
					if (!n.active) continue;
					change |= processNode(n);
				}
			} while (change);

			// Apply first remaining possible first link
			for (Circle n: nodeList) {
				if (n.remain == 0) continue;
				for (int i = 0; i < 4; i++) {
					if (n.max[i] > n.val[i] && n.val[i] == 0) return getCommand(n, i);
				}
			}

			// If not found, apply first remaining possible second link
			for (Circle n: nodeList) {
				if (n.remain == 0) continue;
				for (int i = 0; i < 4; i++) {
					if (n.max[i] > n.val[i]) return getCommand(n, i);
				}
				return FAIL;
			}

			// End game. Is the graph fully connected?
			for (Circle n: nodeList) {
				n.links.clear();
				for (int i = 0; i < 4; i++) {
					if (n.val[i] > 0) singleLink(n, n.neighbors[i]);
				}
			}
			BFSGraph bfs = new BFSGraph(this);
			bfs.diffuse(nodeList.get(0));
			return (bfs.scanned == size() ? END : FAIL);
		} catch (InvalidBoardException ex) {
			return FAIL;
		}
	}

	private void forbidLink() {
		int i = rollback >> 24;
		int l = (rollback >> 12) & 0xFFF;
		int c = rollback & 0xFFF;
		Circle n = nodeMap[l][c];
		Circle m = n.neighbors[i];
		int j = (i + 2) % 4;
		n.max[i] = n.val[i];
		m.max[j] = m.val[j];
	}

	private void apply(int command) throws InvalidBoardException {
		int i = command >> 24;
		int l = (command >> 12) & 0xFFF;
		int c = command & 0xFFF;
		Circle n = nodeMap[l][c];
		createLink(n, i, 1);
	}

	private int getCommand(Circle n, int i) {
		int command = i << 24 | n.l << 12 | n.c;
		rollback = command;
		return command;
	}

	// These rules need to be applied only once
	private void initialProcessNode(Circle n) throws InvalidBoardException {

		if (nodeList.size() <= 2) return;

		// Case: 1-1
		if (n.nb == 1) {
			for (int i = 0; i < 4; i++) {
				if (n.neighbors[i].nb == 1) {
					n.setMax(i, 0);
				}
			}
		}

		// Case: 2-2
		if (n.nb == 2) {
			for (int i = 0; i < 4; i++) {
				if (n.neighbors[i].nb == 2) {
					n.setMax(i, 1);
				}
			}
		}
	}

	private boolean processNode(Circle n) throws InvalidBoardException {
		boolean change = false;

		// Limit slots to neighbor possibilities.
		for (int i = 0; i < 4; i++) {
			int r = n.neighbors[i].remain;
			if (n.val[i] + r < n.max[i]) {
				n.setMax(i, n.val[i] + r);
				change = true;
			}
		}

		int availSlots = n.max[0] + n.max[1] + n.max[2] + n.max[3] - n.val[0]- n.val[1] - n.val[2]- n.val[3];

		// Case: not enough available slots
		if (n.remain > availSlots) throw new InvalidBoardException();

		// Case: all avail slots can be filled
		if (n.remain == availSlots) {
			for (int i = 0; i < 4; i++) {
				if (n.max[i] > n.val[i]) {
					createLink(n, i, n.max[i] - n.val[i]);
					change = true;
				}
			}			
		}

		// Case: all avail slots can be filled but 1.
		if (n.remain == availSlots - 1) {
			for (int i = 0; i < 4; i++) {
				if (n.max[i] == n.val[i] + 2) {
					createLink(n, i, 1);
					change = true;
				}
			}			
		}

		// Case: only one slot can be filled.
		if (n.remain == 1) {
			for (int i = 0; i < 4; i++) {
				if (n.max[i] == n.val[i] + 2) {
					n.max[i] = 1;
					Circle m = n.neighbors[i];
					int j = (i + 2) % 4;
					if (m.max[j] == m.val[j] + 2) m.max[j] = 1;
					change = true;
				}
			}			
		}

		// Case: no slot can be filled.
		if (n.remain == 0) {
			for (int i = 0; i < 4; i++) {
				Circle m = n.neighbors[i];
				int j = (i + 2) % 4;
				if (m.max[j] > m.val[j]) {
					m.max[j] = m.val[j];
					change = true;
				}
			}
			n.active = false;
		}

		return change;
	}

	private void createLink(Circle n, int i, int nb) throws InvalidBoardException {
		Circle m = n.neighbors[i];
		int j = (i + 2) % 4;
		if (m.val[j] + nb > m.max[j]) throw new InvalidBoardException();
		if (n.val[i] == 0) {
			// Forbid intersections
			int l = n.l + dirl[i];
			int c = n.c + dirc[i];
			while (l != m.l || c != m.c) {
				int i1 = (i + 1) % 4;
				for (int x = 0; x < 2; x++) {
					i1 = (i1 + 2) % 4;
					int l1 = l + dirl[i1];
					int c1 = c + dirc[i1];
					while (l1 >= 0 && l1 < ln && c1 >= 0 && c1 < cn) {
						Circle cir = nodeMap[l1][c1];
						if (cir != null) {
							cir.max[(i1 + 2) % 4] = 0;
							break;
						}
						l1 += dirl[i1];
						c1 += dirc[i1];
					}
				}
				l += dirl[i];
				c += dirc[i];
			}
		}
		n.val[i] += nb;
		m.val[j] += nb;
		n.remain -= nb;
		m.remain -= nb;
		stringCommands.add(n.c + " " + n.l + " " + m.c + " " + m.l + " " + nb);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Circle[] line: nodeMap) sb.append(Arrays.toString(line) + "\n");
		return sb.toString();
	}
}

class Circle extends Node {

	static final Circle N0 = new Circle(-1, 0, -1, -1);

	final int nb;
	int remain;
	boolean active = true;

	Circle[] neighbors = new Circle[4]; // up, right, down, left
	int val[] = new int[4];
	int max[] = new int[4];

	final int l;
	final int c;

	public Circle(int id, int w, int l, int c) {
		super(id);
		nb = w;
		int maxVal = Math.min(nb, 2);
		for (int i = 0; i < 4; i++) max[i] = maxVal;
		remain = w;
		this.l = l;
		this.c = c;
	}

	public void setMax(int i, int v) throws InvalidBoardException {
		if (v < val[i]) throw new InvalidBoardException();
		max[i] = v;
	}

	@Override
	public String toString() {
		return "C" + nb + "(" + l + "," + c + "):" + val[0] + val[1] + val[2] + val[3] + "/" + max[0] + max[1] + max[2] + max[3];
	}
}

@SuppressWarnings("serial")
class InvalidBoardException extends Exception {}