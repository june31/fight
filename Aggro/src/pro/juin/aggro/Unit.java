package pro.juin.aggro;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Unit extends AggroCommon {

	static Unit main;
	
	String pack = ""; // default package by default
	String className; // Does not contain '_'
	String body;
	Collection<String> javaImports = new ArrayList<>();
	Collection<String> projectImports = new ArrayList<>();
	
	List<String> classNames = new ArrayList<>();
	
	boolean added = false;

	Unit(Path path) {
		try {
			parse(path);
		} catch (IOException | RuntimeException ex) {
			fail("Could not parse " + path + ".", ex);
		}
	}

	private void parse(Path path) throws IOException {
		StringBuilder builder = new StringBuilder();
		int state = 0; // 0: header, 1: main class, 2: body

		try (BufferedReader stream = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1)) {
			String line;
			while ((line = stream.readLine()) != null) {
				String sLine = line.strip();
				String wLine = " " + sLine;

				// Header
				if (state == 0) {
					if (line.contains("class ") || line.contains("interface ") || line.contains("enum ")
							|| line.contains("record ") || line.startsWith("@")) {
						state = 1;
					} else {
						if (sLine.startsWith("package ")) {
							int scPos = sLine.indexOf(';');
							if (scPos == -1) fail(path, "Unrecognized package definition.");
							pack = sLine.substring(8, scPos).strip();
						} else if (sLine.startsWith("import ")) {
							int scPos = sLine.indexOf(';');
							if (scPos == -1) fail(path, "Unrecognized import definition: " + sLine);
							manageImport(javaImports, projectImports, path, sLine.substring(7, scPos).strip());
						}
						continue;
					}
				}				

				// Main class: check if class name contains a _ sign.
				if (state == 1) {
					if (wLine.contains(" class ") || wLine.contains(" interface ") || wLine.contains(" enum ") || wLine.contains(" record ")) {
						className = getName(wLine);
						if (className.contains("_")) {
							if (!className.equals(mainClassInitialName)) return; // skip unit.
							String newClassName = platform.getTargetClassName();
							line = line.replace(className, newClassName);
							className = newClassName;
						}
						state = 2;
					}
				}

				// Main appender
				if (wLine.contains(" class ") || wLine.contains(" interface ") || wLine.contains(" enum ") || wLine.contains(" record ")) {
					// Get Class/interface/... name
					String cName = getName(wLine);
					classNames.add(cName);

					if (cName.equals(AggroProperties.mainClassInitialName)) {
						main = this;
					} else {
						line = line.replace("public class ", "class ")
								.replace("public final class ", "final class ")
								.replace("public abstract class ", "abstract class ")
								.replace("public interface ", "interface ")
								.replace("public enum ", "enum ")
								.replace("public record ", "record ");
					}
				}
				builder.append(line + "\n");
			}
		}

		body = builder.toString();
		if (className.equals(AggroProperties.getScannerClassName())) body = replaceScanner(body);
	}

	private String getName(String line) {
		int clPos = line.indexOf(" class ");
		int len = 7;
		if (clPos == -1) {
			clPos = line.indexOf(" interface ");
			len = 11;
		}
		if (clPos == -1) {
			clPos = line.indexOf(" enum ");
			len = 6;
		}
		if (clPos == -1) {
			clPos = line.indexOf(" record ");
			len = 8;
		}
		String name = line.substring(clPos + len);
		final int M = 0x7FFFFFFF;
		int endPos = min(name.indexOf('<') & M, name.indexOf(' ') & M, name.indexOf('{') & M);
		if (endPos != M) name = name.substring(0, endPos);

		return name.strip();
	}
	
	private static int min(int a, int b, int c) {
		return Math.min(a, Math.min(b, c));
	}

	private void manageImport(Collection<String> localJavaImports, Collection<String> localProjectImports, Path path, String text) {
		if (text.startsWith("static ")) fail(path, "Static imports are not supported.");
		if (text.indexOf('_') != -1) fail(path, "Classes with _ sign shall not be imported.");
		int dotPos = text.lastIndexOf('.');
		if (dotPos != -1) {
			String pack = text.substring(0, dotPos);
			if (pack.startsWith("java.") || pack.startsWith("javax.") || pack.startsWith("com.google.gson")) {
				localJavaImports.add(pack + ".*");
				return;
			}
		}
		if (text.endsWith("*")) fail(path, "Project related 'star imports' are not supported.");
		localProjectImports.add(text);
	}
	
	private String replaceScanner(String text) {
		String className = AggroProperties.getScannerClassName();
		String name = AggroProperties.getScannerConstantName();
		String value = AggroProperties.getScannerConstantValue();
		String match = text.lines()
			.filter(line -> (line.contains(" static ") || line.strip().startsWith("static ")) && line.contains(" " + name + " ="))
			.findFirst()
			.orElse(null);
		if (match == null) fail("Could not find match for " + name + " constant in " + className + " scanner class.");
		String decl = " " + name + " =";
		int begin = match.indexOf(decl) + decl.length();
		int end = match.lastIndexOf(';');
		if (end == -1) fail("Missing ';' while trying to update " + className + " scanner class.");
		String newLine = match.substring(0, begin) + " " + value + match.substring(end);
		int pos = text.indexOf(match);
		return text.substring(0, pos) + newLine + text.substring(pos + match.length(), text.length());
	}
	
	@Override
	public String toString() {
		return className;
	}
}
