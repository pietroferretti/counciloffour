package it.polimi.ingsw.ps14.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.message.DisconnectionMsg;
import it.polimi.ingsw.ps14.message.fromclient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromclient.DoneBuyingMsg;
import it.polimi.ingsw.ps14.message.fromclient.NobilityRequestAnswerMsg;
import it.polimi.ingsw.ps14.message.fromclient.PlayerNameMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellMsg;
import it.polimi.ingsw.ps14.message.fromclient.SellNoneMsg;
import it.polimi.ingsw.ps14.message.fromclient.TurnActionMsg;
import it.polimi.ingsw.ps14.message.fromserver.ErrorMsg;
import it.polimi.ingsw.ps14.message.fromserver.GameEndedMsg;
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
import it.polimi.ingsw.ps14.server.ServerView;

/**
 * Receives messages from the ServerView and decides what to do. Decides if and
 * how to modify the Model when it receives an action or a message. Updates the
 * state of the game after modifying the Model. This class is the only one that
 * modifies the Model.
 * 
 * @see it.polimi.ingsw.ps14.model.Model Model
 * @see it.polimi.ingsw.ps14.server.ServerView ServerView
 * @see it.polimi.ingsw.ps14.message.Message Message
 * @see it.polimi.ingsw.ps14.model.State State
 */
public class Controller implements Observer {
	private static final Logger LOGGER = Logger.getLogger(Controller.class
			.getName());

	private static final long TURNCOUNTDOWN = 30; 	// 30 seconds

	private Model model;
	private List<Player> players;

	private Timer turnTimer;

	/**
	 * Build the controller with model. Saves a reference to the model, and a
	 * list of all the players. The list is randomized, and represents the order
	 * of the player turns. The controller sets the order of the players in the
	 * model.
	 * 
	 * @param model
	 *            the model of the game
	 */
	public Controller(Model model) {
		this.model = model;
		this.players = model.getPlayers();
		Collections.shuffle(players);

		model.setPlayerOrder(players);
		model.loadNextPlayer();

		resetTimer();

	}

	/**
	 * Interprets a message sent from a ServerView. Modifies the model and
	 * updates its state if needed.
	 * 
	 * @param o
	 *            - a ServerView that notified the controller
	 * @param arg
	 *            - a Message
	 */
	@Override
	public void update(Observable o, Object arg) {

		ServerView serverView = (ServerView) o;

		LOGGER.info(String.format("Received object %s", arg));

		if (arg instanceof PlayerNameMsg) {

			updatePlayerName(serverView, ((PlayerNameMsg) arg).getPlayerName());

		} else if (arg instanceof TurnActionMsg) {

			resetTimer();
			executeTurnAction(serverView, ((TurnActionMsg) arg).getAction());

		} else if (arg instanceof SellMsg) {

			resetTimer();
			executeSellAction(serverView, ((SellMsg) arg).getAction());

		} else if (arg instanceof BuyMsg) {

			resetTimer();
			executeBuyAction(serverView, ((BuyMsg) arg).getAction());

		} else if (arg instanceof DoneBuyingMsg) {

			resetTimer();
			doneBuying(serverView);

		} else if (arg instanceof SellNoneMsg) {

			resetTimer();
			sellNone(serverView);

		} else if (arg instanceof NobilityRequestAnswerMsg) {

			resetTimer();
			handleNobilityAnswer(serverView,
					((NobilityRequestAnswerMsg) arg).getIDs());

		} else if (arg instanceof DisconnectionMsg) {

			removeFromActivePlayers(serverView);

		} else {

			LOGGER.warning("Message not recognized!");

		}
	}

	/**
	 * Sets the player name in the Model.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param name
	 *            the name of the player
	 */
	private void updatePlayerName(ServerView playerView, String name) {

		model.id2player(playerView.getPlayerID()).setName(name);

	}

	/**
	 * Called if the action sent by the view is a TurnAction. Does checks and
	 * executes the action, updates the state of the game phases and turns.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param action
	 *            the action to be executed
	 */
	private void executeTurnAction(ServerView playerView, TurnAction action) {

		switch (model.getGamePhase()) {
		case TURNS:

			turnActionDuringTurns(playerView, action);
			break;

		case MARKET:

			// players cannot perform TurnActions during the market phase
			sendErrorMsg(playerView,
					"You cannot do that action during the Market phase");
			break;

		case FINALTURNS:

			turnActionDuringFinalTurns(playerView, action);
			break;

		case END:

			sendErrorMsg(playerView,
					"The game has ended, you cannot perform this action.");
			break;

		default:

			LOGGER.warning("Trying to execute a TurnAction when the gamephase is null!!");
			break;
		}

	}

