package currentCG;

import tools.Test;

public class CGS_TEst {
	public static void main(String[] args) {
		String[] t = new String[] { "1", "3", "4", "2" };
		System.out.println(Test.max(t, x -> Integer.parseInt(x)));
	}
}
