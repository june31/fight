package tools.voronoi.model;

import java.util.List;
import java.util.Objects;

import tools.tuple.Pos;

public class PlayerTrack {
	public int player;
	public List<Pos> track;
	
	public PlayerTrack() {}
	public PlayerTrack(int p, List<Pos> t) { player = p; track = t; }
	
	@Override
	public int hashCode() {
		return Objects.hash(player, track);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PlayerTrack other = (PlayerTrack) obj;
		return player == other.player && Objects.equals(track, other.track);
	}
	
	@Override
	public String toString() { return player + ":" + track; }
}
