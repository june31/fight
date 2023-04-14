package tooltests.dfs;

import java.util.ArrayList;
import java.util.List;

import tools.board.SoloBoard;
import tools.dfs.DFSSolo;
import tools.output.Out;
import tools.scanner.Scan;
import tools.tables.Table;

// https://www.codingame.com/ide/puzzle/there-is-no-spoon-episode-2
class CGP_DFSSolo_ThereIsNoSpoon2 {
	public static void main(String[] args) {
		int[][] map = Scan.readMapCL();
		Out.debug(map[0].length);
		Out.debug(map.length);
		Table.debugMap(map);
		for (String link: new DFSSolo<>(() -> new Board(map)).process().links)
			System.out.println(link);
	}
}

class Board extends SoloBoard {

	private int[][] map;
	
	public List<String> links = new ArrayList<>();
	
	public Board(int[][] map) {
		this.map = map;
	}

	@Override
	public void copyTo(SoloBoard sb) {
		Board b = (Board) sb; 
		b.links.clear();
		b.links.addAll(links);
	}

	@Override
	public int process(int command) {
		if (command == SoloBoard.INIT) {
			
		}
		return 0;
	}
}

class Node {
	
	static final Node N0 = new Node(0);
	
	int initW;
	int remainW;
	
	
	
	Node[] neighbors = new Node[4]; // up, right, down, left

	public Node(int w) {
		// TODO Auto-generated constructor stub
	}
}