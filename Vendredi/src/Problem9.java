import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Problem9  {
	private static JFrame frame;

	public static final String[] INPUT = {
			"56 111 18 51 106 51 51 95 97 111 18 120 51 59 52 51 51 58 77 88 55 111 75 88 71 79",
			"37 21 37 27 16 29",
			"6 10 16 10 84 35 84 42 11 38",
			"-42 191 184 -15 184 -19 185 -19 186 -15 -42 190 92 93",
			"42 34 33 118 -3 178 151 29 42 59 0 178 106 29 33 65 149 87",
			"100 108 114 64 114 63 50 61 74 68 76 68 58 108 50 64 69 92",
			"184 -20 184 -22 185 -27 185 -28 158 166 -17 -18 -17 -20 160 154 160 -32 -30 166 93 105",
			"-126 -107 -111 249 227 -83 265 260 265 263 235 245 251 233 236 245 227 229 -110 244 251 236 -110 240 -126 257 -111 -99 85 43",
			"190 -29 190 165 190 163 80 97",
			"193 -26 193 -25 91 125 70 125 193 167 74 131"
	};
	public static int MIN_X;
	public static int MAX_X;
	public static int MIN_Y;
	public static int MAX_Y;
	public static int SQUARE_SIZE;
	public static int X;
	public static int Y;
	public static int N = 0; 
	public static int dx, dy;
	public static Pos me;
	public static List<Pos> baddies;
	public static StringBuilder commands;
	public static JPanel p;
	public static boolean lost;

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		baddies = new ArrayList<>();
		lost = false;
		commands = new StringBuilder();

		MIN_X = Integer.MAX_VALUE;
		MAX_X = Integer.MIN_VALUE;
		MIN_Y = Integer.MAX_VALUE;
		MAX_Y = Integer.MIN_VALUE;
		var ts = INPUT[N].split(" ");
		for (int i = 0; i < ts.length / 2; i++) {
			Pos p = new Pos(Integer.parseInt(ts[2 * i + 1]), Integer.parseInt(ts[2 * i]));
			baddies.add(p);
			if (MIN_X > p.c) MIN_X = p.c;
			if (MAX_X < p.c) MAX_X = p.c;
			if (MIN_Y > p.l) MIN_Y = p.l;
			if (MAX_Y < p.l) MAX_Y = p.l;
		}
		X = MAX_X - MIN_X + 1;
		Y = MAX_Y - MIN_Y + 1;
		me = baddies.remove(baddies.size() - 1);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int xs = (screenSize.width - 30) / X;
		int ys = (screenSize.height - 100) / Y;
		SQUARE_SIZE = Math.min(xs, ys);
		if (SQUARE_SIZE == 0) SQUARE_SIZE = 1;
		if (SQUARE_SIZE > 10) SQUARE_SIZE = 10;

		frame = new JFrame();
		int bn = N == 0 ? 10 : N;
		frame.setTitle("BOARD " + bn + " - Arrows to move, Esc to reset, Enter to print moves, 0-9 to select board (0 = board 10)");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		p = new JPanel() { public void paintComponent(Graphics g) { super.paintComponent(g); draw(g); }};
		p.setPreferredSize(new Dimension(X * (SQUARE_SIZE + 1) + 5, Y * (SQUARE_SIZE + 1) + 5));
		p.setBackground(Color.WHITE);
		register("UP", () -> { dx = 0; dy = 1; commands.append('U'); next(); });
		register("DOWN", () -> { dx = 0; dy = -1; commands.append('D'); next(); });
		register("LEFT", () -> { dx = -1; dy = 0; commands.append('L'); next(); });
		register("RIGHT", () -> { dx = 1; dy = 0; commands.append('R'); next(); });
		register("ENTER", () -> { System.out.println(commands); });
		register("ESCAPE", () -> { frame.dispose(); main(null); });
		for (int i = 0; i < 10; i++) { int fi = i; register("" + i, () -> { N = fi; frame.dispose(); main(null); }); }

		frame.add(p);
		frame.setSize(X * SQUARE_SIZE + 20, Y * SQUARE_SIZE + 47);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.repaint();
		frame.setVisible(true);
	}

	@SuppressWarnings("serial")
	private static void register(String key, Runnable action) {
		Object o = new Object();
		p.getInputMap().put(KeyStroke.getKeyStroke(key), o);
		p.getActionMap().put(o, new AbstractAction() {
			public void actionPerformed(ActionEvent e) { action.run(); }
		});
		
	}

	private static void next() {
		me = new Pos(me.l + dy, me.c + dx);
		List<Pos> bl = new ArrayList<>();
		Set<Pos> all = new HashSet<>();
		for (Pos p : baddies) {
			int l = p.l;
			if (p.l > me.l) l--; 
			else if (p.l < me.l) l++; 
			int c = p.c;
			if (p.c > me.c) c--; 
			else if (p.c < me.c) c++;
			Pos q = new Pos(l, c);
			if (all.contains(q)) {
				bl.remove(q);
			} else bl.add(q);
			all.add(q);
		}
		baddies = bl;
		if (baddies.contains(me)) lost = true;
		p.repaint();
	}

	private static void draw(Graphics g) {
		for (int row = 0; row < Y; row++) {
			for (int col = 0; col < X; col++) {
				Pos p = new Pos(-row + MAX_Y, col + MIN_X);
				g.setColor(lost ? Color.PINK : baddies.isEmpty() ? Color.BLUE : Color.BLACK);
				if (baddies.contains(p)) g.setColor(Color.RED);
				if (me.equals(p)) g.setColor(Color.YELLOW);
				g.fillRect(1 + col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}
}

class Pos {
	public int l;
	public int c;
	public Pos(int line, int col) { l = line; c = col; }
	public int hashCode() {	return Integer.rotateLeft(l, 16) ^ c; }
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Pos other = (Pos) obj;
		return c == other.c && l == other.l;
	}
}
