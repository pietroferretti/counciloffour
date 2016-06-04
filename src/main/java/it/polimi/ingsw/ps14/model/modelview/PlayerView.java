package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.Player;

public class PlayerView extends Observable implements Observer {

	private Player playerCopy;

	public PlayerView(Player playerCopy) {
		this.playerCopy = playerCopy;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (!(o instanceof Player)) {
			throw new IllegalArgumentException();
		}
		try {
			setPlayerCopy(((Player) o).clone());

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setPlayerCopy(Player playerCopy) {
		this.playerCopy = playerCopy;
		setChanged();
		notifyObservers();
	}

	public Player getPlayerCopy() {
		return playerCopy;
	}
}
