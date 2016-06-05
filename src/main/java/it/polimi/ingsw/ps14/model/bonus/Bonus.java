package it.polimi.ingsw.ps14.model.bonus;

// quantity =0 or <0 throw exception

public abstract class Bonus {

	private int quantity;

	public Bonus(int quantity) {
		if (quantity < 1)
			throw new IllegalArgumentException("Impossible quantity for this bonus");
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}
}