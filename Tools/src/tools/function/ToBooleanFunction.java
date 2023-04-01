package tools.function;

@FunctionalInterface
public interface ToBooleanFunction<T> {
    boolean applyAsBoolean(T value);
}