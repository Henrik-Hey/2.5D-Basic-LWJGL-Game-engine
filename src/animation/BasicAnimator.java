package animation;

import entities.Entity;

public class BasicAnimator {

    private float time = 0;

    public BasicAnimator(){

    }

    public void animate(Entity entity, int numberOfFrames, float animSpeed){
        int index = (int) (time / animSpeed) + 1;
        if (index >= numberOfFrames){
            time = 0;
        }
        entity.setTextureIndex(index);
    }

    public void updateAnimation(){
        time++;
    }

}
