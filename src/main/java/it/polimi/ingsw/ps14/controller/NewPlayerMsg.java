package it.polimi.ingsw.ps14.controller;

public class NewPlayerMsg implements Message {

	private String name;

	public NewPlayerMsg(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
