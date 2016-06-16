package it.polimi.ingsw.ps14.view;

import it.polimi.ingsw.ps14.message.ActionMsg;
import it.polimi.ingsw.ps14.message.AssistantNumberChangedMsg;
import it.polimi.ingsw.ps14.message.AvailableCouncillorsChangedMsg;
import it.polimi.ingsw.ps14.message.ChooseUsedPermitMsg;
import it.polimi.ingsw.ps14.message.EndTurnMsg;
import it.polimi.ingsw.ps14.message.GameStartedMsg;
import it.polimi.ingsw.ps14.message.KingBonusesChanged;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.NewCurrentPlayerMsg;
import it.polimi.ingsw.ps14.message.NewGamePhaseMsg;
import it.polimi.ingsw.ps14.message.NewMarketStateMsg;
import it.polimi.ingsw.ps14.message.PlayerChangedPrivateMsg;
import it.polimi.ingsw.ps14.message.PlayerChangedPublicMsg;
import it.polimi.ingsw.ps14.message.RegionBonusesChangedMsg;
import it.polimi.ingsw.ps14.message.SoldItemMsg;
import it.polimi.ingsw.ps14.message.TurnFinishedMsg;
import it.polimi.ingsw.ps14.message.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.UpdateOtherPlayerMsg;
import it.polimi.ingsw.ps14.message.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.Action;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermiteTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumWithHelpOfKingAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.ChangeBusinessPermitTilesAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.EngageAssistantAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.PerformAdditionalMainActionAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.SendAssistantToElectCouncillorAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interpreter {
	Scanner scan = new Scanner(System.in);

	public String parseMsg(Message msg) {
		if (msg instanceof AssistantNumberChangedMsg) {
			AssistantNumberChangedMsg arg = (AssistantNumberChangedMsg) msg;

		}
		if (msg instanceof AvailableCouncillorsChangedMsg) {

		}

		if (msg instanceof AvailableCouncillorsChangedMsg) {

		}
		if (msg instanceof EndTurnMsg) {

		}
		if (msg instanceof GameStartedMsg) {

		}
		if (msg instanceof KingBonusesChanged) {

		}
		if (msg instanceof NewCurrentPlayerMsg) {

		}
		if (msg instanceof NewGamePhaseMsg) {

		}
		if (msg instanceof NewMarketStateMsg) {

		}
		if (msg instanceof PlayerChangedPrivateMsg) {

		}
		if (msg instanceof PlayerChangedPublicMsg) {

		}
		if (msg instanceof RegionBonusesChangedMsg) {

		}
		if (msg instanceof SoldItemMsg) {

		}
		if (msg instanceof TurnFinishedMsg) {
			// qui o in input dall'utente

		}
		if (msg instanceof UpdateOtherPlayerMsg) {

		}
		if (msg instanceof UpdateGameBoardMsg) {

		}
		if (msg instanceof UpdateThisPlayerMsg) {

		}
		return null;
	}

	public void sentAction(Action input) {
		new ActionMsg(input);
	}

	public Message parseString(String input, Integer playerID) {
		RegionType rt;
		Integer permID;
		ColorCouncillor cc;
		List<PoliticCard> politics = new ArrayList<>();
		String st;
		String[] word = input.split(" ");

		switch (word[0]) {

		// DRAW
		case "DRAW":
			return (new ActionMsg(new DrawCardAction(playerID)));

			// ELECT COLOR REGIONTYPE_KING
		case "ELECT":
			if (word.length != 3)
				return null;

			cc = string2colorCouncillor(word[1]);
			st = string2regionTypeKing(word[2]);

			if (cc == null)
				return null;
			if (st == null)
				return null;
			return (new ActionMsg(new ElectCouncillorAction(playerID, cc, st)));

			// ACQUIRE REGIONTYPE PERMIT_ID COLOR_POLITIC
		case "ACQUIRE":
			if (word.length < 4)
				return null;

			rt = string2RegionType(word[1]);
			if (rt == null)
				return null;

			permID = string2int(word[2]);
			if (permID == null)
				return null;

			for (int i = 3; i < word.length; i++)
				politics.add(string2politicCard(word[i]));

			return new ActionMsg(new AcquireBusinessPermiteTileAction(permID,
					rt, permID, politics));

			// BUILD-WITH-KING CITYname CARDS
		case "BUILD-WITH-KING":

			if (word.length < 3)
				return null;

			String city = word[1];

			for (int i = 3; i < word.length; i++)
				politics.add(string2politicCard(word[i]));

			return new ActionMsg(new BuildEmporiumWithHelpOfKingAction(
					playerID, city, politics));

			// BUILD-WITH-PERMIT PERMITid CITYname
		case "BUILD-WITH-PERMIT":
			if (word.length != 3)
				return null;
			try {
				Integer permitID = Integer.parseInt(word[1]);
				return new ActionMsg(new BuildEmporiumUsingPermitTileAction(
						playerID, permitID, word[2]));
			} catch (NumberFormatException e) {
				return null;
			}

			// ENGAGE
		case "ENGAGE":
			if (word.length != 1)
				return null;
			return new ActionMsg(new EngageAssistantAction(playerID));

			// CHANGE REGIONTYPE
		case "CHANGE":
			if (word.length != 2)
				return null;
			rt = string2RegionType(word[1]);
			return new ActionMsg(new ChangeBusinessPermitTilesAction(playerID,
					rt));

			// MAIN
		case "MAIN":
			if (word.length != 1)
				return null;
			return new ActionMsg(
					new PerformAdditionalMainActionAction(playerID));

			// ELECT-WITH-ASSISTANT REGIONTYPE COLORCOUNCILLOR
		case "ELECT-WITH-ASSISTANT":
			if (word.length != 3)
				return null;
			rt = string2RegionType(word[1]);
			cc = string2colorCouncillor(word[2]);

			return new ActionMsg(new SendAssistantToElectCouncillorAction(
					playerID, rt, cc));

			// USED-CARD PERMITid
		case "USED-CARD":
			if (word.length == 2)
				return null;
			try {
				permID = Integer.parseInt(word[1]);
				// FIXME
				return new ChooseUsedPermitMsg(permID);
			} catch (NumberFormatException e) {
				return null;
			}
			
		case "FINISH":
			if(word.length!=1) return null;
			return new TurnFinishedMsg(playerID);

		default:
			return null;
		}

	}

	private Integer string2int(String string) {
		Integer permID = null;
		try {
			permID = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return null;
		}
		return permID;
	}

	private String string2regionTypeKing(String string) {

		if (string2RegionType(string) != null)
			return string;
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

	private RegionType string2RegionType(String input) {
		RegionType[] regions = RegionType.values();

		for (RegionType reg : regions) {
			if (reg.name().compareTo(input) == 0)
				return reg;
		}
		return null;
	}

	private PoliticCard string2politicCard(String string) {
		ColorPolitic[] colors = ColorPolitic.values();

		for (ColorPolitic cp : colors)
			if (string.compareTo(cp.name()) == 0)
				return new PoliticCard(ColorPolitic.valueOf(string));
		return null;
	}
}
