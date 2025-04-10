package spring25;

import java.util.Random;

public class _HexRandomList {
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int value = random.nextInt();
            System.out.print(String.format("0x%08X", value));
            System.out.println(" " + String.format("%32s", Integer.toBinaryString(value)).replace(' ', '0'));
        }
    }
}
