package it.polimi.ingsw.ps14.view;

import java.util.Observable;
import java.util.Observer;

public abstract class View extends Observable implements Observer{
	
	private final int id;

	public View (int id) {
		this.id = id;
	}
	
	public int getPlayerID() {
		return id;
	};
	
//	public abstract String getPlayerName();
	
//	public abstract void showOtherPlayersDetails();
//
//	public abstract void showGameboard(GameBoard gameBoard);
//
//	public abstract void showMainActions();
//
//	public abstract void showQuickActions();

//	public abstract void setModelView(ModelView modelView);


	
//	public void showThisPlayerDetails(Player player) {
//		// TODO Auto-generated method stub
//	}
//
//	public Message writeMsg(String inputLine) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void readMsg(Message message) {
//		// TODO Auto-generated method stub
//
//	}
}
