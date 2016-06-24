package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableCouncillorsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.CitiesColorBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.MarketUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;

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
	private boolean markedStarted = false;

	// --------------------------MODEL------------------------
	private List<PlayerView> playersView;
	private StateView stateView;

	// ------------------------GAMEBOARD-----------------------
	private List<RegionView> regionsView;
	private KingView kingView;
	private NobilityTrackView nobilityTrackView;
	private AvailableAssistantsView availableAssistantsView;
	private AvailableCouncillorsView availableCouncillorsView;
	private KingBonusesView kingBonusesView;
	private CitiesColorBonusesView citiesColorBonusesView;
	private MarketView marketView;

	private MessageView messageView;

	public ModelView(Model model) {

		playersView = new ArrayList<>(model.getPlayers().size());

		// add a playerView for each player
		int index = 0;
		for (Player player : model.getPlayers()) {
			playersView.add(new PlayerView(player));
			// playerView observes player
			player.addObserver(playersView.get(index));
			index++;
		}

		regionsView = new ArrayList<>(model.getGameBoard().getRegions().size());

		int i = 0;
		for (Region region : model.getGameBoard().getRegions()) {
			regionsView.add(new RegionView(region));
			region.addObserver(regionsView.get(i));
			i++;
		}

		kingView = new KingView(model.getGameBoard().getKing());
		model.getGameBoard().getKing().addObserver(kingView);

		nobilityTrackView = new NobilityTrackView(model.getGameBoard().getNobilityTrack());

		stateView = new StateView(model.getState());
		model.getState().addObserver(stateView);

		availableAssistantsView = new AvailableAssistantsView(model.getGameBoard().getAvailableAssistants());
		model.getGameBoard().addObserver(availableAssistantsView);

		availableCouncillorsView = new AvailableCouncillorsView(model.getGameBoard().getAvailableCouncillors());
		model.getGameBoard().addObserver(availableCouncillorsView);

		kingBonusesView = new KingBonusesView(model.getGameBoard().getKingBonuses().peek());
		model.getGameBoard().addObserver(kingBonusesView);

		citiesColorBonusesView = new CitiesColorBonusesView(model.getGameBoard().getColorBonuses());
		model.getGameBoard().addObserver(citiesColorBonusesView);

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
		stateView.addObserver(this);
		availableAssistantsView.addObserver(this);
		availableCouncillorsView.addObserver(this);
		kingBonusesView.addObserver(this);
		citiesColorBonusesView.addObserver(this);
		messageView.addObserver(this);
		marketView.addObserver(this);
	}

	public List<PlayerView> getPlayersView() {
		return playersView;
	}

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

	public CitiesColorBonusesView getCitiesColorBonusesView() {
		return citiesColorBonusesView;
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

		} else if (o instanceof StateView) {
			setChanged();
			notifyObservers(new StateUpdatedMsg(((StateView) o).getStateCopy()));
			if (((StateView) o).getStateCopy().getCurrentMarketState() == MarketState.BUYING
					&& markedStarted == false) {
				setChanged();
				notifyObservers(new MarketUpdatedMsg(marketView.getMarketCopy(), "MARKETSTAND"));
				markedStarted = true;
			}
			if (((StateView) o).getStateCopy().getCurrentMarketState() == MarketState.END)
				markedStarted = false;

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
		} else if (o instanceof CitiesColorBonusesView) {
			setChanged();
			notifyObservers(new CitiesColorBonusesUpdatedMsg(((CitiesColorBonusesView) o).getColorBonusesCopy()));
		} else if (o instanceof MarketView) {
			if (stateView.getStateCopy().getCurrentMarketState() == MarketState.BUYING) {
				setChanged();
				notifyObservers(new MarketUpdatedMsg(((MarketView) o).getMarketCopy(),(String) message));
			}
		} else if (o instanceof MessageView) {
			// TODO controllare
			if (((MessageView) o).getMessageCopy() != null) {
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
