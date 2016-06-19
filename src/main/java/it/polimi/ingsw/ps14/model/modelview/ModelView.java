package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableCouncillorsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.MarketUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.NobilityTrackUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.RegionBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;

/*
 * Questa classe viene introdotta per disaccoppiare il model dalla view
 * e quindi impedire eventuali modifiche non permesse al model.
 */
/**
 * This class aim to divide the model part from the view part especially to
 * prevent any changes in model done by the view. It contains a deep copy of
 * everything that the view can know.
 *
 */
public class ModelView extends Observable implements Observer, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5876325204323947651L;

	// TODO modelview osserva model NEL MAIN
	// --------------------------MODEL------------------------
	private List<PlayerView> playersView;
//	private CurrentPlayerView currentPlayerView;
//
//	private GamePhaseView gamePhaseView;
//	// private TurnStateView currentTurnStateView; secondo me non serve
//	private MarketStateView marketStateView;
	private StateView stateView;

	// ------------------------GAMEBOARD-----------------------
	private List<RegionView> regionsView;
	private KingView kingView;
	private NobilityTrackView nobilityTrackView;
	private AvailableAssistantsView availableAssistantsView;
	private AvailableCouncillorsView availableCouncillorsView;
	private KingBonusesView kingBonusesView;
	private RegionBonusesView regionBonusesView;
	private MarketView marketView;

	private MessageView messageView;

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

//		gamePhaseView = new GamePhaseView(model.getGamePhase());
//		model.addObserver(gamePhaseView);
//
//		currentPlayerView = new CurrentPlayerView(model.getCurrentPlayer().getName(), model.getCurrentPlayer().getId());
//		model.addObserver(currentPlayerView);
//
//		marketStateView = new MarketStateView(model.getCurrentMarketState());
//		model.addObserver(marketStateView);

		stateView = new StateView(model.getState());
		model.getState().addObserver(stateView);
		
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

		marketView = new MarketView(model.getMarket());
		model.getMarket().addObserver(marketView);

		messageView = new MessageView(model.getMessageObservable());
		model.getMessageObservable().addObserver(messageView);

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
//		gamePhaseView.addObserver(this);
//		currentPlayerView.addObserver(this);
//		marketStateView.addObserver(this);
		stateView.addObserver(this);
		availableAssistantsView.addObserver(this);
		availableCouncillorsView.addObserver(this);
		kingBonusesView.addObserver(this);
		regionBonusesView.addObserver(this);
		messageView.addObserver(this);
	}

	public List<PlayerView> getPlayersView() {
		return playersView;
	}

//	public CurrentPlayerView getCurrentPlayerView() {
//		return currentPlayerView;
//	}
//
//	public GamePhaseView getGamePhaseView() {
//		return gamePhaseView;
//	}
//
//	public MarketStateView getMarketStateView() {
//		return marketStateView;
//	}
	
	public StateView getStateView() {
		return stateView;
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

	public AvailableAssistantsView getAvailableAssistantsView() {
		return availableAssistantsView;
	}

	public AvailableCouncillorsView getAvailableCouncillorsView() {
		return availableCouncillorsView;
	}

	public KingBonusesView getKingBonusesView() {
		return kingBonusesView;
	}

	public RegionBonusesView getRegionBonusesView() {
		return regionBonusesView;
	}

	public MarketView getMarketView() {
		return marketView;
	}

	public MessageView getMessageView() {
		return messageView;
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
			notifyObservers(new NobilityTrackUpdatedMsg(((NobilityTrackView) o).getNobilityTrackCopy()));
//		} else if (o instanceof GamePhaseView) {
//			setChanged();
//			notifyObservers(new GamePhaseUpdatedMsg(((GamePhaseView) o).getGamePhaseCopy()));
//		} else if (o instanceof CurrentPlayerView) {
//			setChanged();
//			notifyObservers(new CurrentPlayerUpdatedMsg(((CurrentPlayerView) o).getCurrentPlayerNameCopy(),
//					((CurrentPlayerView) o).getCurrentPlayerIDCopy()));
//		} else if (o instanceof MarketStateView) {
//			setChanged();
//			notifyObservers(new MarketStateUpdatedMsg(((MarketStateView) o).getCurrentMarketStateCopy()));
		} else if (o instanceof StateView) {
			
			setChanged();
			notifyObservers(new StateUpdatedMsg(((StateView) o).getStateCopy()));
			
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
		} else if (o instanceof MarketView) {
			setChanged();
			notifyObservers(new MarketUpdatedMsg(((MarketView) o).getMarketCopy()));
		} else if (o instanceof MessageView) {
			// TODO controllare 
			Message messageToSend = ((MessageView) o).getMessageCopy();
			if (messageToSend != null) {
				setChanged();
				notifyObservers(((MessageView) o).getMessageCopy());
			}
		} else
			throw new IllegalArgumentException();
	}

	/**
	 * It returns a specific player.
	 * 
	 * @param id
	 *            - int ID of the player we're searching for
	 * @return Player with that specific ID or null if it doesn't exist.
	 */
	public Player getPlayerByID(int id) {
		for (PlayerView playerView : playersView) {
			if (playerView.getPlayerCopy().getId() == id)
				return playerView.getPlayerCopy();
		}
		return null;
	}
}
