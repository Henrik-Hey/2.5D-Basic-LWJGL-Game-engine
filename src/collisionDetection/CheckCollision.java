package collisionDetection;

public class CheckCollision {

    public static boolean isInCircle(float xCenter, float zCenter, float x, float z, float radius){
        x+=1;
        z+=1;
        if(Math.pow((x - xCenter), 2) + Math.pow((z - zCenter), 2) < Math.pow(radius, 2)){
            return true;
        }
        return false;
    }

}
