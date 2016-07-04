package it.polimi.ingsw.ps14.client.view;

import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import it.polimi.ingsw.ps14.client.Communication;
import it.polimi.ingsw.ps14.client.view.gui.GUI;
import it.polimi.ingsw.ps14.message.Message;
import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GamePhase;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.King;
import it.polimi.ingsw.ps14.model.Market;
import it.polimi.ingsw.ps14.model.MarketState;
import it.polimi.ingsw.ps14.model.NobilityTrack;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.Region;
import it.polimi.ingsw.ps14.model.State;
import it.polimi.ingsw.ps14.model.WaitingFor;
import it.polimi.ingsw.ps14.model.bonus.Bonus;
import it.polimi.ingsw.ps14.model.turnstates.CardDrawnState;
import it.polimi.ingsw.ps14.model.turnstates.EndTurnState;
import it.polimi.ingsw.ps14.model.turnstates.InitialTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.MainAndQuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.QuickActionDoneTurnState;
import it.polimi.ingsw.ps14.model.turnstates.TurnState;

public class GUIView extends ClientView implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(GUIView.class.getName());

	private static final String MAPS_DIRECTORY = "src/main/resources/maps/";
	
    private GUI mainWindow;
    private Communication communication;
    private List<List<String>> endResults;

    public GUIView(String name) {
        super.setPlayerName(name);
    }

    @Override
    public void readMessage(Message message) {
        // TODO Auto-generated method stub
        mainWindow.getInfoArea().append(String.format("%n %s", message.toString()));
    }

    @Override
    public void run() {
        Integer id = playerID;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainWindow = new GUI(id, name, communication);
                mainWindow.setState(gameState);
                mainWindow.setVisible(true);
            }
        });

