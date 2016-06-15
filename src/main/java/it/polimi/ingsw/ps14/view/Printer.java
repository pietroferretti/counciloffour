package it.polimi.ingsw.ps14.view;

import java.io.PrintStream;
import java.util.List;
import java.util.Queue;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.bonus.Bonus;

public class Printer {

	private PrintStream output;

	public Printer(PrintStream output) {
		this.output = output;
	}

	/**
	 * It prints strings :)
	 * 
	 * @param s
	 *            Any string.
	 */
	protected void print(String s) {
		output.println(s);
	}

	// ----------------------------BONUS------------------------------

	/**
	 * It prints all bonuses of a single object.
	 * 
	 * @param bonusCard
	 */
	private void printBonuses(List<Bonus> bonusCard) {
		// copied from BonusList :D
		

	}

	private void printBonusVictoryPoint(Bonus bon) {
		
	}

	private void printBonusPoliticCard(Bonus bon) {
		
	}

	private void printBonusNobility(Bonus bon) {
		
	}

	private void printBonusMainAction() {
		
	}

	private void printBonusCoin(Bonus bon) {
		
	}

	private void printBonusAssistant(Bonus bon) {
		
	}

	// -------------------------BUSINESS PERMITS---------------------------

	private void printBusinessPermit(BusinessPermit businessPermit) {
		print("Permit to build in: ");
		printCitiesName(businessPermit.getCities());
		print("");
		print("Bonus: ");
		printBonuses(businessPermit.getBonus().getBonusCard());
	}

	private void printBusinessPermitsRegion(BusinessPermit[] businessPermits) {
		int i = 0;
		print("BUSINESS PERMITS:");
		print("");
		for (BusinessPermit businessPermit : businessPermits) {
			output.format("%d° PERMIT:%n", i);
			printBusinessPermit(businessPermit);
		}
	}

	/**
	 * It prints a player's used permit bonuses numbering them starting from 1
	 * (in order to make easier a player's choice). So, each bonus is identified
	 * by its own position+1.
	 * 
	 * @param usedCards
	 */
	private void printUsedBusinessPermitsPlayer(List<BusinessPermit> usedCards) {
		print("Used Bonus:");
		int i = 1;
		for (BusinessPermit businessPermit : usedCards) {
			output.format("%d° used bonus:%n", i);
			printBonuses(businessPermit.getBonus().getBonusCard());
		}
	}

	/**
	 * 
	 * @param validCards
	 *            - Player's usable permits
	 */
	private void printValidBusinessPermitsPlayer(List<BusinessPermit> validCards) {
		if (validCards.isEmpty())
			print("You don't own any business permit");
		else {
			int i = 0;
			print("Business Permits:");
			print("");
			for (BusinessPermit businessPermit : validCards) {
				output.format("%d° PERMIT:%n", i);
				printBusinessPermit(businessPermit);
			}
		}
	}

	// ---------------------------CITIES--------------------------

	private void printCitiesName(List<City> cities) {
		for (City city : cities) {
			print(city.getName());
		}
	}

	private void printCities(List<City> cities) {
		for (City city : cities) {
			printCity(city);
		}
	}

	private void printCity(City city) {
		print(city.getName().toUpperCase());
		print("");
		output.print("Color: ");
		output.print(city.getColor());
		print("");
		print("Neighbors:");
		printCitiesName(city.getNeighbors());
		print("Bonus:");
		printBonuses(city.getToken().getBonusCard());
		print("");
	}

	// ---------------------------COUNCIL----------------------------

	private void printCouncil(Queue<ColorCouncillor> councillors) {
		print("head");
		for (ColorCouncillor colorCouncillor : councillors) {
			output.println(colorCouncillor);
		}
		print("tail");
	}

	// ---------------------------KING-----------------------------

	protected void printKing(King king) {
		
	}

	// -----------------------NOBILITY TRACK-------------------------
	/**
	 * It prints a list of nobility points and correlated bonuses; it also
	 * prints each player's nobility points.
	 * 
	 * @param nobilityTrack
	 * @param players
	 */
	protected void printNobilityTrack(NobilityTrack nobilityTrack, List<Player> players) {

		// TODO COME CACCHIO FUNZIONA?? è da rivedere la classe

		for (Player player : players) {
			output.format("%s has %d nobility point(s)", player.getName().toUpperCase(), player.getLevel());
		}

	}

	// ------------------------------PLAYER------------------------------

	/**
	 * It prints a specific player's politic cards and bonuses from used
	 * business permits.
	 * 
	 * @param player
	 */
	public void printPlayerPrivateDetails(Player player) {
		printPoliticCards(player.getHand());
		printUsedBusinessPermitsPlayer(player.getBusinessHand().getUsedCards());
	}

	/**
	 * It prints a specific player's name, color, business permits, assistants
	 * number, nobility level, victory points
	 * 
	 * @param player
	 */
	public void printPlayerPublicDetails(Player player) {
		print("Name: " + player.getName());

		// TODO print("Color: " + player.getColor().toString());

		print("Coins: " + Integer.toString(player.getCoins()));
		printValidBusinessPermitsPlayer(player.getBusinessHand().getValidCards());
		print("Assistants: " + Integer.toString(player.getAssistants()));
		print("Nobility level: " + Integer.toString(player.getLevel()));
		print("Victory Points: " + Integer.toString(player.getPoints()));

	}

	// -------------------------POLITIC CARDS-------------------------

	/**
	 * It prints a player's politic cards numbering them starting from 1 (in
	 * order to make easier a player's choice). So, each card is identified by
	 * its own position+1.
	 * 
	 * @param hand
	 *            Player's politic cards
	 */
	private void printPoliticCards(List<PoliticCard> hand) {
		print("Politic cards:");
		int i = 1;
		for (PoliticCard politicCard : hand) {
			output.format("%d° card:%n", i);
			printPoliticCard(politicCard);
		}
	}

	/**
	 * It prints the color of a specific politic card.
	 * 
	 * @param politicCard
	 */
	private void printPoliticCard(PoliticCard politicCard) {
		print(politicCard.getColor().toString());
	}

	// ---------------------------REGIONS-----------------------------

	/**
	 * For each region prints: type, bonus, coucil, permits available, cities
	 * 
	 * @param regions
	 */
	protected void printRegions(Region region) {

		print("TYPE:");
		print("");
		output.println(region.getType());

		print("BONUS:");
		print("");
		printBonusVictoryPoint(region.getBonusRegion());

		print("COUNCIL:");
		printCouncil(region.getBalcony().readBalcony());

		printBusinessPermitsRegion(region.getBusinessPermits().getAvailablePermits());

		output.format("This region contains %d cities", region.getCities().size());
		print("");
		print("CITIES:");
		print("");
		printCitiesName(region.getCities());
		printCities(region.getCities());

	}

	// -----------------------VICTORY PATH---------------------------

	protected void printVictoryPoints(List<Player> players) {
		for (Player player : players) {
			output.format("%s has %d victory point(s)", player.getName().toUpperCase(), player.getPoints());
		}
	}

}
