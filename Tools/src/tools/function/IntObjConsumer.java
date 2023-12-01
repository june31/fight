package tools.function;

@FunctionalInterface
public interface IntObjConsumer<A> {
	void accept(int i, A v);
}
