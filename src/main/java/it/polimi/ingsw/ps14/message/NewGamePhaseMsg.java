package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.GamePhase;

public class NewGamePhaseMsg {

	private GamePhase newGamePhase;

	public NewGamePhaseMsg(GamePhase newGamePhase) {
		this.newGamePhase = newGamePhase;
	}

	public GamePhase getNewGamePhase() {
		return newGamePhase;
	}
}
