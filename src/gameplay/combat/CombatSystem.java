package gameplay.combat;

import ai.DemoAI;
import collisionDetection.CheckCollision;
import inventories.Item;
import player.Player;

import java.util.ArrayList;

public class CombatSystem {

    private static Player player;
    private static ArrayList<DemoAI> enemies;

    public static void init(Player player, ArrayList<DemoAI> enemies){
        CombatSystem.player  = player;
        CombatSystem.enemies = enemies;
    }

    public static void update(){

    }

    public static void updatePlayerCombatSystem(Item currentWeapon){
        for(DemoAI demoAI: enemies){
            if(CheckCollision.isInCircle(player.getPlayer().getPosition().x, player.getPlayer().getPosition().z,demoAI.getEntity().getPosition().x, demoAI.getEntity().getPosition().z, 12)){
                int currentEnemeyHealth = demoAI.getHealth() - currentWeapon.getDamage();
                if(currentEnemeyHealth > 0){
                    demoAI.setHealth(currentEnemeyHealth);
                }else if(currentEnemeyHealth < 1){
                    demoAI.setAlive(false);
                }
            }
        }
    }

    public static void enemyHit(DemoAI enemy){

    }


}
