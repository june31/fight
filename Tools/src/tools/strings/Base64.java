package tools.strings;

public class Base64 {
	public static String encode(String s) {
		return encode(s.getBytes());
	}

	private static String encode(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i += 3) {
			int b = (bytes[i] & 0xFC) >> 2;
			sb.append(toChar(b));
			b = (bytes[i] & 0x03) << 4;
			if (i + 1 < bytes.length) {
				b |= (bytes[i + 1] & 0xF0) >> 4;
				sb.append(toChar(b));
				b = (bytes[i + 1] & 0x0F) << 2;
				if (i + 2 < bytes.length) {
					b |= (bytes[i + 2] & 0xC0) >> 6;
					sb.append(toChar(b));
					b = bytes[i + 2] & 0x3F;
					sb.append(toChar(b));
				} else {
					sb.append(toChar(b));
					sb.append('=');
				}
			} else {
				sb.append(toChar(b));
				sb.append("==");
			}
		}
		return sb.toString();
	}

	public static String decode(String s) {
		byte[] bytes = new byte[s.length() * 3 / 4];
		int i = 0;
		for (int j = 0; j < s.length(); j += 4) {
			int b = toByte(s.charAt(j)) << 2;
			b |= toByte(s.charAt(j + 1)) >> 4;
			bytes[i++] = (byte) b;
			if (s.charAt(j + 2) != '=') {
				b = toByte(s.charAt(j + 1)) << 4;
				b |= toByte(s.charAt(j + 2)) >> 2;
				bytes[i++] = (byte) b;
				if (s.charAt(j + 3) != '=') {
					b = toByte(s.charAt(j + 2)) << 6;
					b |= toByte(s.charAt(j + 3));
					bytes[i++] = (byte) b;
				}
			}
		}
		return new String(bytes, 0, i);
	}
	
	private static Object toChar(int b) {
        if (b < 26) return (char) (b + 'A');
        if (b < 52) return (char) (b - 26 + 'a');
        if (b < 62) return (char) (b - 52 + '0');
        if (b == 62) return '+';
        return '/';
	}
	
	private static int toByte(char charAt) {
		if (charAt >= 'A' && charAt <= 'Z')
			return charAt - 'A';
		if (charAt >= 'a' && charAt <= 'z')
			return charAt - 'a' + 26;
		if (charAt >= '0' && charAt <= '9')
			return charAt - '0' + 52;
		if (charAt == '+')
			return 62;
		return 63;
	}
}
