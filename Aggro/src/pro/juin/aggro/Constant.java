package pro.juin.aggro;

class Constant extends AggroCommon {
	String className;
	String name;
	String value;

	Constant(String key, String value) {
		int pos = key.indexOf('.');
		if (pos == -1) fail("Invalid 'constant' key in aggro.properties: " + key);
		className = key.substring(0, pos);
		name = key.substring(pos + 1);
		if (!name.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$")) fail("Invalid 'constant' key in aggro.properties: " + key);
		this.value = value;
	}

	String replace(String text) {
		String match = text.lines()
			.filter(line -> (line.contains(" static ") || line.strip().startsWith("static "))
					&& line.contains(" " + name + " ="))
			.findFirst()
			.orElse(null);
		if (match == null) fail();
		return replace(text, match);
	}

	private String replace(String text, String line) {
		String decl = " " + name + " =";
		int begin = line.indexOf(decl) + decl.length();
		int end = line.lastIndexOf(';');
		if (end == -1) fail();
		String newLine = line.substring(0, begin) + " " + value + line.substring(end);
		return replaceFirst(text, line, newLine);
	}

	private void fail() {
		fail("Could not find match for " + name + " constant in " + className + " class.");
	}

	private static String replaceFirst(String txt, String from, String to) {
		int pos = txt.indexOf(from);
		return txt.substring(0, pos) + to + txt.substring(pos + from.length(), txt.length());
	}
}
