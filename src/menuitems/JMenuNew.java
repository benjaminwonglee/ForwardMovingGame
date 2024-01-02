package menuitems;

import gamelogic.Logic;
import graphics.GameFrame;
import graphics.ImageHandler;

import javax.swing.*;
import java.awt.*;

public class JMenuNew extends JMenuItem {

	/**
	 * Defines the JMenuItem for a new game
	 * 
	 * @param string
	 * @param font
	 */
	public JMenuNew(String string, Font font, ImageHandler imageHandler) {
		setFont(font);
		addActionListener(e -> {
            Logic l = new Logic();
            GameFrame f = new GameFrame(l, imageHandler);
            l.setFrame(f);
            l.runTimer();
            l.runDrawTimer();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                throw new RuntimeException("Couldn't start game. Initialization interrupted");
            }
        });
	}

}
