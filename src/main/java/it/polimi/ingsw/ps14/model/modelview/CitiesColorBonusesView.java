package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.ColorCity;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.Model;

/**
 * 
 * It contains an updated copy of the bonus obtainable building emporiums in all
 * the cities with the same color. The bonuses are set to zero when used. It
 * gets an update from the {@link Model} when a bonus is used, updates itself
 * and notifies the {@link ModelView}.
 *
 */
public class CitiesColorBonusesView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7564102966362129716L;
	private Map<ColorCity, Integer> colorBonusesCopy;

	public CitiesColorBonusesView(Map<ColorCity, Integer> colorBonusesCopy) {
		this.colorBonusesCopy = colorBonusesCopy;
	}
	
	public Map<ColorCity, Integer> getColorBonusesCopy() {
		return colorBonusesCopy;
	};

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof GameBoard)) {
			throw new IllegalArgumentException();
		}

		else {
			colorBonusesCopy = new EnumMap<>(((GameBoard) o).getColorBonuses());
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public String toString() {
		return "CitiesColorBonusesView [colorBonuses=" + colorBonusesCopy + "]";
	}

}
