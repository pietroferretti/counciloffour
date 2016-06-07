package it.polimi.ingsw.ps14.model.modelview;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
	private VictoryPathView victoryPathView;

	// private String message;

	public ModelView(List<Player> players) throws CloneNotSupportedException {
		// TODO Auto-generated constructor stub

		playersView = new ArrayList<>();

		// add a playerView for each player
		for (Player player : players) {
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
		victoryPathView.addObserver(this);

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

	public VictoryPathView getVictoryPathView() {
		return victoryPathView;
	}

}
