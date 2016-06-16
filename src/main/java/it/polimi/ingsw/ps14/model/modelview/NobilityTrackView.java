package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps14.model.NobilityTrack;

public class NobilityTrackView extends Observable implements Observer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -876089171429960644L;
	NobilityTrack nobilityTrackCopy;

	public NobilityTrackView(NobilityTrack nobilityTrackCopy) {
		this.nobilityTrackCopy = new NobilityTrack(nobilityTrackCopy);
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

		if (!(o instanceof NobilityTrack)) {
			throw new IllegalArgumentException();
		} else {
			setNobilityTrackCopy(new NobilityTrack((NobilityTrack) o));
		}

	}

}
