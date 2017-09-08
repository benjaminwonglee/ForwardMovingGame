package menuitems;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class JMenuQuit extends JMenuItem {

	private static final long serialVersionUID = -4225758086697776825L;

	public JMenuQuit(String string, Font font) {
		setFont(font);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

	}

}
