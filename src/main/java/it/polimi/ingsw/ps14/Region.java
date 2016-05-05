package it.polimi.ingsw.ps14;

import java.util.ArrayList;

public class Region {

  private RegionType type;

  private ArrayList<City> cities;

  private Balcony balcony;

  private BusinessPermit permitCard1;
  
  private BusinessPermit permitCard2;

  private BusinessDeck permitsDeck;

  private Bonus bonusRegion;

public Balcony getBalcony() {
	return balcony;
}

public BusinessPermit getPermitCard1() {
	return permitCard1;
}

public BusinessPermit getPermitCard2() {
	return permitCard2;
}

public Bonus getBonusRegion() {
	return bonusRegion;
}

public Region(){
}



}