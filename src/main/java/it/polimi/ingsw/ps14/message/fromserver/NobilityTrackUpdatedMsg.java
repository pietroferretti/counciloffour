package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.NobilityTrack;

public class NobilityTrackUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4782315814541860744L;
	
	private NobilityTrack updatedNobilityTrack;

	public NobilityTrackUpdatedMsg(NobilityTrack updatedNobilityTrack) {
		this.updatedNobilityTrack = updatedNobilityTrack;
	}

	public NobilityTrack getUpdatedNobilityTrack() {
		return updatedNobilityTrack;
	}

	@Override
	public String toString() {
		return "NobilityTrackUpdatedMsg [updatedNobilityTrack="
				+ updatedNobilityTrack + "]";
	}
	
	
}
