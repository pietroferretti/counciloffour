package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

/**
 * 
 * It contains an updated copy of a {@link Player} enclosed in the {@link Model}
 * . It gets an update from the {@link Player}, updates itself and notifies the
 * {@link ModelView}.
 *
 */
public class PlayerView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7718233332405533853L;
	private Player playerCopy;

	public PlayerView(Player playerCopy) {
		this.playerCopy = new Player(playerCopy);
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
		} else {
			setPlayerCopy(new Player((Player) o));
			notifyObservers(message);
		}

	}

}
