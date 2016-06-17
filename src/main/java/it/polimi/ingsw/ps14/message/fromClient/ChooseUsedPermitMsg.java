package it.polimi.ingsw.ps14.message.fromClient;

import it.polimi.ingsw.ps14.message.Message;

public class ChooseUsedPermitMsg implements Message {
	
	private Integer busPerID;
	
	public ChooseUsedPermitMsg(int id){
		this.busPerID=id;
	}
	
	public Integer getBusPerID(){
		return busPerID;
	}
}
