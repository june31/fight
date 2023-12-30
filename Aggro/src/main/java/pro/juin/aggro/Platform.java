package pro.juin.aggro;

import java.io.File;

public abstract class Platform extends AggroCommon {

	/**
	 * Retrieve current platform
	 */
	public static void process() {
		File pluginDir = new File("src/main/java/pro/juin/aggro/plugins");
		if (!pluginDir.isDirectory()) fail("Could not find the plugin dir: " + pluginDir.getAbsolutePath());
		for (String pluginFileName : pluginDir.list()) {
			try {
				Class<?> clazz = Class.forName("pro.juin.aggro.plugins." + pluginFileName.substring(0, pluginFileName.length() - 5));
				Platform plugin = (Platform) clazz.getConstructor().newInstance();
				if (plugin.isCompatible(mainClassInitialName)) {
					if (platform != null) {
						fail(mainClassInitialName + " class is compatible with 2 plugins: " + platform.getName() + " and " + plugin.getName() + ".");
					}
					platform = plugin;
				}
			} catch (Exception ex) {
				fail("Could not instantiate plugin: " + pluginFileName + ".", ex);
			}
		}
		if (platform == null) fail("No plugin is compatible with " + mainClassInitialName + " class.");
		System.out.println(mainClassInitialName + ": " + platform.getName() + " platform detected");
	}

	/**
	 * Return true if this plug-in is compatible with the main unit.
	 * 
	 * @param name the main unit name, such as "XX_YY.java"
	 * @return true if this plug-in is compatible
	 */
	private boolean isCompatible(String name) {
		int pos = name.indexOf('_');
		if (pos == -1) return false;
		return isLowerCasePrefixCompatible(name.substring(0, pos).toLowerCase());
	}
	
	/**
	 * Return the platform name.
	 * 
	 * @return the platform name 
	 */
	public abstract String getName();
	
	/**
	 * Return the target class name.
	 * 
	 * @return the target class name
	 */
	public abstract String getTargetClassName();

	/**
	 * Return the target package name. Null if no package (default)
	 * 
	 * @return the target package name
	 */
	public String getTargetPackageName() {
		return null;
	}

	/**
	 * Return true if this plug-in is compatible with the provided prefixes.
	 * 
	 * @param lowerCasePrefix the provided lower case prefix
	 * @return true if this plug-in is compatible
	 */
	protected abstract boolean isLowerCasePrefixCompatible(String lowerCasePrefix);

	/**
	 * Shall the result be stored in the clipboard (default = true)?
	 * 
	 * @return true if the result be stored in the clipboard
	 */
	public boolean clipboardMode() {
		return true;
	}
	
	/**
	 * Shall the scanner be replaced according to the rule in aggro.properties (default = true)?
	 * 
	 * @return true if the scanner shall be replaced
	 */
	public boolean scanReplaceMode() {
		return true;
	}
	
	/**
	 * Is the main class public? If not, it is package protected (default = true).
	 * 
	 * @return true if the main class is public
	 */
	public boolean isPublic() {
		return true;
	}
}
