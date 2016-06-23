//package it.polimi.ingsw.ps14.controller.actions.mainactions;
//
//import static org.junit.Assert.assertTrue;
//
//import java.awt.Color;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//
//import it.polimi.ingsw.ps14.model.Model;
//import it.polimi.ingsw.ps14.model.Player;
//import it.polimi.ingsw.ps14.model.PoliticCard;
//import it.polimi.ingsw.ps14.model.RegionType;
//import it.polimi.ingsw.ps14.model.Settings;
//import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermiteTileAction;
//
//public class AcquireBusinessPermiteTileActionTest {
//
//	@Test
//	public void test() throws IOException {
//		
//		
//		//FIXME dipende troppo da variabili casuali 
//		
//		Settings settingsInstance = null;
//		try {
//			settingsInstance = new Settings("settings.json");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Model model = new Model();
//		
//		List<Player> pls=new ArrayList<>();
//		List<PoliticCard> cards=new ArrayList<>();
////		cards.add(new PoliticCard(ColorPolitic.BLACK));
////		cards.add(new PoliticCard(ColorPolitic.JOLLY));
////		cards.add(new PoliticCard(ColorPolitic.PINK));
//		Player player=new Player("ubaldo",Color.DARK_GRAY,20,12,model.getGameBoard().getPoliticDeck(),4);
//		pls.add(player);
//		model.setPlayers(new ArrayList<Player>(pls));
//
//		cards.add(new PoliticCard(player.getHand().get(0).getColor()));
//		cards.add(new PoliticCard(player.getHand().get(1).getColor()));
//		cards.add(new PoliticCard(player.getHand().get(3).getColor()));
//
//		
//		AcquireBusinessPermiteTileAction action = new AcquireBusinessPermiteTileAction(player.getId(), RegionType.COAST,
//				model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0].getId(),
//				cards);
//		
//		System.out.println(player.getHand().toString());
//
//		assertTrue(action.isValid(model));
//		
//		System.out.println( model.getGameBoard().getRegion(RegionType.COAST).getBusinessPermits().getAvailablePermits()[0].getBonusList().toString());
//
//		
//		action.execute(null, model);
//		
//		System.out.println(player.getHand().toString());
//
//		System.out.println(player.getHand().toString());
//
//	}
//
//}
