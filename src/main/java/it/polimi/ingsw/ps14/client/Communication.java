package it.polimi.ingsw.ps14.client;

import java.util.List;

import it.polimi.ingsw.ps14.model.ColorCouncillor;
import it.polimi.ingsw.ps14.model.GameBoard;
import it.polimi.ingsw.ps14.model.ItemForSale;
import it.polimi.ingsw.ps14.model.Player;
import it.polimi.ingsw.ps14.model.PoliticCard;
import it.polimi.ingsw.ps14.model.RegionType;
import it.polimi.ingsw.ps14.model.actions.DrawCardAction;
import it.polimi.ingsw.ps14.model.actions.EndTurnAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.AcquireBusinessPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumUsingPermitTileAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.BuildEmporiumWithHelpOfKingAction;
import it.polimi.ingsw.ps14.model.actions.mainactions.ElectCouncillorAction;
import it.polimi.ingsw.ps14.model.actions.market.BuyAction;
import it.polimi.ingsw.ps14.model.actions.market.SellAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.ChangeBusinessPermitTilesAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.EngageAssistantAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.PerformAdditionalMainActionAction;
import it.polimi.ingsw.ps14.model.actions.quickactions.SendAssistantToElectCouncillorAction;
/**
 * Interface with all methods that a Client can invoke.
 * 
 *
 */
public interface Communication {
	
	public void setPlayerName(Integer playerID, String name);
	/**
	 * Request for {@link DrawCardAction} from a player with a specific ID.
	 */
	public void drawCard(Integer playerID);
	/**
	 * Request for {@link ElectCouncillorAction} from a player with a specific ID.
	 */
	public void electCouncillor(Integer playerID, ColorCouncillor cc, String regionORking);
	/**
	 * Request for {@link AcquireBusinessPermitTileAction} from a player with a specific ID.
	 */
	public void acquireBusinessPermitTile(Integer playerID,RegionType rt, Integer permID, List<PoliticCard> politics);
	/**
	 * Request for {@link BuildEmporiumWithHelpOfKingAction} from a player with a specific ID.
	 */
	public void buildWithKing(Integer playerID, String city, List<PoliticCard> politics);
	/**
	 * Request for {@link BuildEmporiumUsingPermitTileAction} from a player with a specific ID.
	 */
	public void buildWithPermit(Integer playerID, Integer permitID, String cityname);
	/**
	 * Request for {@link EngageAssistantAction} from a player with a specific ID.
	 */
	public void engage(Integer playerID);
	/**
	 * Request for {@link ChangeBusinessPermitTilesAction} from a player with a specific ID.
	 */
	public void changeBusinessPermitTiles(Integer playerID, RegionType rt);
	/**
	 * Request for {@link PerformAdditionalMainActionAction} from a player with a specific ID.
	 */
	public void performAdditionalMainAction(Integer playerID);
	/**
	 * Request for {@link SendAssistantToElectCouncillorAction} from a player with a specific ID.
	 */
	public void electWithAssistant(Integer playerID,RegionType rt,ColorCouncillor cc);
	/**
	 * Request for {@link EndTurnAction} from a player with a specific ID.
	 */
	public void passTurn(Integer playerID);
	/**
	 * Request for updated {@link Player}'s details with a specific ID.
	 */
	public void showMyDetails(Integer playerID);
	/**
	 * Request for updated details of other {@link Player}s from a player with a specific ID.
	 */
	public void showDetails(Integer playerID);
	/**
	 * Request for updated {@link GameBoard} from a player with a specific ID.
	 */
	public void showGameboard(Integer playerID);
	/**
	 * Request for {@link SellAction} from a player with a specific ID.
	 */
	public void sell(Integer playerID, List<ItemForSale> items);
	/**
	 * Request for {@link BuyAction} from a player with a specific ID.
	 */
	public void buy(Integer playerID,Integer objID,Integer quantity);
	
	public void answerNobilityRequest(Integer playerID, List<String> objectIDs);
	/**
	 * Request for ending selling turn without selling anything from a player with a specific ID.
	 */
	public void sellNone(Integer playerID);
	/**
	 * Request for ending buying turn from a player with a specific ID.
	 */
	public void doneFinishBuying(Integer playerID);
	/**
	 * Request for forwarding a chat message from a player with a specific ID.
	 */
	public void chat(Integer playerID,String chat);
}
