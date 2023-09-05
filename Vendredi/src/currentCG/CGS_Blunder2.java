package currentCG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.scanner.Scan;

public class CGS_Blunder2 {
	public static void main(String[] args) {
		Scan.setDebugMode(false);
		Room start = null;
		int n = Scan.readInt();
		Map<Integer, Room> idToRoom = new HashMap<>();
		idToRoom.put(-1, new Room(-1, 0)); // exit
		Room[] rooms = new Room[n];
		int[] l1 = new int[n];
		int[] l2 = new int[n];
		for (int i = 0; i < n; i++) {
			rooms[i] = new Room(Scan.readInt(), Scan.readInt());
			if (rooms[i].id == 0) start = rooms[i];
			idToRoom.put(rooms[i].id, rooms[i]);
			String s = Scan.readString();
			l1[i] = s.equals("E") ? -1 : Integer.parseInt(s);
			s = Scan.readString();
			l2[i] = s.equals("E") ? -1 : Integer.parseInt(s);
		}
		for (int i = 0; i < n; i++) {
			Room r = rooms[i];
			Room r1 = idToRoom.get(l1[i]);
			r.children.add(r1);
			r1.parents.add(r);
			Room r2 = idToRoom.get(l2[i]);
			if (r1 != r2) {
				r.children.add(r2);
				r2.parents.add(r);
			}
		}
		
		List<Room> todo = new ArrayList<>();

		// Remove unreachable rooms
		todo.add(start);
		while (!todo.isEmpty()) {
			Room r = todo.remove(todo.size() - 1);
			r.reachable = true;
			for (Room child: r.children) {
				if (!child.reachable) todo.add(child);
			}
		}
		for (Room r: rooms) {
			if (!r.reachable) {
				for (Room child: r.children) {
					child.parents.remove(r);
				}
			}
		}
		
		// Process zero's children
		todo.add(start);
		start.total = start.weight;
		while (!todo.isEmpty()) {
			Room r = todo.remove(todo.size() - 1);
			for (Room child: r.children) {
				int tot = r.total + child.weight;
				if (tot > child.total) child.total = tot;
				child.parents.remove(r);
				if (child.parents.isEmpty()) todo.add(child);
			}
		}
		System.out.println(idToRoom.get(-1).total);
	}
}

class Room {
	int id;
	int weight;
	int total;
	boolean reachable = false;
	List<Room> parents = new ArrayList<>();
	List<Room> children = new ArrayList<>();
	public Room(int i, int w) { id = i; weight = w; }
	@Override
	public String toString() { return "R" + id; }
}
