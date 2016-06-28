package it.polimi.ingsw.ps14.gui;

import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JDialog;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.model.ColorCouncillor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nicol
 */
public class ElectCouncillorDialog extends JDialog {

	private static Communication comm;
	private static int playerID;

	/**
	 * Creates new form ElectCouncillorDialog
	 */
	public ElectCouncillorDialog(java.awt.Frame parent, boolean modal, Communication comm, int playerID) {
		super(parent, modal);
		ElectCouncillorDialog.comm = comm;
		ElectCouncillorDialog.playerID = playerID;
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jLabelBalcony = new javax.swing.JLabel();

		buttonGroup1 = new javax.swing.ButtonGroup();
		buttonGroup2 = new javax.swing.ButtonGroup();

		jLayeredPane3 = new javax.swing.JLayeredPane();
		jLayeredPane2 = new javax.swing.JLayeredPane();

		jRadioButton6 = new javax.swing.JRadioButton();
		jRadioButton8 = new javax.swing.JRadioButton();
		jRadioButton9 = new javax.swing.JRadioButton();
		jRadioButton5 = new javax.swing.JRadioButton();
		jRadioButton10 = new javax.swing.JRadioButton();
		jRadioButton7 = new javax.swing.JRadioButton();

		jLabel2 = new javax.swing.JLabel();
		jButtonConfirm = new javax.swing.JButton();

		jLayeredPane4 = new javax.swing.JLayeredPane();
		jLabel3 = new javax.swing.JLabel();
		jLayeredPane1 = new javax.swing.JLayeredPane();
		jRadioButton1 = new javax.swing.JRadioButton();
		jRadioButton2 = new javax.swing.JRadioButton();
		jRadioButton4 = new javax.swing.JRadioButton();
		jRadioButton3 = new javax.swing.JRadioButton();

		jLabelBalcony.setText("Choose balcony:");

		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

		jLayeredPane3.setLayout(new java.awt.BorderLayout());

		jLayeredPane2.setLayout(new java.awt.FlowLayout());

		buttonGroup1.add(jRadioButton6);
		jRadioButton6.setText("BLUE");
		jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton6ActionPerformed(evt);
			}
		});
		jLayeredPane2.add(jRadioButton6);

		buttonGroup1.add(jRadioButton8);
		jRadioButton8.setText("WHITE");
		jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton8ActionPerformed(evt);
			}
		});
		jLayeredPane2.add(jRadioButton8);

		buttonGroup1.add(jRadioButton9);
		jRadioButton9.setText("BLACK");
		jLayeredPane2.add(jRadioButton9);

		buttonGroup1.add(jRadioButton5);
		jRadioButton5.setText("PURPLE");
		jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton5ActionPerformed(evt);
			}
		});
		jLayeredPane2.add(jRadioButton5);

		buttonGroup1.add(jRadioButton10);
		jRadioButton10.setText("ORANGE");
		jRadioButton10.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton10ActionPerformed(evt);
			}
		});
		jLayeredPane2.add(jRadioButton10);

		buttonGroup1.add(jRadioButton7);
		jRadioButton7.setSelected(true);
		jRadioButton7.setText("PINK");
		jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton7ActionPerformed(evt);
			}
		});
		jLayeredPane2.add(jRadioButton7);

		jLayeredPane3.add(jLayeredPane2, java.awt.BorderLayout.CENTER);

		jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel2.setText("Choose color:");
		jLayeredPane3.add(jLabel2, java.awt.BorderLayout.PAGE_START);

		jButtonConfirm.setText("CONFIRM");
		jButtonConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButton2MouseClicked();
			}
		});
		jLayeredPane3.add(jButtonConfirm, java.awt.BorderLayout.PAGE_END);

		getContentPane().add(jLayeredPane3, java.awt.BorderLayout.CENTER);

		jLayeredPane4.setLayout(new java.awt.BorderLayout());

		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("Choose balcony:");
		jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jLayeredPane4.add(jLabel3, java.awt.BorderLayout.NORTH);

		jLayeredPane1.setLayout(new java.awt.FlowLayout());

		buttonGroup2.add(jRadioButton1);
		jRadioButton1.setText("KING");
		jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton1ActionPerformed(evt);
			}
		});
		jLayeredPane1.add(jRadioButton1);

		buttonGroup2.add(jRadioButton2);
		jRadioButton2.setSelected(true);
		jRadioButton2.setText("COAST");
		jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton2ActionPerformed(evt);
			}
		});
		jLayeredPane1.add(jRadioButton2);

		buttonGroup2.add(jRadioButton4);
		jRadioButton4.setText("HILLS");
		jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jRadioButton4ActionPerformed(evt);
			}
		});
		jLayeredPane1.add(jRadioButton4);

		buttonGroup2.add(jRadioButton3);
		jRadioButton3.setText("MOUNTAINS");
		jLayeredPane1.add(jRadioButton3);

		jLayeredPane4.add(jLayeredPane1, java.awt.BorderLayout.PAGE_END);

		getContentPane().add(jLayeredPane4, java.awt.BorderLayout.PAGE_START);

		pack();
	}// </editor-fold>

	private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jRadioButton10ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jRadioButton8ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton2MouseClicked() {
//		ColorCouncillor cc = null;
//		String s = null;
//		for (Enumeration<AbstractButton> buttons = buttonGroup2.getElements(); buttons.hasMoreElements();) {
//			AbstractButton button = buttons.nextElement();
//			if (button.isSelected()) {
//				cc = ColorCouncillor.valueOf(button.getText());
//			}
//		}
//		for (Enumeration<AbstractButton> buttons = buttonGroup1.getElements(); buttons.hasMoreElements();) {
//			AbstractButton button = buttons.nextElement();
//			if (button.isSelected()) {
//				s = button.getText();
//			}
//		}
		System.out.println("cfghbn nmk");
//		comm.electCouncillor(playerID, cc, s);

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(ElectCouncillorDialog.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(ElectCouncillorDialog.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(ElectCouncillorDialog.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ElectCouncillorDialog.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ElectCouncillorDialog dialog = new ElectCouncillorDialog(new javax.swing.JFrame(), true, comm, playerID);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
				
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.ButtonGroup buttonGroup2;
	private javax.swing.JButton jButtonConfirm;
	private javax.swing.JLabel jLabelBalcony;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLayeredPane jLayeredPane1;
	private javax.swing.JLayeredPane jLayeredPane2;
	private javax.swing.JLayeredPane jLayeredPane3;
	private javax.swing.JLayeredPane jLayeredPane4;
	private javax.swing.JRadioButton jRadioButton1;
	private javax.swing.JRadioButton jRadioButton10;
	private javax.swing.JRadioButton jRadioButton2;
	private javax.swing.JRadioButton jRadioButton3;
	private javax.swing.JRadioButton jRadioButton4;
	private javax.swing.JRadioButton jRadioButton5;
	private javax.swing.JRadioButton jRadioButton6;
	private javax.swing.JRadioButton jRadioButton7;
	private javax.swing.JRadioButton jRadioButton8;
	private javax.swing.JRadioButton jRadioButton9;
	// End of variables declaration
}