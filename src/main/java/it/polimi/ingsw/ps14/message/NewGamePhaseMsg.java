package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.Model;

public class NewGamePhaseMsg {
	private Model model;

	public NewGamePhaseMsg(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return model;
	}
}
