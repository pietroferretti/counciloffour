package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.Model;

public class AvailableAssistantsViewTest {

	private static Model model;
	private static ModelView mv;
	private static AvailableAssistantsView aav2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = new Model();
		aav2 = new AvailableAssistantsView(model.getGameBoard().getAvailableAssistants());
		model.getGameBoard().addObserver(aav2);
		
	}

	@Test
	public void testAvailableAssistantsView() {
		AvailableAssistantsView aav = new AvailableAssistantsView(model.getGameBoard().getAvailableAssistants());
		assertEquals(model.getGameBoard().getAvailableAssistants(), aav.getAvailableAssistantsCopy());
	}

	@Test
	public void testUpdate() {
		model.getGameBoard().setAvailableAssistants(70);
		assertEquals(model.getGameBoard().getAvailableAssistants(), aav2.getAvailableAssistantsCopy());
		assertFalse(aav2.hasChanged()); //quindi ha eseguito notifyObserver
	}

}
