package it.polimi.ingsw.ps14.model.actions.mainactions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;

public class BuildEmporiumWithHelpOfKingActionTest {

	
	Model model;
	Player player;
	List<PoliticCard> cards;
	
	@Before
	public void create() throws IOException{

		model = new Model();
		player=new Player(0,20,12,model.getGameBoard().getPoliticDeck(),6,"ubaldo", Color.DARK_GRAY);
		

		List<Player> pls = new ArrayList<>();
		pls.add(player);
		model.setPlayers(pls);
		cards=new ArrayList<>();
		cards.add(new PoliticCard(ColorPolitic.BLACK));
		player.addPolitic(new PoliticCard(ColorPolitic.BLACK));
		
		cards.add(new PoliticCard(ColorPolitic.JOLLY));
		player.addPolitic(new PoliticCard(ColorPolitic.JOLLY));

		cards.add(new PoliticCard(ColorPolitic.WHITE));
		player.addPolitic(new PoliticCard(ColorPolitic.WHITE));

//		cards.add(new PoliticCard(ColorPolitic.ORANGE));
//		player.addPolitic(new PoliticCard(ColorPolitic.ORANGE));

		cards.add(new PoliticCard(ColorPolitic.BLACK));
		player.addPolitic(new PoliticCard(ColorPolitic.BLACK));
		
		model.getGameBoard().getKing().getBalcony().electCouncillor(ColorCouncillor.BLACK);
		model.getGameBoard().getKing().getBalcony().electCouncillor(ColorCouncillor.WHITE);
		model.getGameBoard().getKing().getBalcony().electCouncillor(ColorCouncillor.ORANGE);
		model.getGameBoard().getKing().getBalcony().electCouncillor(ColorCouncillor.BLACK);

		
	}
	
	
	
	@Test
	public void isValidTest() {
		
		MainAction action = new BuildEmporiumWithHelpOfKingAction(new Integer(player.getId()),new String( model.getGameBoard().getCities().get(2).getName()), cards);
		
		
		assertEquals(action.isValid(model),true);

	}
	
	@Test
	public void executeTest() throws IOException {
				
		MainAction action = new BuildEmporiumWithHelpOfKingAction(player.getId(), model.getGameBoard().getCities().get(2).getName(), cards);
		System.out.println(player.toString());
		
		action.execute(null, model);

		assertEquals(model.getGameBoard().getCities().get(2).getEmporiums().get(0),player);
		assertTrue(model.getGameBoard().getPoliticDeck().getDiscardedCards().containsAll(cards));
		assertEquals(model.getGameBoard().getKing().getCity(),model.getGameBoard().getCities().get(2));
		
		
	}

}
