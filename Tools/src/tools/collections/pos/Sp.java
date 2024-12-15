package tools.collections.pos;

import java.util.Collection;
import java.util.TreeSet;

import tools.tuple.Pos;

@SuppressWarnings("serial")
public class Sp extends TreeSet<Pos> {
	public Sp() {}
	public Sp(Collection<Pos> c) { super(c); }
}