	/**
	 * Called if the action is a TurnAction and it's the turns phase. Updates
	 * the model, and switches to the market phase if all the players played one
	 * turn. Switches to the final turns phase if a player built 10 emporiums.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param action
	 *            the action to be executed
	 */
	private void turnActionDuringTurns(ServerView playerView, TurnAction action) {

		// checks if it's the turn of the player that sent the action
		if (playerView.getPlayerID() != model.getCurrentPlayer().getId()) {
			sendErrorMsg(playerView, "It's not your turn! Current player: "
					+ model.getCurrentPlayer().getName());
			return;
		}

		// checks if we are in the right state to execute this action (e.g. we
		// can still perform a Main Action)
		if (!model.getCurrentTurnState().isActionLegal(action, model)) {
			sendErrorMsg(playerView, "You cannot do this action now!");
			return;
		}

		// executes the action and updates the state
		model.setCurrentTurnState(model.getCurrentTurnState().executeAction(
				action, model));

		// checks if a player has built all 10 emporiums
		if (model.getCurrentPlayer().getNumEmporiums() >= 10) {

			// TODO playerView.print("You built 10 emporiums. The game is about
			// to end.");
			System.out.println("Final turns!");

			model.setGamePhase(GamePhase.FINALTURNS);
			model.setCurrentTurnState(new InitialTurnState());

			// find this player's position in the players list
			int index = -1;
			for (Player player : players) {
				if (playerView.getPlayerID() == player.getId()) {
					index = players.indexOf(player);
				}
			}

			if (index == -1) {
				LOGGER.warning("Something went wrong when comparing player IDs in the controller!!");
				return;
			}

			// sets the final players as all the players except the one that
			// built 10 emporiums
			// the turns are in the usual order, starting from the next player
			List<Player> finalPlayers = new ArrayList<>();
			finalPlayers.addAll(players.subList(index + 1, players.size()));
			finalPlayers.addAll(players.subList(0, index));
			model.setPlayerOrder(finalPlayers);
			model.loadNextPlayer();

		} else {
			// the player didn't build his 10th emporium

			// if the action ended the turn
			if (model.getCurrentTurnState() instanceof EndTurnState) {

				// if no more players have to play their turn in this phase
				if (model.getPlayerOrder().isEmpty()) {

					// the turns phase has ended, the market phase starts
					model.setGamePhase(GamePhase.MARKET);
					model.setCurrentMarketState(MarketState.SELLING);
					model.getMarket().clear();

//					marketPlayers = new ArrayList<>(players);
//					Collections.shuffle(marketPlayers);
//					model.setPlayerOrder(marketPlayers);
					model.setPlayerOrder(players);
					model.loadNextPlayer();

				} else {

					// it's the next player's turn
					model.setCurrentTurnState(new InitialTurnState());
					model.loadNextPlayer();

				}
			}
		}
	}

	/**
	 * Called if the action is a TurnAction and it's the final turns phase.
	 * Updates the model, and if all the players played their turn finds the
	 * winner and ends the game.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param action
	 *            the action to be executed
	 */
	private void turnActionDuringFinalTurns(ServerView playerView,
			TurnAction action) {

		// checks if it's the turn of the player that sent the action
		if (playerView.getPlayerID() != model.getCurrentPlayer().getId()) {
			sendErrorMsg(playerView, "It's not your turn! Current player: "
					+ model.getCurrentPlayer().getName());
			return;
		}

		// checks if we are in the right state to execute this action (e.g. we
		// can still perform a Main Action)
		if (!model.getCurrentTurnState().isActionLegal(action, model)) {
			sendErrorMsg(playerView, "You cannot do this action now!");
			return;
		}

		// executes the action and updates the state
		model.setCurrentTurnState(model.getCurrentTurnState().executeAction(
				action, model));

		// if the action ended the player's turn
		if (model.getCurrentTurnState() instanceof EndTurnState) {

			// if no more players have to play their turn
			if (model.getPlayerOrder().isEmpty()) {

				// the game has ended, awarding final points and finding the
				// winner
				model.setGamePhase(GamePhase.END);

				System.out.println("The game has ended.");

				// get all the players, including those that disconnected
				List<Player> playerList = model.getPlayers();

				distributeEndGamePoints(playerList);

				List<Player> rankings = findWinners(playerList);
				sendGameEndedMsg(rankings);

			} else {

				// it's the next player's turn
				model.loadNextPlayer();
				model.setCurrentTurnState(new InitialTurnState());
			}
		}
	}

