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

	private static List<File> toolDirectories = new ArrayList<>();
	private static File outputDir;
	private static String scannerClassName;
	private static String scannerConstantName;
	private static String scannerConstantValue;
	
	/**
	 * Initialize properties by reading "aggro.properties" and parsing the provided file.
	 */
	public static void init() {
		props = new Properties();
		try (InputStream stream = new FileInputStream("aggro.properties")) {
			props.load(stream);
		} catch (IOException ex) {
			fail("Could not load \"aggro.properties\".", ex);
		}

		if (props.size() != 3) fail("""
				File aggro.properties shall contain exactly 3 keys:
				- tools
				- output
				- scanner
				""");
		
		String[] rawTools = getProperty("tools").split(",");
		for (String rawTool : rawTools) {
			File dir = new File(rawTool.strip());
			if (!dir.isDirectory()) fail("In file aggro.properties, tools shall refer to a list of existing source directories.");
			toolDirectories.add(dir);
		}
		
		outputDir = new File(getProperty("output"));
		if (!outputDir.isDirectory()) fail("In file aggro.properties, output shall refer to an existing directory.");
	
		String[] rawScanner = getProperty("scanner").split(",", 3);
		if (rawScanner.length != 3) fail("In file aggro.properties, scanner value format is incorrect.");
		scannerClassName = rawScanner[0].strip();
		scannerConstantName = rawScanner[1].strip();
		scannerConstantValue = rawScanner[2].strip();
	}

	private static String getProperty(String key) {
		String result = props.getProperty(key);
		if (result == null) fail("Missing \"" + key + "\" key in aggro.properties.");
		return result.strip();
	}

	/**
	 * Return the output dir.
	 * 
	 * @return the output dir
	 */
	public static File getOutputDir() {
		return outputDir;
	}

	/**
	 * Return the tool source directories.
	 * 
	 * @return the tool source directories
	 */
	public static List<File> getToolDirectories() {
		return toolDirectories;
	}
	
	/**
	 * Return the scanner class name.
	 * 
	 * @return the scanner class name
	 */
	public static String getScannerClassName() {
		return scannerClassName;
	}
	
	/**
	 * Return the scanner constant name.
	 * 
	 * @return the scanner constant name
	 */
	public static String getScannerConstantName() {
		return scannerConstantName;
	}

	/**
	 * Return the scanner constant value.
	 * 
	 * @return the scanner constant value
	 */
	public static String getScannerConstantValue() {
		return scannerConstantValue;
	}
}

