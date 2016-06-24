package it.polimi.ingsw.ps14.message.fromserver;

import java.util.Map;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.ColorCity;

public class CitiesColorBonusesUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256717322717204745L;

	private Map<ColorCity, Integer> updatedColorBonuses;


	public CitiesColorBonusesUpdatedMsg(Map<ColorCity, Integer> colorBonuses) {
		this.updatedColorBonuses = colorBonuses;
	}

	public Map<ColorCity, Integer> getUpdatedColorBonuses() {
		return updatedColorBonuses;
	};

	@Override
	public String toString() {
		return "CitiesColorBonuses now: BonusGold=" + updatedColorBonuses.get(ColorCity.GOLD)
				+ ", BonusSilver=" + updatedColorBonuses.get(ColorCity.SILVER) + ", BonusBronze="
				+ updatedColorBonuses.get(ColorCity.BRONZE) + ", BonusBlue=" + updatedColorBonuses.get(ColorCity.BLUE);
	}

}
