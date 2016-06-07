package it.polimi.ingsw.ps14.controller;

import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.controller.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.controller.turnstates.TurnState;
import it.polimi.ingsw.ps14.model.Game;
import it.polimi.ingsw.ps14.view.View;

public class TurnLogic {

	private final Player player;
	private Game game;
	private TurnState state;

	private final View playerView;

	public TurnLogic(Player player, Game game, View playerView, TurnState state) {
		this.player = player;
		this.game = game;
		this.playerView = playerView;
		this.state = new InitialTurnState();
	}

	/*
	 * 1) chiedo tipo azione 2) scelgo 3)
	 * 
	 */
	// TODO
	// trovare nome migliore
	// inizio con stampa di ttutti i miei dettagli??
	protected void play() {
		if(state instanceof InitialTurnState){
			playerView.show("Hi " + player.getName() + ", choose if you want to perform a quick or a main action!");
			playerView.showMainActions();
			playerView.showQuickActions();
			//parse answer
			
			
		}
		
		
		
	}
}