//        GUI.start();
    }

    @Override
    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    @Override
    public void setGameState(State gameState) {
        super.setGameState(gameState);
        if (mainWindow != null) {
            mainWindow.setState(gameState);
        }
    }

    @Override
    public void setGameStarted(boolean gameStarted) {
        super.setGameStarted(gameStarted);

        if (gameStarted) {
            communication.setPlayerName(super.playerID, super.name);
        }

    }

    @Override
    public void loadMap(String mapName) throws IOException {
    	// costruisci filename
    	String mapFileName = MAPS_DIRECTORY + mapName + ".json";
		try (BufferedReader mapFile = new BufferedReader(new FileReader(mapFileName))){
			
			JSONTokener jsonMapFile = new JSONTokener(mapFile);
			JSONObject jsonMapFileObject = (JSONObject) jsonMapFile.nextValue();

			String coastFilename = MAPS_DIRECTORY + jsonMapFileObject.getString("coastimage");
			String hillsFilename = MAPS_DIRECTORY + jsonMapFileObject.getString("hillsimage");
			String mountainsFilename = MAPS_DIRECTORY + jsonMapFileObject.getString("mountainsimage");

			Map<Point, String> positions = new HashMap<>();
			
			JSONObject jsonPositions = jsonMapFileObject.getJSONObject("coordinates");
			Iterator<?> positionsKeys = jsonPositions.keys();
			while (positionsKeys.hasNext()) {
				String cityName = (String) positionsKeys.next();
				JSONArray coordinates = jsonPositions.getJSONArray(cityName);
				Point point = new Point(coordinates.getInt(0), coordinates.getInt(1));
				positions.put(point, cityName);
			}
			
			mainWindow.buildMap(coastFilename, hillsFilename, mountainsFilename, positions);

    	// la gui disegna le immagini
    	// la gui costruisce i label e i panel
		} catch (IOException e) {
			LOGGER.warning(String.format("Couldn't load map '%s'. Check if you have the files you need.", mapName));
			throw e;
		}
    }
    
    @Override
    public void showAvailableAssistant(int update) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainWindow.getInfoArea().append("\n" + "Assistant available now: " + update);
            }
        });
    }

    @Override
    public void showAvailableCommands() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (gameState == null) {

                    mainWindow.getInfoArea().append("\n" + "The game hasn't started yet.");

                } else if (gameState.getGamePhase() == GamePhase.TURNS) {

                    showCommandsTurns();

                } else if (gameState.getGamePhase() == GamePhase.FINALTURNS) {

                    mainWindow.getInfoArea().append("\n" + "Final turns!");
                    showCommandsTurns();

                } else if (gameState.getGamePhase() == GamePhase.MARKET) {

                    showCommandsMarket();

                } else if (gameState.getGamePhase() == GamePhase.END) {

                    mainWindow.getInfoArea().append("\n" + "The game has ended.");

                }
            }
        });
    }

    private void showCommandsTurns() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                mainWindow.getInfoArea().append("\n" + "* Turns Phase *");

                if (gameState.getCurrentPlayer().getId() != playerID) {

                    mainWindow.getInfoArea().append("\n" + String.format("It's %s's turn.", gameState.getCurrentPlayer().getName()));

                } else {

                    mainWindow.getInfoArea().append("\n" + "It's your turn.");

                    if (gameState.getWaitingFor() == WaitingFor.NOTHING) {

                        showCommandsTurnStates();

                    } else if (gameState.getWaitingFor() == WaitingFor.TAKEPERMIT) {

                        mainWindow.getInfoArea().append("\n" + "You got a bonus by moving forward in the nobility track!");
                        mainWindow.getInfoArea().append("\n" + String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

                        for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
                            mainWindow.getInfoArea().append("\n" + String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
                        }

                        mainWindow.getChatArea().append("\n" + "Choose with 'choose id1 [id2 ...]'");

                    } else if (gameState.getWaitingFor() == WaitingFor.FROMPERMITS) {

                        mainWindow.getInfoArea().append("\n" + "You got a bonus by moving forward in the nobility track!");
                        mainWindow.getInfoArea().append("\n" + "You can get the benefits of one of the business permits you own for the second time.");
                        mainWindow.getInfoArea().append("\n" + String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

                        for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
                            mainWindow.getInfoArea().append("\n" + String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
                        }

                        mainWindow.getInfoArea().append("\n" + "Choose with 'choose id1 [id2 ...]'");

                    } else if (gameState.getWaitingFor() == WaitingFor.FROMTOKENS) {

                        mainWindow.getInfoArea().append("\n" + "You got a bonus by moving forward in the nobility track!");
                        mainWindow.getInfoArea().append("\n" + "You can get a bonus from one of the cities where you built an emporium");
                        mainWindow.getInfoArea().append("\n" + String.format("You can choose %d of these: ", gameState.getWaitingForHowMany()));

                        for (Map.Entry<String, String> mapEntry : gameState.getAvailableChoices().entrySet()) {
                            mainWindow.getInfoArea().append("\n" + String.format("%s : %s", mapEntry.getKey(), mapEntry.getValue()));
                        }

                        mainWindow.getInfoArea().append("\n" + "Choose with 'choose id1 [id2 ...]'");

                    }

                }

            }
        });
    }

    private void showCommandsTurnStates() {

        TurnState currTurnState = gameState.getCurrentTurnState();
        if (currTurnState instanceof InitialTurnState) {

            mainWindow.getInfoArea().append("\n" + "Draw a card.");

        } else if ((currTurnState instanceof CardDrawnState)
                || (currTurnState instanceof MainActionDoneTurnState && gameState.getAdditionalMainsToDo() > 0)) {

            mainWindow.getInfoArea().append("\n" + "You can do a main or a quick action.");

        } else if ((currTurnState instanceof QuickActionDoneTurnState)
                || (currTurnState instanceof MainAndQuickActionDoneTurnState
                && gameState.getAdditionalMainsToDo() > 0)) {

            mainWindow.getInfoArea().append("\n" + "You can do a main action.");

        } else if (currTurnState instanceof MainActionDoneTurnState && gameState.getAdditionalMainsToDo() == 0) {

            mainWindow.getInfoArea().append("\n" + "You can do a quick action or pass the turn.");

        } else if (currTurnState instanceof MainAndQuickActionDoneTurnState
                && gameState.getAdditionalMainsToDo() == 0) {

            mainWindow.getInfoArea().append("\n" + "You have already done your main and quick action. You have to pass the turn.");

        } else if (currTurnState instanceof EndTurnState) {

            mainWindow.getInfoArea().append("\n" + "End of your turn, you shouldn't be here.");

        }

    }

    private void showCommandsMarket() {

        mainWindow.getInfoArea().append("\n" + "* Market Phase *");

        if (gameState.getCurrentMarketState() == MarketState.SELLING) {

            mainWindow.getInfoArea().append("\n" + "Currently selling.");

            if (gameState.getCurrentPlayer().getId() != playerID) {

                mainWindow.getInfoArea().append("\n" + String.format("It's %s's turn to sell.", gameState.getCurrentPlayer().getName()));

            } else {

                mainWindow.getInfoArea().append("\n" + "It's your turn to sell.");

            }

        } else if (gameState.getCurrentMarketState() == MarketState.BUYING) {

            mainWindow.getInfoArea().append("\n" + "Currently buying.");

            if (gameState.getCurrentPlayer().getId() != playerID) {

                mainWindow.getInfoArea().append("\n" + String.format("It's %s's turn to buy.", gameState.getCurrentPlayer().getName()));

            } else {

                mainWindow.getInfoArea().append("\n" + "It's your turn to buy. You can buy something or pass the turn.");

            }

        } else if (gameState.getCurrentMarketState() == MarketState.END) {

            mainWindow.getInfoArea().append("\n" + "The market has ended, you shouldn't be here.");

        }

    }

    @Override
    public void showAvailableCouncillor(Map<ColorCouncillor, Integer> updatedAvailableCouncillors) {
        ColorCouncillor[] map = ColorCouncillor.values();
        for (ColorCouncillor m : map) {
            mainWindow.getInfoArea().append("\n" + m.toString() + " -> " + updatedAvailableCouncillors.get(m).toString() + "\n");
        }
    }

    @Override
    public void showChatMsg(String author, String text) {
        mainWindow.getChatArea().append("\n" + author + ": " + text);
    }

    @Override
    public void showCitiesColorBonuses(int updatedBonusGold, int updatedBonusSilver, int updatedBonusBronze,
            int updatedBonusBlue) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//    	mainWindow.getChatArea().append("\n"+"CitiesColorBonuses now: BonusGold=" + updatedBonusGold + ", BonusSilver="
