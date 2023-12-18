package challenge.fall2023.model;

import java.util.ArrayList;
import java.util.List;

public class Drone {
	public int id;
	public int l;
	public int c;
	public int emergency;
	public int power;
	
	public int score;
	public int scanCount;
	public List<Fish> scan = new ArrayList<>();
	
}
