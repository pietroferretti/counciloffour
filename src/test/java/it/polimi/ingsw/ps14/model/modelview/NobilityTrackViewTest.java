package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.model.Model;

public class NobilityTrackViewTest {
	private Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
	}

	@Test
	public void testNobilityTrackView() {
		NobilityTrackView ntv1 = new NobilityTrackView(model.getGameBoard().getNobilityTrack());
		System.out.println(model.getGameBoard().getNobilityTrack().toString());
		System.out.println(ntv1.getNobilityTrackCopy().toString());
		assertNotSame(model.getGameBoard().getNobilityTrack(), ntv1.getNobilityTrackCopy());
		assertEquals(model.getGameBoard().getNobilityTrack().toString(), ntv1.getNobilityTrackCopy().toString());

	}

}
