package it.polimi.ingsw.ps14.controller;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.Game;
import it.polimi.ingsw.ps14.view.View;

public class Controller implements Observer {
	
	private Game model;
	private View view;

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
