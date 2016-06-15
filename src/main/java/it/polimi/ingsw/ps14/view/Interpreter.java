package it.polimi.ingsw.ps14.view;

import it.polimi.ingsw.ps14.message.ActionMsg;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interpreter {
	private final Integer playerID;
	Scanner scan = new Scanner(System.in);

	public Interpreter(Integer playerID) {
		this.playerID = playerID;
	}

	public void sent(String input) {

	}

	public void sentAction(Action input) {
		new ActionMsg(input);
	}

	public Message parseString(String input) {
		String[] word = input.split(" ");

		switch (word[0]) {

		// DRAW
		case "DRAW":
			return (new ActionMsg(new DrawCardAction(playerID)));

		// ELECT COLOR REGIONTYPE_KING
		case "ELECT":
			if (word.length != 3)
				return null;

			ColorCouncillor cc;
			String st;

			cc = string2colorCouncillor(word[1]);
			st = string2regionTypeKing(word[2]);

			if (cc == null)
				return null;
			if (st == null)
				return null;
			return (new ActionMsg(new ElectCouncillorAction(playerID, cc, st)));
			

		//ACQUIRE REGIONTYPE PERMIT_ID COLOR_POLITIC
		case "ACQUIRE":
			if(word.length < 4) return null;
			
			RegionType rt;
			Integer permID;
			List<PoliticCard> politics=new ArrayList<>();
			PoliticCard pc;
			
			rt=string2RegionType(word[1]);
			if(rt==null) return null;
			
			permID=string2int(word[2]);
			if(permID==null) return null;
						
		//	for(int i=3;i<word.length;i++)
	        //FIXME    
			
			

		}

			
		
		

		if (input.matches("^(main action | main)$")) {
			// TODO STAMPA COMANDI
			// return new MainActionRequestMsg();
		}

		if (input.matches("^build with king(\\s*\\d*)+$")) {
			// MA LA CITY CHE PRENDE NON è QUELLA DEL RE??

			// return new MainActionRBuildEmporiumWithHelpOfKingtMsg();
		}
		return null;

	}

	private Integer string2int(String string) {
		Integer permID=null;
		try {
			 permID= Integer.parseInt(string);
		} catch (NumberFormatException e) {
		      return null;
		}		return permID;
	}

	private String string2regionTypeKing(String string) {
		
		if(string2RegionType(string)!=null) return string;
		if (string.compareTo("KING") == 0)
			return string;
		return null;
	}

	private ColorCouncillor string2colorCouncillor(String word) {
		// convert string to colorCounicllor
		ColorCouncillor[] colorValue = ColorCouncillor.values();

		for (ColorCouncillor color : colorValue) {
			if (color.name().compareTo(word) == 0)
				return ColorCouncillor.valueOf(word);
		}
		return null;
	}
	
	private RegionType string2RegionType(String input){
		RegionType[] regions = RegionType.values();

		for (RegionType reg : regions) {
			if (reg.name().compareTo(input) == 0)
				return reg;
		}
		return null;
	}
	
	private PoliticCard string2politicCard(String string){
		PoliticCard[] colorValue = 

		for (PoliticCard color : colorValue) {
			if (color.name().compareTo(string) == 0)
				return PoliticCard.valueOf(word);
		}
	}
}
