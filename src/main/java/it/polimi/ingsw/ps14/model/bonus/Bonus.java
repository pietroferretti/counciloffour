package it.polimi.ingsw.ps14.model.bonus;

import java.io.Serializable;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Player;

public interface Bonus extends Serializable {

	public void useBonus(Player player, Model model);

	/**
	 * Prototype Pattern guyz
	 */
	public Bonus makeCopy();
}