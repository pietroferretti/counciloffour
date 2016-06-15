package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.Region;

public class RegionView extends Observable implements Observer {

	private Region regionCopy;

	public RegionView(Region regionCopy) {
		this.regionCopy = regionCopy;
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
