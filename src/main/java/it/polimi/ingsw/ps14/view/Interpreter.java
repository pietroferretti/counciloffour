package it.polimi.ingsw.ps14.view;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.message.fromserver.AvailableAssistantsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.AvailableCouncillorsUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.CitiesColorBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.ErrorMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingBonusesUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.KingUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.NobilityTrackUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.OtherPlayerUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PersonalUpdateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerChangedPrivateMsg;
import it.polimi.ingsw.ps14.message.fromserver.PlayerChangedPublicMsg;
import it.polimi.ingsw.ps14.message.fromserver.RegionUpdatedMsg;
import it.polimi.ingsw.ps14.message.fromserver.SoldItemMsg;
import it.polimi.ingsw.ps14.message.fromserver.StateUpdatedMsg;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interpreter {

	private Communication communication;

	public void setCommunication(Communication communication) {
		this.communication = communication;
	}

	Scanner scan = new Scanner(System.in);

	// TODO ma perché non metterli tutti come toString()?

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

		if (msg instanceof KingBonusesUpdatedMsg) {
			return ((KingBonusesUpdatedMsg) msg).toString();

		}
		if (msg instanceof KingUpdatedMsg) {
			return ((KingUpdatedMsg) msg).toString();

		}

		if (msg instanceof NobilityTrackUpdatedMsg) {
			return ((NobilityTrackUpdatedMsg) msg).toString();

		}
		if (msg instanceof PlayerChangedPrivateMsg) {
			return ((PlayerChangedPrivateMsg) msg).toString();

		}
		if (msg instanceof PlayerChangedPublicMsg) {
			return ((PlayerChangedPublicMsg) msg).getNotice().toString();

		}

		if (msg instanceof CitiesColorBonusesUpdatedMsg) {
			return ((CitiesColorBonusesUpdatedMsg) msg).toString();

		}
		if (msg instanceof RegionUpdatedMsg) {
			return ((RegionUpdatedMsg) msg).getUpdatedRegion().toString();

		}
		if (msg instanceof SoldItemMsg) {
			return ((SoldItemMsg) msg).getItemSold().toString();

		}
		if (msg instanceof StateUpdatedMsg) {
			return ((StateUpdatedMsg) msg).toString();
		}
		// TODO
		if (msg instanceof PersonalUpdateMsg)
			return ((PersonalUpdateMsg) msg).toString();
		if (msg instanceof OtherPlayerUpdateMsg)
			return ((OtherPlayerUpdateMsg) msg).toString();
		return null;
	}

	// public String parseMsg(Message msg) {
	// return null;
	// }
	//
	// public String parseMsg(AvailableAssistantsUpdatedMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(AvailableCouncillorsUpdatedMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(ErrorMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(GamePhaseUpdatedMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(KingBonusesUpdatedMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(KingUpdatedMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(MarketStateUpdatedMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(NewCurrentPlayerMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(NewGamePhaseMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(NewMarketStateMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(NobilityTrackUpdatedMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(PlayerChangedPrivateMsg msg) {
	// return msg.getMessage().toString();
	// }
	//
	// public String parseMsg(PlayerChangedPublicMsg msg) {
	// return msg.getNotice().toString();
	// }
	//
	//
	// public String parseMsg(RegionBonusesUpdatedMsg msg) {
	// return msg.toString();
	// }
	//
	// public String parseMsg(RegionUpdatedMsg msg) {
	// return msg.getUpdatedRegion().toString();
	// }
	//
	// public String parseMsg(SoldItemMsg msg) {
	// return msg.getItemSold().toString();
	// }

	public boolean parseString(String input, Integer playerID) {
		RegionType rt;
		Integer permID;
		ColorCouncillor cc;
		List<PoliticCard> politics = new ArrayList<>();
		String st;
		String[] word = input.split(" ");

		switch (word[0].toUpperCase()) {

		// DRAW
		case "DRAW":
			communication.drawCard(playerID);
			return true;
			// ELECT COLOR REGIONTYPE_KING
		case "ELECT":
			if (word.length != 3)
				return false;

			cc = string2colorCouncillor(word[1]);
			st = string2regionTypeKing(word[2]);

			if (cc == null)
				return false;
			if (st == null)
				return false;
			communication.electCouncillor(playerID, cc, st);
			return true;
			// ACQUIRE REGIONTYPE PERMIT_ID COLOR_POLITIC
		case "ACQUIRE":
			if (word.length < 4)
				return false;

			rt = string2RegionType(word[1]);
			if (rt == null)
				return false;

			permID = string2int(word[2]);
			if (permID == null)
				return false;

			for (int i = 3; i < word.length; i++)
				politics.add(string2politicCard(word[i]));

			communication.acquireBusinessPermitTile(playerID, rt, permID,
					politics);
			return true;
			// BUILD-WITH-KING CITYname CARDS
		case "BUILD-WITH-KING":

			if (word.length < 3)
				return false;

			String city = word[1];

			for (int i = 3; i < word.length; i++)
				politics.add(string2politicCard(word[i]));

			communication.buildWithKing(playerID, city, politics);
			return true;
			// BUILD-WITH-PERMIT CITYname PERMITid
		case "BUILD-WITH-PERMIT":
			if (word.length != 3)
				return false;

			String cityname = word[1];
			try {
				Integer permitID = Integer.parseInt(word[2]);
				communication.buildWithPermit(playerID, permitID, cityname);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

			// ENGAGE
		case "ENGAGE":
			if (word.length != 1)
				return false;
			communication.engage(playerID);
			return true;
			// CHANGE REGIONTYPE
		case "CHANGE":
			if (word.length != 2)
				return false;
			rt = string2RegionType(word[1]);
			communication.changeBusinessPermitTiles(playerID, rt);
			return true;

			// MAIN
		case "MAIN":
			if (word.length != 1)
				return false;
			communication.performAdditionalMainAction(playerID);
			return true;

			// ELECT-WITH-ASSISTANT REGIONTYPE COLORCOUNCILLOR
			// // se rt ==null ->>> balcony del re
		case "ELECT-WITH-ASSISTANT":
			rt = string2RegionType(word[1]);
			if (rt == null && !word[1].matches("KING"))
				return false;
			cc = string2colorCouncillor(word[2]);

			communication.electWithAssistant(playerID, rt, cc);
			return true;
			
		case "CHOOSE":
			if (word.length <= 1) {
				return false;
			}
			
			List<String> chosenIDs = new ArrayList<>();
			for(int i=1; i<word.length; i++) {
				chosenIDs.add(word[i]);
			}
			
			communication.answerNobilityRequest(chosenIDs);
			return true;
			
			// FINISH
		case "FINISH":
		case "PASS":
			if (word.length != 1)
				return false;
			communication.passTurn(playerID);
			return true;
			// SHOW MYDETAILS/DETAILS/GAMEBOARD
		case "SHOW":
			if (word.length != 2)
				return false;

			if (word[1].compareTo("MYDETAILS") == 0) {
				communication.showMyDetails(playerID);
				return true;
			}
			if (word[1].compareTo("DETAILS") == 0) {
				communication.showDetails(playerID);
				return true;
			}
			if (word[1].compareTo("GAMEBOARD") == 0) {
				communication.showGamebord(playerID);
				return true;
			}
			return false;
			// SELL BUSINESS ID1-PRICE,ID2-PRICE,ID3-PRICE... ASSISTANTS
			// NUM-PRICE POLITIC COLOR1-PRICE,COLOR2-PRICE...
		case "SELL":
			String[] splitted;
			String[] stub;
			Integer id,
			price;
			List<ItemForSale> items = new ArrayList<>();
			if (word.length < 3)
				return false;
			for (int i = 1; i < word.length; i++) {
				if (word[i].matches("BUSINESS") && (i + 1) <= word.length) {
					splitted = word[i + 1].split(",");

					for (String s : splitted) {
						stub = s.split("-");
						if (stub.length != 2)
							return false;
						try {
							id = Integer.parseInt(stub[0]);
							price = Integer.parseInt(stub[1]);
						} catch (NumberFormatException e) {
							return false;
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
						return false;
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
							return false;
						ColorPolitic[] colors = ColorPolitic.values();
						for (ColorPolitic c : colors)
							if (c.equals(stub[0])) {

								color = ColorPolitic.valueOf(stub[0]);

								try {
									price = Integer.parseInt(stub[1]);
								} catch (NumberFormatException e) {
									return false;
								}
								items.add(new ItemForSale(color, price,
										playerID));
							}
					}
					communication.sell(items);
					return true;
				}
			}
			return false;

			// BUY ITEM_ID QUANTITY(optional)
		case "BUY":
			Integer quantity = null;
			if (word.length < 2 || word.length > 3)
				return false;

			try {
				permID = Integer.parseInt(word[1]);
				if (word.length == 3) {
					quantity = Integer.parseInt(word[2]);

				}
			} catch (NumberFormatException e) {
				return false;
			}

			communication.buy(permID, playerID, quantity);
			return true;
		default:
			return false;
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
