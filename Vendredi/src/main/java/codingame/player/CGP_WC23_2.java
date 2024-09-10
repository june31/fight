package codingame.player;

public class CGP_WC23_2 {

	/**
	 * @param firstA A first sentence (containing every letter from A to Z) in the alphabet A
	 * @param firstB The same sentence as firstA, translated in the alphabet B
	 * @param secondB A second sentence (containing every letter from A to Z) in the alphabet B
	 * @param secondC The same sentence as secondB, translated in the alphabet C
	 * @param secretC The secret message in alphabet C to be deciphered
	 * @return The message secretC translated to the alphabet A
	 */
	public static String decrypt(String aa1, String bb1, String bb2, String cc2, String x) {
		char[] a1 = aa1.toCharArray();
		char[] b1 = bb1.toCharArray();
		char[] b2 = bb2.toCharArray();
		char[] c2 = cc2.toCharArray();
		char[] aa = new char[26];
		for (int i = 0; i < a1.length; i++) {
			char c = a1[i];
			if (c == ' ') continue;
			aa[b1[i] - 'A'] = c;
		}
		char[] bb = new char[26];
		for (int i = 0; i < b2.length; i++) {
			char c = b2[i];
			if (c == ' ') continue;
			bb[c2[i] - 'A'] = c;
		}
		StringBuilder sb = new StringBuilder();
		for (char c : x.toCharArray()) {
			if (c == ' ') sb.append(' ');
			else sb.append(aa[bb[c-'A']-'A']);
		}
		return sb.toString();
	}

	/**
	 * Try a solution
	 * @param message The message secretC translated to the alphabet A
	 */
	public static void main(String[] args) {
		System.out.println(decrypt(
				"JACKIE WILL BUDGET FOR THE MOST EXPENSIVE ZOOLOGY EQUIPMENT",
				"DAOFJK XJCC PVQNKW STH WUK RTBW KYZKLBJGK MTTCTNI KEVJZRKLW",
				"ZELDA MIGHT FIX THE JOB GROWTH PLANS VERY QUICKLY ON MONDAY",
				"XSFIY TANBD QAO DBS MPR NJPLDB GFYCK USJW HEAZVFW PC TPCIYW",
				"RDJV VZVJVFLR YBV UFRLYGZV M FVVH LD MFNVRLMCYLV JDBV"));
	}
	/* Ignore and do not change the code above */
}


