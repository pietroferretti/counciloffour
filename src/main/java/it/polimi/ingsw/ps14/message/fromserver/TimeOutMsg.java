package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;

public class TimeOutMsg implements Message {
	
	private final int time;
	
	public TimeOutMsg(int time){
		this.time=time;
	}

	
	public int getTimeOut(){
		return time;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -7634804056595311497L;

}
