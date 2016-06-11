package it.polimi.ingsw.ps14.view;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.modelview.ModelView;

public abstract class View extends Observable implements Observer {

	public abstract void showOtherPlayersDetails();

	public abstract void showGameboard();

	public abstract void showThisPlayerDetails();

	public abstract void show(String string);

	public abstract void showMainActions();

	public abstract void showQuickActions();
	
	public abstract int getPlayerID();
	
	public abstract String getPlayerName();
	
	public abstract void setModelView(ModelView modelView);
	
	public abstract void print(String message); // Usato per esempi, andrebbe fatto con la notify da model?
}
