package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gamelogic.Logic;

/**
 * 
 * 
 * @author Benjamin Wong-Lee
 */
public class GameFrame {
	private Logic logic;
	private Drawing drawing;
	private JFrame frame = null;
	private int frameWidth = 750;
	private int frameHeight = 860;
	private int southPanelWidth = frameWidth;
	private int southPanelHeight = 20;
	private int eastPanelWidth = 300;
	private int eastPanelHeight = frameHeight;
	
	public GameFrame(Logic l) {
		this.logic = l;
		this.frame = new JFrame();
		this.drawing = new Drawing(frameWidth - eastPanelWidth, frameHeight, logic.getBoard().getWidth(),
				logic.getBoard().getHeight(), logic);

		frame.setPreferredSize(new Dimension(frameWidth + 100, frameHeight + 100));
		JPanel southPanel = new JPanel();
		JPanel eastPanel = new EastPanel(this, l.getBoard(), l.getTimer());
		defineSouthPanel(southPanel);
		defineEastPanel(eastPanel);
		JMenuBar jMenuBar = defineJMenuBar();

		frame.add(drawing, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.add(eastPanel, BorderLayout.EAST);
		frame.add(jMenuBar, BorderLayout.NORTH);
		frame.addKeyListener(new WalkKeyListener(l.getBoard(), (Drawing) drawing));
		setFrameProperties();
	}

	/**
	 * Defines the JMenuBar on the main frame.
	 * 
	 * @return The JMenuBar for the main Frame.
	 */
	private JMenuBar defineJMenuBar() {
		/* Construct file JMenu and its items */
		JMenu file = new JMenu("File");
		file.setFont(new Font("Monospace", 0, 17));
		JMenuItem fileNew = new JMenuItem("New");
		JMenuItem fileQuit = new JMenuItem("Quit");
		/*
		 * On pressing the JMenu item on the JMenu, perform its respective
		 * action here.
		 */
		fileNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: new game
			}
		});
		fileQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

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

	private void defineSouthPanel(JPanel southPanel) {
		southPanel.setPreferredSize(new Dimension(southPanelWidth, southPanelHeight));
		southPanel.setBackground(new Color(100, 100, 100));
	}

	private void defineEastPanel(JPanel eastPanel) {
		eastPanel.setPreferredSize(new Dimension(eastPanelWidth, eastPanelHeight));
	}

	/**
	 * Final stages of setting up the properties of the frame such as the title,
	 * focus, and visibility.
	 */
	private void setFrameProperties() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(1);
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		
		frame.setTitle("Forward Moving Game");
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.requestFocusInWindow();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public Drawing getDrawing() {
		return drawing;
	}

	public Logic getLogic() {
		return logic;
	}
}
