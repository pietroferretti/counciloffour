package it.polimi.ingsw.ps14.client;

import it.polimi.ingsw.ps14.message.Message;

public abstract class ClientView implements Runnable {

	private MessageHandlerOut msgOut;
	
	public ClientView() {}
	
	public ClientView(MessageHandlerOut msgOut) {
		this.msgOut = msgOut;
	}
	
	public void setHandlerOut(MessageHandlerOut msgOut) {
		this.msgOut = msgOut;
	}
	
	public void handleMessageOut(Message message) {
		msgOut.handleMessage(message);
	}
	
	public abstract void handleMessage(Message message);
	
	@Override
	public abstract void run();
}
