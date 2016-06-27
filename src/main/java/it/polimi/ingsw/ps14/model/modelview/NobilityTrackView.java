package it.polimi.ingsw.ps14.model.modelview;

import java.io.Serializable;

import it.polimi.ingsw.ps14.model.Model;
import it.polimi.ingsw.ps14.model.NobilityTrack;

/**
 * 
 * It contains an updated copy of the {@link NobilityTrack} enclosed in the
 * {@link Model}.
 *
 */
public class NobilityTrackView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -876089171429960644L;
	NobilityTrack nobilityTrackCopy;

	public NobilityTrackView(NobilityTrack nobilityTrackCopy) {
		this.nobilityTrackCopy = new NobilityTrack(new NobilityTrack(nobilityTrackCopy));
	}

	public NobilityTrack getNobilityTrackCopy() {
		return nobilityTrackCopy;
	}

	// private void setNobilityTrackCopy(NobilityTrack nobilityTrackCopy) {
	// this.nobilityTrackCopy = nobilityTrackCopy;
	// setChanged();
	// notifyObservers();
	// }

	// @Override
	// public void update(Observable o, Object arg) {
	//
	// if (!(o instanceof NobilityTrack)) {
	// throw new IllegalArgumentException();
	// } else {
	// setNobilityTrackCopy(new NobilityTrack((NobilityTrack) o));
	// }
	//
	// }

}
