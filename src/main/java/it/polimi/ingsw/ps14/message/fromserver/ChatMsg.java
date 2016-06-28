package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;

public class ChatMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7805337572521767421L;

	private String text;
	private String author;

	public ChatMsg(String text, String author) {
		super();
		this.text = text;
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public String getAuthor() {
		return author;
	}
	
	public String toString(){
		String print;
		print= author+": "+text;
		return print;
	}
	
}
