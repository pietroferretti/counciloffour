package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Settings;

public class PlayerView extends Observable implements Observer {
	private static final Logger LOGGER= Logger.getLogger(Settings.class.getName());

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
			LOGGER.log(Level.SEVERE, "Couldn't copy Player", e);
		}

	}

}
