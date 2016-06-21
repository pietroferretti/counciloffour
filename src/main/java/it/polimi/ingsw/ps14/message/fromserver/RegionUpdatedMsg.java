package it.polimi.ingsw.ps14.message.fromserver;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.Region;

public class RegionUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5577229561678523809L;

	private Region updatedRegion;

	public RegionUpdatedMsg(Region updatedRegion) {
		this.updatedRegion = updatedRegion;
	}

	public Region getUpdatedRegion() {
		return updatedRegion;
	}

	@Override
	public String toString() {
		return "RegionUpdatedMsg [updatedRegion=" + updatedRegion + "]";
	}

}
