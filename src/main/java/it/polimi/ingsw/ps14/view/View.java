package it.polimi.ingsw.ps14.view;

import java.util.Observable;
import java.util.Observer;

public abstract class View extends Observable implements Observer{
	
	private final int id;
	private String name;

	public View (int id) {
		this.id = id;
	}
	
	public int getPlayerID() {
		return id;
	};
	
	public String getPlayerName() {
		return name;
	}
	
	public void setPlayerName(String name) {
		this.name = name;
	}
}
