package it.polimi.ingsw.ps14.model;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.view.PlayerView;

/*
 * Questa classe viene introdotta per disaccoppiare il model dalla view
 * e quindi impedire eventuali modifiche non permesse al model.
 */

public class ModelView extends Observable implements Observer {
	
	private List<PlayerView> playersView;
	//private PlayerView playerView;
	private String message;

	public ModelView(int playersNumber) {
		// TODO Auto-generated constructor stub
		for (int i = 0; i < playersNumber; i++) {
			playersView.add(new PlayerView());
		}
		for (PlayerView playerView : playersView) {
			playerView.addObserver(this);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		//TODO add others
		if (o instanceof PlayerView) {
			setChanged();
			notifyObservers("PLAYERVIEW");
		} else
			throw new IllegalArgumentException();

	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<PlayerView> getPlayersView() {
		return playersView;
	}

}
