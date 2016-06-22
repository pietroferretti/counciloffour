package it.polimi.ingsw.ps14.view;

import java.util.Observable;
import java.util.Observer;

public abstract class View extends Observable implements Observer{

	private Integer id;
	private String name;

	public View (Integer id) {
		this.id = id;
	}
	
	public void setPlayerID(Integer id){
		this.id=id;
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
