package it.polimi.ingsw.ps14.controller;

public class TurnFinishedMsg implements Message {

	private int PlayerID;

	public TurnFinishedMsg(int playerID) {
		PlayerID = playerID;
	}

	public int getPlayerID() {
		return PlayerID;
	}

}
