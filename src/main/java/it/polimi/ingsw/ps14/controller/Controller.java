package it.polimi.ingsw.ps14.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.message.NewPlayerMsg;
import it.polimi.ingsw.ps14.message.fromClient.TurnActionMsg;
import it.polimi.ingsw.ps14.model.GameLogic;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.TurnAction;
import it.polimi.ingsw.ps14.model.actions.market.BuyAction;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.view.View;

/**
 * test
 * @author peter
 *
 */
public class Controller implements Observer {
	private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

	private Model model;
	private List<View> views;
	private List<Player> players;

	public Controller(Model model, List<View> views) {
		this.model = model;
		this.views = views;
		this.players = model.getPlayers();
		Collections.shuffle(players);

		model.setGamePhase(GamePhase.TURNS);
		model.setCurrentTurnState(new InitialTurnState());
		model.setPlayerOrder(players);
		model.nextPlayer();
		model.setCurrentMarketState(MarketState.END);

	}

	/**
	 * @param o
	 *            - a View that notified the controller
	 * @param arg
	 *            - a Message or Action
	 */
	@Override
	public void update(Observable o, Object arg) {
		View serverView = (View) o;

		if (arg instanceof NewPlayerMsg) {
			// TODO verifica id player
			model.getPlayers().get(0).setName(((NewPlayerMsg) arg).getName());
		}
		
		else if (arg instanceof TurnActionMsg) {
			
			// do different things depending on the action
			actionVisitor(serverView, ((TurnActionMsg) arg).getAction());
			
		} else {
			
			LOGGER.warning("Message not recognized!");
		
		}
	}
	
	public void actionVisitor(View serverView, Action action) {
		
		// no specific action recognized
		throw new UnsupportedOperationException();
	
	}
	
	/**
	 * Called if the action sent by the view is a TurnAction.
	 * Does checks and executes the action, updates the state of the game phases and turns.
	 * @param serverView
	 * @param action 
	 */
	public void actionVisitor(View serverView, TurnAction action) {
		
		// if we're in the regular turns phase (i.e. not market)
		if (model.getGamePhase() == GamePhase.TURNS) {
		
			// checks if it's the turn of the player that sent the action
			if (serverView.getPlayerID() == model.getCurrentPlayer().getId()) {
			
				// checks if we are in the right state to execute this action (e.g. we can still perform a Main Action)
				if (model.getCurrentTurnState().isActionLegal(action, model)) {
				
					// executes the action and updates the state
					model.setCurrentTurnState(model.getCurrentTurnState().executeAction(action, model));
					
					// checks if a player has built all 10 emporiums
					if (model.getCurrentPlayer().getNumEmporiums() >= 10) {
						
//						playerView.print("You built 10 emporiums. The game is about to end.");
						System.out.println("Final turns!");
					
						model.setGamePhase(GamePhase.FINALTURNS);

						// find this player's position in the players list
						int index = -1;
						for (Player player : players) {
							if (serverView.getPlayerID() == player.getId()) {
								index = players.indexOf(player);
							}
						}
						
						// sets the final players as all the players except the one that built 10 emporiums
						// the turns are in the usual order, starting from the next player
						List<Player> finalPlayers = new ArrayList<>();
						finalPlayers.addAll(players.subList(index+1, players.size()));
						finalPlayers.addAll(players.subList(0, index));
						model.setPlayerOrder(finalPlayers);
						model.nextPlayer();
						
					} else {
						
						// if the action ended the turn
						if (model.getCurrentTurnState() instanceof EndTurnState) {
		
							// if no more players have to play their turn in this phase
							if (model.getPlayerOrder().isEmpty()) {
								
								// the turns phase has ended, the market phase starts 
								model.setGamePhase(GamePhase.MARKET);
								model.setMarket(new Market());
								
								List<Player> marketPlayers = new ArrayList<>(players);
								Collections.shuffle(marketPlayers);
								model.setPlayerOrder(marketPlayers);
								model.nextPlayer();
		
							} else {
								
								// it's the next player's turn
								model.nextPlayer();
							
							}
					}


					}
				} else {
//					playerView.print("You cannot do this action now!"); // TODO
																		// details
				}
			} else {
//				playerView.print("It's not your turn! Current player: " + model.getCurrentPlayer().toString());
			}

		} else if (model.getGamePhase() == GamePhase.MARKET) {
			
			// players cannot perform TurnActions during the market phase
//			playerView.print("You cannot do that action during the Market phase");

		} else if (model.getGamePhase() == GamePhase.FINALTURNS) {

			// checks if it's the turn of the player that sent the action
			if (serverView.getPlayerID() == model.getCurrentPlayer().getId()) {
				
				// checks if we are in the right state to execute this action (e.g. we can still perform a Main Action)
				if (model.getCurrentTurnState().isActionLegal(action, null)) {
					
					// executes the action and updates the state
					model.setCurrentTurnState(model.getCurrentTurnState().executeAction(action, null));
					
					// if the action ended the player's turn
					if (model.getCurrentTurnState() instanceof EndTurnState) {
						
						// if no more players have to play their turn
						if (model.getPlayerOrder().isEmpty()) {

							// the game has ended, awarding final points and finding the winner
							model.setGamePhase(GamePhase.END);
							System.out.println("The game has ended.");
							
							GameLogic.distributeEndGamePoints(model.getPlayers());
							
							Player winner = GameLogic.findWinner(model.getPlayers());
							
							System.out.println("*** " + winner.getName() + " is the winner! ***");
							// TODO aggiungi dettagli e più :D

						} else {
							
							// it's the next player's turn
							model.nextPlayer();
						}
					}

				} else {
//					playerView.print("You cannot do this action now!"); // TODO
																		// details
				}
			} else {
//				playerView.print("It's not your turn! Current player: " + model.getCurrentPlayer().getName());
			}

		} else if (model.getGamePhase() == GamePhase.END) {
//			playerView.print("The game has ended, you cannot perform this action.");
		}
		
	}
	
