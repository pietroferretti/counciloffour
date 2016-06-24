package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.Region;

/**
 * 
 * It contains an updated copy of the {@link Region} enclosed in the
 * {@link Model}. It gets an update from the {@link Region}, updates itself and
 * notifies the {@link ModelView}.
 *
 */
public class RegionView extends Observable implements Observer, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9193941425700612401L;
	private Region regionCopy;

	public RegionView(Region regionCopy) {

		this.regionCopy = new Region(regionCopy);
	}

	public Region getRegionCopy() {
		return regionCopy;
	}

	private void setRegionCopy(Region regionCopy) {
		this.regionCopy = regionCopy;
		setChanged();
		notifyObservers();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (!(o instanceof Region)) {
			throw new IllegalArgumentException();
		} else {
			setRegionCopy(new Region((Region) o));
		}

	}

}
