package it.polimi.ingsw.ps14.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import it.polimi.ingsw.ps14.message.fromserver.PlayerChangedPrivateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerChangedPublicMsg;

public class Player extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 479024623935337422L;
	
	private static Random rand = new Random();
	private static int idCounter = 1;
	private final int id;
	private String name;
	private Color color;
	
	private int coins;
	private int assistants;
	private int level; 		// nobility
	private int points;
	
	private List<PoliticCard> hand;
	private BusinessCardsPlayer businessHand;
	
	private int numEmporiums = 0;

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
	 * @deprecated color is not random, I don't remember
	 */
	@Deprecated
	public Player(String name, Color color, int coins, int assistants,
			PoliticDeck deck, int numberOfCards) {
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

	public Player(int id, int coins, int assistants, PoliticDeck deck,
			int numberOfCards) {
		this.id = id;
		this.coins = coins;
		this.assistants = assistants;
		level = 0;
		points = 0;
		this.hand = deck.drawMultipleCards(numberOfCards);
		businessHand = new BusinessCardsPlayer();
		// permitTiles = new ArrayList<>();
		// usedPermitTiles = new ArrayList<>();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		color = new Color(r, g, b);
	}

	/**
	 * No argument constructor for Player
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

	public Player(Player player) {
		id = player.id;
		name = player.name;
		color = player.color;
		coins = player.coins;
		assistants = player.assistants;
		level = player.level;
		points = player.points;
		hand = new ArrayList<>(player.hand.size());
		for (PoliticCard politicCard : player.hand) {
			hand.add(new PoliticCard(politicCard));
		}
		businessHand = new BusinessCardsPlayer(player.businessHand);
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
			setChanged();
			notifyObservers(new PlayerChangedPublicMsg(id, name + " paid "
					+ Integer.toString(coins) + " coins!"));
			return true;
		} else
			return false;
	}

	public void addCoins(int coins) {
		this.coins = this.coins + coins;
		setChanged();
		notifyObservers(new PlayerChangedPublicMsg(id, name + " earned "
				+ Integer.toString(coins) + " coins!"));
	}

	public int getAssistants() {
		return assistants;
	}

	public boolean useAssistants(int assistantNumber) {
		if (assistants >= assistantNumber) {
			assistants = assistants - assistantNumber;
			setChanged();
			notifyObservers(new PlayerChangedPublicMsg(id, name + " used "
					+ Integer.toString(assistantNumber) + " assistant(s)!"));
			return true;
		} else
			return false;
	}

	public void addAssistants(int assistants) {
		this.assistants = this.assistants + assistants;
		setChanged();
		notifyObservers(new PlayerChangedPublicMsg(id, name + " gained "
				+ Integer.toString(assistants) + " assistant(s)!"));
	}

	public int getLevel() {
		return level;
	}

	public void setName(String name) {
		// TODO check
		this.name = name;
		setChanged();
		notifyObservers();
	}

	/**
	 * players can upgrade the nobility level up to infinity
	 * 
	 */
	public int levelUp() {
		level++;
		setChanged();
		notifyObservers(new PlayerChangedPublicMsg(id, name + " leveled up!"));
		return level; // returns the new value of level to check bonuses
	}

	/**
	 * upgrade of nobility
	 * 
	 * @param n
	 */
	public void levelUp(int n) {
		level = level + n;
		setChanged();
		notifyObservers(new PlayerChangedPublicMsg(id, name + " gained +"
				+ Integer.toString(n) + " levels! Now his level is "
				+ Integer.toString(level)));
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points = this.points + points;
		setChanged();
		notifyObservers(new PlayerChangedPublicMsg(id, name + " gained +"
				+ Integer.toString(points) + " points! Now he has "
				+ Integer.toString(this.points)));
	}

	public List<PoliticCard> getHand() {
		return hand;
	}

	public void removeColor(ColorPolitic color) {
		int i=0;
		while(i<hand.size() && hand.get(i).getColor()!=color)
			i++;
		if(i<hand.size() && hand.get(i).getColor()==color)
				hand.remove(hand.get(i));
			
	}

	public int getNumberOfCards() {
		return hand.size();
	}

	public void addPolitic(PoliticCard card) {
		hand.add(card);
		setChanged();
		notifyObservers(new PlayerChangedPrivateMsg(id, "You gain "
				+ card.toString()));
	}

	public int getId() {
		return id;
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

	// TODO: eccezione se non c'è una carta nella mano?
	/**
	 * Finds a card with a specific color in the hand, returns null if there
	 * isn't one
	 * 
	 * @param color
	 * @return
	 */
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

	/**
	 * Removes a card from the hand and returns it
	 * 
	 * @param color
	 * @return
	 */
	public PoliticCard useCard(ColorPolitic color) {
		PoliticCard card = findCardInHand(color);
		if (hand.remove(card) == true) {
			setChanged();
			notifyObservers(new PlayerChangedPublicMsg(id, name
					+ " use a Politic card: " + card.toString()));
			return card;
		} else {
			// TODO: eccezione se non trova la carta nella mano
			return null;
		}
	}

	public void removeCard(PoliticCard card) {
		hand.remove(card);
		setChanged();
		notifyObservers(new PlayerChangedPublicMsg(id, name
				+ " use a Politic card: " + card.toString()));

	}

	public BusinessCardsPlayer getBusinessHand() {
		return businessHand;
	}

	public void acquireBusinessPermit(BusinessPermit permitTile) {
		businessHand.acquireBusinessPermit(permitTile);
		setChanged();
		notifyObservers(new PlayerChangedPublicMsg(id, name
				+ " acquired a business permit: " + permitTile.toString()));
	}

	public int getNumberOfPermits() {
		return businessHand.getNumberOfPermits();
	}

	public void sellPermits(BusinessPermit item) {
		// TODO come/cosa cavolo notifico??
		businessHand.sellPermits(item);

	}

	public void incrementNumEmporiums() {
		this.numEmporiums++;
	}

	public int getNumEmporiums() {
		return this.numEmporiums;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", color=" + color + ", coins=" + coins
				+ ", assistants=" + assistants + ", level=" + level
				+ ", points=" + points + ", hand=" + hand + ", businessHand="
				+ businessHand + "]";
	}

	/**
	 * check if politicCards is available in player hand
	 * 
	 * @param color
	 * @return
	 */
	public boolean hasPoliticCard(List<ColorPolitic> colors) {
		List<ColorPolitic> copy = new ArrayList<>();
		for (PoliticCard p : hand)
			copy.add(p.getColor());

		for (ColorPolitic cp : colors)
			if (copy.contains(cp))
				copy.remove(cp);
			else
				return false;
		return true;
	}

}