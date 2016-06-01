package it.polimi.ingsw.ps14.view;

import java.io.PrintStream;
import java.util.List;
import java.util.Queue;

import it.polimi.ingsw.ps14.BusinessPermit;
import it.polimi.ingsw.ps14.City;
import it.polimi.ingsw.ps14.ColorCouncillor;
import it.polimi.ingsw.ps14.King;
import it.polimi.ingsw.ps14.NobilityTrack;
import it.polimi.ingsw.ps14.Player;
import it.polimi.ingsw.ps14.Region;
import it.polimi.ingsw.ps14.model.bonus.*;

public class Printer {

	private PrintStream output;

	public Printer(PrintStream output) {
		this.output = output;
	}

	/*
	 * For each region prints: type, bonus, coucil, permits available, cities
	 */

	private void print(String s) {
		output.println(s);
	}

	private void printBonuses(List<Bonus> bonusCard) {
		// copied from BonusList :D
		for (Bonus bon : bonusCard) {
			if (bon instanceof BonusAssistant)
				printBonusAssistant(bon);
			else if (bon instanceof BonusCoin)
				printBonusCoin(bon);
			else if (bon instanceof BonusMainAction)
				printBonusMainAction(bon);
			else if (bon instanceof BonusNobility)
				printBonusNobility(bon);
			else if (bon instanceof BonusPoliticCard)
				printBonusPoliticCard(bon);
			else if (bon instanceof BonusVictoryPoint)
				printBonusVictoryPoint(bon);
		}

	}

	private void printBonusVictoryPoint(Bonus bon) {
		output.format("+%d victory points\n", bon.getQuantity());
	}

	private void printBonusPoliticCard(Bonus bon) {
		output.format("+%d politic card\n", bon.getQuantity());
	}

	private void printBonusNobility(Bonus bon) {
		output.format("+%d nobility points\n", bon.getQuantity());
	}

	private void printBonusMainAction(Bonus bon) {
		print("+1 main action");
	}

	private void printBonusCoin(Bonus bon) {
		output.format("+%d coins\n", bon.getQuantity());
	}

	private void printBonusAssistant(Bonus bon) {
		output.format("+%d assistant(s)\n", bon.getQuantity());
	}

	private void printBusinessPermit(BusinessPermit businessPermit) {
		print("Permit to build in: ");
		printCitiesName(businessPermit.getCities());
		print("");
		print("Bonus: ");
		printBonuses(businessPermit.getBonus().getBonusCard());
	}

	private void printBusinessPermits(BusinessPermit[] businessPermits) {
		int i = 0;
		print("BUSINESS PERMITS:");
		print("");
		for (BusinessPermit businessPermit : businessPermits) {
			output.format("%d° PERMIT:\n", i);
			printBusinessPermit(businessPermit);
		}
	}

	private void printCitiesName(List<City> cities) {
		for (City city : cities) {
			print(city.getName());
		}
	}

	private void printCouncil(Queue<ColorCouncillor> councillors) {
		print("head");
		for (ColorCouncillor colorCouncillor : councillors) {
			output.println(colorCouncillor);
		}
		print("tail");
	}

	protected void printRegions(List<Region> regions) {

		print("-----REGIONS LIST-----");
		print("");

		for (Region region : regions) {

			print("TYPE:");
			print("");
			output.println(region.getType());

			print("BONUS:");
			print("");
			printBonusVictoryPoint(region.getBonusRegion());

			print("COUNCIL:");
			printCouncil(region.getBalcony().readBalcony());

			printBusinessPermits(region.getBusinessPermits().getAvailablePermit());

			output.format("This region contains %d cities", region.getCities().size());
			print("");
			print("CITIES:");
			print("");
			printCitiesName(region.getCities());
			printCities(region.getCities());
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

	protected void printKing(King king) {
		print("The king is in " + king.getCity().getName());
		print("");
		print("KING'S COUNCIL:");
		printCouncil(king.getBalcony().readBalcony());
	}

	protected void printNobilityTrack(NobilityTrack nobilityTrack, List<Player> players) {
		// TODO COME CACCHIO FUNZIONA LA NOSTRA HASHMAP?? AHAHA
		for (Player player : players) {
			output.format("%s has %d nobility point(s)", player.getName().toUpperCase(), player.getLevel());
		}

	}

	protected void printVictoryPoints(List<Player> players) {
		for (Player player : players) {
			output.format("%s has %d victory point(s)", player.getName().toUpperCase(), player.getPoints());
		}
	}

}
