package it.polimi.ingsw.ps14.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;



public class BalconyTest {

	Queue<ColorCouncillor> coda;
	Balcony balcony; 
	
	public void create(){
		coda=new LinkedList<>();
		coda.add(ColorCouncillor.BLACK);

		coda.add(ColorCouncillor.PINK);

		coda.add(ColorCouncillor.BLUE);

		coda.add(ColorCouncillor.WHITE);


		balcony= new Balcony(coda);
	}
	
	public List<PoliticCard> createPoliticsRight(){
		List<PoliticCard> cards = new ArrayList<>();
		cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.PINK));

		cards.add(new PoliticCard(ColorPolitic.WHITE));
		cards.add(new PoliticCard(ColorPolitic.JOLLY));


		return cards;
	}
	
	public List<PoliticCard> createPoliticsWrong(){
		List<PoliticCard> cards = new ArrayList<>();
		cards.add(new PoliticCard(ColorPolitic.BLACK));
		cards.add(new PoliticCard(ColorPolitic.BLACK));

		cards.add(new PoliticCard(ColorPolitic.PINK));

		cards.add(new PoliticCard(ColorPolitic.WHITE));

		return cards;
	}

	@Test
	public void testElectCouncillor() {
		create();		
		balcony.electCouncillor(ColorCouncillor.ORANGE);
		assertEquals(balcony.readBalcony().peek(),ColorCouncillor.PINK);
	}

	@Test
	public void testCardsMatch() {
		create();
		createPoliticsRight();
		createPoliticsWrong();
		
		assertEquals(balcony.cardsMatch(createPoliticsRight()),true);
		assertEquals(balcony.cardsMatch(createPoliticsWrong()),false);

	}

	@Test
	public void testNumOfMatchingCards() {
		create();
		
		assertEquals(balcony.numOfMatchingCards(createPoliticsRight()), 4);
	}

	@Test
	public void testCouncillorCost() {
		create();
		assertEquals(balcony.councillorCost(createPoliticsRight()),1 );
	}

}
