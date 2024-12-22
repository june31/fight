package tools.strings;

public class SB {
    private StringBuilder sb;

    public SB() {
        this.sb = new StringBuilder();
    }
    
    public SB(int capacity) {
        this.sb = new StringBuilder(capacity);
    }

    public SB(String str) {
        this.sb = new StringBuilder(str);
    }

	public void space() {
        space(" ");
    }
	
	public void space(char c) {
		space("" + c);
	}
	
	public void space(String s) {
		if (sb.length() > 0) sb.append(s);
	}

    public SB spaceAndAppend(String space, Object obj) {
    	space(space);
        sb.append(obj);
        return this;
    }

    public SB spaceAndAppend(Object obj) {
    	space();
        sb.append(obj);
        return this;
    }

    public SB append(Object obj) {
        sb.append(obj);
        return this;
    }

	public SB a(Object obj) {
		sb.append(obj);
		return this;
	}
	
    public SB append(String str) {
        sb.append(str);
        return this;
    }

    public SB a(String str) {
        sb.append(str);
        return this;
    }

    public SB append(StringBuffer sb) {
        this.sb.append(sb);
        return this;
    }

    public SB a(StringBuffer sb) {
        this.sb.append(sb);
        return this;
    }

    public SB append(CharSequence s) {
        sb.append(s);
        return this;
    }

    public SB append(CharSequence s, int start, int end) {
        sb.append(s, start, end);
        return this;
    }

    public SB append(char[] str) {
        sb.append(str);
        return this;
    }

    public SB append(char[] str, int offset, int len) {
        sb.append(str, offset, len);
        return this;
    }

    public SB append(boolean b) {
        sb.append(b);
        return this;
    }

    public SB a(boolean b) {
        sb.append(b);
        return this;
    }

    public SB append(char c) {
        sb.append(c);
        return this;
    }

    public SB a(char c) {
        sb.append(c);
        return this;
    }

    public SB append(int i) {
        sb.append(i);
        return this;
    }

    public SB a(int i) {
        sb.append(i);
        return this;
    }

    public SB c(int i) {
        sb.append((char) i);
        return this;
    }

    public SB append(long lng) {
        sb.append(lng);
        return this;
    }

	public SB a(long lng) {
		sb.append(lng);
		return this;
	}
	
    public SB append(float f) {
        sb.append(f);
        return this;
    }

    public SB append(double d) {
        sb.append(d);
        return this;
    }

    public SB a(double d) {
        sb.append(d);
        return this;
    }
    
    public SB appendCodePoint(int codePoint) {
        sb.appendCodePoint(codePoint);
        return this;
    }

    public SB delete(int start, int end) {
        sb.delete(start, end);
        return this;
    }

    public SB deleteCharAt(int index) {
        sb.deleteCharAt(index);
        return this;
    }

    public SB replace(int start, int end, String str) {
        sb.replace(start, end, str);
        return this;
    }

    public SB insert(int index, char[] str, int offset, int len) {
        sb.insert(index, str, offset, len);
        return this;
    }

    public SB insert(int offset, Object obj) {
        sb.insert(offset, obj);
        return this;
    }

    public SB insert(int offset, String str) {
        sb.insert(offset, str);
        return this;
    }

    public SB insert(int offset, char[] str) {
        sb.insert(offset, str);
        return this;
    }

    public SB insert(int dstOffset, CharSequence s) {
        sb.insert(dstOffset, s);
        return this;
    }

    public SB insert(int dstOffset, CharSequence s, int start, int end) {
        sb.insert(dstOffset, s, start, end);
        return this;
    }

    public SB insert(int offset, boolean b) {
        sb.insert(offset, b);
        return this;
    }

    public SB insert(int offset, char c) {
        sb.insert(offset, c);
        return this;
    }

    public SB insert(int offset, int i) {
        sb.insert(offset, i);
        return this;
    }

    public SB insert(int offset, long l) {
        sb.insert(offset, l);
        return this;
    }

    public SB insert(int offset, float f) {
        sb.insert(offset, f);
        return this;
    }

    public SB insert(int offset, double d) {
        sb.insert(offset, d);
        return this;
    }

    public int indexOf(String str) {
        return sb.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return sb.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return sb.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return sb.lastIndexOf(str, fromIndex);
    }

    public SB reverse() {
        sb.reverse();
        return this;
    }

    public int length() {
        return sb.length();
    }

    public int capacity() {
        return sb.capacity();
    }

    public void ensureCapacity(int minimumCapacity) {
        sb.ensureCapacity(minimumCapacity);
    }

    public void trimToSize() {
        sb.trimToSize();
    }

    public void setLength(int newLength) {
        sb.setLength(newLength);
    }

    public char charAt(int index) {
        return sb.charAt(index);
    }

    public int codePointAt(int index) {
        return sb.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return sb.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return sb.codePointCount(beginIndex, endIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return sb.offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        sb.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public void setCharAt(int index, char ch) {
        sb.setCharAt(index, ch);
    }

    public String substring(int start) {
        return sb.substring(start);
    }

    public CharSequence subSequence(int start, int end) {
        return sb.subSequence(start, end);
    }

    public String substring(int start, int end) {
        return sb.substring(start, end);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
    
	public boolean isEmpty() {
		return sb.length() == 0;
	}
	
	public boolean isBlank() {
		return sb.toString().trim().isEmpty();
	}

	public String s() {
		return sb.toString();
	}

	public void println() {
		System.out.println(this);
	}
	
	public void print() {
		System.out.print(this);
	}
}
