package it.polimi.ingsw.ps14.model.modelview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.ErrorMsg;
import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public class MessageViewTest {
	private Model model;
	private Player player;
	private MessageView mv;
	private Message m;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		player = new Player();
		mv = new MessageView(model.getMessageObservable());
		m = new ErrorMsg(player.getId(), "prova");
		model.getMessageObservable().addObserver(mv);
	}

	@Test
	public void testMessageView() {
		MessageView mv1 = new MessageView(model.getMessageObservable());
		assertNotSame(model.getMessageObservable(), mv1.getMessageCopy());
		assertEquals(model.getMessageObservable().getMessage(), mv1.getMessageCopy());
	}

	@Test
	public void testUpdate() {
		System.out.println("\n---------testUpdate---------\n");
		model.setMessage(m);

		System.out.println(model.getMessageObservable().getMessage());
		System.out.println(mv.getMessageCopy());

		assertNotSame(model.getMessageObservable(), mv.getMessageCopy());
		assertEquals(model.getMessageObservable().getMessage(), null);
		assertEquals("prova", mv.getMessageCopy().toString());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateException() {
		player.addObserver(mv);
		player.addCoins(4);
	}

}
