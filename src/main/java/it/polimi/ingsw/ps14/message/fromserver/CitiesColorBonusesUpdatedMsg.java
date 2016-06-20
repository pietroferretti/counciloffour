package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;

public class CitiesColorBonusesUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256717322717204745L;

	private int updatedBonusGold;
	private int updatedBonusSilver;
	private int updatedBonusBronze;
	private int updatedBonusBlue;

	/**
	 * @param updatedBonusGold
	 * @param updatedBonusSilver
	 * @param updatedBonusBronze
	 * @param updatedBonusBlue
	 */
	public CitiesColorBonusesUpdatedMsg(int updatedBonusGold,
			int updatedBonusSilver, int updatedBonusBronze, int updatedBonusBlue) {
		this.updatedBonusGold = updatedBonusGold;
		this.updatedBonusSilver = updatedBonusSilver;
		this.updatedBonusBronze = updatedBonusBronze;
		this.updatedBonusBlue = updatedBonusBlue;
	}

	public int getUpdatedBonusGold() {
		return updatedBonusGold;
	}

	public int getUpdatedBonusSilver() {
		return updatedBonusSilver;
	}

	public int getUpdatedBonusBronze() {
		return updatedBonusBronze;
	}

	public int getUpdatedBonusBlue() {
		return updatedBonusBlue;
	}

	@Override
	public String toString() {
		return "CitiesColorBonuses now: BonusGold=" + updatedBonusGold
				+ ", BonusSilver=" + updatedBonusSilver + ", BonusBronze="
				+ updatedBonusBronze + ", BonusBlue=" + updatedBonusBlue;
	}

}
