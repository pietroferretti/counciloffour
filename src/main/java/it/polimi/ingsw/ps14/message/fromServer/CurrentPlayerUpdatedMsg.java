package it.polimi.ingsw.ps14.message.fromServer;

import it.polimi.ingsw.ps14.message.Message;

public class CurrentPlayerUpdatedMsg implements Message {

	/**
	 *   now is updateCurrentPlayerIDCopy  turn 
	 */
	private static final long serialVersionUID = -8248983765075072551L;

	private String updatedCurrentPlayerNameCopy;
	private int updateCurrentPlayerIDCopy;

	public CurrentPlayerUpdatedMsg(String updatedCurrentPlayerNameCopy, int updateCurrentPlayerIDCopy) {
		this.updatedCurrentPlayerNameCopy = updatedCurrentPlayerNameCopy;
		this.updateCurrentPlayerIDCopy = updateCurrentPlayerIDCopy;
	}

	public int getUpdateCurrentPlayerIDCopy() {
		return updateCurrentPlayerIDCopy;
	}

	public String getUpdatedCurrentPlayerNameCopy() {
		return updatedCurrentPlayerNameCopy;
	}
}
