package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.State;

public class GameStartedMsg implements Message {
	
	private static final long serialVersionUID = 5977269520411008948L;
	
	private final State initialGameState;
	private final String mapName;
	
	public GameStartedMsg(State initialGameState, String mapName) {
		this.initialGameState = initialGameState;
		this.mapName = mapName;
	}
	
	public State getState() {
		return initialGameState;
	}
	
	public String getMapName() {
		return mapName;
	}
}
