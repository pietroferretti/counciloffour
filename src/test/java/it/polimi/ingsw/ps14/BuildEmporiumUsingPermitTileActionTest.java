package it.polimi.ingsw.ps14;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps14.controller.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.controller.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.Settings;
import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.bonus.BonusAssistant;
import it.polimi.ingsw.ps14.model.bonus.BonusCoin;
import it.polimi.ingsw.ps14.model.bonus.BonusList;
import it.polimi.ingsw.ps14.model.bonus.BonusNobility;
import it.polimi.ingsw.ps14.model.bonus.BonusPoliticCard;
import it.polimi.ingsw.ps14.model.bonus.BonusVictoryPoint;

public class BuildEmporiumUsingPermitTileActionTest {

	@Test
	public void test() {
		Settings settingsInstance = null;
		try {
			settingsInstance = new Settings("settings.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GameBoard gameboard = new GameBoard(settingsInstance);
		
		List<PoliticCard> cards=new ArrayList<>();
//		cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
//		cards.add(new PoliticCard(ColorPolitic.PINK));
		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,gameboard.getPoliticDeck(),6);
		
		List cities=new ArrayList<City>();
		cities.add(gameboard.getCityByName("Arkon"));
		cities.add(gameboard.getCityByName("Esti"));
		List<Bonus> bon=new ArrayList<>();
		bon.add(new BonusAssistant(20));
		bon.add(new BonusCoin(2));
		bon.add(new BonusNobility(1));
		bon.add(new BonusPoliticCard(2));
		bon.add(new BonusVictoryPoint(2));
		BonusList bonus=new BonusList(bon);

		System.out.println(player.toString());
		BusinessPermit busCard = new BusinessPermit(cities,bonus); 
		player.acquireBusinessPermit(busCard);

		MainAction action = new BuildEmporiumUsingPermitTileAction(player, gameboard, busCard, gameboard.getCityByName("Arkon"));
		assertEquals(action.isValid(),true);
		action.execute(null);
		System.out.println(player.toString());

	}

}
