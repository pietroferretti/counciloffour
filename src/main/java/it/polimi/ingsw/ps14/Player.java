package it.polimi.ingsw.ps14;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Player extends Observable implements Cloneable {

	private static int idCounter = 0;
	private final int id;
	private String name;
	private Color color; // TODO: magari cambiare da stringa a qualche
							// tipo come java.awt.Color boh
	private int coins;
	private int assistants;
	private int level; // nobility
	private int points;
	// private List<BusinessPermit> permitTiles;
	// private List<BusinessPermit> usedPermitTiles;
	private List<PoliticCard> hand;
	private BusinessCardsPlayer businessHand;

	public int additionalMainsToDo;

	/**
	 * New player
	 * 
	 * @param name
	 *            player name
	 * @param color
	 *            player color
	 * @param coins
	 *            starting coins
	 * @param assistants
	 *            nubmer of assistant
	 * @param deck
	 *            politic deck from gameboard to draw politicCards
	 * @param numberOfCards
	 *            number of politic cards to drawn
	 * @param id
	 */
	public Player(String name, Color color, int coins, int assistants, PoliticDeck deck, int numberOfCards) {
		this.id = idCounter;
		idCounter++;
		this.name = name;
		this.color = color;
		this.coins = coins;
		this.assistants = assistants;
		level = 0;
		points = 0;
		this.hand = deck.drawMultipleCards(numberOfCards);
		businessHand = new BusinessCardsPlayer();
		// permitTiles = new ArrayList<>();
		// usedPermitTiles = new ArrayList<>();
	}

	/**
	 * non setto nome e colore, tutto il resto a 0
	 */
	public Player() {
		this.id = idCounter;
		idCounter++;
		coins = 0;
		assistants = 0;
		level = 0;
		points = 0;
		hand = new ArrayList<>();
		businessHand = new BusinessCardsPlayer();
	}

	public Player(String name, Color color) {
		this.id = idCounter;
		idCounter++;
		this.name = name;
		this.color = color;
		coins = 0;
		assistants = 0;
		level = 0;
		points = 0;
		// permitTiles = new ArrayList<>();
		// usedPermitTiles = new ArrayList<>();
		hand = new ArrayList<>();
		businessHand = new BusinessCardsPlayer();
	}

	private Player(int id) {
		this.id = id;
	}

	@Override
	public Player clone() throws CloneNotSupportedException {
		Player p = new Player(id);
		p.name = name;
		p.color = color;
		p.coins = coins;
		p.assistants = assistants;
		p.level = level;
		p.points = points;
		p.hand = hand;
		p.businessHand = businessHand;
		// p.permitTiles = permitTIles;
		// p.usedPermitTiles = usedPermitTiles;
		return p;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int getCoins() {
		return coins;
	}

	public boolean useCoins(int coins) {
		if (this.coins >= coins) {
			this.coins = this.coins - coins;
			return true;
		} else
			return false;
	}

	public void addCoins(int coins) {
		this.coins = this.coins + coins;
		setChanged();
		notifyObservers();
	}

	public int getAssistants() {

		return assistants;
	}

	public boolean useAssistants(int assistantNumber) {
		if (assistants >= assistantNumber) {
			assistants = assistants - assistantNumber;
			return true;
		} else
			return false;
	}

	public void addAssistants(int assistants) {
		this.assistants = this.assistants + assistants;
	}

	public int getLevel() {
		return level;
	}

	public void setName(String name) {
		this.name = name;
		setChanged();
		notifyObservers();
	}

	// players can upgrade the nobility level up to infinity
	public int upLevel() {
		level++;
		return level; // returns the new value of level to check bonuses

	}

	// double upgrade of nobility through bonus
	public void upLevel(int n) {
		level = level + n;
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points = this.points + points;
	}

	public List<PoliticCard> getHand() {
		return hand;
	}

	public int getNumberOfCards() {
		return hand.size();
	}

	public void addPolitic(PoliticCard card) {
		hand.add(card);
	}
	
	public int getId() {
		return id;
	}
	@Override
	public boolean equals(Object o) {
		// If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of Player or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Player)) {
            return false;
        }
         
        Player p = (Player) o;
		return p.id==this.id;
	}

	public boolean hasCardInHand(ColorPolitic color) {
		boolean cardInHand = false;
		for (PoliticCard card : hand) {
			if (card.getColor() == color) {
				cardInHand = true;
				break;
			}
		}
		return cardInHand;
	}

	// Finds a card with a specific color in the hand, returns null if there
	// isn't one
	// TODO: eccezione se non c'è una carta nella mano?
	public PoliticCard findCardInHand(ColorPolitic color) {
		PoliticCard cardFound = null;
		for (PoliticCard card : hand) {
			if (card.getColor() == color) {
				cardFound = card;
				break;
			}
		}
		return cardFound;
	}

	// Removes a card from the hand and returns it
	public PoliticCard useCard(ColorPolitic color) {
		PoliticCard card = findCardInHand(color);
		if (hand.remove(card) == true) {
			return card;
		} else {
			// TODO: eccezione se non trova la carta nella mano
			return null;
		}
	}

	public void removeCard(PoliticCard card) {
		hand.remove(card);
	}

	public BusinessCardsPlayer getBusinessHand() {
		return businessHand;
	}

	public void acquireBusinessPermit(BusinessPermit permitTile) {
		businessHand.acquireBusinessPermit(permitTile);
	}

	public int getNumberOfPermits() {
		return businessHand.getNumberOfPermits();
	}

	public void sellPermits(BusinessPermit item) {
		businessHand.sellPermits(item);
	}

}