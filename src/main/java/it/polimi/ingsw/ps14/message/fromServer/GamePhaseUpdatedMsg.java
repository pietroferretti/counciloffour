package it.polimi.ingsw.ps14.message.fromServer;

import it.polimi.ingsw.ps14.message.Message;
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

	@Override
	public String toString() {
		return ("GamePhase: " + updatedGamePhase.toString());
	}
	
	

}
