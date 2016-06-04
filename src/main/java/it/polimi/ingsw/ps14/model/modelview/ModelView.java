package it.polimi.ingsw.ps14.model.modelview;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.Player;

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
	
	
	
	private String message;

	public ModelView(List<Player> players) throws CloneNotSupportedException {
		// TODO Auto-generated constructor stub
		playersView = new ArrayList<>();
		for (Player player : players) {
			playersView.add(new PlayerView(player.clone()));
		}
//		for (PlayerView playerView : playersView) {
//			playerView.addObserver(this);
//		}
		playersView.get(0).addObserver(this);

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

	public String getMessage() {
		return message;
	}
	

}
