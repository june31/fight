package spring25;

import java.util.Random;

public class _HexRandomList {
    public static void main(String[] args) {
        Random random = new Random(); // ou new SecureRandom() si sécurité nécessaire

        final int count = 72;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < count; i++) {
            long value = random.nextLong();
            sb.append(String.format("0x%016Xl", value));
            if (i < count - 1) {
                sb.append(", ");
            }
        }

        System.out.println(sb.toString());
    }
}
