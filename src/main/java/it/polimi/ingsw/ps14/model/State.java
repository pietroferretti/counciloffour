package it.polimi.ingsw.ps14.model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Observable;

import it.polimi.ingsw.ps14.model.turnstates.TurnState;

/**
 * This class contains everything concerning the state of the game: the game
 * phase, the turn or market state, the current player and the players that will
 * come next.
 *
 * Having this data as a different object makes it easier for ModelView to
 * detect changes and notify the clients.
 */
public class State extends Observable implements Serializable {

	private static final long serialVersionUID = 4882929384753627181L;

	private GamePhase gamePhase;
	private TurnState currentTurnState;
	private int additionalMainsToDo;
	private MarketState currentMarketState;
	private Deque<Player> playerOrder;
	private Player currentPlayer;
	private WaitingFor waitingFor;
	/**
	 * The ids of the objects the player can choose from when a request is sent.
	 * They can represent permits (on the board or the player's) or cities (to
	 * find the respective token)
	 */
	private List<Integer> availableChoices; // TODO riscrivere come oggetti

	/**
	 * Empty constructor, everything should be set by Model at the beginning of
	 * a game
	 */
	public State() {
		gamePhase = null;
		currentTurnState = null;
		additionalMainsToDo = 0;
		currentMarketState = null;
		playerOrder = null;
		currentPlayer = null;
		waitingFor = WaitingFor.NOTHING;
		availableChoices = new ArrayList<>();
	}

	/**
	 * Copy constructor, makes a copy of all the fields of the original State
	 * object
	 * 
	 * @param originalState
	 *            the state you want to copy
	 */
	public State(State originalState) {
		gamePhase = originalState.getGamePhase();
		currentTurnState = originalState.getCurrentTurnState().makeCopy();
		additionalMainsToDo = originalState.getAdditionalMainsToDo();
		currentMarketState = originalState.getCurrentMarketState();
		playerOrder = new ArrayDeque<>(originalState.getPlayerOrder());
		currentPlayer = new Player(originalState.getCurrentPlayer());
		waitingFor = originalState.getWaitingFor();
		availableChoices = new ArrayList<>(originalState.getAvailableChoices());
	}

	/**
	 * @return the gamePhase
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}

	/**
	 * @param gamePhase
	 *            the gamePhase to set
	 */
	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the currentTurnState
	 */
	public TurnState getCurrentTurnState() {
		return currentTurnState;
	}

	/**
	 * @param currentTurnState
	 *            the currentTurnState to set
	 */
	public void setCurrentTurnState(TurnState currentTurnState) {
		this.currentTurnState = currentTurnState;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the additionalMainsToDo
	 */
	public int getAdditionalMainsToDo() {
		return additionalMainsToDo;
	}

	/**
	 * @param additionalMainsToDo
	 *            the additionalMainsToDo to set
	 */
	public void setAdditionalMainsToDo(int additionalMainsToDo) {
		this.additionalMainsToDo = additionalMainsToDo;
	}

	/**
	 * @return the currentMarketState
	 */
	public MarketState getCurrentMarketState() {
		return currentMarketState;
	}

	/**
	 * @param currentMarketState
	 *            the currentMarketState to set
	 */
	public void setCurrentMarketState(MarketState currentMarketState) {
		this.currentMarketState = currentMarketState;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the playerOrder
	 */
	public Deque<Player> getPlayerOrder() {
		return playerOrder;
	}

	/**
	 * @param playerOrder
	 *            the playerOrder to set
	 */
	public void setPlayerOrder(Deque<Player> playerOrder) {
		this.playerOrder = playerOrder;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @param currentPlayer
	 *            the currentPlayer to set
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the next player scheduled to play
	 */
	public Player getNextPlayer() {
		return playerOrder.peek();
	}

	/**
	 * Loads the next player from the queue, removing the last one from the
	 * schedule altogether
	 */
	public void loadNextPlayer() {
		currentPlayer = playerOrder.pollFirst();
		setChanged();
		notifyObservers();
	}

	/**
	 * Loads the next player from the queue, but adds the current player to the
	 * end of the queue, to be picked again later.
	 * 
	 * Used only for the market "Buying" phase, when the players can buy more
	 * than once.
	 */
	public void queueAndLoadNextPlayer() {
		playerOrder.addLast(currentPlayer);
		currentPlayer = playerOrder.pollFirst();
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the waitingFor state
	 */
	public WaitingFor getWaitingFor() {
		return waitingFor;
	}

	/**
	 * @param waitingFor
	 *            the waitingFor state to set
	 */
	public void setWaitingFor(WaitingFor waitingFor) {
		this.waitingFor = waitingFor;
	}

	/**
	 * @return the availableChoices
	 */
	public List<Integer> getAvailableChoices() {
		return availableChoices;
	}

	/**
	 * @param availableChoices
	 *            the availableChoices to set
	 */
	public void setAvailableChoices(List<Integer> availableChoices) {
		this.availableChoices = availableChoices;
	}

}