package pro.juin.aggro.compressor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

public class Compressor {

	public static Map<String, Clazz> nameToClassMap = new LinkedHashMap<>();
	public static List<SMethod> staticMethods = new ArrayList<>();
	private static List<Entity> allEntities = new ArrayList<>();

	public static String compress(String code, String tools) {
		ParseResult<CompilationUnit> pr = new JavaParser().parse(tools);
		CompilationUnit cu = pr.getResult().get();

		String mainName = "-main-";
		Clazz main = new Clazz(mainName);
		main.content = code;
		main.valid = true;
		nameToClassMap.put(mainName, main);

		// Collect classes and static methods
		cu.accept(new ClassMethodCollector(), null);

		// Collect class content without static methods
		cu.accept(new ClassWithoutStaticCollector(), null);

		allEntities = new LinkedList<>(nameToClassMap.values());
		allEntities.addAll(staticMethods);
		allEntities.remove(0);
		spread(main);

		/* Validate classes when at least 1 c
		boolean action;
		do {
			action = false;
			for (var c: nameToClassMap.values()) {
				if (c.valid) continue;
				for (var m: c.staticMethods) {
					if (m.valid) {
						c.valid = true;
						action = true;
						spread(c);
					}
				}
			}
		} while (action);*/
		
		StringBuilder sb = new StringBuilder();
		for (var c: nameToClassMap.values()) {
			if (c.name.equals(mainName)) sb.append(c.content);
			else if (c.valid) {
				String s = c.content;
				int p = s.lastIndexOf('}');
				var ssb = new StringBuilder();
				for (var m: c.staticMethods) if (m.valid) ssb.append(right(m.content) + '\n');
				sb.append('\n' + shrink(s.substring(0, p) + ssb + "}\n"));
			}
		}
		
		return sb.toString();
	}

	private static void spread(Entity ref) {
		var subs = new ArrayList<Entity>();
		for (var it = allEntities.iterator(); it.hasNext();) {
			var e = it.next();
			if (ref.content.contains(e.name) || (e.altName != null && ref.content.contains(e.altName))) {
				e.valid = true;
				subs.add(e);
				it.remove();
			}
		}
		for (var e: subs) spread(e);
	}
	
	private static String right(String code) {
		var sb = new StringBuilder();
		for (var l: code.split("\n")) sb.append("    " + l + '\n');
		return sb.toString();
	}

	private static String shrink(String s) {
		var sb = new StringBuilder();
		for (var l: s.replace("\r", "").split("\n")) {
			if (!l.isEmpty()) {
				int i = 0;
				while (l.charAt(i) == ' ') i++;
				var tsb = new StringBuilder();
				for (int j = 0; j < i/4; j++) tsb.append('\t');
				sb.append(tsb + l.substring(i) + '\n');
			}
		}
		return sb.toString();
	}
}
