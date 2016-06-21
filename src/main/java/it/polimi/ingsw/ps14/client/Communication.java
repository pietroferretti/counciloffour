package it.polimi.ingsw.ps14.client;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;

import java.util.List;

public interface Communication {
	
	public void setPlayerName(String name);
	public void drawCard(Integer playerID);
	public void electCouncillor(Integer playerID, ColorCouncillor cc, String regionORking);
	public void acquireBusinessPermitTile(Integer playerID,RegionType rt, Integer permID, List<PoliticCard> politics);
	public void buildWithKing(Integer playerID, String city, List<PoliticCard> politics);
	public void buildWithPermit(Integer playerID, Integer permitID, String cityname);
	public void engage(Integer playerID);
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt);
	public void performAdditionalMainAction(Integer playerID);
	public void electWithAssistant(Integer playerID,RegionType rt,ColorCouncillor cc);
	public void usedCard(Integer permID);
	public void passTurn(Integer playerID);
	public void showMyDetails(Integer playerID);
	public void showDetails(Integer playerID);
	public void showGamebord(Integer playerID);
	public void sell(List<ItemForSale> items);
	public void buy(Integer permID,Integer playerID,Integer quantity);
	
	
}
