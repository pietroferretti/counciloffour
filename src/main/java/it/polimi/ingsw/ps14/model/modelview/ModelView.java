package it.polimi.ingsw.ps14.model.modelview;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;

/*
 * Questa classe viene introdotta per disaccoppiare il model dalla view
 * e quindi impedire eventuali modifiche non permesse al model.
 */

public class ModelView extends Observable implements Observer {
	// TODO modelview osserva model NEL MAIN

	private List<PlayerView> playersView;
	private List<RegionView> regionsView;
	private KingView kingView;
	private NobilityTrackView nobilityTrackView;

	private CurrentPlayerView currentPlayerView;

	private GamePhaseView gamePhaseView;
	// private TurnStateView currentTurnStateView;
	private MarketStateView currentMarketStateView;

	/*
	 * private GameBoard gameBoard; private List<Player> players; private
	 * List<Player> playerOrder;
	 * 
	 */

	public ModelView(Model model) throws CloneNotSupportedException {
		// TODO Ancora tutto da scrivere, copiare tutti i componenti dal model

		playersView = new ArrayList<>(model.getPlayers().size());

		// add a playerView for each player
		int index = 0;
		for (Player player : model.getPlayers()) {
			playersView.add(new PlayerView(new Player(player)));
			// playerView observes player
			player.addObserver(playersView.get(index));
			index++;
		}

		regionsView = new ArrayList<>(model.getGameBoard().getRegions().size());

		int i = 0;
		for (Region region : model.getGameBoard().getRegions()) {
			regionsView.add(new RegionView(new Region(region)));
			region.addObserver(regionsView.get(i));
			i++;
		}

		kingView = new KingView(model.getGameBoard().getKing());
		model.getGameBoard().getKing().addObserver(kingView);

		nobilityTrackView = new NobilityTrackView(model.getGameBoard().getNobilityTrack());
		model.getGameBoard().getNobilityTrack().addObserver(nobilityTrackView);

		gamePhaseView = new GamePhaseView(model.getGamePhase());
		model.addObserver(gamePhaseView);

		currentPlayerView = new CurrentPlayerView(model.getCurrentPlayer().getName(), model.getCurrentPlayer().getId());
		model.addObserver(currentPlayerView);

		currentMarketStateView = new MarketStateView(model.getCurrentMarketState());
		model.addObserver(currentMarketStateView);

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
		gamePhaseView.addObserver(this);
		currentPlayerView.addObserver(this);
		currentMarketStateView.addObserver(this);
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
		} else if (o instanceof Model) {

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
