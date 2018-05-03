package ai;

import collisionDetection.CheckCollision;
import entities.Entity;
import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;
import pathFinding.Node;
import pathFinding.PathFinder;
import player.Player;
import tileMapper.TileMapper;

import java.util.ArrayList;
import java.util.List;

public class DemoAI {

    private Entity entity;
    private int health;
    private boolean isAlive = true;
    private PathFinder finder;
    private Vector3f playerPos;
    private int hearingRange;

    private List<Node>path;

    public DemoAI(Entity entity, int hearingRange, int health, TileMapper mapper){
        this.entity = entity;
        finder = new PathFinder(mapper);
        playerPos = entity.getPosition();
        this.hearingRange = hearingRange;
        this.health = health;
    }

    private void move(Player player){
        playerPos= new Vector3f((int)player.getPlayer().getPosition().x, 0, (int)player.getPlayer().getPosition().z);
        path = finder.findPath(entity.getPosition(), playerPos);
        if(path != null){
            if(path.size() > 0){
                Vector3f pos = new Vector3f((int)path.get(path.size() - 1).getPosition().x, 0, (int)path.get(path.size() - 1).getPosition().z);
                if(entity.getPosition().x < pos.x){
                    entity.increasePosition(0.125f, 0, 0);
                }
                if(entity.getPosition().x > pos.x){
                    entity.increasePosition(-0.125f, 0, 0);
                }
                if(entity.getPosition().z < pos.z){
                    entity.increasePosition(0, 0, 0.125f);
                }
                if(entity.getPosition().z > pos.z){
                    entity.increasePosition(0, 0, -0.125f);
                }
            }
        }
    }

    public void update(Player player){
        if(isAlive) {
            checkHearing(player);
        }
    }

    private void checkHearing(Player player){
        if(CheckCollision.isInCircle(entity.getPosition().x, entity.getPosition().z,
                player.getPlayer().getPosition().x, player.getPlayer().getPosition().z, hearingRange)){
            move(player);
        }
    }

    public Entity getEntity() {
        return entity;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public List<Node> getPath() {
        return path;
    }
}
