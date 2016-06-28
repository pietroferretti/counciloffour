package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.BusinessPermit;
import it.polimi.ingsw.ps14.model.City;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;

public class PlayerViewTest {

	private Model model;
	private Player player, player2, player3;
	private PlayerView playerView2, playerView3;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		model = new Model();
		player = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 4);
		player2 = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		player3 = new Player("ubaldo", Color.DARK_GRAY, 20, 12, model.getGameBoard().getPoliticDeck(), 6);
		playerView2 = new PlayerView(player2);
		playerView3 = new PlayerView(player3);
		player3.addObserver(playerView3);

	}

	@Test
	public void testPlayerView() {
		System.out.println("\n---------testPlayerView---------\n");

		PlayerView pv1 = new PlayerView(player);

		System.out.println(player.toString());
		System.out.println(pv1.getPlayerCopy().toString());

		assertNotSame(player, pv1.getPlayerCopy());
		assertEquals(player.getAssistants(), pv1.getPlayerCopy().getAssistants());

		assertEquals(player.getBusinessHand().getNumberOfPermits(),
				pv1.getPlayerCopy().getBusinessHand().getNumberOfPermits());
		assertEquals(player.getBusinessHand().getUsedCards(), pv1.getPlayerCopy().getBusinessHand().getUsedCards());
		assertEquals(player.getBusinessHand().getValidCards(), pv1.getPlayerCopy().getBusinessHand().getValidCards());
		assertNotSame(player.getBusinessHand(), pv1.getPlayerCopy().getBusinessHand());
		assertEquals(player.getCoins(), pv1.getPlayerCopy().getCoins());
		assertEquals(player.getColor(), pv1.getPlayerCopy().getColor());
		int i = 0;
		for (PoliticCard card : player.getHand()) {
			assertEquals(card.toString(), pv1.getPlayerCopy().getHand().get(i).toString());
			i++;
		}
		assertNotSame(player.getHand(), pv1.getPlayerCopy().getHand());
		assertEquals(player.getId(), pv1.getPlayerCopy().getId());
		assertEquals(player.getLevel(), pv1.getPlayerCopy().getLevel());
		assertEquals(player.getName(), pv1.getPlayerCopy().getName());
		assertEquals(player.getNumEmporiums(), pv1.getPlayerCopy().getNumEmporiums());
		assertEquals(player.getPoints(), pv1.getPlayerCopy().getPoints());
	}

	@Test
	public void testUpdateAssistants() {
		System.out.println("\n---------testUpdateAssistants---------\n");
		System.out.println(player3.toString());

		player3.addAssistants(4);
		assertEquals(player3.getAssistants(), playerView3.getPlayerCopy().getAssistants());
		assertNotSame(player3, playerView3.getPlayerCopy());
		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());

		player3.useAssistants(1);
		assertEquals(player3.getAssistants(), playerView3.getPlayerCopy().getAssistants());
		assertNotSame(player3, playerView3.getPlayerCopy());
		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());
	}

	@Test
	public void testUpdateBusinessHand() {
		System.out.println("\n---------testUpdateBusinessHand---------\n");
		System.out.println(player3.toString());
		// FIXME
		// System.out.println(
		// model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0]);

//		player3.acquireBusinessPermit(
//				model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0]);
		System.out.println(player3.getBusinessHand().toString());
		int index = 0;
		for (BusinessPermit permit : player3.getBusinessHand().getUsedCards()) {
			assertEquals(permit.getId(),
					playerView3.getPlayerCopy().getBusinessHand().getUsedCards().get(index).getId());
			int i = 0;
			for (City city : permit.getCities()) {
				assertEquals(city.getName(), playerView3.getPlayerCopy().getBusinessHand().getUsedCards().get(index)
						.getCities().get(i).getName());
			}

			assertEquals(permit.getBonusList(),
					playerView3.getPlayerCopy().getBusinessHand().getUsedCards().get(index).getBonusList());
		}
		assertNotSame(player3, playerView3.getPlayerCopy());
		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());

		// TODO manca sellPermits
	}

	@Test
	public void testUpdateCoins() {
		System.out.println("\n---------testUpdateCoins---------\n");
		System.out.println(player3.toString());

		player3.addCoins(10);

		assertNotSame(player3, playerView3.getPlayerCopy());
		assertEquals(player3.getCoins(), playerView3.getPlayerCopy().getCoins());
		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());

		player3.useCoins(2);

		assertNotSame(player3, playerView3.getPlayerCopy());
		assertEquals(player3.getCoins(), playerView3.getPlayerCopy().getCoins());
		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());

	}

	@Test
	public void testUpdateHand() {
		System.out.println("\n---------testUpdateHand---------\n");

		System.out.println(player3.toString());
		player3.addPolitic(new PoliticCard(ColorPolitic.PINK));
		player3.addPolitic(new PoliticCard(ColorPolitic.BLUE));
		player3.addPolitic(new PoliticCard(ColorPolitic.ORANGE));
		System.out.println("\nAdd pink, blue, orange\n");
		assertNotSame(player3, playerView3.getPlayerCopy());
		int i = 0;
		for (PoliticCard card : player3.getHand()) {
			assertEquals(card.toString(), playerView3.getPlayerCopy().getHand().get(i).toString());
			i++;
		}

		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());

		player3.removeColor(ColorPolitic.ORANGE);
		System.out.println("\nremove color orange\n");
		assertNotSame(player3, playerView3.getPlayerCopy());
		i = 0;
		for (PoliticCard card : player3.getHand()) {
			assertEquals(card.toString(), playerView3.getPlayerCopy().getHand().get(i).toString());
			i++;
		}

		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());

		player3.useCard(ColorPolitic.BLUE);
		System.out.println("\nuse card blue\n");
		assertNotSame(player3, playerView3.getPlayerCopy());
		i = 0;
		for (PoliticCard card : player3.getHand()) {
			assertEquals(card.toString(), playerView3.getPlayerCopy().getHand().get(i).toString());
			i++;
		}

		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());

	}

	@Test
	public void testUpdateLevel() {
		System.out.println("\n---------testUpdateLevel---------\n");
		System.out.println(player3.toString());

		player3.levelUp();

		assertNotSame(player3, playerView3.getPlayerCopy());
		assertEquals(player3.getLevel(), playerView3.getPlayerCopy().getLevel());
		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());

		// player3.levelUp(4);
		//
		// assertNotSame(player3, playerView3.getPlayerCopy());
		// assertEquals(player3.getLevel(),
		// playerView3.getPlayerCopy().getLevel());
		// System.out.println(player3.toString());
		// System.out.println(playerView3.getPlayerCopy().toString());
	}

	@Test
	public void testUpdatenumEmporiums() {
		System.out.println("\n---------testUpdatenumEmporiums---------\n");
		System.out.println(player3.toString());

		player3.incrementNumEmporiums();

		assertNotSame(player3, playerView3.getPlayerCopy());
		assertEquals(player3.getPoints(), playerView3.getPlayerCopy().getPoints());
		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());
	}

	@Test
	public void testUpdatePoints() {
		System.out.println("\n---------testUpdatePoints---------\n");
		System.out.println(player3.toString());

		player3.addPoints(5);

		assertNotSame(player3, playerView3.getPlayerCopy());
		assertEquals(player3.getPoints(), playerView3.getPlayerCopy().getPoints());
		System.out.println(player3.toString());
		System.out.println(playerView3.getPlayerCopy().toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateException() {
		model.getGameBoard().getRegion(RegionType.COAST).addObserver(playerView2);
		model.getGameBoard().getRegion(RegionType.COAST).setBalcony();
	}

}
