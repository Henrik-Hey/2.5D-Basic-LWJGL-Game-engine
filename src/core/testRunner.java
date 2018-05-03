package core;

import GUIs.GUIRenderer;
import GUIs.GUITexture;
import animation.puppetAnimationSystem.Puppet;
import entities.Camera;
import entities.Entity;
import entities.types.Types;
import fontRendering.TextMaster;
import lighting.Light;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.Loader;
import renderer.MasterRenderer;
import renderer.OBJLoader;
import textures.ModelTexture;

import java.util.ArrayList;

public class testRunner {

    public static ArrayList<Entity> floorTiles;
    public static ArrayList<GUITexture>guiTextures = new ArrayList<>();
    public static ArrayList<Entity> normalMappedEntities = new ArrayList<>();

    public static void main(String[] args)
    {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer(loader);
        GUIRenderer guiRenderer = new GUIRenderer(loader);

        TextMaster.init(loader);

        RawModel model = OBJLoader.loadOBJModel("spriteTile", loader);
        RawModel model1 = NormalMappedObjLoader.loadOBJ("Tile", loader);

        TexturedModel PlayerModel = new TexturedModel(model1, new ModelTexture(loader.loadTexture("playerForwardSheet")));
        PlayerModel.getTexture().setNormalMap(loader.loadTexture("playerForwardSheetNormal"));
        PlayerModel.getTexture().setShineDamper(0);
        PlayerModel.getTexture().setReflectivity(0.15f);
        PlayerModel.getTexture().setHasTransparency(true);
        PlayerModel.getTexture().setNumberOfRows(4);
        Entity playerE = new Entity(PlayerModel, 3, new Vector3f(47.5f,1.25f,40),0,0,0,1.25f, Types.Player);
        playerE.getModel().getTexture().setHasTransparency(true);

        Puppet.init(loader);

        Puppet playerPuppet = new Puppet("Player_puppet");

        Camera camera = new Camera();

        camera.haveCameraFollow(playerE);

        camera.setPosition(new Vector3f(playerE.getPosition().x, playerE.getPosition().y + 15.5f, playerE.getPosition().z+ 20));

        Light light = new Light(new Vector3f(10,2,10), new Vector3f(1f,1f,1f), new Vector3f(1f, 0.01f, 0.01f));

        while(!Display.isCloseRequested()){

            playerPuppet.getPuppetSystem().update();

            camera.update();

            light.setPosition(new Vector3f(playerE.getPosition().x, playerE.getPosition().y + 1, playerE.getPosition().z + 1f));

            for(Entity e: normalMappedEntities){
                if (e.getPosition().x > camera.getPosition().x - 80 && e.getPosition().x < camera.getPosition().x + 80 &&
                        e.getPosition().z > camera.getPosition().z - 140 && e.getPosition().z < camera.getPosition().z + 30){
                    renderer.processNormalMapEntity(e);
                }
            }

            renderer.render(light, camera);

            TextMaster.render();

            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        guiRenderer.cleanUp();
        loader.cleanUP();
        DisplayManager.destroyDisplay();
    }

}
