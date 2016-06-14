package it.polimi.ingsw.ps14.model.actions;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public abstract class TurnAction extends Action {

	public TurnAction(Integer playerID) {
		super(playerID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(Model model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TurnState execute(TurnState previousState,Model model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected Player id2player(Integer id, Model model){
		for(Player p : model.getPlayers())
			if(p.getId()==id) return p;
		return null;
	}

}
