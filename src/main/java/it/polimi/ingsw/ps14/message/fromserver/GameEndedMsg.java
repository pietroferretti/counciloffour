package it.polimi.ingsw.ps14.message.fromserver;

import java.util.List;

import it.polimi.ingsw.ps14.message.Message;

public class GameEndedMsg implements Message {

	private static final long serialVersionUID = 8661223348429957019L;

	private final List<List<String>> endResults;

	public GameEndedMsg(List<List<String>> endResults) {
		this.endResults = endResults;
	}

	/**
	 * @return the endResults
	 */
	public List<List<String>> getEndResults() {
		return endResults;
	}

}
