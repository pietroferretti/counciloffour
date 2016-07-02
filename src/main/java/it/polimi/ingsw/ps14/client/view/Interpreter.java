package it.polimi.ingsw.ps14.client.view;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ColorPolitic;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;

public class Interpreter {

	private Communication communication;
	private CLIView cliView;

	public Interpreter(CLIView cliView) {
		this.cliView = cliView;
	}
	
	public void setCommunication(Communication communication) {
		this.communication = communication;
	}

	public Communication getCommunication() {
		return communication;
	}

	public boolean parseString(String input, Integer playerID) {
		RegionType rt;
		Integer permID;
		ColorCouncillor cc;
		List<PoliticCard> politics = new ArrayList<>();
		String st;
		String[] word = input.split(" ");
		if (word.length < 1)
			return false;
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
			communication.electCouncillor(playerID, cc, st.toUpperCase());
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

			for (int i = 3; i < word.length; i++) {
                            PoliticCard card = string2politicCard(word[i]);
                            if (card == null) {
                                return false;
                            } else {
                                politics.add(card);
                            }
                        }

			communication.acquireBusinessPermitTile(playerID, rt, permID,
					politics);
                        
			return true;
                        
			// BUILD-WITH-KING CITYname CARDS
		case "BUILD-WITH-KING":

			if (word.length < 3)
				return false;

			String city = word[1];

			for (int i = 3; i < word.length; i++) {
                            PoliticCard card = string2politicCard(word[i]);
                            if (card == null) {
                                return false;
                            } else {
                                politics.add(card);
                            }
                        }

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
                        
                        if (rt == null) {
                            return false;
                        }
                        
			communication.changeBusinessPermitTiles(playerID, rt);
			return true;

			// MAIN
		case "MAIN":
			if (word.length != 1)
                            return false;
			communication.performAdditionalMainAction(playerID);
			return true;

			// ELECT-WITH-ASSISTANT REGIONTYPE COLORCOUNCILLOR
			// se rt ==null ->>> balcony del re
		case "ELECT-WITH-ASSISTANT":
			if (word.length != 3)
                            return false;
			rt = string2RegionType(word[1]);
			if (rt == null && !word[1].matches("KING"))
                            return false;
			cc = string2colorCouncillor(word[2]);
                        
                        if (cc == null)
                            return false;

			communication.electWithAssistant(playerID, rt, cc);
			return true;

		case "CHOOSE":
			if (word.length <= 1) {
				return false;
			}

			List<String> chosenIDs = new ArrayList<>();
			for (int i = 1; i < word.length; i++) {
				chosenIDs.add(word[i]);
			}

			communication.answerNobilityRequest(playerID, chosenIDs);
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

			if (word[1].equalsIgnoreCase("MYDETAILS")) {
				communication.showMyDetails(playerID);
				return true;
			}
			if (word[1].equalsIgnoreCase("DETAILS")) {
				communication.showDetails(playerID);
				return true;
			}
			if (word[1].equalsIgnoreCase("GAMEBOARD")) {
				communication.showGameboard(playerID);
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
			if (word.length < 2)
				return false;
			for (int i = 1; i < word.length; i++) {
				if (word[i].equalsIgnoreCase("BUSINESS")
						&& (i + 1) <= word.length) {
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
				if (word[i].equalsIgnoreCase("ASSISTANTS")
						&& (i + 1) <= word.length) {
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
				if (word[i].equalsIgnoreCase("POLITIC") && (i + 1) <= word.length) {
					ColorPolitic color;
					splitted = word[i + 1].split(",");
					for (String s : splitted) {
						stub = s.split("-");
						System.out.println("ok:" + stub[0] + stub[1]);
						if (stub.length != 2)
							return false;
						ColorPolitic[] colors = ColorPolitic.values();
						for (ColorPolitic c : colors)
							if (c.name().matches(stub[0])) {
								color = ColorPolitic.valueOf(stub[0].toUpperCase());
								try {
									price = Integer.parseInt(stub[1]);
								} catch (NumberFormatException e) {
									return false;
								}
								items.add(new ItemForSale(color, price,
										playerID));
							}
					}

				}
				if (word[i].equalsIgnoreCase("NONE")) {
					communication.sellNone(playerID);
					return true;
				}
			}
			
			if (items.isEmpty()) {
				return false;
			}
			
			communication.sell(playerID, items);
			System.out.println("ho inviato " + items);
			return true;

			// BUY ITEM_ID QUANTITY(optional)
		case "BUY":
			Integer quantity = null;
			Integer objID;
			if (word.length < 2 || word.length > 3)
				return false;
			if (word[1].equalsIgnoreCase("FINISH")) {
				communication.doneFinishBuying(playerID);
				return true;
			}

			try {
				objID = Integer.parseInt(word[1]);
				if (word.length == 3) {
					quantity = Integer.parseInt(word[2]);

				}
			} catch (NumberFormatException e) {
				return false;
			}

			communication.buy(playerID, objID, quantity);
			return true;

		case "CHAT":
			communication.chat(playerID, input.substring(4));
			return true;
		
		case "RESULTS":
			cliView.showEndGame();

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
		if (string.equalsIgnoreCase("KING"))
			return string;
		return null;
	}

	private ColorCouncillor string2colorCouncillor(String word) {
		// convert string to colorCounicllor
		ColorCouncillor[] colorValue = ColorCouncillor.values();

		for (ColorCouncillor color : colorValue) {
			if (color.name().equalsIgnoreCase(word))
				return ColorCouncillor.valueOf(word.toUpperCase());
		}
		return null;
	}

	private RegionType string2RegionType(String input) {
		RegionType[] regions = RegionType.values();

		for (RegionType reg : regions) {
			if (reg.name().equalsIgnoreCase(input))
				return reg;
		}
		return null;
	}

	private PoliticCard string2politicCard(String string) {
		ColorPolitic[] colors = ColorPolitic.values();

		for (ColorPolitic cp : colors)
			if (string.equalsIgnoreCase(cp.name()))
				return new PoliticCard(ColorPolitic.valueOf(string.toUpperCase()));
		return null;
	}

}
