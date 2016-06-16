package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.GameBoard;

public class RegionBonusesView extends Observable implements Observer , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7564102966362129716L;
	private int bonusGoldCopy;
	private int bonusSilverCopy;
	private int bonusBronzeCopy;
	private int bonusBlueCopy;

	public RegionBonusesView(int bonusGoldCopy, int bonusSilverCopy, int bonusBronzeCopy, int bonusBlueCopy) {
		this.bonusGoldCopy = bonusGoldCopy;
		this.bonusSilverCopy = bonusSilverCopy;
		this.bonusBronzeCopy = bonusBronzeCopy;
		this.bonusBlueCopy = bonusBlueCopy;
	}

	public int getBonusGoldCopy() {
		return bonusGoldCopy;
	}

	public int getBonusSilverCopy() {
		return bonusSilverCopy;
	}

	public int getBonusBronzeCopy() {
		return bonusBronzeCopy;
	}

	public int getBonusBlueCopy() {
		return bonusBlueCopy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof GameBoard)) {
			throw new IllegalArgumentException();
		}

		else {
			bonusBlueCopy = ((GameBoard) o).getBonusBlue();
			bonusBronzeCopy = ((GameBoard) o).getBonusBronze();
			bonusSilverCopy = ((GameBoard) o).getBonusSilver();
			bonusGoldCopy = ((GameBoard) o).getBonusGold();
			setChanged();
			notifyObservers();
		}
	}

}
