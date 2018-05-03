package gameplay.combat;

import inventories.Item;
import org.lwjgl.input.Keyboard;

public class CombatFramework {

    private Item weapon;
    private int time = 0;
    private boolean canAttack = true;
    private float attackSpeed = 1f;

    public CombatFramework(Item weapon){
        this.weapon = weapon;
    }

    public void update(){
        if(time != 0){
            if ((60 / time) == (int)attackSpeed){
                canAttack = true;
            }
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D) && canAttack){
            CombatSystem.updatePlayerCombatSystem(weapon);
            System.out.println("eyy");
            canAttack = false;
            time = 0;
        }
        if(time == 61){
            time = 1;
        }else{
            time++;
        }
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }
}
