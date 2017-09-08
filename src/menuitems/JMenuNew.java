package menuitems;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import gamelogic.Logic;
import graphics.GameFrame;

public class JMenuNew extends JMenuItem {

	private static final long serialVersionUID = 839389142548172867L;

	/**
	 * Defines the JMenuItem for a new game
	 * 
	 * @param string
	 * @param font
	 */
	public JMenuNew(String string, Font font) {
		setFont(font);
		//TODO: Still needs fixing
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Logic l = new Logic();
				GameFrame f = new GameFrame(l);
				l.setFrame(f);
				l.runTimer();
				l.runDrawTimer();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

}
