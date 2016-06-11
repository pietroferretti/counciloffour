package it.polimi.ingsw.ps14.controller;

public class TurnFinishedMsg implements Message {

	private final int playerID;

	public TurnFinishedMsg(int playerID) {
		this.playerID = playerID;
	}

	public int getPlayerID() {
		return playerID;
	}

}
