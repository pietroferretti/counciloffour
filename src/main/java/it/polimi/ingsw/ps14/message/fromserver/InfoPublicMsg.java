package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;

public class InfoPublicMsg implements Message {

	private static final long serialVersionUID = -3994848996899479353L;

	private final String text;
	
	public InfoPublicMsg(String text) {
		this.text = text;
	}

	public String getInfo() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
}
