package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.GamePhase;

public class GamePhaseUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GamePhase updatedGamePhase;

	public GamePhaseUpdatedMsg(GamePhase updatedGamePhase) {
		this.updatedGamePhase = updatedGamePhase;
	}

	public GamePhase getUpdatedGamePhase() {
		return updatedGamePhase;
	}

}
