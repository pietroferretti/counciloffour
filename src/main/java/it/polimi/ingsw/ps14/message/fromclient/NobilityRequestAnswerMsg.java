package it.polimi.ingsw.ps14.message.fromclient;

import java.util.List;

import it.polimi.ingsw.ps14.message.Message;

public class NobilityRequestAnswerMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8085635959675502572L;
	private List<String> objectIDs;

	public NobilityRequestAnswerMsg(List<String> objectIDs) {
		this.objectIDs = objectIDs;
	}

	public List<String> getIDs() {
		return objectIDs;
	}
}
