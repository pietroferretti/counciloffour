package it.polimi.ingsw.ps14.message;

import it.polimi.ingsw.ps14.model.ColorCouncillor;

import java.util.EnumMap;

public class AvailableCouncillorsChangedMsg implements Message {

	private EnumMap<ColorCouncillor, Integer> newValues;

	public AvailableCouncillorsChangedMsg(EnumMap<ColorCouncillor, Integer> newValues) {
		this.newValues = newValues;
	}

	public EnumMap<ColorCouncillor, Integer> getNewValues() {
		return newValues;
	}

	@Override
	public String toString() {
		String str=null;
		ColorCouncillor[] map= ColorCouncillor.values();
		for(ColorCouncillor m : map)
			str=str+m.toString() + " -> " + newValues.get(m).toString()+"\n";
		return str;
	}
	
	
}
