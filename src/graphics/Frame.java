package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gamelogic.ClockRunner;
import gamelogic.Logic;

public class Frame {
	private Logic logic;
	private Drawing drawing;
	private JFrame frame = null;
	private int frameWidth = 750;
	private int frameHeight = 860;
	private int southPanelWidth = frameWidth;
	private int southPanelHeight = 20;
	private int eastPanelWidth = 300;
	private int eastPanelHeight = frameHeight;
	private ClockRunner clock;

	public Frame(Logic l) {
		this.logic = l;
		this.frame = new JFrame();
		this.drawing = new Drawing(frameWidth - eastPanelWidth, frameHeight, logic.getBoard().getWidth(),
				logic.getBoard().getHeight(), logic);

		frame.setPreferredSize(new Dimension(frameWidth + 100, frameHeight + 100));
		JPanel southPanel = new JPanel();
		JPanel eastPanel = new EastPanel(this, l.getBoard(), clock);
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
		frame.setTitle("Walking Game");
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.requestFocusInWindow();
		frame.pack();
		frame.setVisible(true);
	}

	public Drawing getDrawing() {
		return drawing;
	}

	public Logic getLogic() {
		return logic;
	}

	public void setClock(ClockRunner clock) {
		this.clock = clock;
	}
}
