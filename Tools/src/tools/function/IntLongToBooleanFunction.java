package tools.function;

@FunctionalInterface
public interface IntLongToBooleanFunction {
	boolean applyAsBoolean(int id, int v);
}
