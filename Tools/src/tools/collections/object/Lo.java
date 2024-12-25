package tools.collections.object;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class Lo<T> extends ArrayList<T> {
	public Lo() {}
	public Lo(Collection<T> c) { super(c); }
}
