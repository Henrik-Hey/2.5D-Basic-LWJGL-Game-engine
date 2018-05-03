package entities;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Entity 
{
    
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private String type, marker;
    private float scale;
    private Entity parent;

    private int textureIndex = 0;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, String type)
    {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.type = type;
    }

    public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale, String type)
    {
        this.model = model;
        this.textureIndex = index;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.type = type;
    }

    public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale, String type, Entity parent)
    {
        this.model = model;
        this.textureIndex = index;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.type = type;
        this.parent = parent;
    }

    public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale,
                  String type, String marker)
    {
        this.model = model;
        this.textureIndex = index;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.type = type;
        this.marker = marker;
    }

    public float getTextureXOffset(){
        int column = textureIndex % model.getTexture().getNumberOfRows();
        return (float)column / model.getTexture().getNumberOfRows();
    }

    public float getTextureYOffset(){
        int column = textureIndex / model.getTexture().getNumberOfRows();
        return (float)column / model.getTexture().getNumberOfRows();
    }
    
    public void increasePosition(float dx, float dy, float dz)
    {
        this.position.x+=dx;
        this.position.y+=dy;
        this.position.z+=dz;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void increaseRotation(float dx, float dy, float dz)
    {
        this.rotX+=dx;
        this.rotY+=dy;
        this.rotZ+=dz;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TexturedModel getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void increaseRotation(Vector3f rotation){
        rotX += rotation.x;
        rotY += rotation.y;
        rotZ += rotation.z;
    }

    public void setRotation(Vector3f rotation){
        rotX = rotation.x;
        rotY = rotation.y;
        rotZ = rotation.z;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }
}
