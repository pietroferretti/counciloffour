package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.BusinessCardsRegion;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermiteTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.MainAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.ChangeBusinessPermitTilesAction;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;

public class RegionViewTest {

	private Model model;
	private Player player;
	private Action actionBalcony, actionPermits;
	private RegionView rv;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		player = new Player();
		player.setName("Nicole");
		model.getPlayers().add(player);
		model.setCurrentPlayer(player);

		rv = new RegionView(model.getGameBoard().getRegion(RegionType.COAST));

		model.getGameBoard().getRegion(RegionType.COAST).addObserver(rv);

		actionBalcony = new ElectCouncillorAction(player.getId(), ColorCouncillor.BLUE, "COAST");

	}

	@Test
	public void testRegionView() {
		// TODO fare controllo pezzo a pezzo

		RegionView rvCOAST = new RegionView(model.getGameBoard().getRegion(RegionType.COAST));
		assertNotSame(model.getGameBoard().getRegion(RegionType.COAST), rvCOAST.getRegionCopy());

		RegionView rvHILLS = new RegionView(model.getGameBoard().getRegion(RegionType.HILLS));
		assertNotSame(model.getGameBoard().getRegion(RegionType.HILLS), rvHILLS.getRegionCopy());

		RegionView rvMOUNTAINS = new RegionView(model.getGameBoard().getRegion(RegionType.MOUNTAINS));
		assertNotSame(model.getGameBoard().getRegion(RegionType.MOUNTAINS), rvMOUNTAINS.getRegionCopy());

	}

	@Test
	public void testUpdateBalcony() {

		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBalcony().toString());
		System.out.println(rv.getRegionCopy().getBalcony().toString());

		System.out.println("Add BLUE councillor:");

		actionBalcony.execute(new InitialTurnState(), model);

		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBalcony().toString());
		System.out.print(rv.getRegionCopy().getBalcony().toString());

		assertNotSame(model.getGameBoard().getRegion(RegionType.COAST).getBalcony(), rv.getRegionCopy().getBalcony());

	}

	@Test
	public void testUpdateBonusRegion() {

		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBonusRegion().toString());
		System.out.print(rv.getRegionCopy().getBonusRegion());

		// TODO controllo nelle action quando ho tutti empori della stessa
		// region
	}

	@Test
	public void testUpdateBusinessPermits() {
		System.out.println("testUpdateBusinessPermits");

		actionPermits = new ChangeBusinessPermitTilesAction(player.getId(), RegionType.COAST);

		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().toString());
		System.out.print(rv.getRegionCopy().getBusinessPermits());

		assertNotSame(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits(),
				rv.getRegionCopy().getBusinessPermits());

		actionPermits.execute(new MainActionDoneTurnState(0), model);

		System.out.println(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().toString());
		System.out.print(rv.getRegionCopy().getBusinessPermits());

		assertNotSame(model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits(),
				rv.getRegionCopy().getBusinessPermits());
		// TODO altre azioni che modificano
		// MainAction action = new
		// AcquireBusinessPermiteTileAction(player.getId(), RegionType.COAST,
		// model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0].getId(),
		// cards);

	}

	@Test
	public void testUpdateCities() {
		System.out.println("\ntestUpdateCities\n");

		System.out.print(rv.getRegionCopy().getCities().get(0).toString());

		System.out.println("\nBuilding Emporium :\n)");
		model.getGameBoard().getRegion(RegionType.COAST).getCities().get(0).buildEmporium(player);
		System.out.println(rv.getRegionCopy().getCities().get(0).toString());

		assertNotSame(model.getGameBoard().getRegion(RegionType.COAST).getCities(), rv.getRegionCopy().getCities());
		// TODO fare controllo su ogni cosa
		// assertEquals(model.getGameBoard().getRegion(RegionType.COAST).getCities().get(0).getEmporiums(),
		// rv.getRegionCopy().getCities().get(0).getEmporiums());

		assertNotSame(model.getGameBoard().getRegion(RegionType.COAST).getCities().get(0).getEmporiums(),
				rv.getRegionCopy().getCities().get(0).getEmporiums());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateException() {
		player.addObserver(rv);
		player.addCoins(4);
	}

}
