package tools.collections;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class IList {
	public static <A> List<A> intersect(Collection<A> c1, Collection<A> c2) {
		return c1.stream()
		  .distinct()
		  .filter(c2::contains)
		  .collect(Collectors.toList());
	}
}
