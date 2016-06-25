package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;
/**
 * Assistant in gameboard are changed!  
 */

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
	
	@Override
	public String toString() {
		return "Assistant available in the game: "+updatedAvailableAssistants;
	}

}
