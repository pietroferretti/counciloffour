package it.polimi.ingsw.ps14.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.GameStartedMsg;
import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

/**
 * The model, contains the data and the state of the game
 */
public class Model extends Observable implements Serializable {

	private static final long serialVersionUID = -4787221737865002835L;

	private static final String SETTINGS_FILENAME = "src/main/resources/settings.json";

	private static int idCounter = 1;
	private final int idGame;

	private List<Player> players;
	private GameBoard gameBoard;
	private Market market;

	private State state;
	private List<Bonus> bonusesToDo;

	private MessageObservable message;

	public Model() throws IOException {
		idGame = idCounter;
		idCounter++;
		gameBoard = new GameBoard(new Settings(SETTINGS_FILENAME));
		players = new ArrayList<>();
		market = new Market();
		state = new State();
		bonusesToDo = new ArrayList<>();

		setGamePhase(GamePhase.TURNS);
		setCurrentTurnState(new InitialTurnState());
		setCurrentMarketState(MarketState.END);

		message = new MessageObservable();
	}

	public void startGame() {
		setMessage(new GameStartedMsg(getState()));
		setChanged();
		notifyObservers();
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
		setPlayerOrder(players);
		loadNextPlayer();
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * 
	 * @param id
	 *            - the id of the player we're looking for
	 * @return The player with that id, null if no player in this game has that
	 *         id
	 */
	public Player getIDPlayer(int id) {
		for (Player player : players) {
			if (player.getId() == id)
				return player;
		}
		return null;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setGamePhase(GamePhase phase) {
		state.setGamePhase(phase);
	}

	public GamePhase getGamePhase() {
		return state.getGamePhase();
	}

	public Player getCurrentPlayer() {
		return state.getCurrentPlayer();
	}

	public void setCurrentPlayer(Player currentPlayer) {
		state.setCurrentPlayer(currentPlayer);
	}

	public TurnState getCurrentTurnState() {
		return state.getCurrentTurnState();
	}

	public void setCurrentTurnState(TurnState currentTurnState) {
		state.setCurrentTurnState(currentTurnState);
	}

	/**
	 * @return the additionalMainsToDo
	 */
	public int getAdditionalMainsToDo() {
		return state.getAdditionalMainsToDo();
	}

	/**
	 * @param additionalMainsToDo
	 *            the additionalMainsToDo to set
	 */
	public void setAdditionalMainsToDo(int additionalMainsToDo) {
		state.setAdditionalMainsToDo(additionalMainsToDo);
	}

	public void incrementAdditionalMainsToDo() {
		state.setAdditionalMainsToDo(state.getAdditionalMainsToDo() + 1);
	}

	public void decrementAdditionalMainsToDo() {
		state.setAdditionalMainsToDo(state.getAdditionalMainsToDo() - 1);
	}

	public MarketState getCurrentMarketState() {
		return state.getCurrentMarketState();
	}

	public void setCurrentMarketState(MarketState currentMarketState) {
		state.setCurrentMarketState(currentMarketState);
	}

	public Deque<Player> getPlayerOrder() {
		return state.getPlayerOrder();
	}

	public void setPlayerOrder(List<Player> playerOrder) {
		state.setPlayerOrder(new ArrayDeque<>(playerOrder));
	}

	public Player getNextPlayer() {
		return state.getNextPlayer();
	}

	public void loadNextPlayer() {
		state.loadNextPlayer();
	}

	public void queueAndLoadNextPlayer() {
		state.queueAndLoadNextPlayer();
	}

	/**
	 * @return the waitingFor state
	 */
	public WaitingFor getWaitingFor() {
		return state.getWaitingFor();
	}

	/**
	 * @param waitingFor
	 *            the waitingFor state to set
	 */
	public void setWaitingFor(WaitingFor waitingFor) {
		state.setWaitingFor(waitingFor);
	}

	public Integer getWaitingForHowMany() {
		return state.getWaitingForHowMany();
	}

	public void setWaitingForHowMany(Integer number) {
		state.setWaitingForHowMany(number);
	}

	/**
	 * @return the availableChoices
	 */
	public Map<String, String> getAvailableChoices() {
		return state.getAvailableChoices();
	}

	/**
	 * @param availableChoices
	 *            the availableChoices to set
	 */
	public void setAvailableChoices(Map<String, String> availableChoices) {
		state.setAvailableChoices(availableChoices);
	}

	public List<Bonus> getBonusesToDo() {
		return bonusesToDo;
	}

	public Bonus popBonusToDo() {
		return bonusesToDo.remove(0);
	}

	public void setBonusesToDo(List<Bonus> bonusesToDo) {
		this.bonusesToDo = bonusesToDo;
	}

	public void addBonusesToDo(List<Bonus> newBonuses) {
		this.bonusesToDo.addAll(newBonuses);
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
		setChanged();
		notifyObservers();
	}

	public MessageObservable getMessageObservable() {
		return message;
	}

	public Message getMessage() {
		return message.getMessage();
	}

	public void setMessage(Message messageToSend) {
		this.message.setMessage(messageToSend);
	}

	public void clearMessage() {
		message = null;
	}

	/**
	 * It returns a specific {@link Player} given his ID.
	 * 
	 * @param id
	 * @return {@link Player}.
	 */
	public Player id2player(int id) {
		for (Player p : players)
			if (p.getId() == id)
				return p;
		return null;
	}

	/**
	 * It returns a specific {@link BusinessPermit} given its ID and its owner(
	 * {@link Player}).
	 * 
	 * @param permitID
	 * @param player
	 * @return {@link BusinessPermit}
	 */
	public BusinessPermit id2permit(int permitID, Player player) {
		if (player == null)
			return null;
		try {
			for (BusinessPermit bp : player.getBusinessHand().getValidCards())
				if (bp.getId() == permitID)
					return bp;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

	public BusinessPermit id2permit(int permitID, Region region) {
		try {
			for (BusinessPermit bp : region.getBusinessPermits().getAvailablePermits()) {
				System.out.println(permitID + "==" + bp.getId());
				if (bp.getId() == permitID) {
					System.out.println("bp trovato");
					return bp;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public City name2city(String name) {
		try {
			for (City c : gameBoard.getCities())
				if (c.getName().equalsIgnoreCase(name))
					return c;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
