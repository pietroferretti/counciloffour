package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.message.RegionBonusesChangedMsg;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Player;

public class RegionBonusesView extends Observable implements Observer {

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
		if (arg instanceof RegionBonusesChangedMsg) {
			bonusBlueCopy = ((RegionBonusesChangedMsg) arg).getNewBonusBlue();
			bonusBronzeCopy = ((RegionBonusesChangedMsg) arg).getNewBonusBronze();
			bonusSilverCopy = ((RegionBonusesChangedMsg) arg).getNewBonusSilver();
			bonusGoldCopy = ((RegionBonusesChangedMsg) arg).getNewBonusGold();
			setChanged();
			notifyObservers();
		}

	}

}
