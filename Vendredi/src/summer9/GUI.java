package summer9;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import tools.tuple.Pos;

public class GUI extends Common {

	private static JFrame frame;

	public static void main(String[] args) {
		var ts = INPUT.split(" ");
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
		System.out.println("X in [" + MIN_X + ", " + MAX_X + "]; Y in [" + MIN_Y + ", " + MAX_Y + "]");
		me = baddies.remove(baddies.size() - 1);

		
		
		frame = new JFrame();
		frame.setTitle("Exo 9");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		BoardPanel boardPanel = new BoardPanel();
		frame.add(boardPanel);
		frame.setSize(X * (SQUARE_SIZE + 0) + 20, Y * (SQUARE_SIZE + 0) + 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.repaint();
		frame.setVisible(true);
		
	}
}
