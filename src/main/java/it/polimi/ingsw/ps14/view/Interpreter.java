package it.polimi.ingsw.ps14.view;

import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.NewGamePhaseMsg;
import it.polimi.ingsw.ps14.message.NewMarketStateMsg;
import it.polimi.ingsw.ps14.message.TurnFinishedMsg;
import it.polimi.ingsw.ps14.message.fromClient.BuyMsg;
import it.polimi.ingsw.ps14.message.fromClient.ChooseUsedPermitMsg;
import it.polimi.ingsw.ps14.message.fromClient.NewCurrentPlayerMsg;
import it.polimi.ingsw.ps14.message.fromClient.SellMsg;
import it.polimi.ingsw.ps14.message.fromClient.TurnActionMsg;
import it.polimi.ingsw.ps14.message.fromClient.UpdateGameBoardMsg;
import it.polimi.ingsw.ps14.message.fromClient.UpdateOtherPlayerMsg;
import it.polimi.ingsw.ps14.message.fromClient.UpdateThisPlayerMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableCouncillorsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.ErrorMsg;
import it.polimi.ingsw.ps14.message.fromserver.GamePhaseUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.MarketStateUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.NobilityTrackdUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerChangedPrivateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerChangedPublicMsg;
import it.polimi.ingsw.ps14.message.fromserver.RegionBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.SoldItemMsg;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermiteTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumWithHelpOfKingAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.market.BuyAction;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;
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

		if (msg instanceof AvailableAssistantsUpdatedMsg) {
			return ((AvailableAssistantsUpdatedMsg) msg).toString();
		}
		if (msg instanceof AvailableCouncillorsUpdatedMsg) {
			return ((AvailableCouncillorsUpdatedMsg) msg).toString();

		}
		if (msg instanceof ErrorMsg) {
			return ((ErrorMsg) msg).toString();

		}
		if (msg instanceof GamePhaseUpdatedMsg) {
			return ((GamePhaseUpdatedMsg) msg).toString();

		}
		if (msg instanceof KingBonusesUpdatedMsg) {
			return ((KingBonusesUpdatedMsg) msg).toString();

		}
		if (msg instanceof KingUpdatedMsg) {
			return ((KingUpdatedMsg) msg).toString();

		}
		if (msg instanceof MarketStateUpdatedMsg) {
			return ((MarketStateUpdatedMsg) msg).toString();

		}
		if (msg instanceof NewCurrentPlayerMsg) {
			return ((NewCurrentPlayerMsg) msg).toString();

		}
		if (msg instanceof NewGamePhaseMsg) {
			return ((NewGamePhaseMsg) msg).toString();

		}
		if (msg instanceof NewMarketStateMsg) {
			return ((NewMarketStateMsg) msg).toString();

		}
		if (msg instanceof NobilityTrackdUpdatedMsg) {
			return ((NobilityTrackdUpdatedMsg) msg).toString();

		}
		if (msg instanceof PlayerChangedPrivateMsg) {
			return ((PlayerChangedPrivateMsg) msg).getMessage().toString();

		}
		if (msg instanceof PlayerChangedPublicMsg) {
			return ((PlayerChangedPublicMsg) msg).getNotice().toString();

		}

		if (msg instanceof RegionBonusesUpdatedMsg) {
			return ((RegionBonusesUpdatedMsg) msg).toString();

		}
		if (msg instanceof RegionUpdatedMsg) {
			return ((RegionUpdatedMsg) msg).getUpdatedRegion().toString();

		}
		if (msg instanceof SoldItemMsg) {
			return ((SoldItemMsg) msg).getItemSold().toString();

		}

		return null;
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
			return (new TurnActionMsg(new DrawCardAction(playerID)));

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
			return (new TurnActionMsg(new ElectCouncillorAction(playerID, cc,
					st)));

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

			return new TurnActionMsg(new AcquireBusinessPermiteTileAction(
					permID, rt, permID, new ArrayList<PoliticCard>(politics)));

			// BUILD-WITH-KING CITYname CARDS
		case "BUILD-WITH-KING":

			if (word.length < 3)
				return null;

			String city = word[1];

			for (int i = 3; i < word.length; i++)
				politics.add(string2politicCard(word[i]));

			return new TurnActionMsg(new BuildEmporiumWithHelpOfKingAction(
					playerID, city, politics));

			// BUILD-WITH-PERMIT PERMITid CITYname
		case "BUILD-WITH-PERMIT":
			if (word.length != 3)
				return null;
			try {
				Integer permitID = Integer.parseInt(word[1]);
				return new TurnActionMsg(
						new BuildEmporiumUsingPermitTileAction(playerID,
								permitID, word[2]));
			} catch (NumberFormatException e) {
				return null;
			}

			// ENGAGE
		case "ENGAGE":
			if (word.length != 1)
				return null;
			return new TurnActionMsg(new EngageAssistantAction(playerID));

			// CHANGE REGIONTYPE
		case "CHANGE":
			if (word.length != 2)
				return null;
			rt = string2RegionType(word[1]);
			return new TurnActionMsg(new ChangeBusinessPermitTilesAction(
					playerID, rt));

			// MAIN
		case "MAIN":
			if (word.length != 1)
				return null;
			return new TurnActionMsg(new PerformAdditionalMainActionAction(
					playerID));

			// ELECT-WITH-ASSISTANT REGIONTYPE COLORCOUNCILLOR
		case "ELECT-WITH-ASSISTANT":
			if (word.length != 3)
				return null;
			rt = string2RegionType(word[1]);
			cc = string2colorCouncillor(word[2]);

			return new TurnActionMsg(new SendAssistantToElectCouncillorAction(
					playerID, rt, cc));

			// USED-CARD PERMITid
		case "USED-CARD":
			if (word.length == 2)
				return null;
			try {
				permID = Integer.parseInt(word[1]);
				return new ChooseUsedPermitMsg(permID);
			} catch (NumberFormatException e) {
				return null;
			}
			// FINISH
		case "FINISH":
			if (word.length != 1)
				return null;
			return new TurnFinishedMsg(playerID);

			// SHOW MYDETAILS/DETAILS/GAMEBOARD
		case "SHOW":
			if (word.length != 2)
				return null;

			if (word[1].compareTo("MYDETAILS") == 0) {
				try {
					playerID = Integer.parseInt(word[1]);
					return new UpdateThisPlayerMsg(playerID);
				} catch (NumberFormatException e) {
					return null;
				}
			}
			if (word[1].compareTo("DETAILS") == 0) {
				try {
					playerID = Integer.parseInt(word[1]);
					return new UpdateOtherPlayerMsg(playerID);
				} catch (NumberFormatException e) {
					return null;
				}
			}
			if (word[1].compareTo("GAMEBOARD") == 0) {
				try {
					playerID = Integer.parseInt(word[1]);
					return new UpdateGameBoardMsg();
				} catch (NumberFormatException e) {
					return null;
				}
			}
			// SELL BUSINESS ID1-PRICE,ID2-PRICE,ID3-PRICE... ASSISTANTS
			// NUM-PRICE POLITIC COLOR1-PRICE,COLOR2-PRICE...
		case "SELL":
			String[] splitted;
			String[] stub;
			Integer id,
			price;
			List<ItemForSale> items = new ArrayList<>();
			if (word.length < 3)
				return null;
			for (int i = 1; i < word.length; i++) {
				if (word[i].matches("BUSINESS") && (i + 1) <= word.length) {
					splitted = word[i + 1].split(",");

					for (String s : splitted) {
						stub = s.split("-");
						if (stub.length != 2)
							return null;
						try {
							id = Integer.parseInt(stub[0]);
							price = Integer.parseInt(stub[1]);
						} catch (NumberFormatException e) {
							return null;
						}
						items.add(new ItemForSale(ItemForSale.Type.BUSINESS,
								id, price, playerID));
					}
				}
				if (word[i].matches("ASSISTANTS") && (i + 1) <= word.length) {
					splitted = word[i + 1].split("-");
					try {
						id = Integer.parseInt(splitted[0]);
						price = Integer.parseInt(splitted[1]);
					} catch (NumberFormatException e) {
						return null;
					}
					items.add(new ItemForSale(ItemForSale.Type.ASSISTANT, id,
							price, playerID));
				}
				if (word[i].matches("POLITIC") && (i + 1) <= word.length) {
					ColorPolitic color;
					splitted = word[i + 1].split(",");

					for (String s : splitted) {
						stub = s.split("-");
						if (stub.length != 2)
							return null;
						ColorPolitic[] colors = ColorPolitic.values();
						for (ColorPolitic c : colors)
							if (c.equals(stub[0])) {

								color = ColorPolitic.valueOf(stub[0]);

								try {
									price = Integer.parseInt(stub[1]);
								} catch (NumberFormatException e) {
									return null;
								}
								items.add(new ItemForSale(color, price,
										playerID));
							}
					}
					return new SellMsg(new SellAction(items));
				}
			}
			return null;

			// BUY ITEM_ID QUANTITY(optional)
		case "BUY":
			Integer quantity=null;
			if (word.length < 2 || word.length > 3)
				return null;

			try {
				permID = Integer.parseInt(word[1]);
				if (word.length == 3) {
					quantity = Integer.parseInt(word[2]);

				}
			} catch (NumberFormatException e) {
				return null;
			}
			if(quantity==null)
				return new BuyMsg(new BuyAction(permID,playerID));
			if(quantity>0)
				return new BuyMsg(new BuyAction(permID,playerID,quantity));
			return null;
			
		
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
