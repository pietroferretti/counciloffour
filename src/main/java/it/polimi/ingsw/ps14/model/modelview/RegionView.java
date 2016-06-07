package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.Region;
import it.polimi.ingsw.ps14.Settings;

public class RegionView extends Observable implements Observer {
	private static final Logger LOGGER= Logger.getLogger(Settings.class.getName());

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

		// TODO controllare

		if (!(o instanceof Region)) {
			throw new IllegalArgumentException();
		}
		try {
			setRegionCopy(((Region) o).clone());
		} catch (CloneNotSupportedException e) {
			LOGGER.log(Level.SEVERE, "Couldn't copy Region.", e);
		}
	}

}
