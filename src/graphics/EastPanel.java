package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gamelogic.Board;

/**
 * 
 * @author Benjamin Wong-Lee
 */
public class EastPanel extends JPanel {

	private static final long serialVersionUID = -297177084727878389L;

	// Total number of items in game (not including Blank), board holds this.
	private int maxInventorySize = 0;

	private Set<JLabel> invSlots;

	private Timer clock;

	public EastPanel(GameFrame frame, Board board, Timer clock) {
		invSlots = new HashSet<JLabel>();
		maxInventorySize = board.getMaxInventorySize();
		this.clock = clock;
		addButtons(frame, board);
		createInventoryLabels(frame, board);
	}

	private void addButtons(GameFrame frame, Board board) {
		JButton left = new JButton();
		JButton right = new JButton();

		left.setFont(new Font("Verdana", Font.ITALIC, 18));
		right.setFont(new Font("Verdana", Font.ITALIC, 18));

		left.setText("Left");
		right.setText("Right");
		left.setPreferredSize(new Dimension(120, 50));
		right.setPreferredSize(new Dimension(120, 50));

		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (board.movePlayerInDirection("left")) {
					frame.getDrawing().repaint();
				}
			}
		});
		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (board.movePlayerInDirection("right")) {
					frame.getDrawing().repaint();
				}
			}
		});

		left.setFocusable(false);
		right.setFocusable(false);

		this.add(left);
		this.add(right);
	}

	private void createInventoryLabels(GameFrame frame, Board board) {
		Dimension preferredSize = new Dimension(130, 130);
		for (int i = 0; i < maxInventorySize; i++) {

			// TODO: remove once fully tested
			/* Read the image */
			BufferedImage invPic = null;
			String item = "flippers";
			try {
				invPic = ImageIO.read(new File("images/" + item + ".bmp"));
			} catch (IOException e) {
				System.err.println("There was an error reading an inventory image");
				e.printStackTrace();
			}

			// /* Read the image */
			// BufferedImage invPic = null;
			// String item = board.getPlayer().getInventory().get(i).getName();
			// try {
			// invPic = ImageIO.read(new File("images/" + item + ".bmp"));
			// } catch (IOException e) {
			// e.printStackTrace();
			// }

			/* Scale the image */
			Image img = (Image) invPic;
			Image scaled = img.getScaledInstance(preferredSize.width, preferredSize.height, 0);

			ImageIcon invItem = new ImageIcon(scaled);
			JLabel invSlot = new JLabel(invItem);

			invSlot.setFocusable(false);
			invSlot.setPreferredSize(preferredSize);

			this.add(invSlot, this.getLayout());
			this.invSlots.add(invSlot);
		}

	}

	// private void updateInventoryIcons(Frame frame, Board board) {
	// // TODO: Change as player picks up items
	// for (Item i : board.getPlayer().getInventory()) {
	// }
	// }

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(170, 0, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		int rows = 20;
		int cols = 10;
		int squareHt = (int) getHeight() / rows;
		int squareWd = (int) getWidth() / cols;
		g.setColor(new Color(0, 30, 30));
		boolean fill = true;
		for (int row = 0; row <= rows; row++) {
			for (int col = 0; col <= cols; col++) {
				if (fill == true) {
					g.fillRect(col * squareWd, row * squareHt, squareWd, squareHt);
					fill = false;
					continue;
				}
				fill = true;
			}
			g.setColor(new Color(g.getColor().getRed() + (160 / (rows + 1)), 30, 30));
			if (cols % 2 == 1) {
				if (!fill) {
					fill = true;
				} else {
					fill = false;
				}
			}
		}
	}

	public void setMaxInventoryNumber(int i) {
		maxInventorySize = i;
	}
}
