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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;

import gamelogic.Board;
import gamelogic.Logic;
import gamelogic.Player;

/**
 * Draws everything on the side panel. Stores all the buttons on there as well.
 * 
 * @author Benjamin Wong-Lee
 */
public class EastPanel extends JPanel {

	private static final long serialVersionUID = -297177084727878389L;

	// Total number of items in game (not including Blank), Board holds this.
	private int maxInventorySize = 0;

	private List<JLabel> invSlots;
	private int timeRunning;
	private Dimension invIconSize = new Dimension(130, 130);

	private GameFrame frame;
	private boolean colorChange1 = false;
	private boolean colorChange2 = false;

	private JLabel timeLabel;
	private JLabel lifeLabel;

	/**
	 * Creates the main game menu panel on the right of the game.
	 * 
	 * @param frame
	 * @param board
	 * @param timeRunning
	 */
	public EastPanel(GameFrame frame, Board board, int timeRunning) {
		invSlots = new ArrayList<JLabel>();
		maxInventorySize = Board.getMaxInventorySize();
		this.timeRunning = timeRunning;
		this.frame = frame;

		/*
		 * Set bounds of all elements of the panel since layout is null. May not
		 * be the best design, but nice for customized design; being able to
		 * place elements in the actual defined positions.
		 */
		this.setLayout(null);

		createLifePanel();
		createTimePanel();
		addButtons(frame, board);
		createInventoryLabels(frame.getLogic().getPlayer());
	}

	/**
	 * Creates the panel displaying how much life the player has left.
	 */
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

	/**
	 * Creates the panel displaying how much time has passed from the start of
	 * the game.
	 */
	private void createTimePanel() {
		JLabel time = new JLabel();
		time.setPreferredSize(new Dimension(240, 100));
		time.setBorder(new BorderWithLabel("" + timeRunning, new Font("Lucida Sans", Font.BOLD, 24)));
		time.setFocusable(false);
		this.add(time);
		this.timeLabel = time;
		timeLabel.setBounds(new Rectangle(10, 120, 280, 160));
	}

	/**
	 * Creates a label over a component which displays a string. Drawn as a
	 * border over the entire button.
	 * 
	 * @author Benjamin Wong-Lee
	 */
	public class BorderWithLabel extends AbstractBorder {
		private static final long serialVersionUID = -2327653635624519881L;
		private String name = "";
		private Font font;

		public BorderWithLabel(String name, Font font) {
			this.name = name;
			this.font = font;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			for (int i = 0; i < 10; i++) {
				g.setColor(new Color(80 - i * 2, 80 - i * 2, 180));
				g.fillRect(x + i, y + i, width - (i * 2), height - (i * 2));
			}
			g.setColor(new Color(250, 250, 255));
			g.fillRect(x + 20, y + 20, width - 40, height - 40);
			g.setFont(font);
			g.setColor(new Color(0, 0, 100));
			g.drawString(name, width / 2 - (g.getFontMetrics().stringWidth(name) / 2), height / 2 + 8);
		}
	}

	private void addButtons(GameFrame frame, Board board) {
		JButton left = new JButton();
		JButton right = new JButton();
		Font buttonFont = new Font("Lucida Sans", Font.BOLD, 18);
		left.setPreferredSize(new Dimension(120, 50));
		right.setPreferredSize(new Dimension(120, 50));
		left.setBorder(new BorderWithLabel("Left", buttonFont));
		right.setBorder(new BorderWithLabel("Right", buttonFont));

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

	/**
	 * Creates the boxes for inventory pickup items. The amount of slots depends
	 * on the maximum inventory size. Changes depending on the items in the
	 * players inventory.
	 * 
	 * @param player
	 *            The player that has the inventory.
	 */
	private void createInventoryLabels(Player player) {
		for (int i = 0; i < maxInventorySize; i++) {
			String item = player.getInventory().get(i).getName();
			/* Read and scale the image */
			Image scaled = readAndScaleImage(item, invIconSize.width, invIconSize.height);
			JLabel invSlot = new JLabel(new ImageIcon(scaled));

			invSlot.setFocusable(false);
			invSlot.setPreferredSize(invIconSize);

			int startHt = 290;
			int multiplier = 0;
			if (i < 2) {
				multiplier = i;
			} else if (i < 4) {
				// Same as above but -2 from i and increase height
				multiplier = i - 2;
				startHt = 290 + invIconSize.height + 15;
			} else if (i < 6) {
				multiplier = i - 4;
				startHt = 290 + invIconSize.height * 2 + 30;
			}
			invSlot.setBounds(new Rectangle(invIconSize.width * multiplier + 10 + (15 * multiplier), startHt,
					invIconSize.width, invIconSize.height));
			this.add(invSlot);
			this.invSlots.add(invSlot);
		}

	}

	/**
	 * Reads the image from the images folder
	 * 
	 * @param item
	 *            The string that is the name of the item
	 * @param width
	 *            The width to scale the image at
	 * @param height
	 *            The height to scale the image at
	 * @return
	 */
	private Image readAndScaleImage(String item, int width, int height) {
		BufferedImage invPic = null;
		try {
			invPic = ImageIO.read(new File("images/" + item + ".png"));
		} catch (IOException e) {
			System.err.println("There was an error reading an inventory image: " + item);
			e.printStackTrace();
		}
		Image img = (Image) invPic;
		Image scaled = img.getScaledInstance(width, height, 0);
		return scaled;
	}

	/**
	 * Updates the visual inventory. Called after a player picks up an item.
	 * 
	 * @param player
	 *            The player whose inventory will be read.
	 */
	public void updateInventoryLabels(Player player) {
		for (int i = 0; i < player.getInventory().size(); i++) {
			String item = player.getInventory().get(i).getName();
			// Read and scale the image
			Image scaled = readAndScaleImage(item, invIconSize.width, invIconSize.height);
			ImageIcon invItem = new ImageIcon(scaled);
			JLabel label = invSlots.get(i);
			label.setIcon(invItem);
		}
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		drawPatternOnSidePanel(g);
		updateTimerVisual();
	}

	/**
	 *  Draws up the timer component visible to the user.
	 */
	private void updateTimerVisual() {
		timeLabel.setBorder(
				new BorderWithLabel(frame.getLogic().getTimeString(), new Font("Lucida Sans", Font.BOLD, 24)));
		this.add(timeLabel);
		timeLabel.setBounds(new Rectangle(10, 120, 280, 160));
	}

	/**
	 * Updates the life panel on the side panel. Called when life needs to be
	 * updated.
	 */
	public void updateLifePanel() {
		switch (frame.getLogic().getPlayer().getLife()) {
		case 3:
			try {
				Image img = ImageIO.read(new File("images/life3.png"));
				ImageIcon image = new ImageIcon(img);
				lifeLabel.setIcon(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
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
	}

	/**
	 * Draw the background pattern on the SidePanel (EastPanel).
	 * 
	 * @param g
	 *            The graphics object to draw the pattern
	 */
	private void drawPatternOnSidePanel(Graphics g) {
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
