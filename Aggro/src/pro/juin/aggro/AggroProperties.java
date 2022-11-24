package pro.juin.aggro;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class AggroProperties extends AggroCommon {
	
	private static Properties props = new Properties();
	private static String mainClassSimpleName; // may contain '$'
	private static String mainClassFinalName; // stripped from '$'
	private static List<File> srcMainList = new ArrayList<>();
	private static List<File> srcAltList = new ArrayList<>();
	private static List<Constant> constantList = new ArrayList<>();
	
	/**
	 * Read "aggro.properties" and initialize properties.
	 */
	public static void init() {
		try (InputStream stream = AggroProperties.class.getResourceAsStream("/aggro.properties")) {
			if (stream == null) throw new Error("aggro.properties could not be accessed. Please make sure the program is run in ${workspace_loc:Aggro}.");
			props.load(stream);
		} catch (IOException ex) {
			throw new Error("Could not load \"aggro.properties\"", ex);
		}

		mainClassSimpleName = getProperty("main.class");
		int dollarPos = mainClassSimpleName.indexOf('$');
		mainClassFinalName = dollarPos == -1 ? mainClassSimpleName : mainClassSimpleName.substring(0, dollarPos);

		props.forEach((keyObj, valueObj) -> {
			String key = keyObj.toString();
			String value = valueObj.toString().strip();
			if (key.equals("main.class")) return;
			if (key.startsWith("sources.main.")) {
				File srcDir = new File(value);
				if (!srcDir.isDirectory()) fail("Property " + key + " points to a non-existing folder.");
				srcMainList.add(srcDir);
			} else if (key.startsWith("sources.alt.")) {
				File srcDir = new File(value);
				if (!srcDir.isDirectory()) fail("Property " + key + " points to a non-existing folder.");
				srcAltList.add(srcDir);
			} else {
				constantList.add(new Constant(key, value));
			}
		});
	}

	private static String getProperty(String key) {
		String result = props.getProperty(key).strip();
		if (result == null) fail("Missing \"" + key + "\" key in aggro.properties.");
		return result;
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
