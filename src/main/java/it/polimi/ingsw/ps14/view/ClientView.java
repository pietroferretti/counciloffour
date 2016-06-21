package it.polimi.ingsw.ps14.view;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.client.socket.SocketCommunication;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.State;

public abstract class ClientView implements Runnable {

	protected Integer playerID;
	protected String name;
	protected boolean gameStarted;
	protected State gameState;
	protected boolean myTurn;

	public void setPlayerID(Integer playerID) {
		this.playerID = playerID;
	}

	public Integer getPlayerID() {
		return playerID;
	}
	
	public void setPlayerName(String name) {
		this.name = name;
	}
	
	public String getPlayerName(){
		return name;
	}

	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	public State getGameState() {
		return gameState;
	}

	public void setGameState(State gameState) {
		this.gameState = gameState;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	public abstract void readMessage(Message message);

	@Override
	public abstract void run();

	public abstract void setCommunication(Communication communication); 
}
