package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;

import gamelogic.Board;
import gamelogic.Player;

/**
 * Draws everything on the side panel. Stores all the buttons on there as well.
 * 
 * @author Benjamin Wong-Lee
 */
public class EastPanel extends JPanel {

	private static final long serialVersionUID = -297177084727878389L;

	// Total number of items in game (not including Blank), board holds this.
	private int maxInventorySize = 0;

	private Set<JLabel> invSlots;
	private int timeRunning;

	private GameFrame frame;
	private boolean colorChange1 = false;
	private boolean colorChange2 = false;

	private JLabel timeLabel;
	private JLabel lifeLabel;

	public EastPanel(GameFrame frame, Board board, int timeRunning) {
		invSlots = new HashSet<JLabel>();
		maxInventorySize = board.getMaxInventorySize();
		this.timeRunning = timeRunning;
		this.frame = frame;

		/* Set bounds or all elements of the panel since layout is null */
		this.setLayout(null);

		createLifePanel();
		createTimePanel();
		addButtons(frame, board);
		createInventoryLabels(frame, board, frame.getLogic().getCurrentPlayer());
	}

	private void createLifePanel() {
		JLabel life = new JLabel();
		try {
			Image img = ImageIO.read(new File("images/life3.png"));
			ImageIcon image = new ImageIcon(img);
			life.setIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		life.setPreferredSize(new Dimension(400, 150));
		life.setFocusable(false);
		this.add(life);
		this.lifeLabel = life;
		life.setBounds(new Rectangle(10, 10, 280, 100));
	}

	private void createTimePanel() {
		JLabel time = new JLabel();
		time.setPreferredSize(new Dimension(240, 100));
		time.setBorder(new CreateBorderWithLabel("" + timeRunning));
		time.setFocusable(false);
		this.add(time);
		this.timeLabel = time;
		timeLabel.setBounds(new Rectangle(10, 120, 280, 160));
	}

	private class CreateBorderWithLabel extends AbstractBorder {
		private static final long serialVersionUID = -2327653635624519881L;
		private String name = "";

		public CreateBorderWithLabel(String name) {
			this.name = name;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.setColor(new Color(80, 80, 180));
			g.fillRect(x, y, width, height);
			g.setColor(new Color(60, 60, 160));
			g.fillRect(x + 10, y + 10, width - 20, height - 20);
			g.setColor(new Color(250, 250, 255));
			g.fillRect(x + 20, y + 20, width - 40, height - 40);
			g.setFont(new Font("Lucida Sans", Font.BOLD, 18));
			g.setColor(new Color(0, 0, 100));
			g.drawString(name, width / 2 - (g.getFontMetrics().stringWidth(name) / 2), height / 2 + 8);
		}
	}

	private class CreateTimeLabel extends AbstractBorder {
		private static final long serialVersionUID = -2506069849287210090L;
		private String time;

		public CreateTimeLabel(String time) {
			this.time = time;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.setColor(new Color(80, 80, 180));
			g.fillRect(x, y, width, height);
			g.setColor(new Color(60, 60, 160));
			g.fillRect(x + 10, y + 10, width - 20, height - 20);
			g.setColor(new Color(250, 250, 255));
			g.fillRect(x + 20, y + 20, width - 40, height - 40);
			g.setFont(new Font("Lucida Sans", Font.BOLD, 24));
			g.setColor(new Color(0, 0, 100));
			g.drawString(time, width / 2 - (g.getFontMetrics().stringWidth(time) / 2), height / 2 + 8);
		}
	}

	private void addButtons(GameFrame frame, Board board) {
		JButton left = new JButton();
		JButton right = new JButton();

		left.setText("");
		right.setText("");
		left.setPreferredSize(new Dimension(120, 50));
		right.setPreferredSize(new Dimension(120, 50));
		left.setBorder(new CreateBorderWithLabel("Left"));
		right.setBorder(new CreateBorderWithLabel("Right"));

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

		left.setBounds(new Rectangle(10, 760, 130, 100));
		right.setBounds(new Rectangle(160, 760, 130, 100));
	}

	private void createInventoryLabels(GameFrame frame, Board board, Player player) {
		Dimension preferredSize = new Dimension(130, 130);
		for (int i = 0; i < maxInventorySize; i++) {
			// TODO: remove once fully tested
			/* Read the image */
			BufferedImage invPic = null;
			String item = player.getInventory().get(i).getName();
			try {
				invPic = ImageIO.read(new File("images/" + item + ".png"));
			} catch (IOException e) {
				System.err.println("There was an error reading an inventory image: " + item);
				e.printStackTrace();
			}

			// /* Read the image */
			// BufferedImage invPic = null;
			// String item = board.getPlayer().getInventory().get(i).getName();
			// try {
			// invPic = ImageIO.read(new File("images/" + item + ".png"));
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

			if (i < 2) {
				invSlot.setBounds(new Rectangle(-25 + (150 * i), 260, 200, 225));
			} else if (i < 4) {
				invSlot.setBounds(new Rectangle(-25 + (145 * (i - 2)), 410, 200, 225));
			}

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
		if (colorChange1) {
			// Green
			g.setColor(new Color(0, 170, 0));
		} else if (colorChange2) {
			// Blue
			g.setColor(new Color(0, 0, 170));
		} else {
			// Red
			g.setColor(new Color(170, 0, 0));
		}
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
			if (colorChange1) {
				// Green
				g.setColor(new Color(30, g.getColor().getGreen() + (160 / (rows + 1)), 30));
			} else if (colorChange2) {
				// Blue
				g.setColor(new Color(30, 30, g.getColor().getBlue() + (160 / (rows + 1))));
			} else {
				// Red
				g.setColor(new Color(g.getColor().getRed() + (160 / (rows + 1)), 30, 30));
			}
			if (cols % 2 == 1) {
				if (!fill) {
					fill = true;
				} else {
					fill = false;
				}
			}
		}

		switch (frame.getLogic().getCurrentPlayer().getLife()) {
		case 2:
			try {
				Image img = ImageIO.read(new File("images/life2.png"));
				ImageIcon image = new ImageIcon(img);
				lifeLabel.setIcon(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				Image img = ImageIO.read(new File("images/life1.png"));
				ImageIcon image = new ImageIcon(img);
				lifeLabel.setIcon(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

		// Draw up timer component
		timeLabel.setBorder(new CreateTimeLabel(frame.getLogic().getTimeString()));
		this.add(timeLabel);
		timeLabel.setBounds(new Rectangle(10, 120, 280, 160));
	}

	public void setMaxInventoryNumber(int i) {
		maxInventorySize = i;
	}

	public boolean getColorChange1() {
		return colorChange1;
	}

	public void setColorChange1(boolean colorChange1) {
		this.colorChange1 = colorChange1;
	}

	public boolean getColorChange2() {
		return colorChange2;
	}

	public void setColorChange2(boolean colorChange2) {
		this.colorChange2 = colorChange2;
	}

}