	/**
	 * Called if the action sent by the view is a SellAction and it's the market
	 * selling phase. Updates the model, and if all the players played are done
	 * selling switches to the buying phase.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param action
	 *            the action to be executed
	 */
	private void executeSellAction(ServerView playerView, SellAction action) {

		// checks if we're actually in the market phase
		if (model.getGamePhase() != GamePhase.MARKET) {
			sendErrorMsg(playerView,
					"You can only do this during the Market phase.");
			return;
		}

		// checks if we are in the market selling phase
		if (model.getCurrentMarketState() != MarketState.SELLING) {
			sendErrorMsg(playerView, "You cannot sell now.");
			return;
		}

		// checks if it's the turn of the player that sent the action
		if (playerView.getPlayerID() != model.getCurrentPlayer().getId()) {
			sendErrorMsg(playerView, "It's not your turn! Current player: "
					+ model.getCurrentPlayer().getName());
			return;
		}

		if (!action.isValid(model)) {
			sendErrorMsg(playerView, "You cannot do this action now!"); // details
			return;
		}

		action.execute(model.getMarket());

		// if no more players have to play their turn
		if (model.getPlayerOrder().isEmpty()) {

			model.setCurrentMarketState(MarketState.BUYING);
			List<Player> marketPlayers = new ArrayList<>(players);
			Collections.shuffle(marketPlayers);
			model.setPlayerOrder(marketPlayers);
			model.loadNextPlayer();
//			model.setCurrentPlayer(model.getNextPlayer());
//			model.getPlayerOrder().removeFirst();

		} else {

			// it's the next player's turn
			model.loadNextPlayer();
		}
	}

	/**
	 * Called if the action sent by the view is a BuyAction and it's the market
	 * buying phase. Updates the model, and switches to the next player.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param action
	 *            the action to be executed
	 */
	private void executeBuyAction(ServerView playerView, BuyAction action) {

		// checks if we're actually in the market phase
		if (model.getGamePhase() != GamePhase.MARKET) {
			sendErrorMsg(playerView,
					"You can only do this during the Market phase.");
			return;
		}

		if (model.getCurrentMarketState() != MarketState.BUYING) {
			sendErrorMsg(playerView, "You cannot buy now.");
			return;
		}

		if (playerView.getPlayerID() != model.getCurrentPlayer().getId()) {
			sendErrorMsg(playerView, "It's not your turn! Current player: "
					+ model.getCurrentPlayer().getName());
			return;
		}

		if (action.isValid(model)) {

			action.execute(model);
			model.queueAndLoadNextPlayer();

		} else {

			sendErrorMsg(playerView, "You cannot do this action now!");

		}
	}

	/**
	 * Called when view signals that the player is done buying. Removes the
	 * player from the list of players still buying and if everyones is done
	 * switches to the turns phase.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 */
	private void doneBuying(ServerView playerView) {

		// checks if we're actually in the market phase
		if (model.getGamePhase() != GamePhase.MARKET) {
			sendErrorMsg(playerView,
					"You can only do this during the Market phase.");
			return;
		}

		if (model.getCurrentMarketState() != MarketState.BUYING) {
			sendErrorMsg(playerView, "You cannot do this now.");
			return;
		}

		if (playerView.getPlayerID() != model.getCurrentPlayer().getId()) {
			sendErrorMsg(playerView, "It's not your turn! Current player: "
					+ model.getCurrentPlayer().getName());
			return;
		}

		if (!model.getPlayerOrder().isEmpty()) {

			model.loadNextPlayer();

		} else {

			model.getMarket().clear();
			model.setGamePhase(GamePhase.TURNS);
			model.setCurrentTurnState(new InitialTurnState());
			model.setPlayerOrder(players);
			model.loadNextPlayer();

		}
	}

