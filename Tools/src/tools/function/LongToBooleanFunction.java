package tools.function;

@FunctionalInterface
public interface LongToBooleanFunction {
	boolean applyAsBoolean(long x);
}
