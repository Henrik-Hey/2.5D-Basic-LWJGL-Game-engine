package gameplay.trading;

import GUIs.GUIRenderer;
import ai.NPCs.Merchant;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import player.Player;
import renderer.Loader;

import java.util.ArrayList;
import java.util.List;

public class TradingGUIItem {

    private static Vector2f position = new Vector2f(0, 695);
    private static List<Vector2f> positions = new ArrayList<>();
    private Vector2f size = new Vector2f(315, 30);
    private GUIText guiText;
    private static Loader loader;
    private static GUIRenderer guiRenderer;
    private static Merchant merchant;

    public static void init(Loader loader, GUIRenderer renderer, Merchant merchant){
        TradingGUIItem.loader      = loader;
        TradingGUIItem.guiRenderer = renderer;
        TradingGUIItem.merchant    = merchant;
    }

    public TradingGUIItem(GUIText guiText){
        this.guiText = guiText;
        Vector2f position1 = position;
        positions.add(position1);
        position = new Vector2f(position.x, position.y -= size.y);
    }

    public GUIText getGuiText() {
        return guiText;
    }

    public static void checkSelection(TradingGUIItem tradingGUIItem, int index, List<TradingGUIItem> tradingGUIItems){
        if(Mouse.getX() > positions.get(index).x && Mouse.getX() < tradingGUIItem.size.x &&
                Mouse.getY() > (positions.get(index).y - tradingGUIItem.size.y) && Mouse.getY() < positions.get(index).y){
            tradingGUIItems.get(index).guiText.setColour(0,0,1);
            ItemInfoDisplayer.DisplayItemInfo(tradingGUIItems.get(index), guiRenderer, loader);
            String text = tradingGUIItems.get(index).getGuiText().getTextString();
            tradingGUIItems.get(index).guiText.setTextString(text  + "                                        cost: $" +
                    merchant.getInventory().getItems().get(index).getCost() + " weight: " + merchant.getInventory().getItems().get(index).getWeight());
            TextMaster.loadText(tradingGUIItems.get(index).getGuiText());
            tradingGUIItems.get(index).guiText.setTextString(text);
            if(Mouse.isButtonDown(0)){
                TextMaster.removeText(TradingGUI.getPlayerMoney());
                Player.money -= merchant.getInventory().getItems().get(index).getCost();
                TextMaster.loadText(TradingGUI.getPlayerMoney());
            }
        }else{
            tradingGUIItems.get(index).guiText.setColour(0,1,1);
            TextMaster.loadText(tradingGUIItems.get(index).getGuiText());
        }
    }

}
