package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.Player;

public class PlayerView extends Observable implements Observer {

	private Player playerCopy;

	public PlayerView(Player playerCopy) {
		this.playerCopy = playerCopy;
	}

	private void setPlayerCopy(Player playerCopy) {
		this.playerCopy = playerCopy;
		setChanged();

	}

	public Player getPlayerCopy() {
		return playerCopy;
	}

	@Override
	public void update(Observable o, Object message) {

		if (!(o instanceof Player)) {
			throw new IllegalArgumentException();
		}
		try {
			setPlayerCopy(((Player) o).clone());
			notifyObservers(message);

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}