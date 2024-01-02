package graphics;

import gamelogic.Board;
import gamelogic.Logic;
import menuitems.JMenuNew;
import menuitems.JMenuQuit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The main frame for the actual game. Holds most of the in-game graphics
 * components.
 *
 * @author Benjamin Wong-Lee
 */
public class GameFrame extends JFrame {

	public static final String GAME_TITLE = "Forward Moving Game";

	private static final int frameWidth = 750;
	private static final int frameHeight = 860;
	private static final int southPanelWidth = frameWidth;
	private static final int southPanelHeight = 20;
	private static final int eastPanelWidth = 300;
	private static final int eastPanelHeight = frameHeight;

	private final Logic logic;
	private final ImageHandler imageHandler;

	private final Drawing drawing;
	private final EastPanel sidePanel;

	/**
	 * Main constructor of game graphics components is done here.
	 *
	 * @param l
	 *            The logic to be used as the connection between the user input
	 *            and view.
	 */
	public GameFrame(Logic l, ImageHandler imageHandler) {
		this.logic = l;
		this.imageHandler = imageHandler;

		Board board = logic.getBoard();
		this.drawing = new Drawing(frameWidth - eastPanelWidth, frameHeight,
				board.getWidth(), board.getHeight(),
				logic, imageHandler);

		this.setPreferredSize(new Dimension(frameWidth + 100, frameHeight + 100));

		JPanel southPanel = new JPanel();
		EastPanel eastPanel = new EastPanel(this, imageHandler, board, l.getTimeRunning());

		defineSouthPanel(southPanel);
		defineEastPanel(eastPanel);

		JMenuBar jMenuBar = defineJMenuBar();

		this.sidePanel = eastPanel;

		this.add(drawing, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(jMenuBar, BorderLayout.NORTH);
		this.addKeyListener(new WalkKeyListener(board, drawing));

		setFrameProperties();
	}

	/**
	 * Defines the JMenuBar on the main frame.
	 *
	 * @return The JMenuBar for the main Frame.
	 */
	private JMenuBar defineJMenuBar() {
		Font font = new Font("Times New Roman", Font.BOLD, 18);
		/* Construct file JMenu and its items */
		JMenu file = new JMenu("File");
		file.setFont(font);
		JMenuItem fileNew = new JMenuNew("New", font, imageHandler);
		JMenuItem fileQuit = new JMenuQuit("Quit", font);
		List<JMenuItem> fileMenuItems = new ArrayList<>();
		fileMenuItems.add(fileNew);
		fileMenuItems.add(fileQuit);

		/* Set the JMenu items to belong to the JMenu bar */
		JMenuBar menuBar = new JMenuBar();

		for (JMenuItem menuItem : fileMenuItems) {
			file.add(menuItem);
		}
		menuBar.add(file);
		return menuBar;
	}

	/**
	 * Give the ability to close this window when this is called from another
	 * window.
	 */
	public void dispatchClose() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Defines aspects of the SouthPanel which is effectively a status bar.
	 *
	 * @param southPanel
	 *            The South Panel to define
	 */
	private void defineSouthPanel(JPanel southPanel) {
		southPanel.setPreferredSize(new Dimension(southPanelWidth, southPanelHeight));
		southPanel.setBackground(new Color(100, 100, 100));
	}

	/**
	 * Defines aspects of the EastPanel which is the main menu bar in the game
	 * on the east of the main game screen.
	 *
	 * @param eastPanel The East Panel to define
	 */
	private void defineEastPanel(EastPanel eastPanel) {
		eastPanel.setPreferredSize(new Dimension(eastPanelWidth, eastPanelHeight));
	}

	/**
	 * Final stages of setting up the properties of the frame such as the title,
	 * focus, and visibility.
	 */
	private void setFrameProperties() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new GameWindowListener(this));
		this.setTitle(GAME_TITLE);
		this.setResizable(false);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public Drawing getDrawing() {
		return drawing;
	}

	public Logic getLogic() {
		return logic;
	}

	public EastPanel getSidePanel() {
		return sidePanel;
	}
}
