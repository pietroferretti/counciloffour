package it.polimi.ingsw.ps14.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.controller.Controller;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.modelview.ModelView;

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
			playerNum++;
		}
		
		model.setPlayers(playerList);
		
		if (playerList.size() == 2) {
			// special rules for a 2 player game
			// adds pre-built emporiums to random cities
			
			LOGGER.info("Building a game with 2 players!");
			
			Player fakePlayer = new Player(-1, 0, 0, null, 0, "Ubaldo");
			List<City> cities = new ArrayList<>(model.getGameBoard().getCities());
			
			Random random = new Random();
			int emporiums = 3 + random.nextInt(7);    // 3 to 9 emporiums
			
			for (int i=0; i<emporiums; i++) {
				int cityIndex = random.nextInt(cities.size());
				if(!cities.get(cityIndex).isEmporiumBuilt(fakePlayer)) {		// just to be sure 
					cities.get(cityIndex).buildEmporium(fakePlayer);
				}
				cities.remove(cityIndex);
			}
		}
		
		LOGGER.info("Creating Controller.");
		controller = new Controller(model);
		
		LOGGER.info("Creating ModelView.");
		modelView = new ModelView(model);
		
		ServerChat chat=new ServerChat(views);
	
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
