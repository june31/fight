package tooltests.voronoi;

import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;
import tools.voronoi.Voronoi2D;
import tools.voronoi.model.VorMode;

public class CGS_Flood {
	// https://www.codingame.com/ide/puzzle/flood-fill-example
	public static void main(String[] args) {
		int[][] map = Scan.readMapCL();
		Pos[] allPos = Table.findAll(map, x -> x != '.' && x != '#');
		Voronoi2D v = new Voronoi2D(map);
		v.setStandardSideEffect(false, () -> Table.get(map, allPos[v.index]));
		v.setContactSideEffect('+');
		v.diffuse(allPos, '#', VorMode.SYNC_FLUID);
		Table.printMap(map);
	}
}
