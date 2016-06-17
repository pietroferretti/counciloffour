package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.NobilityTrack;

public class NobilityTrackdUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4782315814541860744L;
	
	private NobilityTrack updatedNobilityTrack;

	public NobilityTrackdUpdatedMsg(NobilityTrack updatedNobilityTrack) {
		this.updatedNobilityTrack = updatedNobilityTrack;
	}

	public NobilityTrack getUpdatedNobilityTrack() {
		return updatedNobilityTrack;
	}
	
	
}
