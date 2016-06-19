package it.polimi.ingsw.ps14.model;

import java.util.Observable;

import it.polimi.ingsw.ps14.message.Message;

/**
 * This class makes it easier for ModelView to check when a new message is ready to be sent
 */
public class MessageObservable extends Observable {

	private Message message;
	
	public MessageObservable() {
		message = null;
	}
	
	public MessageObservable(Message message) {
		this.message = message;
	}

	public MessageObservable(MessageObservable original) {
		this.message = original.getMessage();
	}
	
	public Message getMessage() {
		return message;
	}
	
	public void setMessage(Message message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}
	
	public void clearMessage() {
		message = null;
	}
}
