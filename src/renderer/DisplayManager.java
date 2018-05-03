package renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager 
{
    private static final int WIDTH = 1280, HEIGHT = 720, FPS = 30;

    private static long lastFrameTime;
    private static float delta;

    public static void createDisplay()
    {
        ContextAttribs attribs = new ContextAttribs(3,3)
                .withForwardCompatible(true)
                .withProfileCore(true);

        System.setProperty( "org.lwjgl.opengl.Window.undecorated" , "true");
        try{
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("3D game engine V.0.0.1");
            Display.create(new PixelFormat(), attribs);
            Display.setFullscreen(true);
        }catch( LWJGLException e){
            System.err.println("ERROR: Could not create window!");
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastFrameTime = getCurrentTime();
    }

    public static void updateDisplay()
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))System.exit(0);
        delta = 0;
        Display.sync(FPS);
        Display.update();
        long currentTimeFrame = getCurrentTime();
        delta = (currentTimeFrame - lastFrameTime)/1000f;
        lastFrameTime = currentTimeFrame;
    }

    public static float getDelta() {
        return delta;
    }

    public static void destroyDisplay()
    {
        Display.destroy();
    }

    private static long getCurrentTime() {
        return Sys.getTime()*1000/Sys.getTimerResolution();
    }
}
