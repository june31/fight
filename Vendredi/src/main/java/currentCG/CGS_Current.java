package currentCG;

import tools.dfs.DFSMap;
import tools.scanner.Scan;
import tools.strings.S;
import tools.tables.Table;

public class CGS_Current {
    public static void main(String args[]) {
    	var map = Scan.readMapLC();
    	var dfs = new DFSMap(map);
    	S.o(dfs.addScore(() -> dfs.v2 >= '0' && dfs.v2 <= '9' ? dfs.v2 - '0' : 0)
			.start(Table.find(map, 'X')));
    }
}
