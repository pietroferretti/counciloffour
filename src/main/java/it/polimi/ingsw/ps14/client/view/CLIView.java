package it.polimi.ingsw.ps14.client.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.WaitingFor;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

/**
 * --------------------------Command Line Interface-----------------------
 * It acquires infos from player and show the game state each time something
 * has changed
 * 
 */
public class CLIView extends ClientView implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(CLIView.class.getName());

	private Interpreter interpreter;
	private Scanner in;
	
	private List<List<String>> endResults;

	public CLIView(Scanner scanner, String name) {
		super.setPlayerName(name);
		interpreter = new Interpreter(this);
		in = scanner;
	}

	public void print(String string) {
		System.out.println(string);
	}

	@Override
	public void readMessage(Message message) {
		print(message.toString());
	}

	@Override
	public void run() {

		while (true) {

			String input = in.nextLine();
			
			if (input.equalsIgnoreCase("EXIT")) {
				
				break;
			
			} else if (input.equalsIgnoreCase("INSTRUCTIONS") || input.toUpperCase().equalsIgnoreCase("HELP")) {
				showInstructions();

			} else if (!isGameStarted()) {
				
				print("The game hasn't started yet.");

			} else {

				if (!interpreter.parseString(input, getPlayerID())) {
					print("Command not recognized! Retry:");
				}
			}
		}
	}

	@Override
	public void setCommunication(Communication communication) {
		interpreter.setCommunication(communication);
	}

	@Override
	public void setGameStarted(boolean gameStarted) {
		super.setGameStarted(gameStarted);

		if (gameStarted) {
			interpreter.getCommunication().setPlayerName(super.playerID, super.name);
		}

	}
	
	@Override
	public void loadMap(String mapName) {
		// we don't need to do anything on the CLI
		print(String.format("Loaded map %s", mapName));
	}

	@Override
	public void showAvailableAssistant(int update) {
		print("");
		print("Assistant available now: " + update);
	}

	@Override
	public void showAvailableCommands() {

		print("");
		
		if (gameState == null) {

			print("The game hasn't started yet. You can see the available commands with 'instructions'");

		} else if (gameState.getGamePhase() == GamePhase.TURNS) {

			showCommandsTurns();

		} else if (gameState.getGamePhase() == GamePhase.FINALTURNS) {

			print("Final turns!");
			showCommandsTurns();

		} else if (gameState.getGamePhase() == GamePhase.MARKET) {

			showCommandsMarket();

		} else if (gameState.getGamePhase() == GamePhase.END) {

			print("The game has ended. Enter 'results' to see the rankings.");

		}

	}

	private void showCommandsTurns() {

		print("* Turns Phase *");

		if (gameState.getCurrentPlayer().getId() != playerID) {

			print(String.format("It's %s's turn.", gameState.getCurrentPlayer().getName()));

		} else {

			print("It's your turn. Enter a command:");

			if (gameState.getWaitingFor() == WaitingFor.NOTHING) {

				showCommandsTurnStates();

			} else if (gameState.getWaitingFor() == WaitingFor.TAKEPERMIT) {

				print("You got a bonus by moving forward in the nobility track!");
				print(String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

				for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
					print(String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
				}

				print("Choose with 'choose <id1> [<id2> ...]'");

			} else if (gameState.getWaitingFor() == WaitingFor.FROMPERMITS) {

				print("You got a bonus by moving forward in the nobility track!");
				print("You can get the benefits of one of the business permits you own for the second time.");
				print(String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

				for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
					print(String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
				}

				print("Choose with 'choose <id1> [<id2> ...]'");

			} else if (gameState.getWaitingFor() == WaitingFor.FROMTOKENS) {

				print("You got a bonus by moving forward in the nobility track!");
				print("You can get a bonus from one of the cities where you built an emporium");
				print(String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

				for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
					print(String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
				}

				print("Choose with 'choose <id1> [<id2> ...]'");

			}

		}

	}

	private void showCommandsTurnStates() {

		TurnState currTurnState = gameState.getCurrentTurnState();
		if (currTurnState instanceof InitialTurnState) {

			print("Draw a card. Enter 'draw':");

		} else if ((currTurnState instanceof CardDrawnState)
				|| (currTurnState instanceof MainActionDoneTurnState && gameState.getAdditionalMainsToDo() > 0)) {

			print("You can do a main or a quick action. Enter a command:");

		} else if ((currTurnState instanceof QuickActionDoneTurnState)
				|| (currTurnState instanceof MainAndQuickActionDoneTurnState
						&& gameState.getAdditionalMainsToDo() > 0)) {

			print("You can do a main action. Enter a command:");

		} else if (currTurnState instanceof MainActionDoneTurnState && gameState.getAdditionalMainsToDo() == 0) {

			print("You can do a quick action or pass the turn. Enter a command:");

		} else if (currTurnState instanceof MainAndQuickActionDoneTurnState
				&& gameState.getAdditionalMainsToDo() == 0) {

			print("You have already done your main and quick action. You have to pass the turn. Enter 'pass':");

		} else if (currTurnState instanceof EndTurnState) {

			print("End of your turn, you shouldn't be here.");
			LOGGER.warning("Something went wrong, it's still this player's turn even after it ended!!");

		}

	}
	
	private void showCommandsMarket() {

		print("* Market Phase *");

		if (gameState.getCurrentMarketState() == MarketState.SELLING) {

			print("Currently selling.");

			if (gameState.getCurrentPlayer().getId() != playerID) {

				print(String.format("It's %s's turn to sell.", gameState.getCurrentPlayer().getName()));

			} else {

				print("It's your turn to sell.");

			}

		} else if (gameState.getCurrentMarketState() == MarketState.BUYING) {

			print("Currently buying.");

			if (gameState.getCurrentPlayer().getId() != playerID) {

				print(String.format("It's %s's turn to buy.", gameState.getCurrentPlayer().getName()));

			} else {

				print("It's your turn to buy. You can buy something or pass the turn.");

			}

		} else if (gameState.getCurrentMarketState() == MarketState.END) {

			print("The market has ended, you shouldn't be here.");
			LOGGER.warning("Something went wrong, the game is still in the market phase even after it ended!!");

		}

	}

	@Override
	public void showAvailableCouncillor(Map<ColorCouncillor, Integer> updatedAvailableCouncillors) {
		ColorCouncillor[] map = ColorCouncillor.values();
		print("");
		print("Councillors available on the gameboard:");
		for (ColorCouncillor m : map)
			print(m.toString() + " -> " + updatedAvailableCouncillors.get(m).toString());
	}

	@Override
	public void showChatMsg(String author, String text) {
		System.out.println(author+"@CHAT: "+text);
	}

	@Override
	public void showCitiesColorBonuses(int updatedBonusGold, int updatedBonusSilver, int updatedBonusBronze,
			int updatedBonusBlue) {
		
		print("");
		print("Available city color bonuses:");
		
		if (updatedBonusGold != 0) {
			print(String.format("Gold cities: %d victory points", updatedBonusGold));
		}
		
		if (updatedBonusSilver != 0) {
			print(String.format("Silver cities: %d victory points", updatedBonusSilver));
		}
		
		if (updatedBonusBronze != 0) {
			print(String.format("Bronze cities: %d victory points", updatedBonusBronze));
		}
		
		if (updatedBonusBlue != 0) {
			print(String.format("Blue cities: %d victory points", updatedBonusBlue));
		}

		if (updatedBonusGold == 0 && updatedBonusSilver == 0 && updatedBonusBronze == 0 && updatedBonusBlue == 0) {
			print("All the city color construction bonuses have been used already!");
		}
	}


	public void showEndGame() {
		
		if (endResults == null) {
			print("The game hasn't ended yet.");
			return;
		}
		
		// 0 - id
		// 1 - name
		// 2 - points
		// 3 - assistants
		// 4 - cards
		// 5 - emporiums
		// 6 - nobility
		// 7 - permits
		// 8 - coins

		print("");
		print("*** The Game Has Ended ***");
		print(String.format("The winner is %s, with %s points!", endResults.get(0).get(1), endResults.get(0).get(2)));

		if (this.getPlayerID().equals(Integer.valueOf(endResults.get(0).get(0)))) {
			print("Congratulations! You won!");
		}

		print("");
		print("Complete rankings:");

		for (int i = 1; i < endResults.size(); i++) {
			List<String> plrRes = endResults.get(i);

			print(String.format("%d) %s with %s points, %s assistants and %s cards.", i, plrRes.get(1),
					plrRes.get(2), plrRes.get(3), plrRes.get(4)));
			print(String.format("  %s emporiums,  %s nobility,  %s permits,  %s coins", plrRes.get(5),
					plrRes.get(6), plrRes.get(7), plrRes.get(8)));
		}

	}

	@Override
	public void showEndGame(List<List<String>> endResults) {
		this.endResults = endResults;
		showEndGame();
	}

	@Override
	public void showGameStart() {
		print("Game Started! Good Luck :)");
	}

	@Override
	public void showInfo(String text) {
		print(text);
	}


	private void showInstructions() {
		print("*** Commands: ");
		print("");
		print("draw - draw a politic card");
		print("pass - pass the turn");
		print("");
		print("* Main Actions:");
		print("elect <coast|hills|mountains|king> <color> - elect a councillor in the chosen balcony");
		print("acquire <coast|hills|mountains> <permit_id> <card_color> [<card_color> ...] - acquire the permit with id 'permit_id' from the chosen region using the cards specified");
		print("build-with-king <city_name> <card_color> [<card_color> ...] - build an emporium in the city 'city_name' with the help of the king using the cards specified");
		print("build-with-permit <city_name> <permit_id> - build an emporium in the city 'city_name' using the permit specified");
		print("");
		print("* Quick Actions:");
		print("engage - engage an assistant");
		print("change <coast|hills|mountains> - change the face up business permits in the region specified");
		print("main - perform another main action");
		print("elect-with-assistant <coast|hills|mountains|king> <color> - elect a councillor in the chosen balcony with the help of an assistant");
		print("");
		print("* Market");
		print("sell [business <id1>-<price> [<id2>-<price>,...]] [assistants <num>-<price> [<num>-<price> ...]] [politic <color1>-<price> [<color2>-<price>,...]] - sell things that you own");
		print("sell none - don't sell anything");
		print("buy <item_id> [<quantity>] - buy stuff, if you want to buy some of the assistants in a bundle insert the quantity");
		print("buy finish - end your buying phase");
		print("");
		print("show <mydetails|otherdetails|gameboard> - show details about the game or the players");
		print("chat <your_message_here> - sends a message to all the players via the chat");
		print("results - show the end results (after the game is over)");
		print("exit - close the game");
	}

	@Override
	public void showItemSold(ItemForSale item) {
		print(item.toString());
	}

	@Override
	public void showKingBonus(int updatedShowableKingBonus) {
		print(String.format("Next King bonus: %d victory points", updatedShowableKingBonus));
	}

	@Override
	public void showKingUpdate(King updatedKing) {
		print(updatedKing.toString());
	}

	@Override
	public void showMarket(Market updatedMarket) {
		print(updatedMarket.toString());
	}

	@Override
	public void showNobilityTrack(NobilityTrack updatedNobilityTrack) {
		print(updatedNobilityTrack.toString());
	}

	@Override
	public void showOtherPlayer(int id, String name, Color color, int coins, int assistants, int level, int points,
			int numEmporiums) {
		print("\nName: " + name + "\nColor: " + color.toString() + "\nCoins: " + Integer.toString(coins)
				+ "\nAssistants: " + Integer.toString(assistants) + "\nNobility: " + Integer.toString(level)
				+ "\nVictory Points: " + Integer.toString(points));
	}

	@Override
	public void showPersonalDetails(Player p) {
		print(p.toString());
	}

	@Override
	public void showPlayerChangesPrivate(Player p,String message) {
		print(message);
	}

	@Override
	public void showPlayerChangesPublic(String notice) {
		print(notice);
	}
	
	@Override
	public void showPrivateMsg(String text) {
		print(text);
	}

	@Override
	public void showRegion(Region updatedRegion) {
		print(updatedRegion.toString());
	}


}
