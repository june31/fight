package tools.collections.object;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class Lo<T> extends ArrayList<T> {
	public Lo() {}
	public Lo(Collection<T> c) { super(c); }
	
	public T first() { return get(0); }
	public T last() { return get(size() - 1); }
}