	/**
	 * Called if the action sent by the view is a SellAction (market phase).
	 * Does checks and executes the action, updates the state of the game and market phases.
	 * @param serverView
	 * @param action
	 */
	public void actionVisitor(View serverView, SellAction action) {
		
		// checks if we're actually in the market phase
		if (model.getGamePhase() == GamePhase.MARKET) {
			
			// checks if we are in the market selling phase
			if (model.getCurrentMarketState() == MarketState.SELLING) {
				
				// checks if it's the turn of the player that sent the action
				if (serverView.getPlayerID() == model.getCurrentPlayer().getId()) {
					
					if (action.isValid(model)) {
					
						action.execute(model.getMarket());
				
					} else {
				
//						playerView.print("You cannot do this action now!"); // TODO
																			// details
					}
				} else {
					
//					playerView.print("It's not your turn! Current player: " + model.getCurrentPlayer().getName());
				
				}
			} else {

//				playerView.print("You cannot sell now.");

			}
		} else {
			
//			playerView.print("You can only do this during the Market phase.");
		
		}
		
	}

	public void actionVisitor(View playerView, BuyAction action) {
		
		// checks if we're actually in the market phase
		if (model.getGamePhase() == GamePhase.MARKET) {
			if (model.getCurrentMarketState() == MarketState.BUYING) {
				if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
					if (action.isValid(model, model.getMarket())) {
						action.execute(model, model.getMarket());
						// action cambia lo stato del market se
						// tutti hanno finito:
						// market -> END
						// gamephase -> TURNS
						// currentstate -> InitialTurnState
						// playerOrder -> new ArrayList<>(players);
						// nextPlayer();
					} else {
//						playerView.print("You cannot do this action now!"); // TODO
																			// details
					}
				} else {
//					playerView.print("It's not your turn! Current player: " + model.getCurrentPlayer().getName());
				}
			} else {
//				playerView.print("You cannot buy now.");
			}
		} else {
//			playerView.print("You can only do this during the Market phase.");
		}
	
	}
}
