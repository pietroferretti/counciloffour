package it.polimi.ingsw.ps14.message;

public class ChooseUsedPermitMsg implements Message {
	Integer busPerID;
	public ChooseUsedPermitMsg(int id){
		this.busPerID=id;
	}
}
