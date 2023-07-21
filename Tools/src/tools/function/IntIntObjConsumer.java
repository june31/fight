package tools.function;

@FunctionalInterface
public interface IntIntObjConsumer<A> {
	void accept(int l, int c, A v);
}
