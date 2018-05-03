package player;

import animation.BasicAnimator;
import entities.Entity;
import entities.types.Types;
import gameplay.combat.CombatFramework;
import gameplay.combat.CombatSystem;
import inventories.Item;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import pathFinding.Node;
import pathFinding.PathFinder;
import renderer.Loader;
import textures.ModelTexture;
import tileMapper.TileMapper;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private static int dir = 0;
    private static float speed = 0.14f;

    private Entity player;
    public static int money = 100000;
    private ModelTexture playerBack;
    private ModelTexture playerForward;
    private ModelTexture playerRight;
    private ModelTexture playerLeft;
    private BasicAnimator animator;
    private TileMapper mapper;
    private int x = 0, y =0, z = 0;
    private boolean isMoving = false, canBeMoved = true;
    private boolean isColliding = false;
    private List<Node> path;
    private PathFinder finder;
    private CombatFramework combatFramework;

    public Player(Entity player, Loader loader, TileMapper mapper){
        this.player = player;
        playerForward = new ModelTexture(loader.loadTexture("playerForwardSheet"));
        playerForward.setNormalMap(loader.loadTexture("playerForwardSheetNormal"));
        playerBack = new ModelTexture(loader.loadTexture("playerBackSprite"));
        playerBack.setNormalMap(loader.loadTexture("playerBackSpriteNormal"));
        playerRight = new ModelTexture(loader.loadTexture("playerRightSheet"));
        playerRight.setNormalMap(loader.loadTexture("playerRightSheetNormal"));
        playerLeft = new ModelTexture(loader.loadTexture("playerLeftSheet"));
        playerLeft.setNormalMap(loader.loadTexture("playerLeftSheetNormal"));
        animator = new BasicAnimator();
        this.mapper = mapper;
        finder = new PathFinder(mapper);
        combatFramework = new CombatFramework(new Item(0, "", 30, 0, 0, ""));
    }

    public void update(ArrayList<Entity> entities){
        move(entities);
        animator.updateAnimation();
        updatePlayerSheet();
        combatFramework.update();
    }

    public Entity getPlayer(){
        return player;
    }


    private void checkInputs(){
        if(canBeMoved){
            if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
                z++;
            }else if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
                z--;
            }else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
                x--;
            }else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
                x++;
            }
        }
    }

    private void resetInputs(){
            x = 0;
            z = 0;
    }

    private void move(ArrayList<Entity> entities){
        checkInputs ();
        isColliding = false;
        isMoving = false;
        if (z > 0) {
            for (Entity e : entities) {
                if (!e.getType ().equals (Types.Player) || !e.getType ().equals (Types.treeComp)) {
                    if (mapper.checkIfTileSolid ((int) player.getPosition ().x, (int) player.getPosition ().z + 1))
                        isColliding = true;
                }
            }
            if (!isColliding) player.increasePosition (0, 0, speed);
            animator.animate (player, 7, 10);
            isMoving = true;
            dir = 0;
        } else if (z < 0) {
            for (Entity e : entities) {
                if (!e.getType ().equals (Types.Player) || !e.getType ().equals (Types.treeComp)) {
                    if (mapper.checkIfTileSolid ((int) player.getPosition ().x, (int) player.getPosition ().z - 1))
                        isColliding = true;
                }
            }
            if (!isColliding) player.increasePosition (0, 0, -speed);
            animator.animate (player, 7, 10);
            isMoving = true;
            dir = 1;
        } else if (x > 0) {
            for (Entity e : entities) {
                if (!e.getType ().equals (Types.Player) || !e.getType ().equals (Types.treeComp)) {
                    if (mapper.checkIfTileSolid ((int) player.getPosition ().x + 1, (int) player.getPosition ().z))
                        isColliding = true;
                }
            }
            if (!isColliding) player.increasePosition (speed, 0, 0f);
            animator.animate (player, 7, 10);
            isMoving = true;
            dir = 2;
        } else if (x < 0) {
            for (Entity e : entities) {
                if (mapper.checkIfTileSolid ((int) player.getPosition ().x - 1, (int) player.getPosition ().z))
                    isColliding = true;
            }
            if (!isColliding) player.increasePosition (-speed, 0, 0f);
            animator.animate (player, 7, 10);
            isMoving = true;
            dir = 3;
        }
        if (!isMoving) {
            player.setTextureIndex (0);
        }
        resetInputs ();
    }

    private void updatePlayerSheet(){
        if (dir == 0){
            player.getModel().setTexture(playerForward);
            player.getModel().getTexture().setNumberOfRows(4);
        }else if (dir == 1){
            player.getModel().setTexture(playerBack);
            player.getModel().getTexture().setNumberOfRows(4);
        }else if (dir == 2){
            player.getModel().setTexture(playerRight);
            player.getModel().getTexture().setNumberOfRows(4);
        }else if (dir == 3){
            player.getModel().setTexture(playerLeft);
            player.getModel().getTexture().setNumberOfRows(4);
        }
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isCanBeMoved() {
        return canBeMoved;
    }

    public void setCanBeMoved(boolean canBeMoved) {
        this.canBeMoved = canBeMoved;
    }
}
