package it.polimi.ingsw.ps14.model.modelview;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.NobilityTrack;

public class NobilityTrackView extends Observable implements Observer {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
