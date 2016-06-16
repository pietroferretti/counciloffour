package it.polimi.ingsw.ps14.message;

public class AssistantNumberChangedMsg implements Message {

	private int newNumber;

	public AssistantNumberChangedMsg(int newNumber) {
		this.newNumber = newNumber;
	}

	public int getNewNumber() {
		return newNumber;
	}

	@Override
	public String toString() {
		return "Assistant available now: "+newNumber;
	}
	
	
}
