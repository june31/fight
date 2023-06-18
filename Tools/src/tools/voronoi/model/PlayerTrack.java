package tools.voronoi.model;

import java.util.List;

import tools.tuple.Pos;

public class PlayerTrack {
	public int player;
	public List<Pos> track;
	
	public PlayerTrack() {}
	public PlayerTrack(int p, List<Pos> t) { player = p; track = t; }
}
