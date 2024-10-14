package currentCG;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class _CaveRenderer extends JPanel {

    // Constants
    private static final int SEED = 420003; // Change as needed
    private static final int H = 100; // Initial height for ceiling
    private static final int L = 150000; // Cave length
    private static final double PIXEL_SIZE = 0.02; // Size of each pixel
    private static final Random r = new Random(0);
    private static final int p = PIXEL_SIZE < 1 ? 1 : (int) PIXEL_SIZE;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cave Renderer");
        _CaveRenderer caveRenderer = new _CaveRenderer();
        frame.add(caveRenderer);
        frame.setSize((int) (L * PIXEL_SIZE), (int) ((H + 250) * p));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[] floor = new int[L];
        int[] ceil = new int[L];

        // Initialize the starting points
        floor[0] = 0;
        ceil[0] = H;

        // Generate the floor and ceiling arrays
        for (int i = 1; i < L; i++) {
            floor[i] = floor[i - 1] + rnd();
            ceil[i] = ceil[i - 1] + rnd();
        }

        // Draw the cave
        drawCave(g, floor, ceil);
    }

    // Pseudo-random function based on the provided formula
    int s = SEED;
    int rnd() {
         s = (int) ((s * 16807L) % 0x7FFFFFFFL);
         return s % 7 - 3;
    }
    // Draw the cave based on the floor and ceiling arrays
    private void drawCave(Graphics g, int[] floor, int[] ceil) {
        g.setColor(Color.BLACK);
        for (int x = 0; x < L; x++) {
            for (int y = ceil[x]; y >= floor[x]; y--) {
                g.fillRect((int) (x * p), (100 + H - y) * p, p, p);
            }
        }
    }
}
