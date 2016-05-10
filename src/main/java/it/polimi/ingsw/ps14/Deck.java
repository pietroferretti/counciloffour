package it.polimi.ingsw.ps14;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Deck {
	
	public ArrayList<Card> deck;
	public ArrayList<Card> drawnCards;


	public abstract Card drawCard();

	public void shuffle(){
		Collections.shuffle(deck);
	}
	
}