package gameplay.trading;

import GUIs.GUIRenderer;
import GUIs.GUITexture;
import ai.NPCs.Merchant;
import entities.Camera;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Text;
import player.Player;
import renderer.DisplayManager;
import renderer.Loader;

import java.util.ArrayList;
import java.util.List;

public class TradingGUI {

    private static List<GUIText> guiTexts               = new ArrayList<> ();
    private static List<GUITexture> guiTextures         = new ArrayList<> ();
    private static List<TradingGUIItem> tradingGUIItems = new ArrayList<>();
    private static List<Merchant> merchants;
    private static GUIRenderer guiRenderer;
    private static Camera camera;
    private static Merchant merchant = null;
    private static Player player;
    private static GUIText playerMoney;
    private static int time = 60;
    private static boolean currencyRemoved = true;

    public static void init(GUIRenderer guiRenderer,  Camera camera, List<Merchant> merchants, Player player){
        TradingGUI.guiRenderer = guiRenderer;
        TradingGUI.camera      = camera;
        TradingGUI.merchants   = merchants;
        TradingGUI.player      = player;
    }

    public static void createTradingGUI(Loader loader, String name){
        guiTexts.clear();
        tradingGUIItems.clear();
        for (Merchant merchant1 : merchants) {
            if (merchant1.getName().equals(name)) {
                merchant = merchant1;
                break;
            }
        }
        assert merchant != null;
        playerMoney = new GUIText("$" +Player.money + "", 2, merchant.getFont_Tahoma(), new Vector2f (-0.125f, 0.000f), 0.8f, false);
        playerMoney .setColour(0,1,1);
        TextMaster .loadText(playerMoney);
        TradingGUIItem         .init(loader,guiRenderer,merchant);
        GUITexture tex1        = new GUITexture(loader.loadTexture("inventory1"), new Vector2f (-1.25f,0.0f), new Vector2f(0.25f,1f));
        guiTextures            .add(tex1);
        merchant               .getInventory().setInventoryLocation(merchant.getInventoryLocation());
        GUIText Category       = new GUIText("Weapons", 2, merchant.getFont_Tahoma(), new Vector2f (-0.125f, 0.000f), 0.8f, false);
        guiTexts               .add(Category);
        float textyPos         = 0.04f;
        float textyxos         = -0.19f;
        for(int i = 0; i < merchant.getInventory().getItems().size(); i++){
            guiTexts.add(new GUIText(merchant.getInventory().getItems().get(i).getName(), 1, merchant.getFont_Tahoma(), new Vector2f (textyxos-=0.05f, textyPos+=0.0375f), 0.8f, false));
        }
        for(GUIText guiText: guiTexts){
            guiText   .setColour(0, 1, 1);
            TextMaster.loadText(guiText);
            if(!guiText.getTextString().equals("Weapons")){
                tradingGUIItems.add(new TradingGUIItem(guiText));
            }
        }
        playerMoney = new GUIText("$" +Player.money + "", 2, merchant.getFont_Tahoma(), new Vector2f (0.625f, 0.000f), 0.8f, false);
        playerMoney .setColour(0,1,1);
        TextMaster  .loadText(playerMoney);
        currencyRemoved = false;
    }

    public static void update(){
        guiRenderer.Render(guiTextures);
    }

    private static void beginTrade(){
        time = 60;
        for(int i = 0; i < tradingGUIItems.size(); i++){
            TradingGUIItem.checkSelection(tradingGUIItems.get(i), i, tradingGUIItems);
        }
    }

    public static void slideOnScreen(){
        camera.pullToPoint(merchant.getEntity().getPosition().x, merchant.getEntity().getPosition().y + 3.5f, merchant.getEntity().getPosition().z + 10, 15);
        for(GUITexture guiTexture: guiTextures){
            guiTexture.slideInX(new Vector2f(-0.76f,0.0f));
        }
        for(GUIText guiText:guiTexts){
            guiText.slideInX(new Vector2f(0.01f,0.0f));
        }
        beginTrade();
    }

    public static void slideOffScreen(){
        time -= 1;
        for(GUITexture guiTexture: guiTextures){
            guiTexture.slideOutX(new Vector2f(-1.25f,0.0f));
        }
        guiTexts.forEach (GUIText::slideOutX);
        List<Boolean> isOffScreen = new ArrayList<>();
        for(GUITexture guiTexture: guiTextures){
            if(guiTexture.getPosition().x == -1.25f){
                isOffScreen.add(true);
            }else{
                isOffScreen.add(false);
            }
        }
        isOffScreen.stream ().filter (anIsOffScreen -> anIsOffScreen).forEach (anIsOffScreen -> {
            deleteDisplay ();
        });
        camera.pullBackFromPoint(
                camera.getFollowEntity().getPosition().x,
                camera.getFollowEntity().getPosition().y + 15.5f,
                camera.getFollowEntity().getPosition().z + 20f,
                40
        );
        if(camera.isAtFollowPoint() || time <= 0){
            player.setCanBeMoved(true);
            camera.setPosition(new Vector3f(camera.getFollowEntity().getPosition().x,
                    camera.getFollowEntity().getPosition().y + 15.5f,
                    camera.getFollowEntity().getPosition().z + 20f));
            time = 1;
        }
        if(!currencyRemoved){
            TextMaster.removeText(playerMoney);
            currencyRemoved = true;
        }
    }
    private static void deleteDisplay(){
        guiTextures.clear();
        guiTexts.clear();
    }

    public static GUIText getPlayerMoney() {
        return playerMoney;
    }
}
