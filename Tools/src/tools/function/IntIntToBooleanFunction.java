package tools.function;

@FunctionalInterface
public interface IntIntToBooleanFunction {
	boolean applyAsBoolean(int id, int v);
}
