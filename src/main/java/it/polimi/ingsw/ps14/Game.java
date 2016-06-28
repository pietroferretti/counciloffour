package it.polimi.ingsw.ps14;

import it.polimi.ingsw.ps14.controller.Controller;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.modelview.ModelView;
import it.polimi.ingsw.ps14.server.Chat;
import it.polimi.ingsw.ps14.server.ServerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Game {
	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

	private final Model model;
	private final ModelView modelView;
	private final List<ServerView> views;
	private final Controller controller;
	
	public Game(List<ServerView> viewList) throws IOException {
		views = new ArrayList<>(viewList);
		
		LOGGER.info("Creating Model.");
		model = new Model();


		Collections.shuffle(views);	// randomizes the viewList to choose the player order
		
		List<Player> playerList = new ArrayList<>();
		int playerNum = 0;
		for (ServerView view : views) {
			Player player = new Player(view.getPlayerID(), 10 + playerNum, 1 + playerNum, model.getGameBoard().getPoliticDeck(), 6);
			playerList.add(player);
		}
		
		model.setPlayers(playerList);
		
		LOGGER.info("Creating Controller.");
		controller = new Controller(model);
		
		LOGGER.info("Creating ModelView.");
		modelView = new ModelView(model);
		
		Chat chat=new Chat(views);
	
		for (ServerView view : views) {
			view.setChat(chat);
			view.addObserver(controller);
			modelView.addObserver(view);
			view.setModelView(modelView);
			
		}

		LOGGER.info("Starting game.");
		model.startGame();
	
	}		
}	
