package pro.juin.aggro;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Unit extends AggroCommon {

	static Unit main;
	static boolean foundMain = false;
	
	String pack = ""; // default package by default
	String className;
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

		try (BufferedReader stream = Files.newBufferedReader(path)) {
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

				// Main class: check if class name contains a $ sign.
				if (state == 1) {
					if (wLine.contains(" class ") || wLine.contains(" interface ") || wLine.contains(" enum ") || wLine.contains(" record ")) {
						int clPos = wLine.indexOf(" class ");
						if (clPos != -1) {
							className = wLine.substring(clPos + 7);
							int spPos = className.indexOf(' ');
							if (spPos == -1) {
								int brPos = className.indexOf('{');
								if (brPos == -1) spPos = className.length(); // Non-Egyptian brackets
								else spPos = brPos; // Missing space
							}
							className = className.substring(0, spPos);
							int dolPos = className.indexOf('$');
							if (dolPos != -1) {
								if (!className.equals(AggroProperties.getMainClassSimpleName())) return; // skip unit.
								String newClassName = className.substring(0, dolPos);
								line = line.replace(className, newClassName);
								className = newClassName;
							}
							
						}
						state = 2;
					}
				}

				// Main appender
				if (wLine.contains(" class ") || wLine.contains(" interface ") || wLine.contains(" enum ") || wLine.contains(" record ")) {
					// Get Class/interface/... name
					int clPos = wLine.indexOf(" class ");
					int len = 7;
					if (clPos == -1) {
						clPos = wLine.indexOf(" interface ");
						len = 11;
					}
					if (clPos == -1) {
						clPos = wLine.indexOf(" enum ");
						len = 6;
					}
					if (clPos == -1) {
						clPos = wLine.indexOf(" record ");
						len = 8;
					}
					String cName = wLine.substring(clPos + len);
					int spPos = cName.indexOf(' ');
					if (spPos == -1) {
						int brPos = cName.indexOf('{');
						if (brPos == -1) spPos = cName.length(); // Non-Egyptian brackets
						else spPos = brPos; // Missing space
					}
					cName = cName.substring(0, spPos);
					classNames.add(cName);

					if (cName.equals(AggroProperties.getMainClassSimpleName())) {
						foundMain = true;
					} else {
						line = line.replace("public class ", "class ")
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
		for (Constant c : AggroProperties.getConstantList()) {
			if (c.className.equals(className)) body = c.replace(body);
		}
	}

	private void manageImport(Collection<String> localJavaImports, Collection<String> localProjectImports, Path path, String text) {
		if (text.startsWith("static ")) fail(path, "Static imports are not supported.");
		if (text.indexOf('$') != -1) fail(path, "Classes with $ sign shall not be imported.");
		int dotPos = text.lastIndexOf('.');
		if (dotPos != -1) {
			String pack = text.substring(0, dotPos);
			if (pack.startsWith("java.") || pack.startsWith("javax.")) {
				localJavaImports.add(pack + ".*");
				return;
			}
		}
		if (text.endsWith("*")) fail(path, "Project related 'star imports' are not supported.");
		localProjectImports.add(text);
	}
}
