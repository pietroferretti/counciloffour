package it.polimi.ingsw.ps14;

import java.util.Vector;

public interface Deck {

    public Vector  myRegion;

  public Card drawCard();

  public void shuffle();

}