	private void sellNone(ServerView playerView) {

		// checks if we're actually in the market phase
		if (model.getGamePhase() != GamePhase.MARKET) {
			sendErrorMsg(playerView,
					"You can only do this during the Market phase.");
			return;
		}

		if (model.getCurrentMarketState() != MarketState.SELLING) {
			sendErrorMsg(playerView, "You cannot do this now.");
			return;
		}

		if (playerView.getPlayerID() != model.getCurrentPlayer().getId()) {
			sendErrorMsg(playerView, "It's not your turn! Current player: "
					+ model.getCurrentPlayer().getName());
			return;
		}

		if (!model.getPlayerOrder().isEmpty()) {

			model.loadNextPlayer();

		} else {

			model.setCurrentMarketState(MarketState.BUYING);
			model.setPlayerOrder(players);
			model.loadNextPlayer();
		}
	}

	/**
	 * Called when the player sent a message answering a nobility bonus request.
	 * Updates the model and switches back to the regular turns.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param chosenIDs
	 *            the ids chosen from the available choices
	 * @see it.polimi.ingsw.ps14.model.WaitingFor WaitingFor
	 * @see it.polimi.ingsw.ps14.model.bonus.SpecialNobilityBonus
	 *      SpecialNobilityBonus
	 */
	private void handleNobilityAnswer(ServerView playerView,
			List<String> chosenIDs) {

		if (model.getWaitingFor() == WaitingFor.NOTHING) {

			sendErrorMsg(playerView,
					"You cannot answer a nobility bonus request if there isn't one.");

		} else {

			if (chosenIDs == null || chosenIDs.isEmpty()) {

				sendErrorMsg(playerView, "You didn't choose anything!");

			} else if (!(idsInAvailableChoices(chosenIDs)
					&& allIDsAreDifferent(chosenIDs) && (chosenIDs.size() <= model
					.getWaitingForHowMany()))) {

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

	/**
	 * Checks if the ids sent by the player correspond to objects that can
	 * actually be picked.
	 * 
	 * @param chosenIDs
	 *            a list of ids, as strings
	 * @return {@code true} if the ids are contained in the available choices
	 */
	private boolean idsInAvailableChoices(List<String> chosenIDs) {

		for (String id : chosenIDs) {
			if (!model.getAvailableChoices().containsKey(id)) {
				return false;
			}
		}

		return true;

	}

	/**
	 * Checks if all the ids in the list are different
	 * 
	 * @param chosenIDs
	 *            a list of ids, as strings
	 * @return {@code true} if all the ids are different
	 */
	private boolean allIDsAreDifferent(List<String> chosenIDs) {

		HashSet<String> uniqueIDs = new HashSet<>(chosenIDs);
		return chosenIDs.size() == uniqueIDs.size();

	}

	/**
	 * Called when the controller receives an answer to a "take a free permit"
	 * request. Updates the model.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param chosenIDs
	 *            the list of permit ids chosen
	 */
	private void handleTakePermit(ServerView playerView, List<String> chosenIDs) {

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

	/**
	 * Called when the controller receives an answer to a
	 * "regain a bonus from your permits" request. Updates the model.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param chosenIDs
	 *            the list of permit ids chosen
	 */
	private void handleFromPermits(ServerView playerView, List<String> permitIDs) {

		Player player = model.id2player(playerView.getPlayerID());

		BusinessPermit permit;
		Bonus bonus;
		for (String permitID : permitIDs) {

			permit = player.getBusinessHand().id2permit(
					Integer.valueOf(permitID));

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

	/**
	 * Called when the controller receives an answer to a
	 * "gain a bonus from your cities' tokens" request. Updates the model.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param chosenIDs
	 *            the list of city ids chosen
	 */
	private void handleFromTokens(ServerView playerView, List<String> cityNames) {

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

	/**
	 * Applies all the bonuses that were saved in the model when the game was
	 * suspended for a nobility request.
	 * 
	 * @param player
	 *            the current player
	 */
	private void applyBonusesToDo(Player player) {
		Bonus bonus;
		while (!model.getBonusesToDo().isEmpty()) {

			bonus = model.popBonusToDo();
			if (!(bonus instanceof SpecialNobilityBonus)) {

				bonus.useBonus(player, model);

			} else {

				bonus.useBonus(player, model); // sets the WaitingFor and
												// related states appropriately
				break; // goes back to waiting for a message from the client
						// leaving the remaining bonuses where they are
			}

		}
	}

	/**
	 * "Sends" an error message by putting the message in the {@code message}
	 * field in Model. The Model will notify ModelView, that will in turn send
	 * the message to the right view.
	 * 
	 * @param playerView
	 *            the view associated to the player
	 * @param errorMessage
	 *            the message to send
	 */
	private void sendErrorMsg(ServerView playerView, String errorMessage) {
		model.setMessage(new ErrorMsg(playerView.getPlayerID(), errorMessage));
	}

	/**
	 * Sends the final message at the end of the game, with every info about the
	 * winner and the rankings.
	 * 
	 * @param players
	 *            the list of players ordered by ranking
	 */
	private void sendGameEndedMsg(List<Player> players) {

		List<List<String>> endResults = new ArrayList<>();

		for (Player player : players) {
			List<String> playerResult = new ArrayList<>();
			playerResult.add(String.valueOf(player.getId()));
			playerResult.add(player.getName());
			playerResult.add(String.valueOf(player.getPoints()));
			playerResult.add(String.valueOf(player.getAssistants()));
			playerResult.add(String.valueOf(player.getNumberOfCards()));
			playerResult.add(String.valueOf(player.getNumEmporiums()));
			playerResult.add(String.valueOf(player.getLevel()));
			playerResult.add(String.valueOf(player.getNumberOfPermits()));
			playerResult.add(String.valueOf(player.getCoins()));

			endResults.add(playerResult);
		}

		GameEndedMsg message = new GameEndedMsg(endResults);
		model.setMessage(message);
	}

	/**
	 * Distributes all the final bonus points, for being farther in the nobility
	 * track and for owning the most business permits.
	 * 
	 * <p>
	 * 5 points to the first player, 2 to the second. If more than one player is
	 * first, only they get 5 points. If there's only one first player and more
	 * than one second, the second players get 2 points each.
	 * </p>
	 * 
	 * <p>
	 * For the business permits: 3 points to the player that owns the most
	 * permits.
	 * </p>
	 * 
	 * @param players
	 *            a list of all the players
	 */
	private void distributeEndGamePoints(List<Player> players) {

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

	/**
	 * Finds the players farthest in the nobility track.
	 * 
	 * @param players
	 *            a list of all the players
	 * @return a list that contains a list players placed first, and another for
	 *         the ones placed second
	 */
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
			} else if (players.get(i).getLevel() == firstHighNobility.get(0)
					.getLevel()) {
				firstHighNobility.add(players.get(i));
			} else if (players.get(i).getLevel() > secondHighNobility.get(0)
					.getLevel()) {
				secondHighNobility.clear();
				secondHighNobility.add(players.get(i));
			} else if (players.get(i).getLevel() == secondHighNobility.get(0)
					.getLevel()) {
				secondHighNobility.add(players.get(i));
			}
		}

		result.add(firstHighNobility);
		result.add(secondHighNobility);
		return result;
	}

	/**
	 * Give the players the final points gained from the nobility track.
	 * 
	 * @param firstHighNobility
	 *            a list of the players placed first
	 * @param secondHighNobility
	 *            a list of the players placed second
	 */
	private void awardPointsNobility(List<Player> firstHighNobility,
			List<Player> secondHighNobility) {
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

	/**
	 * Finds the player(s) that owns the biggest number of business permits.
	 * 
	 * @param players
	 * @return the player that owns the most business permits.
	 */
	private List<Player> findMostPermits(List<Player> players) {
		List<Player> mostPermits = new ArrayList<>();

		mostPermits.add(players.get(0));
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getBusinessHand().getNumberOfPermits() > mostPermits
					.get(0).getBusinessHand().getNumberOfPermits()) {
				mostPermits.clear();
				mostPermits.add(players.get(i));
			} else if (players.get(i).getLevel() == mostPermits.get(0)
					.getLevel()) {
				mostPermits.add(players.get(i));
			}
		}

		return mostPermits;
	}

	/**
	 * Ranks the players by comparing points, assistants and cards.
	 * 
	 * @param players
	 *            the list of all the players
	 * @return the players ordered by ranking
	 */
	private List<Player> findWinners(List<Player> players) {

		/**
		 * Compares players by points, then cards, then assistants
		 */
		class PlayerComparator implements Comparator<Player> {

			@Override
			public int compare(Player o1, Player o2) {

				int result = o1.getPoints() - o2.getPoints();
				if (result != 0) {
					return result;
				}

				result = o1.getAssistants() - o2.getAssistants();
				if (result != 0) {
					return result;
				}

				return o1.getNumberOfCards() - o2.getNumberOfCards();
			}
		}

		List<Player> sortedList = new ArrayList<>(players);
		sortedList.sort(new PlayerComparator());
		Collections.reverse(sortedList);
		return sortedList;
	}

	/**
	 * Removes the player associated to the serverview from the list of those
	 * still playing the game. If it's currently the player's turn, the turn
	 * switches to the next player, and to the next phase if needed.
	 * 
	 * @param serverView
	 */
	private void removeFromActivePlayers(ServerView serverView) {

		Player player = model.id2player(serverView.getPlayerID());

		// remove the player from the list of active players
		players.remove(player);

		// remove the player from the next players
		if (model.getPlayerOrder().contains(player)) {
			model.getPlayerOrder().remove(player);
		}

		// load next player (and phase) if it's the turn
		// of the player that disconnected
		if (model.getCurrentPlayer().getId() == player.getId()) {
			nextTurn();
		}

	}

	/**
	 * Loads the next player, and switches to the next phase if needed
	 */
	private void nextTurn() {

		// when the game switches to the next turn because the timer expired,
		// all the pending bonuses and requests are forgotten
		if (model.getWaitingFor() != WaitingFor.NOTHING) {
			model.setWaitingFor(WaitingFor.NOTHING);
			model.setWaitingForHowMany(0);
			model.setAvailableChoices(null);
		}

		if (model.getGamePhase() == GamePhase.TURNS) {

			// if no more players have to play their turn in this phase
			if (model.getPlayerOrder().isEmpty()) {

				// the turns phase has ended, the market phase starts
				model.setGamePhase(GamePhase.MARKET);
				model.setCurrentMarketState(MarketState.SELLING);
				model.getMarket().clear();

				model.setPlayerOrder(players);
				model.loadNextPlayer();

			} else {

				// it's the next player's turn
				model.setCurrentTurnState(new InitialTurnState());
				model.loadNextPlayer();

			}

		} else if (model.getGamePhase() == GamePhase.MARKET) {

			if (model.getCurrentMarketState() == MarketState.SELLING) {

				if (!model.getPlayerOrder().isEmpty()) {

					model.loadNextPlayer();

				} else {

					model.setCurrentMarketState(MarketState.BUYING);
					List<Player> marketPlayers = new ArrayList<>(players);
					Collections.shuffle(marketPlayers);
					model.setPlayerOrder(marketPlayers);
					model.loadNextPlayer();

				}

			} else if (model.getCurrentMarketState() == MarketState.BUYING) {

				if (!model.getPlayerOrder().isEmpty()) {

					model.loadNextPlayer();

				} else {

					model.getMarket().clear();
					model.setGamePhase(GamePhase.TURNS);
					model.setCurrentTurnState(new InitialTurnState());
					model.setPlayerOrder(players);
					model.loadNextPlayer();

				}

			}

		} else if (model.getGamePhase() == GamePhase.FINALTURNS) {

			// if no more players have to play their turn
			if (model.getPlayerOrder().isEmpty()) {

				// the game has ended, awarding final points and finding the
				// winner
				model.setGamePhase(GamePhase.END);

				System.out.println("The game has ended.");

				// get all the players, including those that disconnected
				List<Player> playerList = model.getPlayers();

				distributeEndGamePoints(playerList);

				List<Player> rankings = findWinners(playerList);
				sendGameEndedMsg(rankings);

			} else {

				// it's the next player's turn
				model.loadNextPlayer();
				model.setCurrentTurnState(new InitialTurnState());
			}

		}

		resetTimer();
	}

	/**
	 * Resets the turn timer back to TURNCOUNTDOWN
	 */
	private void resetTimer() {
		if (turnTimer != null) {
			turnTimer.cancel();
		}

		turnTimer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				//TODO TimerScaduto Msg message = new TimerScadutoMsg("tempo
				// scaduto");
				// model.setMessage(message);
				nextTurn();
			}
		};
		turnTimer.schedule(task, TURNCOUNTDOWN * 1000);
	}

}
