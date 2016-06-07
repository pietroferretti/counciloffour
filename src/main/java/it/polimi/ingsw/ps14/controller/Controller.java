package it.polimi.ingsw.ps14.controller;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.Game;
import it.polimi.ingsw.ps14.view.CLIView;
import it.polimi.ingsw.ps14.view.GUIView;
import it.polimi.ingsw.ps14.view.View;

public class Controller implements Observer {

	private Game model;
	private List<View> views;

	public Controller(Game model, List<View> views) {
		this.model = model;
		this.views = views;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// if (!(o instanceof Game)) {
		// throw new IllegalArgumentException();
		// }
		if (arg instanceof NewPlayerMsg) {
			// TODO verifica id player
			model.getPlayers().get(0).setName(((NewPlayerMsg) arg).getName());
		}
		
		if (arg instanceof GameStartedMsg) {
			
			for (View view : views) {
				if (view instanceof CLIView) {
					view.showGameboard();
					view.showOtherPlayersDetails();
					view.showThisPlayerDetails();
				} else if (view instanceof GUIView) {
					// TODO
				} else
					throw new IllegalArgumentException("Views contains something different from GUI/CLI view");
			}
		}
		
		else if(arg instanceof TurnFinishedMsg){
			//TODO
		}

		

	}

}
