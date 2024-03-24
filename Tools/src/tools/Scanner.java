package tools;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import tools.scanner.ScanHelper;

public class Scanner implements Closeable, Iterator<String> {
	private java.util.Scanner scanner;

	public Scanner(InputStream unused) {
		this();
	}

	public Scanner(Reader unused) {
		this();
	}
	
	public Scanner() {
		scanner = new java.util.Scanner(ScanHelper.retrieveInputStream());
		scanner.useLocale(Locale.US); // '.' should be the decimal separator, not ','.
	}

	public Scanner(String path) {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement elt = stacktrace[stacktrace.length - 1]; // Main() stacktrace;
		String className = elt.getClassName();
		String dirName = className.contains(".") ? "src/" + className.substring(0, className.lastIndexOf('.')).replace('.', '/') : "src";
		File inputFile = new File(dirName, path);
		try {
			scanner = new java.util.Scanner(new FileInputStream(inputFile));
			scanner.useLocale(Locale.US); // '.' should be the decimal separator, not ','.
		} catch (IOException ex) {
			System.err.println("Could not create scanner from file " + inputFile.getAbsolutePath());
			System.exit(1);
		}
	}

	public void close() {
		scanner.close();
	}

	public Pattern delimiter() {
		return scanner.delimiter();
	}

	public boolean equals(Object obj) {
		return scanner.equals(obj);
	}

	public String findInLine(Pattern pattern) {
		return scanner.findInLine(pattern);
	}

	public String findInLine(String pattern) {
		return scanner.findInLine(pattern);
	}

	public String findWithinHorizon(Pattern pattern, int horizon) {
		return scanner.findWithinHorizon(pattern, horizon);
	}

	public String findWithinHorizon(String pattern, int horizon) {
		return scanner.findWithinHorizon(pattern, horizon);
	}

	public void forEachRemaining(Consumer<? super String> arg0) {
		scanner.forEachRemaining(arg0);
	}

	public boolean hasNext() {
		return scanner.hasNext();
	}

	public boolean hasNext(Pattern pattern) {
		return scanner.hasNext(pattern);
	}

	public boolean hasNext(String pattern) {
		return scanner.hasNext(pattern);
	}

	public boolean hasNextBigDecimal() {
		return scanner.hasNextBigDecimal();
	}

	public boolean hasNextBigInteger() {
		return scanner.hasNextBigInteger();
	}

	public boolean hasNextBigInteger(int radix) {
		return scanner.hasNextBigInteger(radix);
	}

	public boolean hasNextBoolean() {
		return scanner.hasNextBoolean();
	}

	public boolean hasNextByte() {
		return scanner.hasNextByte();
	}

	public boolean hasNextByte(int radix) {
		return scanner.hasNextByte(radix);
	}

	public boolean hasNextDouble() {
		return scanner.hasNextDouble();
	}

	public boolean hasNextFloat() {
		return scanner.hasNextFloat();
	}

	public boolean hasNextInt() {
		return scanner.hasNextInt();
	}

	public boolean hasNextInt(int radix) {
		return scanner.hasNextInt(radix);
	}

	public boolean hasNextLine() {
		return scanner.hasNextLine();
	}

	public boolean hasNextLong() {
		return scanner.hasNextLong();
	}

	public boolean hasNextLong(int radix) {
		return scanner.hasNextLong(radix);
	}

	public boolean hasNextShort() {
		return scanner.hasNextShort();
	}

	public boolean hasNextShort(int radix) {
		return scanner.hasNextShort(radix);
	}

	public int hashCode() {
		return scanner.hashCode();
	}

	public IOException ioException() {
		return scanner.ioException();
	}

	public Locale locale() {
		return scanner.locale();
	}

	public MatchResult match() {
		return scanner.match();
	}

	public String next() {
		return scanner.next();
	}

	public String next(Pattern pattern) {
		return scanner.next(pattern);
	}

	public String next(String pattern) {
		return scanner.next(pattern);
	}

	public BigDecimal nextBigDecimal() {
		return scanner.nextBigDecimal();
	}

	public BigInteger nextBigInteger() {
		return scanner.nextBigInteger();
	}

	public BigInteger nextBigInteger(int radix) {
		return scanner.nextBigInteger(radix);
	}

	public boolean nextBoolean() {
		return scanner.nextBoolean();
	}

	public byte nextByte() {
		return scanner.nextByte();
	}

	public byte nextByte(int radix) {
		return scanner.nextByte(radix);
	}

	public double nextDouble() {
		return scanner.nextDouble();
	}

	public float nextFloat() {
		return scanner.nextFloat();
	}

	public int nextInt() {
		return scanner.nextInt();
	}

	public int nextInt(int radix) {
		return scanner.nextInt(radix);
	}

	public String nextLine() {
		return scanner.nextLine();
	}

	public long nextLong() {
		return scanner.nextLong();
	}

	public long nextLong(int radix) {
		return scanner.nextLong(radix);
	}

	public short nextShort() {
		return scanner.nextShort();
	}

	public short nextShort(int radix) {
		return scanner.nextShort(radix);
	}

	public int radix() {
		return scanner.radix();
	}

	public void remove() {
		scanner.remove();
	}

	public java.util.Scanner reset() {
		return scanner.reset();
	}

	public java.util.Scanner skip(Pattern pattern) {
		return scanner.skip(pattern);
	}

	public java.util.Scanner skip(String pattern) {
		return scanner.skip(pattern);
	}

	public String toString() {
		return scanner.toString();
	}

	public java.util.Scanner useDelimiter(Pattern pattern) {
		return scanner.useDelimiter(pattern);
	}

	public java.util.Scanner useDelimiter(String pattern) {
		return scanner.useDelimiter(pattern);
	}

	public java.util.Scanner useLocale(Locale locale) {
		return scanner.useLocale(locale);
	}

	public java.util.Scanner useRadix(int radix) {
		return scanner.useRadix(radix);
	}
}
