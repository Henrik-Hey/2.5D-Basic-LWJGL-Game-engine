package GUIs;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;
import renderer.DisplayManager;

import java.text.DecimalFormat;

public class GUITexture {

    private int texture;
    private Vector2f position;
    private Vector2f scale;
    private float slideSpeed = 0.5f;
    private DecimalFormat df = new DecimalFormat();

    public GUITexture(int texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
    }

    public void slideInX(Vector2f newPosition){
        if(newPosition.x != this.position.x){
            if(this.position.x < newPosition.x)this.position.x+=slideSpeed * DisplayManager.getDelta();
        }
    }

    public void slideOutX(Vector2f newPosition){
        df.setMaximumFractionDigits(2);
        if(newPosition.x != this.position.x){
            this.position.x = Float.parseFloat(df.format(this.position.x-=slideSpeed * DisplayManager.getDelta()));
        }
    }

    public float getSlideSpeed() {
        return slideSpeed;
    }

    public void setSlideSpeed(float slideSpeed) {
        this.slideSpeed = slideSpeed;
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
}
