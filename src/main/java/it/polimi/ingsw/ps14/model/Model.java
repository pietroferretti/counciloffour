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

	private GamePhase gamePhase;
	private Player currentPlayer;
	private TurnState currentTurnState;
	private MarketState currentMarketState;
	private Deque<Player> playerOrder;

	private Message message;

	public Model() throws IOException {
		idGame = idCounter;
		idCounter++;
		gameBoard = new GameBoard(new Settings("settings.json"));
		players = new ArrayList<>();
		market = new Market();
	}

	public Model(List<Player> players) throws IOException {
		idGame = idCounter;
		idCounter++;
		gameBoard = new GameBoard(new Settings("settings.json"));
		this.players = players;
		market = new Market();
	}

	public Model(Model m) {
		idGame = idCounter;
		idCounter++;
		// TODO Auto-generated constructor stub
	}
	
	public void startGame() {
		message = new GameStartedMsg();
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
		// magari una exception?
		return null;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setGamePhase(GamePhase phase) {
		this.gamePhase = phase;
		setChanged();
		notifyObservers();
	}

	public GamePhase getGamePhase() {
		return gamePhase;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public TurnState getCurrentTurnState() {
		return currentTurnState;
	}

	public void setCurrentTurnState(TurnState currentTurnState) {
		this.currentTurnState = currentTurnState;
		setChanged();
		notifyObservers();
	}

	public MarketState getCurrentMarketState() {
		return currentMarketState;
	}

	public void setCurrentMarketState(MarketState currentMarketState) {
		this.currentMarketState = currentMarketState;
		setChanged();
		notifyObservers();
	}

	public Deque<Player> getPlayerOrder() {
		return playerOrder;
	}

	public void setPlayerOrder(List<Player> playerOrder) {
		this.playerOrder = new ArrayDeque<>(playerOrder);
	}

	public Player getNextPlayer() {
		return playerOrder.peek();
	}

	public void loadNextPlayer() {
		currentPlayer = playerOrder.pollFirst();
		setChanged();
		notifyObservers();
	}

	public void queueAndLoadNextPlayer() {
		playerOrder.addLast(currentPlayer);
		currentPlayer = playerOrder.pollFirst();
		setChanged();
		notifyObservers();
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
		setChanged();
		notifyObservers();
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
		setChanged();
		notifyObservers();
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message messageToSend) {
		this.message = messageToSend;
		setChanged();
		notifyObservers();
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
