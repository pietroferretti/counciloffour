package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.State;

public class StateUpdatedMsg implements Message {


	private static final long serialVersionUID = 5148749654155846861L;

	private State updatedState;

	public StateUpdatedMsg(State updatedState) {
		this.updatedState = updatedState;
	}

	public State getUpdatedState() {
		return updatedState;
	}

	@Override
	public String toString() {
		return "StateUpdatedMsg [updatedState=" + updatedState + "]";
	}

}

