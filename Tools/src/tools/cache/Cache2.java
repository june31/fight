package tools.cache;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cache2 {
	private static final Map<Object, Object> cache = new HashMap<>();
	private static boolean state = false;
	private static Object result;
	private static final Object NOT_FOUND = new Object();
	private static Method method;

	public static boolean find(Object... params) {
		if (state) {
			state = false;
			return false;
		}
		if (method == null) method = getCaller();
		Object key;
		if (params.length == 1) key = params[0];
		else key = Arrays.asList(params);
		Object value = cache.getOrDefault(key, NOT_FOUND);
		if (value == NOT_FOUND) {
			state = true;
			try {
				value = method.invoke(null, params);
			} catch (Exception e) { throw new RuntimeException(e); }
			cache.put(key, value);
		}
		result = value;
		return true;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get() {
		return (T) result;
	}

	private static Method getCaller() {
		try {
			var frame = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
					.walk(frames -> frames.skip(2).findFirst().orElseThrow());
			String name = frame.getMethodName();
			Method[] methods = frame.getDeclaringClass().getDeclaredMethods();
			for (Method m: methods) if (m.getName().equals(name)) {
				m.setAccessible(true);
				return m;
			}
			return null;
		} catch (Exception e) { throw new RuntimeException(e); }
	}
}
