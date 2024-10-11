package tools.scanner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ScanHelper {

	public static InputStream retrieveInputStream() {
		StackTraceElement[] st = Thread.currentThread().getStackTrace();
		String entryPoint = st[st.length - 1].getClassName();
		int pos = entryPoint.lastIndexOf('.') + 1;
		String start = new File("src/main/java").exists() ? "src/main/java/" : "src/";
		String path = start + entryPoint.substring(0, pos).replace('.', '/');
		String name = entryPoint.substring(pos) + ".txt";
		try {
			return new BufferedInputStream(new FileInputStream(path + name));
		} catch (FileNotFoundException ex) {
			File file = new File(path + name);
			System.err.println("File " + file.getAbsolutePath() + " does not exist.");
			try {
				if (file.createNewFile()) {
					System.err.println("It has been created on the file system. You may need to refresh your IDE.");
				} else {
					System.err.println("It could not be created on the file system!");
				}
			} catch (IOException ex2) {
				System.err.println("It could not be created on the file system! Reason:\n" + ex2.getMessage());
			}
			System.exit(1);
			return null;
		}
	}
}
