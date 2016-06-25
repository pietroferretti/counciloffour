package it.polimi.ingsw.ps14.message.fromserver;

import java.util.Map;



import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.ColorCouncillor;

/**
 * councillors in gameboard are changed
 * 
 *
 */

public class AvailableCouncillorsUpdatedMsg implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1997374367344870474L;

	private Map<ColorCouncillor, Integer> updatedAvailableCouncillors;

	public AvailableCouncillorsUpdatedMsg(Map<ColorCouncillor, Integer> map) {
		this.updatedAvailableCouncillors = map;
	}

	public Map<ColorCouncillor, Integer> getUpdatedAvailableCouncillors() {
		return updatedAvailableCouncillors;
	}
	@Override
	public String toString() {
		String str=null;
		ColorCouncillor[] map= ColorCouncillor.values();
		for(ColorCouncillor m : map)
			str=str+m.toString() + " -> " + updatedAvailableCouncillors.get(m).toString()+"\n";
		return str;
	}
}