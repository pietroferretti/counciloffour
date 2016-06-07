package it.polimi.ingsw.ps14.view;

import java.util.Observer;

public interface View extends Observer {

	public void showOtherPlayersDetails();

	public void showGameboard();

	public void showThisPlayerDetails();

	public void show(String string);

	public void showMainActions();

	public void showQuickActions();
}
