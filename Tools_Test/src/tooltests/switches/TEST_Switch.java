package tooltests.switches;

import tools.switches.Switch;
import tools.tables.Table;

public class TEST_Switch {
	public static void main(String[] args) {
		boolean[][] sw1 = {
				{ true, false, false, false },
				{ false, true, false, false },
				{ false, false, true, false },
				{ false, false, false, true },
				{ true, true, false, false },
				{ false, true, true, false },
				{ false, false, true, true }
		};
		
		Table.println(Switch.retrieveZeroSeeds(sw1));
	}
}
