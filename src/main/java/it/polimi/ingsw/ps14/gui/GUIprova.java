package it.polimi.ingsw.ps14.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIprova {

	private ActionPanel actionPanel  = new ActionPanel();

	public static void main(String[] args) {
		JFrame window = new JFrame("CO4");
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
	}

}