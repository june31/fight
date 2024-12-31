package tools.cache;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Memo3 {
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
		if (method == null) method = getCaller(params);
		Object key = params.length == 1 ? params[0] : Arrays.asList(params);
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

	private static Method getCaller(Object... params) {
		try {
			var frame = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
					.walk(frames -> frames.skip(2).findFirst().orElseThrow());
			String name = frame.getMethodName();
			Method[] methods = frame.getDeclaringClass().getDeclaredMethods();

			for (Method m : methods) {
				if (m.getName().equals(name) && isCompatible(m.getParameterTypes(), params)) {
					m.setAccessible(true);
					return m;
				}
			}
			throw new NoSuchMethodException();
		} catch (Exception e) { throw new RuntimeException(
				"Memo.find(args) shall be used with enclosing method arguments."); }
	}

	private static boolean isCompatible(Class<?>[] paramTypes, Object[] args) {
		if (paramTypes.length != args.length) return false;
		for (int i = 0; i < paramTypes.length; i++) {
			if (args[i] != null) {
				if (!isAssignable(paramTypes[i], args[i].getClass())) return false;
			} else if (paramTypes[i].isPrimitive()) return false;
		}
		return true;
	}

	private static boolean isAssignable(Class<?> paramType, Class<?> argType) {
		if (paramType.isAssignableFrom(argType)) return true;
		if (paramType.isPrimitive()) {
			if (paramType == int.class && argType == Integer.class) return true;
			if (paramType == long.class && argType == Long.class) return true;
			if (paramType == double.class && argType == Double.class) return true;
			if (paramType == float.class && argType == Float.class) return true;
			if (paramType == boolean.class && argType == Boolean.class) return true;
			if (paramType == char.class && argType == Character.class) return true;
			if (paramType == byte.class && argType == Byte.class) return true;
			if (paramType == short.class && argType == Short.class) return true;
		}
		return false;
	}
}