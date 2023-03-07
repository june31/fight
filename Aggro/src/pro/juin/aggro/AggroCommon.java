package pro.juin.aggro;

import java.io.File;
import java.nio.file.Path;

class AggroCommon {
	
	protected static File mainSourceDir;
	protected static String mainClassInitialName;
	protected static Platform platform;
	
	protected static void fail(String text) {
		System.err.println(text);
		System.exit(-1);
	}
	
	protected static void fail(String text, Throwable t) {
		System.err.println(text);
		t.printStackTrace();
		System.exit(-1);
	}
	
	protected static void fail(File file, String text) {
		System.err.println("In file " + file + ": " + text);
		System.exit(-1);
	}

	protected static void fail(Path path, String text) {
		System.err.println("In file " + path + ": " + text);
		System.exit(-1);
	}
}
