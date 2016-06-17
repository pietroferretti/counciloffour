package it.polimi.ingsw.ps14.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import it.polimi.ingsw.ps14.model.turnstates.TurnState;

// TODO notify ogni volta che qualcosa viene modificato?
public class Model extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4787221737865002835L;
	
	private List<Player> players;
	private GameBoard gameBoard;
	private Market market;

	private GamePhase gamePhase;
	private Player currentPlayer;
	private TurnState currentTurnState;
	private MarketState currentMarketState;
	private List<Player> playerOrder;

	public Model() throws IOException {
		gameBoard = new GameBoard(new Settings("settings.json"));
		players = new ArrayList<>();
	}

	public Model(List<Player> players) throws IOException {
		gameBoard = new GameBoard(new Settings("settings.json"));
		this.players = players;
	}

	public Model(Model m) {
		// TODO Auto-generated constructor stub
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
	}

	public MarketState getCurrentMarketState() {
		return currentMarketState;
	}

	public void setCurrentMarketState(MarketState currentMarketState) {
		this.currentMarketState = currentMarketState;
		setChanged();
		notifyObservers();
	}

	public List<Player> getPlayerOrder() {
		return playerOrder;
	}

	public void setPlayerOrder(List<Player> playerOrder) {
		this.playerOrder = playerOrder;
	}

	public void nextPlayer() {
		currentPlayer = playerOrder.remove(0);
		setChanged();
		notifyObservers();
	}

	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

}
