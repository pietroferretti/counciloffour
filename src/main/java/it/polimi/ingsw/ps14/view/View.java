package it.polimi.ingsw.ps14.view;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.controller.Message;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.modelview.ModelView;

public abstract class View extends Observable implements Observer{

	public abstract void showOtherPlayersDetails();

	public abstract void showGameboard(GameBoard gameBoard);

	public abstract void showMainActions();

	public abstract void showQuickActions();

	public abstract String getPlayerName();

	public abstract void setModelView(ModelView modelView);

	public abstract int getPlayerID();
	
	public void showThisPlayerDetails(Player player) {
		// TODO Auto-generated method stub
	}

	public Message writeMsg(String inputLine) {
		// TODO Auto-generated method stub
		return null;
	}

	public void readMsg(Message message) {
		// TODO Auto-generated method stub

	}
}
