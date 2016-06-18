package it.polimi.ingsw.ps14.message.fromclient;

import it.polimi.ingsw.ps14.message.Message;

public class ChooseUsedPermitMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8085635959675502572L;
	private Integer busPerID;

	public ChooseUsedPermitMsg(int id) {
		this.busPerID = id;
	}

	public Integer getBusPerID() {
		return busPerID;
	}
}
