package tools.function;

@FunctionalInterface
public interface IntLongPredicate {
	boolean test(int id, long v);
}
