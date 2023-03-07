package pro.juin.aggro;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
	private static final String LAST_UNIT_FILENAME = "AggroClass.txt";

	public static void main(String[] args) {
		checkArgs(args);
		AggroProperties.init();
		manageArgs(args);
		Platform.process();

		// Scan main sources.
		List<Unit> sources = scan(mainSourceDir);
		if (Unit.main == null) fail(mainClassInitialName + " main class not found.");

		// Scan tool sources.
		List<Unit> tools = new ArrayList<>();
		for (File toolDir : AggroProperties.getToolDirectories()) tools.addAll(scan(toolDir));
		
		Map<String, Unit> longNameToUnit = new HashMap<>();
		for (Unit unit : tools) {
			longNameToUnit.put(unit.pack + '.' + unit.className, unit);
		}

		StringBuilder importBuilder = new StringBuilder();
		StringBuilder bodyBuilder = new StringBuilder();

		Set<String> javaImports = new TreeSet<>();
		Set<String> projectImports = new TreeSet<>();

		// Body: main class
		bodyBuilder.append("\n" + Unit.main.body);
		javaImports.addAll(Unit.main.javaImports);
		projectImports.addAll(Unit.main.projectImports);

		// Body: support classes
		for (Unit unit : sources) {
			if (unit != Unit.main && unit.body != null) { // body is null in skipped classes (_) 
				bodyBuilder.append("\n" + unit.body);
				javaImports.addAll(unit.javaImports);
				projectImports.addAll(unit.projectImports);
			}
		}

		// Body: dependencies
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

		// Optional package declaration
		String pack = platform.getTargetPackageName();
		if (pack != null) {
			importBuilder.append("package " + pack + ";\n\n");
		}

		// Imports (java/javax only)
		for (String imp : javaImports) {
			importBuilder.append("import " + imp + ";\n");
		}

		String contents = HEADER + importBuilder + bodyBuilder;
		System.out.println("Target source successfully generated (" + (contents.length() / 1024) + " KB)");

		if (platform.clipboardMode()) {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection(contents), null);
			System.out.println("Target source copied to clipboard.");
		}

		Path output = Path.of(AggroProperties.getOutputDir().getPath(), platform.getTargetClassName() + ".java");
		try {
			Files.writeString(output, contents);
			System.out.println("Target source saved to " + output + ".");
		} catch (IOException ex) {
			fail("Target source could not be saved to " + output + ".", ex);
		}
	}

	private static void checkArgs(String[] args) {
		if (args.length != 1) fail("""
				Usage:
				1) Create an External Tool configuration
				Location: <javaw.exe path>
				Working directory: ${workspace_loc:/Aggro}
				Arguments: -classpath ${workspace_loc:/Aggro/bin} pro.juin.aggro.Aggro ${selected_resource_loc}
				2) Assign Preferences > General > Keys > "Run Last Launched External Tool" to any shortcut, such as F12.
				3) Edit "aggro.properties"
				""");
	}

	private static void manageArgs(String[] args) {
		String path = args[0].replace('\\', '/');
		File file = new File(path);
		String name = file.getName();
		mainSourceDir = file.getParentFile();
		int firstPos = name.indexOf('.');
		String javaPath = null;
		mainClassInitialName = name.substring(0, firstPos);
		if (firstPos > 0) {
			javaPath = mainSourceDir.getPath() + '/' + mainClassInitialName + ".java";
			if (!new File(javaPath).isFile()) javaPath = null;
		}
		if (!mainClassInitialName.contains("_")) javaPath = null;
		String lastPath = AggroProperties.getOutputDir() + "/" + LAST_UNIT_FILENAME;
		boolean hasRead = false;
		if (javaPath == null) {
			try {
				javaPath = Files.readString(Path.of(lastPath)).strip();
				hasRead = true;
				if (!new File(javaPath).isFile()) {
					fail("Main class cannot be retrieved. Please select main class and run Aggro again.");
				}
				int p1 = javaPath.lastIndexOf('/');
				int p2 = javaPath.lastIndexOf('.');
				mainClassInitialName = javaPath.substring(p1 + 1, p2);
				mainSourceDir = new File(javaPath).getParentFile();
			} catch (IOException ex) {
				fail("Main class cannot be retrieved, and also \"" + lastPath + "\" cannot be read. Please select main class and run Aggro again.");
			}
		}
		if (!hasRead) {
			try {
				Files.write(Path.of(lastPath), javaPath.getBytes());
			} catch (IOException ex) {
				System.err.println("Warning: Could not save Class name in " + lastPath + " for future use: " + ex.getMessage());
			}
		}
	}

	private static List<Unit> scan(File dir) {
		List<Unit> units = new ArrayList<>();
		Path path = dir.toPath();
		try {
			Files.walk(path)
			.filter(p -> p.toString().toLowerCase().endsWith(".java"))
			.map(p -> new Unit(p))
			.forEach(u -> units.add(u));
		} catch (IOException | RuntimeException ex) {
			fail("Could not read " + path + "." , ex);
		}
		return units;
	}
}
