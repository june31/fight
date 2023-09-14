package summer9;
import static summer9.Common.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import tools.tuple.Pos;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	
	public static final StringBuilder sb = new StringBuilder();

	public BoardPanel() {
		setPreferredSize(new Dimension(X * (SQUARE_SIZE + 1) + 5, Y * (SQUARE_SIZE + 1) + 5));
		setBackground(Color.WHITE);
		Object keyLeft = new Object();
		getInputMap().put(KeyStroke.getKeyStroke("LEFT"), keyLeft);
		getActionMap().put(keyLeft, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dx = -1;
				dy = 0;
				sb.append('L');
				next();
			}
		});

		Object keyRight = new Object();
		getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), keyRight);
		getActionMap().put(keyRight, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dx = 1;
				dy = 0;
				sb.append('R');
				next();
			}
		});

		Object keyUp = new Object();
		getInputMap().put(KeyStroke.getKeyStroke("UP"), keyUp);
		getActionMap().put(keyUp, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dx = 0;
				dy = 1;
				sb.append('U');
				next();
			}
		});

		Object keyDown = new Object();
		getInputMap().put(KeyStroke.getKeyStroke("DOWN"), keyDown);
		getActionMap().put(keyDown, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dx = 0;
				dy = -1;
				sb.append('D');
				next();
			}
		});

		Object keyEnter = new Object();
		getInputMap().put(KeyStroke.getKeyStroke("ENTER"), keyEnter);
		getActionMap().put(keyEnter, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(sb);
				System.exit(1);
			}
		});
	}

	protected void next() {
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
		System.out.println(turn++);
		System.out.println(bl);
		System.out.println(me);
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int row = 0; row < Y; row++) {
			for (int col = 0; col < X; col++) {
				Pos p = new Pos(-row + MAX_Y, col + MIN_X);
				g.setColor(Color.BLACK);
				if (baddies.contains(p)) g.setColor(Color.RED);
				if (me.equals(p)) g.setColor(Color.YELLOW);
				g.fillRect(1 + col * (SQUARE_SIZE + 0), row * (SQUARE_SIZE + 0), SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}
}
