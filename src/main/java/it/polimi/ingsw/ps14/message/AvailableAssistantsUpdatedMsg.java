package it.polimi.ingsw.ps14.message;

public class AvailableAssistantsUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1253359333300860144L;

	private int updatedAvailableAssistants;

	public AvailableAssistantsUpdatedMsg(int updatedAvailableAssistants) {
		this.updatedAvailableAssistants = updatedAvailableAssistants;
	}

	public int getUpdatedAvailableAssistants() {
		return updatedAvailableAssistants;
	}

}
