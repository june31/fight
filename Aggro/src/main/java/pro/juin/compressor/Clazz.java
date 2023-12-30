package pro.juin.compressor;

import java.util.ArrayList;
import java.util.List;

public class Clazz extends Entity {
	public List<SMethod> staticMethods = new ArrayList<>();
	public Clazz(String name) { this.name = name; }
}
