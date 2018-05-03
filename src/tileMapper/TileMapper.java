package tileMapper;

import entities.Camera;
import entities.Entity;
import entities.types.Types;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import player.Player;
import renderer.Loader;
import renderer.MasterRenderer;
import textures.ModelTexture;
import tiledMaps.TMXFileReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TileMapper {

    private ArrayList<Entity> tiles;
    private ArrayList<Integer> xpos;
    private ArrayList<Integer> zpos;
    private TMXFileReader tmxFileReader;
    private static TexturedModel grassTile;
    private static TexturedModel floorTile;
    private static TexturedModel tree1;
    private static TexturedModel rock;
    private static MasterRenderer renderer;

    public TileMapper(Loader loader, RawModel tileModel, MasterRenderer renderer, Entity player){
        tiles    = new ArrayList<>();
        xpos     = new ArrayList<>();
        zpos     = new ArrayList<>();
        TileMapper.renderer = renderer;
        loadTiles(loader, tileModel);
    }

    private void loadTiles(Loader loader, RawModel tileModel){
        tmxFileReader = new TMXFileReader("demoMap");
        rock = new TexturedModel(tileModel, new ModelTexture(loader.loadTexture("rock")));
        rock.getTexture().setNormalMap(loader.loadTexture("rockNormal"));
        rock.getTexture().setShineDamper(10);
        rock.getTexture().setReflectivity(0.25f);
        grassTile = new TexturedModel(tileModel, new ModelTexture(loader.loadTexture(tmxFileReader.getImageSourcePath())));
        grassTile.getTexture().setNormalMap(loader.loadTexture(tmxFileReader.getImageSourcePath() + "Normal"));
        grassTile.getTexture().setReflectivity(0.35f);
        grassTile.getTexture().setShineDamper(10f);
        grassTile.getTexture().setNumberOfRows(4);
        tree1 = new TexturedModel(tileModel, new ModelTexture(loader.loadTexture("tree1Final")));
        tree1.getTexture().setNormalMap(loader.loadTexture("tree1finalNormal"));
        floorTile = new TexturedModel(tileModel, new ModelTexture(loader.loadTexture("dirtground")));
        floorTile.getTexture().setNormalMap(loader.loadTexture("dirtgroundNormal"));
    }

    public void generateLevel(ArrayList<Entity> entities){
        for (int i = 0; i < tmxFileReader.getObjectsXpos().size(); i++){
            int x = tmxFileReader.getObjectsXpos().get(i);
            int z = tmxFileReader.getObjectsYpos().get(i);
            setTile (x, z, Types.SolidTile, tmxFileReader.getObjectsTypes().get(i), entities);
        }
    }

    public void init(){
        float xOffset = 0;
        float zOffset = 0;
        ArrayList<Integer> tilesPos = tmxFileReader.getTileLocations();
        for (int z = 0; z < tmxFileReader.getHeight(); z++){
            zpos.add(z);
            for (int x = 0; x < tmxFileReader.getWidth(); x++){
                xpos.add(x);
                tiles.add(new Entity(grassTile, tilesPos.get(tmxFileReader.getWidth() * z + x), new Vector3f(xOffset, 0, zOffset), -90,0,0,1.075f, Types.Tile, Types.None));
                xOffset += 2;
            }
            zOffset += 2;
            xOffset  = 0;
        }
    }

    public void render(Camera camera){
        for(Entity e: tiles){
            if (e.getPosition().x > camera.getPosition().x - 45 && e.getPosition().x < camera.getPosition().x + 45 &&
                    e.getPosition().z > camera.getPosition().z - 140 && e.getPosition().z < camera.getPosition().z + 30){
                renderer.processNormalMapEntity(e);
            }
        }
    }

    public void getTile(int x, int z){
        for (Entity e:tiles){
            if (x > e.getPosition().x - 2 && x < e.getPosition().x + 1 && z > e.getPosition().z - 2 && z < e.getPosition().z + 1
                    && !e.getType().equals(Types.SolidTile)){
            }
        }
    }

    public boolean checkIfTileSolid(float x, float z){
        for (Entity e:tiles){
            if (x > e.getPosition().x - 1.15f && x < e.getPosition().x + 1.15f && z - 0.25f > e.getPosition().z - 1.15f && z + 0.25f < e.getPosition().z + 1.15f
                    && e.getType().equals(Types.SolidTile)){
                return true;
            }
        }
        return false;
    }

    public void setTile(int x, int z, String type, String marker,ArrayList<Entity> entities){
        for (Entity e:tiles){
            if (x > e.getPosition().x - 1.25f && x < e.getPosition().x + 1.25f && z > e.getPosition().z - 1.25f && z < e.getPosition().z + 1.25f){
                e.setType(type);
                e.setMarker(marker);
            }
            if (e.getMarker().equals(Types.Tree)){
                entities.add(new Entity(tree1, new Vector3f(e.getPosition().x + 0.35f, 5.5f, e.getPosition().z + 0.5f),0,0,0,6, Types.Object));
               }
            if (e.getMarker().equals(Types.Rock)){
                entities.add(new Entity(rock, 0, new Vector3f(e.getPosition().x, 1.5f, e.getPosition().z + 0.5f),0,0,0,1.2f, Types.Object));
            }
        }
    }

    public ArrayList<Entity> getTiles() {
        return tiles;
    }

}


