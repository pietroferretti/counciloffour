package it.polimi.ingsw.ps14.message;

public class CurrentPlayerUpdatedMsg implements Message {

	/**
	 * 
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
