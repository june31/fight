package challenge.fall2023;

import challenge.fall2023.model.Drone;
import challenge.fall2023.model.Fish;
import tools.scanner.Scan;

class CGP_Main {

	public static void main(String args[]) {
		for (int i = 0; i < Fish.count; i++) new Fish(Scan.readInt(), Scan.readInt(), Scan.readInt());
		Fish[] fishes = Fish.all;
		// game loop
		Drone me = new Drone();
		Drone foe = new Drone();
		while (true) {
			me.score = Scan.readInt();
			foe.score = Scan.readInt();
			me.scanCount = Scan.readInt();
			for (int i = 0; i < me.scanCount; i++) {
				Fish f = fishes[Scan.readInt()];
				me.scan.add(f);
				f.me = true;
			}
			foe.scanCount = Scan.readInt();
			for (int i = 0; i < foe.scanCount; i++) {
				Fish f = fishes[Scan.readInt()];
				foe.scan.add(f);
				f.foe = true;
			}
			Scan.readInt(); // 1
			me.id = Scan.readInt();
			me.c = Scan.readInt();
			me.l = Scan.readInt();
			me.emergency = Scan.readInt();
			me.power = Scan.readInt();
			Scan.readInt(); // 1
			foe.id = Scan.readInt();
			foe.c = Scan.readInt();
			foe.l = Scan.readInt();
			foe.emergency = Scan.readInt();
			foe.power = Scan.readInt();
			int droneScanCount = Scan.readInt();
			for (int i = 0; i < droneScanCount; i++) {
				int droneId = Scan.readInt();
				int creatureId = Scan.readInt();
			}
			int visibleCreatureCount = Scan.readInt();
			for (int i = 0; i < visibleCreatureCount; i++) {
				Fish f = fishes[Scan.readInt()];
				f.c = Scan.readInt();
				f.l = Scan.readInt();
				f.vc = Scan.readInt();
				f.vl = Scan.readInt();
			}
			int radarBlipCount = Scan.readInt();
			for (int i = 0; i < radarBlipCount; i++) {
				int droneId = Scan.readInt();
				int creatureId = Scan.readInt();
				String radar = Scan.readString();
			}
			//Pos p = ne
			System.out.println("WAIT 1"); // MOVE <x> <y> <light (1|0)> | WAIT <light (1|0)>
		}
	}
}
