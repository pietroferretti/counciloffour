package it.polimi.ingsw.ps14;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.controller.Controller;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.view.View;

public class Game {
	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

	private final Model model;
	private final ModelView modelView;
//	private final ServerView serverView;
	private final List<View> views;
	private final Controller controller;
	
	public Game(List<View> viewList) throws IOException, CloneNotSupportedException {
		
		views = viewList;
		
		model = new Model();
		
		// TODO different amount of coins and assistants

		List<Player> playerList = new ArrayList<>();
		for (View view : views) {
			Player player = new Player(view.getPlayerID(), 10, 2, model.getGameBoard().getPoliticDeck(), 6);
			playerList.add(player);
		}
		
		model.setPlayers(playerList);
		
		controller = new Controller(model, views);
		
		modelView = new ModelView(model);
	
		for (View view : views) {
			modelView.addObserver(view);
			
		}
		
//		modelView.addObserver(serverView);
//		serverView.addObserver(controller);
		}		
	}	
