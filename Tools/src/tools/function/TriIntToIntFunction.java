package tools.function;

@FunctionalInterface
public interface TriIntToIntFunction {
    int apply(int l, int c, int v);
}