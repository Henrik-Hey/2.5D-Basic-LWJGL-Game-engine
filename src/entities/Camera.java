package entities;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;

import java.text.DecimalFormat;

public class Camera 
{

    private Vector3f position = new Vector3f(0, 50, 0);
    private float pitch;
    private float yaw;
    private float roll;
    private boolean isFollowing = false, informativeZoomComplete = true, zoomInComplete = true;
    private Entity followEntity;
    private DecimalFormat df = new DecimalFormat();
    private float zoomSpeed;
    
    public Camera() {
        pitch = 40;
    }

    public void update(){
        zoomSpeed = DisplayManager.getDelta() * 10;
        follow(0f, 15.5f, 20f);
    }

    public void haveCameraFollow(Entity entity){
        isFollowing = true;
        followEntity = entity;
        setPosition(followEntity.getPosition());
    }

    public void follow(float xOffset, float yOffset, float zOffset){
        if(informativeZoomComplete || zoomInComplete){
            setPosition(new Vector3f(
                    followEntity.getPosition().x + xOffset,
                    followEntity.getPosition().y + yOffset,
                    followEntity.getPosition().z + zOffset
            ));
        }
    }

    public boolean isAtFollowPoint(){
        if(informativeZoomComplete){
            return true;
        }else{
            return false;
        }
    }

    public void pullToPoint(float targetX, float targetY, float targetZ, float targetPitch){
        informativeZoomComplete = false;
        zoomInComplete = false;
        if(position.x > targetX) position.x-=zoomSpeed;
        if(position.y > targetY) position.y-=zoomSpeed;
        if(position.z > targetZ)position.z-=zoomSpeed;
        if(pitch > targetPitch)pitch-=zoomSpeed * 2;
        if(position.x == targetX && position.y == targetY && position.z == targetZ && pitch == targetPitch){
            zoomInComplete = true;
        }
    }

    public void pullBackFromPoint(float targetX, float targetY, float targetZ, float targetPitch){
        df.setMaximumFractionDigits(1);
        targetX = Float.parseFloat(df.format(targetX));
        targetY = Float.parseFloat(df.format(targetY));
        targetZ = Float.parseFloat(df.format(targetZ));
        if(!informativeZoomComplete){
            if(Float.parseFloat(df.format(position.x)) < targetX)position.x+=zoomSpeed;
            if(Float.parseFloat(df.format(position.y)) < targetY)position.y+=zoomSpeed;
            if(Float.parseFloat(df.format(position.z)) < targetZ)position.z+=zoomSpeed;
            if(pitch < targetPitch)pitch+=zoomSpeed * 2;
            if(Float.parseFloat(df.format(position.x)) == targetX &&
                    Float.parseFloat(df.format(position.y)) == targetY &&
                    Float.parseFloat(df.format(position.z)) == targetZ &&
                    Float.parseFloat(df.format(pitch)) == targetPitch){
                informativeZoomComplete = true;
            }
        }
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Entity getFollowEntity() {
        return followEntity;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public float getPitch() 
    {
        return pitch;
    }

    public float getYaw() 
    {
        return yaw;
    }

    public float getRoll() 
    {
        return roll;
    }
    
    
    
}
