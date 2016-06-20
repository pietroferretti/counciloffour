package it.polimi.ingsw.ps14;

import it.polimi.ingsw.ps14.client.RMI.ClientViewRemote;
import it.polimi.ingsw.ps14.controller.Controller;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Game {
	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

	private final Model model;
	private final ModelView modelView;
	private final List<View> viewsSocket;
	private final List<ClientViewRemote> viewsRMI;
	private final Controller controller;
	
	public Game(List<View> viewList, List<ClientViewRemote> viewsRMI) throws IOException, CloneNotSupportedException {
		this.viewsRMI=viewsRMI;
		viewsSocket = viewList;
		
		LOGGER.info("Creating Model.");
		model = new Model();
		
		// TODO different amount of coins and assistants

		List<Player> playerList = new ArrayList<>();
		for (View view : viewsSocket) {
			Player player = new Player(view.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6);
			playerList.add(player);
		}
		//__________________________________________________________---
		for (ClientViewRemote view : viewsRMI) {
			Player player = new Player(view.getID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6);
			playerList.add(player);
		}
		//--------------------------------------------------------------
		
		model.setPlayers(playerList);
		
		LOGGER.info("Creating Controller.");
		controller = new Controller(model);
		
		LOGGER.info("Creating ModelView.");
		modelView = new ModelView(model);
	
		for (View view : viewsSocket) {
			
			view.addObserver(controller);
			modelView.addObserver(view);
			
		}
//______________________________________________
		for(ClientViewRemote view : viewsRMI) {
			
			view.addObserver(controller);
			modelView.addObserver(view);
		}
		//_---------------------------------
		
		LOGGER.info("Starting game.");
		model.startGame();
	
	}		
}	
