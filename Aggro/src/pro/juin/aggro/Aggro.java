package pro.juin.aggro;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Aggro extends AggroCommon {

	private static final String HEADER = "// Aggregated at " + new Date() + "\n\n";

	public static void main(String[] args) throws IOException {
		AggroProperties.init();

		// Scan main sources.
		List<Unit> sources = scan(AggroProperties.getMainSourceDirectories());

		// Scan alternative sources.
		List<Unit> alts = scan(AggroProperties.getAltSourceDirectories());
		Map<String, Unit> longNameToUnit = new HashMap<>();
		for (Unit unit : alts) {
			longNameToUnit.put(unit.pack + '.' + unit.className, unit);
		}

		StringBuilder importBuilder = new StringBuilder();
		StringBuilder bodyBuilder = new StringBuilder();
		
		Set<String> javaImports = new TreeSet<>();
		Set<String> projectImports = new TreeSet<>();

		// Body: main classes
		
		for (Unit unit : sources) {
			if (unit.body != null) { // body is null in skipped classes ($) 
				bodyBuilder.append("\n" + unit.body);
				javaImports.addAll(unit.javaImports);
				projectImports.addAll(unit.projectImports);
			}
		}
		
		// Body: dependancies
		Set<String> allClassNames = new HashSet<>();
		List<String> importsToManage = new ArrayList<>(projectImports);
		while (!importsToManage.isEmpty()) {
			List<String> importsForLoop = new ArrayList<>(importsToManage);
			importsToManage.clear();
			for (String imp : importsForLoop) {
				Unit unit = longNameToUnit.get(imp);
				if (unit == null) fail("Class " + imp + " is not in project scope.");
				if (unit.added) continue;
				for (String cName : unit.classNames) {
					if (allClassNames.contains(cName)) fail(cName + " is defined twice in project.");
					allClassNames.add(cName);
				}
				bodyBuilder.append("\n" + unit.body);
				javaImports.addAll(unit.javaImports);
				importsToManage.addAll(unit.projectImports);
				unit.added = true;
			}
		}
		
		// Imports (java/javax only)
		for (String imp : javaImports) {
			importBuilder.append("import " + imp + ";\n");
		}
		
		String contents = HEADER + importBuilder + bodyBuilder;
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(contents), null);
		System.out.println("Target source copied to clipboard (" + (contents.length() / 1024) + " KB)");
	}

	private static List<Unit> scan(List<File> dirs) {
		List<Unit> units = new ArrayList<>();
		dirs.stream()
		.map(dir -> dir.toPath())
		.forEach(path -> {
			try {
				Files.walk(path)
					.filter(p -> p.toString().toLowerCase().endsWith(".java"))
					.map(p -> new Unit(p))
					.forEach(u -> units.add(u));
			} catch (IOException | RuntimeException ex) {
				fail("Could not read " + path + "." , ex);
			}
		});
		return units;
	}
}
