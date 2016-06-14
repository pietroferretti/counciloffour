package it.polimi.ingsw.ps14.model.modelview;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

/*
 * Questa classe viene introdotta per disaccoppiare il model dalla view
 * e quindi impedire eventuali modifiche non permesse al model.
 */

public class ModelView extends Observable implements Observer {

	private List<PlayerView> playersView;

	private List<RegionView> regionsView;
	private KingView kingView;
	private NobilityTrackView nobilityTrackView;

	// private String message;

	public ModelView(Model model) throws CloneNotSupportedException {
		// TODO Ancora tutto da scrivere, copiare tutti i componenti dal model

		playersView = new ArrayList<>();
		regionsView = new ArrayList<>();
		kingView = new KingView(model.getGameBoard().getKing());
		nobilityTrackView = new NobilityTrackView(model.getGameBoard().getNobilityTrack());
		
		
		
		// add a playerView for each player
		for (Player player : model.getPlayers()) {
			playersView.add(new PlayerView(player.clone()));
		}

		// ModelView observes each playerView
		for (PlayerView playerView : playersView) {
			playerView.addObserver(this);
		}

		// ModelView observes each regionView
		for (RegionView regionView : regionsView) {
			regionView.addObserver(this);
		}

		kingView.addObserver(this);
		nobilityTrackView.addObserver(this);

	}

	@Override
	public void update(Observable o, Object message) {
		// TODO check
		if (o instanceof PlayerView) {
			setChanged();
			notifyObservers(message);
		} else if (o instanceof RegionView) {
			setChanged();
			notifyObservers("REGIONVIEW");
		} else if (o instanceof KingView) {
			setChanged();
			notifyObservers("KINGVIEW");
		} else if (o instanceof NobilityTrackView) {
			setChanged();
			notifyObservers("NOBILITYTRACKVIEW");
		} else if (o instanceof VictoryPathView) {
			setChanged();
			notifyObservers("VICTORYPATHVIEW");
		} else
			throw new IllegalArgumentException();

	}

	public List<PlayerView> getPlayersView() {
		return playersView;
	}

	public List<RegionView> getRegionsView() {
		return regionsView;
	}

	public KingView getKingView() {
		return kingView;
	}

	public NobilityTrackView getNobilityTrackView() {
		return nobilityTrackView;
	}

}
