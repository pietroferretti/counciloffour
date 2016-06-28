package it.polimi.ingsw.ps14.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ActionPanel extends JPanel {
	
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;
	private javax.swing.JButton jButton8;

	private void initComponents() {

		jButton2 = new JButton();
		jButton3 = new JButton();
		jButton4 = new JButton();
		jButton5 = new JButton();
		jButton6 = new JButton();
		jButton7 = new JButton();
		jButton8 = new JButton();

		jButton2.setText("Engage Assistant");
		jButton3.setText("Build Emporium With King");
		jButton4.setText("Acquire Permit");
		jButton5.setText("Build Emporium With Permit");
		jButton6.setText("Change Permit Tiles");
		jButton7.setText("Send Assistant To Elect Councillor");
		jButton8.setText("Additional Main Action");
		
		setLayout(mgr);
	}
}
