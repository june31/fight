package pro.juin.aggro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class AggroProperties extends AggroCommon {
	
	private static Properties props = new Properties();
	private static String mainClassSimpleName; // may contain '_'
	private static String mainClassFinalName; // stripped from '_'
	private static String pack;
	private static boolean clipboardMode;
	private static boolean fileMode;
	private static File outputFile;
	private static List<File> srcMainList = new ArrayList<>();
	private static List<File> srcAltList = new ArrayList<>();
	private static List<Constant> constantList = new ArrayList<>();
	
	/**
	 * Read "aggro.properties" and initialize properties.
	 */
	public static void init() {
		try (InputStream stream = new FileInputStream("aggro.properties")) {
			props.load(stream);
		} catch (IOException ex) {
			throw new Error("Could not load \"aggro.properties\".", ex);
		}

		mainClassSimpleName = getProperty("main.class");
		int underPos = mainClassSimpleName.indexOf('_');
		mainClassFinalName = underPos == -1 ? mainClassSimpleName : mainClassSimpleName.substring(0, underPos);
		pack = getOptionalProperty("pack");
		clipboardMode = getProperty("output.clipboard").equalsIgnoreCase("true");
		fileMode = getProperty("output.file").equalsIgnoreCase("true");
		if (fileMode) {
			outputFile = new File(getProperty("output.path"));
		}
		props.forEach((keyObj, valueObj) -> {
			String key = keyObj.toString();
			String value = valueObj.toString().strip();
			if (key.startsWith("sources.main.")) {
				File srcDir = new File(value);
				if (!srcDir.isDirectory()) fail("Property " + key + " points to a non-existing folder.");
				srcMainList.add(srcDir);
			} else if (key.startsWith("sources.alt.")) {
				File srcDir = new File(value);
				if (!srcDir.isDirectory()) fail("Property " + key + " points to a non-existing folder.");
				srcAltList.add(srcDir);
			} else {
				char first = key.charAt(0);
				if (first >= 'A' && first <= 'Z') constantList.add(new Constant(key, value));
			}
		});
	}

	private static String getProperty(String key) {
		String result = props.getProperty(key);
		if (result == null) fail("Missing \"" + key + "\" key in aggro.properties.");
		return result.strip();
	}
	
	private static String getOptionalProperty(String key) {
		String result = props.getProperty(key);
		return result == null ? null : result.strip();
	}

	/**
	 * Return the main class simple name.
	 * 
	 * @return the main class simple name
	 */
	public static String getMainClassSimpleName() {
		return mainClassSimpleName;
	}

	/**
	 * Return the main class final name.
	 * 
	 * @return the main class final name
	 */
	public static String getMainClassFinalName() {
		return mainClassFinalName;
	}
	
	/**
	 * Return the optional package declaration.
	 * 
	 * @return the optional package declaration
	 */
	public static String getPack() {
		return pack;
	}
	
	/**
	 * Return true if output shall be copied to clipboard.
	 * 
	 * @return true if output shall be copied to clipboard
	 */
	public static boolean isClipboardMode() {
		return clipboardMode;
	}

	/**
	 * Return true if output shall be copied to file.
	 * 
	 * @return true if output shall be copied to file
	 */
	public static boolean isFileMode() {
		return fileMode;
	}

	/**
	 * Return the output file.
	 * 
	 * @return the output file
	 */
	public static File getOutputFile() {
		return outputFile;
	}
	/**
	 * Return the main source directories.
	 * 
	 * @return the main source directories
	 */
	public static List<File> getMainSourceDirectories() {
		return srcMainList;
	}

	/**
	 * Return the alternate source directories.
	 * 
	 * @return the alternate source directories
	 */
	public static List<File> getAltSourceDirectories() {
		return srcAltList;
	}

	/**
	 * Return the Constant list.
	 * 
	 * @return the Constant list
	 */
	public static List<Constant> getConstantList() {
		return constantList;
	}
}
