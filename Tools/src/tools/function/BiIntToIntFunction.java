package tools.function;

@FunctionalInterface
public interface BiIntToIntFunction {
    int applyAsInt(int x, int y);
}