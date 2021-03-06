package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gamelogic.Logic;
import menuitems.JMenuNew;
import menuitems.JMenuQuit;

/**
 * The main frame for the actual game. Holds most of the in-game graphics
 * components.
 *
 * @author Benjamin Wong-Lee
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = -6004994995100565980L;

	private Logic logic;
	private Drawing drawing;
	private int frameWidth = 750;
	private int frameHeight = 860;
	private int southPanelWidth = frameWidth;
	private int southPanelHeight = 20;
	private int eastPanelWidth = 300;
	private int eastPanelHeight = frameHeight;
	private EastPanel sidePanel;
	private JMenuBar jMenu;

	/**
	 * Main constructor of game graphics components is done here.
	 * 
	 * @param l
	 *            The logic to be used as the connection between the user input
	 *            and view.
	 */
	public GameFrame(Logic l) {
		this.logic = l;
		this.drawing = new Drawing(frameWidth - eastPanelWidth, frameHeight, logic.getBoard().getWidth(),
				logic.getBoard().getHeight(), logic);
		this.setPreferredSize(new Dimension(frameWidth + 100, frameHeight + 100));
		JPanel southPanel = new JPanel();
		JPanel eastPanel = new EastPanel(this, l.getBoard(), l.getTimeRunning());
		defineSouthPanel(southPanel);
		defineEastPanel(eastPanel);
		JMenuBar jMenuBar = defineJMenuBar();
		this.sidePanel = (EastPanel) eastPanel;
		this.jMenu = jMenuBar;

		this.add(drawing, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(jMenuBar, BorderLayout.NORTH);
		this.addKeyListener(new WalkKeyListener(l.getBoard(), (Drawing) drawing));
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
		JMenuItem fileNew = new JMenuNew("New", font);
		JMenuItem fileQuit = new JMenuQuit("Quit", font);
		List<JMenuItem> fileJMenuItems = new ArrayList<JMenuItem>();
		fileJMenuItems.add(fileNew);
		fileJMenuItems.add(fileQuit);

		/* Set the JMenu items to belong to the JMenu bar */
		JMenuBar jmenubar = new JMenuBar();

		for (int i = 0; i < fileJMenuItems.size(); i++) {
			file.add(fileJMenuItems.get(i));
		}
		jmenubar.add(file);
		return jmenubar;
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
	 * @param EastPanel
	 *            The East Panel to define
	 */
	private void defineEastPanel(JPanel eastPanel) {
		eastPanel.setPreferredSize(new Dimension(eastPanelWidth, eastPanelHeight));
	}

	/**
	 * Final stages of setting up the properties of the frame such as the title,
	 * focus, and visibility.
	 */
	private void setFrameProperties() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new GameWindowListener(this));
		this.setTitle("Forward Moving Game");
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

	public JMenuBar getjMenu() {
		return jMenu;
	}
}
