package winter24;

import tools.bfs.BFS;
import tools.collections.pos.Lp;
import tools.scanner.Scan;
import tools.tables.Table;
import tools.tuple.Pos;

class CGP_Main {

    public static void main(String args[]) {
        int nc = Scan.readInt();
        int nl = Scan.readInt();
        int turn = 0;

        // game loop
        while (true) {
        	turn++;
        	int[][] map = new int[nl][nc];
            int entityCount = Scan.readInt();
            for (int i = 0; i < entityCount; i++) {
                int c = Scan.readInt();
                int l = Scan.readInt();
                // WALL, ROOT, BASIC, TENTACLE, HARVESTER, SPORER, A, B, C, D
                String ent = Scan.readString();
                int owner = Scan.readInt(); // 1 if your organ, 0 if enemy organ, -1 if neither
                Pos p = new Pos(l,c);
                Lp la = new Lp();
                Lp lb = new Lp();
                Lp lc = new Lp();
                Lp ld = new Lp();
                map[l][c] = switch (ent + owner) {
                case "WALL-1" -> '#';
                case "ROOT0" -> 'r';
                case "ROOT1" -> 'R';
                case "BASIC0" -> 'b';
                case "BASIC1" -> 'B';
                case "TENTACLE0" -> 't';
                case "TENTACLE1" -> 'T';
                case "HARVESTER0" -> 'h';
                case "HARVESTER1" -> 'H';
                case "SPORER0" -> 's';
                case "SPORER1" -> 'S';
                case "A-1" -> { la.add(p); yield 'A'; }
                case "B-1" -> { lb.add(p); yield 'B'; }
                case "C-1" -> { lc.add(p); yield 'C'; }
                case "D-1" -> { ld.add(p); yield 'D'; }
                default -> '?';
                };
                int organId = Scan.readInt(); // id of this entity if it's an organ, 0 otherwise
                String organDir = Scan.readString(); // N,E,S,W or X if not an organ
                int organParentId = Scan.readInt();
                int organRootId = Scan.readInt();
            }
            int myA = Scan.readInt();
            int myB = Scan.readInt();
            int myC = Scan.readInt();
            int myD = Scan.readInt(); // your protein stock
            int oppA = Scan.readInt();
            int oppB = Scan.readInt();
            int oppC = Scan.readInt();
            int oppD = Scan.readInt(); // opponent's protein stock
            int requiredActionsCount = Scan.readInt(); // your number of organisms, output an action for each one in any order

            
        	BFS bfs = new BFS(map);

            if (turn == 1) {
            	
            }

            
            Table.debug(map);
            for (int i = 0; i < requiredActionsCount; i++) {
            }
        }
    }
}