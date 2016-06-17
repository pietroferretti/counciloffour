package it.polimi.ingsw.ps14.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.message.ActionMsg;
import it.polimi.ingsw.ps14.message.NewPlayerMsg;
import it.polimi.ingsw.ps14.model.GameLogic;
import it.polimi.ingsw.ps14.model.GamePhase;
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
		View playerView = (View) o;

		if (arg instanceof NewPlayerMsg) {
			// TODO verifica id player
			model.getPlayers().get(0).setName(((NewPlayerMsg) arg).getName());
		}
		
		else if (arg instanceof ActionMsg) {
			
			actionVisitor(playerView, ((ActionMsg) arg).getAction());
			
		} else if (arg instanceof SellAction) {
			
			actionVisitor(playerView, ((ActionMsg) arg).getAction());
		
		} else if (arg instanceof BuyAction) {
			
			actionVisitor(playerView, ((ActionMsg) arg).getAction());
			
		} else {
			
			LOGGER.warning("Message/Action not recognized!");
		
		}
	}
	
	public void actionVisitor(View playerView, Action action) {
		throw new UnsupportedOperationException();
	}
	
	public void actionVisitor(View playerView, TurnAction action) {
		if (model.getGamePhase() == GamePhase.TURNS) {
			if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
				if (model.getCurrentTurnState().isActionLegal(action, null)) {
					model.setCurrentTurnState(model.getCurrentTurnState().executeAction(action, null));
					if (model.getCurrentTurnState() instanceof EndTurnState) {

						if (model.getPlayerOrder().isEmpty()) {
							model.setGamePhase(GamePhase.MARKET);
							List<Player> marketPlayers = new ArrayList<>(players);
							Collections.shuffle(marketPlayers);
							model.setPlayerOrder(marketPlayers);
							model.nextPlayer();

						} else {
							model.nextPlayer();
						}
					}

					if (model.getCurrentPlayer().getNumEmporiums() >= 10) {
//						playerView.print("You built 10 emporiums. The game is about to end.");
						System.out.println("Final turns!");
						model.setGamePhase(GamePhase.FINALTURNS);
						// set playerorder = tutti i giocatori tranne questo
						// che ha finito
						model.nextPlayer();
					}
				} else {
//					playerView.print("You cannot do this action now!"); // TODO
																		// details
				}
			} else {
//				playerView.print("It's not your turn! Current player: " + model.getCurrentPlayer().toString());
			}

		} else if (model.getGamePhase() == GamePhase.MARKET) {
//			playerView.print("You cannot do that action during the Market phase");

		} else if (model.getGamePhase() == GamePhase.FINALTURNS) {
			// TODO
			if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
				if (model.getCurrentTurnState().isActionLegal(action, null)) {
					model.setCurrentTurnState(model.getCurrentTurnState().executeAction(action, null));
					if (model.getCurrentTurnState() instanceof EndTurnState) {
						if (model.getPlayerOrder().isEmpty()) {

							System.out.println("The game has ended.");
							GameLogic.distributeEndGamePoints(model.getPlayers());
							Player winner = GameLogic.findWinner(model.getPlayers());
							System.out.println("*** " + winner.getName() + " is the winner! ***");
							// TODO aggiungi dettagli e più :D

						} else {
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
	
	public void actionVisitor(View playerView, SellAction action) {
		
		if (model.getGamePhase() == GamePhase.MARKET) {
			if (model.getCurrentMarketState() == MarketState.SELLING) {
				if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
					if (action.isValid(model)) {
						action.sell();//TODO add parameter market. Market should be created at each beginning of MarketPhase in the game
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
		
		if (model.getGamePhase() == GamePhase.MARKET) {
			if (model.getCurrentMarketState() == MarketState.BUYING) {
				if (playerView.getPlayerID() == model.getCurrentPlayer().getId()) {
					if (action.isValid(model, market)) {
						action.buy(market);
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
