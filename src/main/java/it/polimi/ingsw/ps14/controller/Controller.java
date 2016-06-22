package it.polimi.ingsw.ps14.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.message.fromclient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromclient.DoneBuyingMsg;
import it.polimi.ingsw.ps14.message.fromclient.NobilityRequestAnswerMsg;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellMsg;
import it.polimi.ingsw.ps14.message.fromclient.TurnActionMsg;
import it.polimi.ingsw.ps14.message.fromserver.ErrorMsg;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.WaitingFor;
import it.polimi.ingsw.ps14.model.actions.TurnAction;
import it.polimi.ingsw.ps14.model.actions.market.BuyAction;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;
import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.bonus.SpecialNobilityBonus;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.view.View;

/**
 *
 */
public class Controller implements Observer {
	private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

	private Model model;
	private List<Player> players;
	private List<Player> marketPlayers;

	public Controller(Model model) {
		this.model = model;
		this.players = model.getPlayers();
		Collections.shuffle(players);

		model.setPlayerOrder(players);
		model.loadNextPlayer();

	}

	/**
	 * @param o
	 *            - a View that notified the controller
	 * @param arg
	 *            - a Message or Action
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("nel controller");

		View serverView = (View) o;
		
		LOGGER.info(String.format("Received object %s", arg));
		
		if (arg instanceof PlayerNameMsg) {
			
			updatePlayerName(serverView, ((PlayerNameMsg) arg).getPlayerName());
			
		} else if (arg instanceof TurnActionMsg) {
			
			executeTurnAction(serverView, ((TurnActionMsg) arg).getAction());
			
		} else if (arg instanceof SellMsg) {
		
			executeSellAction(serverView, ((SellMsg) arg).getAction());
			
		} else if (arg instanceof BuyMsg) {
			
			executeBuyAction(serverView, ((BuyMsg) arg).getAction());
			
		} else if (arg instanceof DoneBuyingMsg) {
			
			doneBuying(serverView);
			
		} else if (arg instanceof NobilityRequestAnswerMsg) {
			
			handleNobilityAnswer(serverView, ((NobilityRequestAnswerMsg) arg).getIDs());
		
		} else {
			
			LOGGER.warning("Message not recognized!");
		
		}
	}
	
	private void updatePlayerName(View playerView, String name) {
		
		model.id2player(playerView.getPlayerID()).setName(name);
		
	}
	
	/**
	 * Called if the action sent by the view is a TurnAction.
	 * Does checks and executes the action, updates the state of the game phases and turns.
	 * @param playerView
	 * @param action 
	 */
	private void executeTurnAction(View playerView, TurnAction action) {
		
		// if we're in the regular turns phase (i.e. not market)
		if (model.getGamePhase() == GamePhase.TURNS) {
		
			// checks if it's the turn of the player that sent the action
			if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
			
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
							if (playerView.getPlayerID() == player.getId()) {
								index = players.indexOf(player);
							}
						}
						
						// sets the final players as all the players except the one that built 10 emporiums
						// the turns are in the usual order, starting from the next player
						List<Player> finalPlayers = new ArrayList<>();
						finalPlayers.addAll(players.subList(index+1, players.size()));
						finalPlayers.addAll(players.subList(0, index));
						model.setPlayerOrder(finalPlayers);
						model.loadNextPlayer();
						
					} else {
						
						// if the action ended the turn
						if (model.getCurrentTurnState() instanceof EndTurnState) {
		
							// if no more players have to play their turn in this phase
							if (model.getPlayerOrder().isEmpty()) {
								
								// the turns phase has ended, the market phase starts 
								model.setGamePhase(GamePhase.MARKET);
								model.setCurrentMarketState(MarketState.SELLING);
								model.getMarket().clear();
								
								marketPlayers = new ArrayList<>(players);
								Collections.shuffle(marketPlayers);
								model.setPlayerOrder(marketPlayers);
								model.setCurrentPlayer(model.getNextPlayer());
								model.getPlayerOrder().removeFirst();
		
							} else {
								
								// it's the next player's turn
								model.setCurrentTurnState(new InitialTurnState());
								model.loadNextPlayer();
							
							}
						}
					}
					
				} else {
					sendErrorMsg(playerView, "You cannot do this action now!");
				}
				
			} else {
				sendErrorMsg(playerView, "It's not your turn! Current player: " + model.getCurrentPlayer().getName());
			}

		} else if (model.getGamePhase() == GamePhase.MARKET) {
			
			// players cannot perform TurnActions during the market phase
			sendErrorMsg(playerView, "You cannot do that action during the Market phase");

		} else if (model.getGamePhase() == GamePhase.FINALTURNS) {

			// checks if it's the turn of the player that sent the action
			if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
				
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
							
							distributeEndGamePoints(model.getPlayers());
							
							Player winner = findWinner(model.getPlayers());
							
							System.out.println("*** " + winner.getName() + " is the winner! ***");
							// TODO rifare come messaggio

						} else {
							
							// it's the next player's turn
							model.loadNextPlayer();
							model.setCurrentTurnState(new InitialTurnState());
						}
					}

				} else {
					
					sendErrorMsg(playerView, "You cannot do this action now!"); 																// details
				
				}
			} else {
				
				sendErrorMsg(playerView, "It's not your turn! Current player: " + model.getCurrentPlayer().getName());
			
			}

		} else if (model.getGamePhase() == GamePhase.END) {
			
			sendErrorMsg(playerView, "The game has ended, you cannot perform this action.");
		
		}
		
	}
	
	/**
	 * Called if the action sent by the view is a SellAction (market phase).
	 * Does checks and executes the action, updates the state of the game and market phases.
	 * @param playerView
	 * @param action
	 */
	public void executeSellAction(View playerView, SellAction action) {
		
		// checks if we're actually in the market phase
		if (model.getGamePhase() == GamePhase.MARKET) {
			
			// checks if we are in the market selling phase
			if (model.getCurrentMarketState() == MarketState.SELLING) {
				
				// checks if it's the turn of the player that sent the action
				if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
					
					if (action.isValid(model)) {
					
						action.execute(model.getMarket());

						// if no more players have to play their turn
						if (model.getPlayerOrder().isEmpty()) {

							model.setCurrentMarketState(MarketState.BUYING);
							model.setPlayerOrder(marketPlayers);
							model.setCurrentPlayer(model.getNextPlayer());
							model.getPlayerOrder().removeFirst();

						} else {
							
							// it's the next player's turn
							model.loadNextPlayer();
						}
						
					} else {
				
						sendErrorMsg(playerView, "You cannot do this action now!");																	// details
					}
				} else {
					
					sendErrorMsg(playerView, "It's not your turn! Current player: " + model.getCurrentPlayer().getName());
				
				}
			} else {

				sendErrorMsg(playerView, "You cannot sell now.");

			}
		} else {
			
			sendErrorMsg(playerView, "You can only do this during the Market phase.");
		
		}
		
	}

	private void executeBuyAction(View playerView, BuyAction action) {
		
		// checks if we're actually in the market phase
		if (model.getGamePhase() == GamePhase.MARKET) {
			
			if (model.getCurrentMarketState() == MarketState.BUYING) {
				
				if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
					
					if (action.isValid(model)) {
						
						action.execute(model);
						
						model.queueAndLoadNextPlayer();
						
					} else {
						
						sendErrorMsg(playerView, "You cannot do this action now!");
						
					}
					
				} else {
					
					sendErrorMsg(playerView, "It's not your turn! Current player: " + model.getCurrentPlayer().getName());
				
				}
				
			} else {
				
				sendErrorMsg(playerView, "You cannot buy now.");
			
			}
			
		} else {
			
			sendErrorMsg(playerView, "You can only do this during the Market phase.");
		
		}
	
	}
	
	private void doneBuying(View playerView) {
		
		// checks if we're actually in the market phase
		if (model.getGamePhase() == GamePhase.MARKET) {
			
			if (model.getCurrentMarketState() == MarketState.BUYING) {
				
				if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
					
					if (!model.getPlayerOrder().isEmpty()) {
					
						model.loadNextPlayer();
					
					} else {
						
						model.getMarket().clear();
						model.setGamePhase(GamePhase.TURNS);
						model.setCurrentTurnState(new InitialTurnState());
						model.setPlayerOrder(players);
						model.loadNextPlayer();
						
					}
					
					
				} else {
					
					sendErrorMsg(playerView, "It's not your turn! Current player: " + model.getCurrentPlayer().getName());
				
				}
				
			} else {
				
				sendErrorMsg(playerView, "You cannot do this now.");
			
			}
			
		} else {
			
			sendErrorMsg(playerView, "You can only do this during the Market phase.");
		
		}
		
	}
	
	private void handleNobilityAnswer(View playerView, List<String> chosenIDs) {
		
		if (model.getWaitingFor() == WaitingFor.NOTHING) {
			
			sendErrorMsg(playerView, "You cannot answer a nobility bonus request if there isn't one.");
			
		} else {
			
			if (chosenIDs == null || chosenIDs.isEmpty()) {
				
				sendErrorMsg(playerView, "You didn't choose anything!");
				
			} else if (!(idsInAvailableChoices(chosenIDs) && allIDsAreDifferent(chosenIDs) && (chosenIDs.size() <= model.getWaitingForHowMany()))) {
				
				sendErrorMsg(playerView, "Invalid choices.");
				
			} else {
				
				if (model.getWaitingFor() == WaitingFor.TAKEPERMIT) {
					
					handleTakePermit(playerView, chosenIDs);
					
				} else if (model.getWaitingFor() == WaitingFor.FROMPERMITS) {
					
					handleFromPermits(playerView, chosenIDs);
					
				} else if (model.getWaitingFor() == WaitingFor.FROMTOKENS) {
					
					handleFromTokens(playerView, chosenIDs);
					
				}
				
				model.setAvailableChoices(new HashMap<>());
				model.setWaitingForHowMany(0);
				model.setWaitingFor(WaitingFor.NOTHING);
				
				Player player = model.id2player(playerView.getPlayerID());
				applyBonusesToDo(player);

				
			}
			
		}
		
	}
	
	private boolean idsInAvailableChoices(List<String> chosenIDs) {

		for (String id : chosenIDs) {
			if (!model.getAvailableChoices().containsKey(id)) {
				return false;
			}
		}
		
		return true;
		
	}
	
	private boolean allIDsAreDifferent(List<String> chosenIDs) {
		
		HashSet<String> uniqueIDs = new HashSet<>(chosenIDs);
		return chosenIDs.size() == uniqueIDs.size();
		
	}
	
	private void handleTakePermit(View playerView, List<String> chosenIDs) {
		
		Player player = model.id2player(playerView.getPlayerID());
		
		Region region = null;
		BusinessPermit permit = null;
		BusinessPermit tempPermit;
		
		for (String idString : chosenIDs) {
			for (Region r : model.getGameBoard().getRegions()) {
				tempPermit = model.id2permit(Integer.valueOf(idString), region);
				if (tempPermit != null) {
					permit = tempPermit;
					region = r;
				}
			}
			
			if (region != null) {
				// acquire permit
				player.getBusinessHand().acquireBusinessPermit(permit);

				// change face up card in region
				region.getBusinessPermits().substituteCard(permit);
				// notifies changes in business deck
				region.setBusinessPermits();

				permit.getBonusList().useBonus(player, model);
				
			} else {
				
				LOGGER.warning("Something went wrong when interpreting the answer to a nobility request!");
				sendErrorMsg(playerView, "Invalid answer!");
				
			}
		}
	}
	
	private void handleFromPermits(View playerView, List<String> permitIDs) {
		
		Player player = model.id2player(playerView.getPlayerID());
		
		BusinessPermit permit;
		Bonus bonus;
		for (String permitID : permitIDs) {
			
			permit = player.getBusinessHand().id2permit(Integer.valueOf(permitID));
			
			if (permit != null) {
				bonus = permit.getBonusList();
				
				if (bonus != null) {
					bonus.useBonus(player, model);
				}
				
			} else {
				
				LOGGER.warning("Something went wrong when interpreting the answer to a nobility request!");
				sendErrorMsg(playerView, "Invalid answer!");
				
			}
		}		
	}
	
	private void handleFromTokens(View playerView, List<String> cityNames) {
		
		Player player = model.id2player(playerView.getPlayerID());
		
		City city;
		Bonus bonus;
		for (String cityName : cityNames) {
			
			city = model.name2city(cityName);
			
			if (city != null) {
				bonus = city.getToken();
				
				if (bonus != null) {
					bonus.useBonus(player, model);
				}
				
			} else {
				
				LOGGER.warning("Something went wrong when interpreting the answer to a nobility request!");
				sendErrorMsg(playerView, "Invalid answer!");
				
			}
		}
		
	}
	
	private void applyBonusesToDo(Player player) {
		Bonus bonus;
		while (!model.getBonusesToDo().isEmpty()) {
			
			bonus = model.popBonusToDo();
			if (!(bonus instanceof SpecialNobilityBonus)) {
				
				bonus.useBonus(player, model);
				
			} else {
				
				bonus.useBonus(player, model);		// sets the WaitingFor and related states appropriately
				break;								// goes back to waiting for a message from the client
													// leaving the remaining bonuses where they are
			}
			
		}
	}
	
	private void sendErrorMsg(View playerView, String errorMessage) {
		model.setMessage(new ErrorMsg(playerView.getPlayerID(), errorMessage));
	}
	
	private void distributeEndGamePoints(List<Player> players) {
		/*
		 * Assegna punti aggiuntivi ai giocatori alla fine del gioco
		 * NobilityTrack: 5 punti al primo, 2 al secondo - se più giocatori sono
		 * primi, 5 punti solo a loro - se più giocatori sono secondi, 2 punti
		 * ciascuno --> BusinessPermits: 3 punti per il giocatore che ne ha
		 * comprati di più
		 *
		 */

		// Find players with the highest (or second highest) nobility level
		List<List<Player>> highNobilityLists;
		List<Player> firstHighNobility;
		List<Player> secondHighNobility;

		highNobilityLists = findHighNobles(players);
		firstHighNobility = highNobilityLists.get(0);
		secondHighNobility = highNobilityLists.get(1);

		// Give points to the players with the highest nobility
		awardPointsNobility(firstHighNobility, secondHighNobility);

		// Find players with the most permits
		List<Player> mostPermits = findMostPermits(players);

		// Give points to the players with the most permits
		for (Player permitMaster : mostPermits) {
			permitMaster.addPoints(3);
		}
	}

	// I used a list of lists to return two values:
	private List<List<Player>> findHighNobles(List<Player> players) {
		List<Player> firstHighNobility = new ArrayList<>();
		List<Player> secondHighNobility = new ArrayList<>();
		List<List<Player>> result = new ArrayList<>();

		// Initialize lists by checking the first two players
		if (players.get(0).getLevel() > players.get(1).getLevel()) {
			firstHighNobility.add(players.get(0));
			secondHighNobility.add(players.get(1));
		} else if (players.get(0).getLevel() == players.get(1).getLevel()) {
			firstHighNobility.add(players.get(0));
			firstHighNobility.add(players.get(1));
		} else {
			firstHighNobility.add(players.get(1));
			secondHighNobility.add(players.get(0));
		}

		// Find players with first and second highest nobility
		for (int i = 2; i < players.size(); i++) {
			if (players.get(i).getLevel() > firstHighNobility.get(0).getLevel()) {
				firstHighNobility.clear();
				firstHighNobility.add(players.get(i));
			} else if (players.get(i).getLevel() == firstHighNobility.get(0).getLevel()) {
				firstHighNobility.add(players.get(i));
			} else if (players.get(i).getLevel() > secondHighNobility.get(0).getLevel()) {
				secondHighNobility.clear();
				secondHighNobility.add(players.get(i));
			} else if (players.get(i).getLevel() == secondHighNobility.get(0).getLevel()) {
				secondHighNobility.add(players.get(i));
			}
		}

		result.add(firstHighNobility);
		result.add(secondHighNobility);
		return result;
	}

	private void awardPointsNobility(List<Player> firstHighNobility, List<Player> secondHighNobility) {
		if (firstHighNobility.size() == 1) {
			firstHighNobility.get(0).addPoints(5);
			for (Player secondHighNoble : secondHighNobility) {
				secondHighNoble.addPoints(2);
			}
		} else {
			for (Player firstHighNoble : firstHighNobility) {
				firstHighNoble.addPoints(5);
			}
		}
	}

	private List<Player> findMostPermits(List<Player> players) {
		List<Player> mostPermits = new ArrayList<>();

		mostPermits.add(players.get(0));
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getBusinessHand().getNumberOfPermits() > mostPermits.get(0).getBusinessHand()
					.getNumberOfPermits()) {
				mostPermits.clear();
				mostPermits.add(players.get(i));
			} else if (players.get(i).getLevel() == mostPermits.get(0).getLevel()) {
				mostPermits.add(players.get(i));
			}
		}

		return mostPermits;
	}

	/** 
	 * Find the winner by comparing points (and assistants and cards if
	 * there's a draw)
	 */
	private Player findWinner(List<Player> players) {
		Player winner;

		winner = players.get(0);
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getPoints() > winner.getPoints()) {
				winner = players.get(i);
			} else if (players.get(i).getPoints() == winner.getPoints()) {
				if (players.get(i).getAssistants() > winner.getAssistants()) {
					winner = players.get(i);
				} else if (players.get(i).getAssistants() == winner.getAssistants()) {
					if (players.get(0).getNumberOfCards() > winner.getNumberOfCards()) {
						winner = players.get(i);
					} else if (players.get(0).getNumberOfCards() == winner.getNumberOfCards()) {
						// TODO: se proprio c'è parità assoluta bisogna
						// riscriverlo in modo che ritorni una lista di
						// vincitori, in alternativa ritorna il primo per
						// default lol
					}
				}
			}
		}

		return winner;
	}
	
	
}
