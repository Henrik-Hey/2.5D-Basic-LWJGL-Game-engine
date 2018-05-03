package core;

import GUIs.GUIRenderer;
import GUIs.GUITexture;
import ai.DemoAI;
import ai.NPCs.Merchant;
import animation.BasicAnimator;
import entities.Camera;
import entities.Entity;
import entities.types.Types;
import fontRendering.TextMaster;
import gameplay.combat.CombatSystem;
import gameplay.trading.TradingGUI;
import normalMappingObjConverter.NormalMappedObjLoader;
import org.lwjgl.util.vector.Vector2f;
import player.Player;
import renderer.*;
import models.RawModel;
import models.TexturedModel;
import lighting.Light;
import textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import tileMapper.TileMapper;

import java.util.ArrayList;
import java.util.List;

public class GameEngineLoop 
{

    public static ArrayList<Entity> floorTiles;
    public static ArrayList<GUITexture>guiTextures = new ArrayList<>();

    public static void main(String[] args) 
    {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer(loader);
        GUIRenderer guiRenderer = new GUIRenderer(loader);

        TextMaster.init(loader);

        GUITexture tex1 = new GUITexture(loader.loadTexture("inventory1"), new Vector2f(-1.25f,0.0f), new Vector2f(0.25f,1f));

        guiTextures.add(tex1);

        ArrayList<Entity> normalMappedEntities = new ArrayList<>();
        floorTiles = new ArrayList<>();

        RawModel model = OBJLoader.loadOBJModel("spriteTile", loader);
        RawModel model1 = NormalMappedObjLoader.loadOBJ("Tile", loader);

        TexturedModel tree1 = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree1")));
        tree1.getTexture().setShineDamper(10);
        tree1.getTexture().setReflectivity(0.0f);
        tree1.getTexture().setHasTransparency(true);
        tree1.getTexture().setNumberOfRows(2);

        TexturedModel buildingModel = new TexturedModel(model1, new ModelTexture(loader.loadTexture("building")));
        buildingModel.getTexture().setNormalMap(loader.loadTexture("buildingNormal"));
        buildingModel.getTexture().setShineDamper(10);
        buildingModel.getTexture().setReflectivity(0.7f);
        Entity building = new Entity(buildingModel, new Vector3f(16.5f,10.0f,0),0,0,0,10, Types.treeComp);
        Entity building2 = new Entity(buildingModel, new Vector3f(51.5f,10.0f,20),0,0,0,10, Types.treeComp);

        TexturedModel PlayerModel = new TexturedModel(model1, new ModelTexture(loader.loadTexture("playerForwardSheet")));
        PlayerModel.getTexture().setNormalMap(loader.loadTexture("playerForwardSheetNormal"));
        PlayerModel.getTexture().setShineDamper(0);
        PlayerModel.getTexture().setReflectivity(0.15f);
        PlayerModel.getTexture().setHasTransparency(true);
        PlayerModel.getTexture().setNumberOfRows(4);
        Entity playerE = new Entity(PlayerModel, 3, new Vector3f(47.5f,1.25f,40),0,0,0,1.25f, Types.Player);
        playerE.getModel().getTexture().setHasTransparency(true);

        Entity enemy = new Entity(PlayerModel, 3, new Vector3f(40,1.25f,20),0,0,0,1.25f, Types.None);
        enemy.getModel().getTexture().setHasTransparency(true);

        TexturedModel merchantModel = new TexturedModel(model1, new ModelTexture(loader.loadTexture("jerald")));
        merchantModel.getTexture().setNormalMap(loader.loadTexture("jeraldNormalMap"));
        merchantModel.getTexture().setReflectivity(0.15f);
        merchantModel.getTexture().setShineDamper(10);
        merchantModel.getTexture().setNumberOfRows(2);

        TexturedModel textBoxModel = new TexturedModel (model1, new ModelTexture (loader.loadTexture("HoveringTextBox")));
        textBoxModel.getTexture().setNumberOfRows(2);
        Entity textBox = new Entity(textBoxModel, 0, new Vector3f(35.5f, 2.25f, 40),0,0,0,0.5f, Types.None);

        Entity merchantE = new Entity(merchantModel, 0, new Vector3f(35, 1.25f, 40),0,0,0,1.25f, Types.None);
        Merchant merchant = new Merchant(merchantE, 100, 100, "Jerald", "", "", "Merchant1_Inventory", true, loader);
        List<Merchant> merchants = new ArrayList<>();
        merchants.add(merchant);

        TileMapper mapper = new TileMapper(loader, model1, renderer, playerE);
        mapper.init();

        mapper.generateLevel(normalMappedEntities);

        normalMappedEntities.add(merchantE);
        normalMappedEntities.add(textBox);
        normalMappedEntities.add(playerE);
        normalMappedEntities.add(enemy);
        normalMappedEntities.add(building);
        normalMappedEntities.add(building2);

        DemoAI demoAI = new DemoAI(enemy, 15, 100, mapper);

        Camera camera = new Camera();
        Player player = new Player(playerE, loader, mapper);

        camera.haveCameraFollow(playerE);

        camera.setPosition(new Vector3f(playerE.getPosition().x, playerE.getPosition().y + 15.5f, playerE.getPosition().z+ 20));

        Light light = new Light(new Vector3f(10,2,10), new Vector3f(1f,1f,1f));

        BasicAnimator animator = new BasicAnimator();

        TradingGUI.init(guiRenderer, camera, merchants, player);

        ArrayList<DemoAI> enemies = new ArrayList<>();
        enemies.add(demoAI);
        CombatSystem.init(player, enemies);

        light.setPosition(new Vector3f(playerE.getPosition().x, playerE.getPosition().y + 10, playerE.getPosition().z + 1f));

        while(!Display.isCloseRequested()){

            animator.animate(textBox, 4, 20);
            animator.updateAnimation();

            player.update(normalMappedEntities);

            camera.update();

            mapper.render(camera);

            demoAI.update(player);

            for(Entity e: normalMappedEntities){
                if (e.getPosition().x > camera.getPosition().x - 80 && e.getPosition().x < camera.getPosition().x + 80 &&
                        e.getPosition().z > camera.getPosition().z - 140 && e.getPosition().z < camera.getPosition().z + 30){
                    renderer.processNormalMapEntity(e);
                }
            }

            renderer.render(light, camera);

            merchant.update(player, camera);

            TextMaster.render();

            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        guiRenderer.cleanUp();
        loader.cleanUP();
        DisplayManager.destroyDisplay();
    }
    
}
