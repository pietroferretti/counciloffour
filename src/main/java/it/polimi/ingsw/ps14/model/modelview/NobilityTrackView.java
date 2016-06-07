package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps14.NobilityTrack;
import it.polimi.ingsw.ps14.Settings;

public class NobilityTrackView extends Observable implements Observer {
	private static final Logger LOGGER= Logger.getLogger(Settings.class.getName());

	NobilityTrack nobilityTrackCopy;

	public NobilityTrackView(NobilityTrack nobilityTrackCopy) {
		this.nobilityTrackCopy = nobilityTrackCopy;
	}

	public NobilityTrack getNobilityTrackCopy() {
		return nobilityTrackCopy;
	}

	private void setNobilityTrackCopy(NobilityTrack nobilityTrackCopy) {
		this.nobilityTrackCopy = nobilityTrackCopy;
		setChanged();
		notifyObservers();
	}

	@Override
	public void update(Observable o, Object arg) {

		// TODO controllare

		if (!(o instanceof NobilityTrack)) {
			throw new IllegalArgumentException();
		}
		try {
			setNobilityTrackCopy(((NobilityTrack) o).clone());
		} catch (CloneNotSupportedException e) {
			LOGGER.log(Level.SEVERE, "Couldn't copy NobilityTrack", e);
		}

	}

}
