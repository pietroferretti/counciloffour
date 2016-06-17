package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.fromServer.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromServer.AvailableCouncillorsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromServer.CurrentPlayerUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromServer.GamePhaseUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromServer.KingBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromServer.KingUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromServer.MarketStateUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromServer.NobilityTrackdUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromServer.RegionBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromServer.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;

/*
 * Questa classe viene introdotta per disaccoppiare il model dalla view
 * e quindi impedire eventuali modifiche non permesse al model.
 */

public class ModelView extends Observable implements Observer, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5876325204323947651L;

	// TODO modelview osserva model NEL MAIN
	// --------------------------MODEL------------------------
	private List<PlayerView> playersView;
	private CurrentPlayerView currentPlayerView;

	private GamePhaseView gamePhaseView;
	// private TurnStateView currentTurnStateView; secondo me non serve
	private MarketStateView marketStateView;

	// ------------------------GAMEBOARD-----------------------
	private List<RegionView> regionsView;
	private KingView kingView;
	private NobilityTrackView nobilityTrackView;
	private AvailableAssistantsView availableAssistantsView;
	private AvailableCouncillorsView availableCouncillorsView;
	private KingBonusesView kingBonusesView;
	private RegionBonusesView regionBonusesView;

	// private PoliticDeckView politicDeckView;

	// private List<City> cities; non penso serva tanto passa dalla regione o
	// no?
	/*
	 * private GameBoard gameBoard; private List<Player> players; private
	 * List<Player> playerOrder;
	 * 
	 */

	public ModelView(Model model) {

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

		marketStateView = new MarketStateView(model.getCurrentMarketState());
		model.addObserver(marketStateView);

		availableAssistantsView = new AvailableAssistantsView(model.getGameBoard().getAvailableAssistants());
		model.getGameBoard().addObserver(availableAssistantsView);

		availableCouncillorsView = new AvailableCouncillorsView(model.getGameBoard().getAvailableCouncillors());
		model.getGameBoard().addObserver(availableCouncillorsView);

		kingBonusesView = new KingBonusesView(model.getGameBoard().getKingBonuses().peek());
		model.getGameBoard().addObserver(kingBonusesView);

		regionBonusesView = new RegionBonusesView(model.getGameBoard().getBonusGold(),
				model.getGameBoard().getBonusSilver(), model.getGameBoard().getBonusBronze(),
				model.getGameBoard().getBonusBlue());
		model.getGameBoard().addObserver(regionBonusesView);

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
		marketStateView.addObserver(this);
		availableAssistantsView.addObserver(this);
		availableCouncillorsView.addObserver(this);
		kingBonusesView.addObserver(this);
		regionBonusesView.addObserver(this);
	}

	@Override
	public void update(Observable o, Object message) {
		if (o instanceof PlayerView) {
			setChanged();
			notifyObservers(message);
		} else if (o instanceof RegionView) {
			setChanged();
			notifyObservers(new RegionUpdatedMsg(((RegionView) o).getRegionCopy()));
		} else if (o instanceof KingView) {
			setChanged();
			notifyObservers(new KingUpdatedMsg(((KingView) o).getKingCopy()));
		} else if (o instanceof NobilityTrackView) {
			setChanged();
			notifyObservers(new NobilityTrackdUpdatedMsg(((NobilityTrackView) o).getNobilityTrackCopy()));
		} else if (o instanceof GamePhaseView) {
			setChanged();
			notifyObservers(new GamePhaseUpdatedMsg(((GamePhaseView) o).getGamePhaseCopy()));
		} else if (o instanceof CurrentPlayerView) {
			setChanged();
			notifyObservers(new CurrentPlayerUpdatedMsg(((CurrentPlayerView) o).getCurrentPlayerNameCopy(),
					((CurrentPlayerView) o).getCurrentPlayerIDCopy()));
		} else if (o instanceof MarketStateView) {
			setChanged();
			notifyObservers(new MarketStateUpdatedMsg(((MarketStateView) o).getCurrentMarketStateCopy()));
		} else if (o instanceof AvailableAssistantsView) {
			setChanged();
			notifyObservers(
					new AvailableAssistantsUpdatedMsg(((AvailableAssistantsView) o).getAvailableAssistantsCopy()));
		} else if (o instanceof AvailableCouncillorsView) {
			setChanged();
			notifyObservers(
					new AvailableCouncillorsUpdatedMsg(((AvailableCouncillorsView) o).getAvailableCouncillorsCopy()));
		} else if (o instanceof KingBonusesView) {
			setChanged();
			notifyObservers(new KingBonusesUpdatedMsg(((KingBonusesView) o).getShowableKingBonus()));
		} else if (o instanceof RegionBonusesView) {
			setChanged();
			notifyObservers(new RegionBonusesUpdatedMsg(((RegionBonusesView) o).getBonusGoldCopy(),
					((RegionBonusesView) o).getBonusSilverCopy(), ((RegionBonusesView) o).getBonusBronzeCopy(),
					((RegionBonusesView) o).getBonusBlueCopy()));
		} else
			throw new IllegalArgumentException();
	}
}