//				+ updatedBonusSilver + ", BonusBronze=" + updatedBonusBronze + ", BonusBlue=" + updatedBonusBlue);
                mainWindow.getInfoArea().append("");
                mainWindow.getInfoArea().append("Available city color bonuses:");


                    mainWindow.getInfoArea().append(String.format("%nGold cities: %d victory points", updatedBonusGold));
                    mainWindow.getGoldCity().removeAll();
                    mainWindow.getGoldCity().add(new JLabel(Integer.toString(updatedBonusGold)));
                    mainWindow.getGoldCity().revalidate();

                

                    mainWindow.getInfoArea().append(String.format("%nSilver cities: %d victory points", updatedBonusSilver));
                    mainWindow.getSilverCity().removeAll();
                    mainWindow.getSilverCity().add(new JLabel(Integer.toString(updatedBonusSilver)));
                    mainWindow.getSilverCity().revalidate();

                

                    mainWindow.getInfoArea().append(String.format("%nBronze cities: %d victory points", updatedBonusBronze));
                    mainWindow.getBronzeCity().removeAll();
                    mainWindow.getBronzeCity().add(new JLabel(Integer.toString(updatedBonusBronze)));
                    mainWindow.getBronzeCity().revalidate();


                    mainWindow.getInfoArea().append(String.format("%nBlue cities: %d victory points", updatedBonusBlue));
                    mainWindow.getBlueCity().removeAll();
                    mainWindow.getBlueCity().add(new JLabel(Integer.toString(updatedBonusBlue) ));
                    mainWindow.getBlueCity().revalidate();

                
            }
        });
    }

    @Override
    public void showEndGame(List<List<String>> endResults) {
        this.endResults = endResults;
        if (endResults == null) {
            mainWindow.getInfoArea().append("\n" + "The game hasn't ended yet.");
            return;
        }

        // 0 - id
        // 1 - name
        // 2 - points
        // 3 - assistants
        // 4 - cards
        // 5 - emporiums
        // 6 - nobility
        // 7 - permits
        // 8 - coins
        mainWindow.getInfoArea().append("\n" + "*** THE GAME HAS ENDED ***");
        mainWindow.getInfoArea().append("\n" + String.format("The winner is %s, with %s points!", endResults.get(0).get(1), endResults.get(0).get(2)));

        if (this.getPlayerID().equals(Integer.valueOf(endResults.get(0).get(0)))) {
            mainWindow.getInfoArea().append("\n" + "Congratulations! You won!");
        }

        mainWindow.getInfoArea().append("\n" + "Complete rankings:");

        for (int i = 0; i < endResults.size(); i++) {
            List<String> plrRes = endResults.get(i);

            mainWindow.getInfoArea().append("\n" + String.format("%d) %s with %s points, %s assistants and %s cards.", i, plrRes.get(1),
                    plrRes.get(2), plrRes.get(3), plrRes.get(4)));
            mainWindow.getInfoArea().append("\n" + String.format("  %s emporiums,  %s nobility,  %s permits,  %s coins", plrRes.get(5),
                    plrRes.get(6), plrRes.get(7), plrRes.get(8)));
        }

    }

    @Override
    public void showGameStart() {
        // TODO Auto-generated method stub
        GameStartedDialog d;
        d = new GameStartedDialog(mainWindow, true);
        d.setVisible(true);
        communication.showMyDetails(playerID);
        communication.showGameboard(playerID);
    }

    @Override
    public void showInfo(String text) {
        // TODO Auto-generated method stub
        mainWindow.getInfoArea().append("\n" + text);
    }

    @Override
    public void showItemSold(ItemForSale item) {
        mainWindow.getInfoArea().append("\n" + item.toString());

    }

    @Override
    public void showKingBonus(int updatedShowableKingBonus) {
//        mainWindow.getCLIarea().append("\n"+"KingBonusesUpdatedMsg [updatedShowableKingBonus=" + updatedShowableKingBonus + "]");
        mainWindow.getkingBonus().setText(String.format("%nNext King bonus: %d victory points", updatedShowableKingBonus));

    }

    @Override
    public void showKingUpdate(King updatedKing) {
//		mainWindow.getCLIarea().append("\n"+"KingUpdatedMsg [updatedKing=" + updatedKing + "]");
        mainWindow.getInfoArea().append("\n" + updatedKing.toString());
        mainWindow.getKingCity().setText(updatedKing.getCity().getName());
        mainWindow.showCouncillor(updatedKing.getBalcony(), null);

    }

    @Override
    public void showMarket(Market updatedMarket) {
        mainWindow.getInfoArea().append("\n" + updatedMarket.toString());

    }

    @Override
    public void showNobilityTrack(NobilityTrack updatedNobilityTrack) {
        JLabel lv;
        mainWindow.getnobilityTrack().removeAll();
//        JLabellv=new javax.swing.JLabel("<html><div WIDTH=200px>"+updatedNobilityTrack.toString()+"</div></html><br>");
//        lv.setFont(new java.awt.Font("Arial", 1, 11));
//        mainWindow.getnobilityTrack().add(lv);
        for (Map.Entry<Integer, Bonus> entry : updatedNobilityTrack.getBonusesByLevel().entrySet()) {
            lv = new javax.swing.JLabel("<html>" + Integer.toString(entry.getKey()) + ")<br><div WIDTH=200px>" + entry.getValue() + "</div></html><br>");
            lv.setFont(new java.awt.Font("Arial", 0, 11));
            lv.setVisible(true);
            mainWindow.getnobilityTrack().add(lv);
        }
        mainWindow.getnobilityTrack().revalidate();

    }

    @Override
    public void showOtherPlayer(int id, String name, Color color, int coins, int assistants, int level, int points,
            int numEmporiums) {
        mainWindow.getInfoArea().append("\n" + "\nName: " + name + "\nColor: " + color.toString() + "\nCoins: " + Integer.toString(coins)
                + "\nAssistants: " + Integer.toString(assistants) + "\nNobility level: " + Integer.toString(level)
                + "\nVictory Points: " + Integer.toString(points));
    }

    @Override
    public void showPersonalDetails(Player p) {
         SwingUtilities.invokeLater(new Runnable() {
            @Override 
            public void run() {
        mainWindow.getUserProfile().removeAll();
        mainWindow.getUserProfile().revalidate();
        mainWindow.getCoins().setText(Integer.toString(p.getCoins()));
        mainWindow.getAssistants().setText(Integer.toString(p.getAssistants()));
        mainWindow.getVictoryPoints().setText(Integer.toString(p.getPoints()));
        mainWindow.getNobility().setText(Integer.toString(p.getLevel()));
        for (PoliticCard pc : p.getHand()) {
            mainWindow.showPoliticCard(pc.getColor());
        }
        mainWindow.getUserProfile().revalidate();
        //TODO my permit
    }});}

    @Override
    public void showPlayerChangesPrivate(Player p, String message) {
        // TODO Auto-generated method stub
//        System.out.println("it.polimi.ingsw.ps14.client.view.GUIView.showPlayerChangesPrivate()");	
//        System.out.println(message);
        mainWindow.getInfoArea().append("\n" + message);
        showPersonalDetails(p);

    }

    @Override
    public void showPlayerChangesPublic(String notice) {
        mainWindow.getInfoArea().append("\n" + notice);
        communication.showMyDetails(playerID);
    }

    @Override
    public void showPrivateMsg(String text) {
        // TODO Auto-generated method stub
        mainWindow.getInfoArea().append("\n" + text);

    }

    @Override
    public void showRegion(Region updatedRegion) {
        mainWindow.getInfoArea().append("\n" + updatedRegion.toString());
        mainWindow.showCouncillor(updatedRegion.getBalcony(), updatedRegion.getType());
        mainWindow.showPermit(updatedRegion.getAvailablePermits(), updatedRegion.getType());
    }
}
