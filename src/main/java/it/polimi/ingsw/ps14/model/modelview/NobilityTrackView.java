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

}
