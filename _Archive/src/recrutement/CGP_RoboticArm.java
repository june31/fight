package recrutement;

import java.io.PrintStream;
import java.util.Scanner;

class CGP_RoboticArm {

    public static String solve(int pos, int[] b1, boolean claw) {
        int n = b1.length; 
        int[] b2 = new int[n];
        int s = 0;
        for (int x : b1) s += x;
        s += claw ? 1 : 0;
        for (int i = 0; i < n; i++) b2[i] = (s + n - 1 - i) / n;
        if (b2[pos] > b1[pos] && claw) return "PLACE";
        if (b2[pos] < b1[pos] && !claw) return "PICK";
        boolean okl = true;
        int left = 0;
        for (int i = 0; i < pos; i++) {
            left += b1[i] - b2[i];
            okl &= b1[i] == b2[i];
        }
        if (okl) return "RIGHT";
        if (left < 0 && !claw) return "RIGHT";
        return "LEFT";
    }

    /* Ignore and do not change the code below */
    @SuppressWarnings("resource")
	public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int clawPos = in.nextInt();
            boolean boxInClaw = in.nextInt() != 0;
            int stacks = in.nextInt();
            int[] boxes = new int[stacks];
            for (int i = 0; i < stacks; i++) {
                boxes[i] = in.nextInt();
            }
            PrintStream outStream = System.out;
            System.setOut(System.err);
            String action = solve(clawPos, boxes, boxInClaw);
            System.setOut(outStream);
            System.out.println(action);
        }
    }
    /* Ignore and do not change the code above */
}