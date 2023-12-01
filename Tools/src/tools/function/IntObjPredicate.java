package tools.function;

@FunctionalInterface
public interface IntObjPredicate<A> {
	boolean test(int id, A v);
}
