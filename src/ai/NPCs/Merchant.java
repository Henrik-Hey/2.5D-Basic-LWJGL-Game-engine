package ai.NPCs;

import collisionDetection.CheckCollision;
import entities.Camera;
import entities.Entity;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import inventories.Inventory;
import gameplay.trading.TradingGUI;
import org.lwjgl.input.Keyboard;
import player.Player;
import renderer.Loader;

import java.io.File;
import java.util.ArrayList;

public class Merchant{

    private int health;
    private float speed;
    private Entity entity;
    private String name, type, faction, inventoryLocation;
    private Inventory inventory;
    private boolean talkable;
    private boolean isTrading = false, requestingTrade = false;
    private GUIText text_1;
    private FontType Font_Tahoma;
    private ArrayList<GUIText> guiTexts = new ArrayList<>();
    private Loader loader;

    public Merchant(Entity entity, int health, float speed, String name, String type, String faction, String inventoryLocation, boolean talkable, Loader loader) {
        this.entity            = entity;
        this.health            = health;
        this.speed             = speed;
        this.name              = name;
        this.type              = type;
        this.faction           = faction;
        this.inventoryLocation = inventoryLocation;
        this.talkable          = talkable;
        inventory              = new Inventory();
        Font_Tahoma   = new FontType(loader.loadTexture("tahoma"), new File ("res/tahoma.fnt"));
        this.loader = loader;
    }

    public void update(Player player, Camera camera){
        if(Keyboard.isKeyDown(Keyboard.KEY_A) && !requestingTrade){
            requestingTrade = true;
            TradingGUI.createTradingGUI(loader, name);
        }
        if(isTalkable() && CheckCollision.isInCircle(entity.getPosition().x, entity.getPosition().z,
                player.getPlayer().getPosition().x, player.getPlayer().getPosition().z, 3) &&
                requestingTrade){
            isTrading           = true;
            player              .setCanBeMoved(false);
            if(Keyboard.isKeyDown(Keyboard.KEY_S) && requestingTrade){
                requestingTrade = false;
            }
        }else{
            isTrading = false;
        }
        if(isTrading){
            TradingGUI.slideOnScreen();
        }else{
            TradingGUI.slideOffScreen();
        }
        TradingGUI    .update();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isTalkable() {
        return talkable;
    }

    public boolean isTrading() {
        return isTrading;
    }

    public float getSpeed() {
        return speed;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getInventoryLocation() {
        return inventoryLocation;
    }

    public FontType getFont_Tahoma() {
        return Font_Tahoma;
    }

    public String getFaction() {
        return faction;
    }
}
