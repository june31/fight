package tools.cache;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

// Supports: multithreading, multiple methods can use cache without interference. 
// Limits: static methods only, cached method name shall be unique within class, keys rely on deepToString. 
public class Cache {
	private static final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();
	private static final ThreadLocal<Boolean> recursionState = ThreadLocal.withInitial(() -> false);
	private static final ThreadLocal<Object> result = new ThreadLocal<>();
	private static final Object NOT_FOUND = new Object();

	public static boolean find(Object... params) {
		if (recursionState.get()) {
			recursionState.set(false);
			return false;
		}
		try {
			Method method = getCaller();
			String key = method.getName() + '|' + Arrays.deepToString(params);
			Object value = cache.getOrDefault(key, NOT_FOUND);
			if (value == NOT_FOUND) {
				recursionState.set(true);
				value = method.invoke(null, params);
				cache.put(key, value);
			}
			result.set(value);
			return true;
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	@SuppressWarnings("unchecked")
	public static <T> T get() {
		return (T) result.get();
	}

	private static Method getCaller() throws Exception {
		var frame = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
				.walk(frames -> frames.skip(2).findFirst().orElseThrow());
		String name = frame.getMethodName();
		Method[] methods = frame.getDeclaringClass().getDeclaredMethods();
		for (Method m: methods) if (m.getName().equals(name)) {
			m.setAccessible(true);
			return m;
		}
		return null;
	}
}
