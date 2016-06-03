package it.polimi.ingsw.ps14.view;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.Player;

public class PlayerView extends Observable implements Observer {

	private Player playerCopy;

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (!(o instanceof Player)) {
			throw new IllegalArgumentException();
		}
		try {
			playerCopy = ((Player) arg).clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers();

	}

}
