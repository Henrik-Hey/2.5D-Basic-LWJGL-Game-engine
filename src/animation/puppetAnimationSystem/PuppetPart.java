package animation.puppetAnimationSystem;

import entities.Entity;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class PuppetPart {

    private PuppetPart parent;
    private Vector3f startPos, endPos;
    private Vector3f rot;
    private float size;
    private Entity entity;

    public PuppetPart(Entity entity, float size){
        this.rot    = new Vector3f(entity.getRotX(), entity.getRotY(), entity.getRotZ());
        this.startPos = entity.getPosition();
        this.entity = entity;
        this.endPos = new Vector3f(startPos.x, startPos.y - size, startPos.z);
        this.parent = null;
    }

    public PuppetPart(PuppetPart parent, Entity entity, float size){
        this.parent = parent;
        this.size   = size;
        this.entity = entity;
        if(parent!=null){
            this.startPos = new Vector3f(entity.getPosition().x, parent.getStartPos ().y + size, parent.getStartPos().z);
            this.rot = new Vector3f(parent.getRot().x, parent.getRot().y, parent.getRot().z);
        }
        this.endPos = new Vector3f(startPos.x, startPos.y - size, startPos.z);
    }
    float x = 0;
    float z = 0;
    public void update(){
        entity.increaseRotation(1,0,0);
        if(parent != null){
            this.x += 0.0125f;
            this.z += 0.0125f;
            Vector2f currentPos = new Vector2f((float)(size * Math.sin(this.x)), (float)(size * Math.cos(this.z)));
            entity.setPosition(new Vector3f(parent.entity.getPosition().x, parent.entity.getPosition().y + currentPos.y, parent.entity.getPosition().z + currentPos.x));
            entity.setRotation(new Vector3f (parent.entity.getRotX() + 0,0,0));
            System.out.println(currentPos);
        }
    }

    public Vector3f getCenter(Vector3f endPos){
        Vector3f center;
        center = new Vector3f((startPos.x + endPos.x)/2, (startPos.y + endPos.y)/2, (startPos.z + endPos.z)/2);
        return center;
    }

    public void setStartPos(Vector3f startPos) {
        this.startPos = startPos;
    }

    public void setRot(Vector3f rot) {
        this.rot = rot;
    }

    public Vector3f getRot() {
        return rot;
    }

    public Vector3f getStartPos() {
        return startPos;
    }

    public float getSize() {
        return size;
    }
}
