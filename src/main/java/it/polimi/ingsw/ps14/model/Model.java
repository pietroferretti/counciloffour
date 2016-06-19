package it.polimi.ingsw.ps14.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Observable;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.GameStartedMsg;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

// TODO notify ogni volta che qualcosa viene modificato?
public class Model extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4787221737865002835L;

	private static int idCounter = 1;
	private final int idGame;

	private List<Player> players;
	private GameBoard gameBoard;
	private Market market;

//	private GamePhase gamePhase;
//	private Player currentPlayer;
//	private TurnState currentTurnState;
//	private MarketState currentMarketState;
//	private Deque<Player> playerOrder;
	private State state;

	private MessageObservable message;

	public Model() throws IOException {
		idGame = idCounter;
		idCounter++;
		gameBoard = new GameBoard(new Settings("settings.json"));
		players = new ArrayList<>();
		market = new Market();
		state = new State();
		
		setGamePhase(GamePhase.TURNS);
		setCurrentTurnState(new InitialTurnState());
		setCurrentMarketState(MarketState.END);
		
		message = new MessageObservable();
	}

	public Model(List<Player> players) throws IOException {
		idGame = idCounter;
		idCounter++;
		gameBoard = new GameBoard(new Settings("settings.json"));
		this.players = players;
		market = new Market();
		
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
	 * @param additionalMainsToDo the additionalMainsToDo to set
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
	 * @param waitingFor the waitingFor state to set
	 */
	public void setWaitingFor(WaitingFor waitingFor) {
		state.setWaitingFor(waitingFor);
	}

	/**
	 * @return the availableChoices
	 */
	public List<Integer> getAvailableChoices() {
		return state.getAvailableChoices();
	}

	/**
	 * @param availableChoices the availableChoices to set
	 */
	public void setAvailableChoices(List<Integer> availableChoices) {
		state.setAvailableChoices(availableChoices);
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
	
	public Player id2player(Integer id) {
		for (Player p :players)
			if (p.getId() == id)
				return p;
		return null;
	}

	public BusinessPermit id2permit(Integer permitID, Player player) {
		for (BusinessPermit bp : player.getBusinessHand().getValidCards())
			if (bp.getId() == permitID)
				return bp;
		return null;
	}
	

	public BusinessPermit id2permit(Integer permitID, Region region) {
		for (BusinessPermit bp : region.getBusinessPermits().getAvailablePermits())
			if (bp.getId() == permitID)
				return bp;
		return null;
	}

	public City id2city(String name) {
		for (City c : gameBoard.getCities())
			if (c.getName().compareTo(name) == 0)
				return c;
		return null;
	}
}
