package it.polimi.ingsw.ps14.message.fromServer;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.King;

public class KingUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6936108562446277733L;

	private King updatedKing;

	public KingUpdatedMsg(King updatedKing) {
		this.updatedKing = updatedKing;
	}

	public King getUpdatedKing() {
		return updatedKing;
	}

}
