package animation.puppetAnimationSystem;

import core.GameEngineLoop;
import core.testRunner;
import entities.Entity;
import entities.types.Types;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import org.lwjgl.util.vector.Vector3f;
import renderer.Loader;
import textures.ModelTexture;

public class Puppet {

    private static Loader loader;
    public static RawModel model;

    private PuppetSystem puppetSystem;

    public static void init(Loader loader){
        Puppet.loader = loader;
        Puppet.model  = NormalMappedObjLoader.loadOBJ("Tile", loader);
    }

    public Puppet(String animFileName){
        PuppetAnimationFile puppetAnimationFile = new PuppetAnimationFile(animFileName);
        TexturedModel puppetSegmentModel = new TexturedModel(model, new ModelTexture (loader.loadTexture("playerPuppetSheet")));
        puppetSegmentModel.getTexture().setNormalMap(loader.loadTexture("playerForwardSheetNormal"));
        puppetSegmentModel.getTexture().setShineDamper(0);
        puppetSegmentModel.getTexture().setReflectivity(0.15f);
        puppetSegmentModel.getTexture().setHasTransparency(true);
        puppetSegmentModel.getTexture().setNumberOfRows(4);
        Entity puppetCore = new Entity(puppetSegmentModel, 1, new Vector3f (47.5f,1.25f,40),0,0,0,1f, Types.Player);
        puppetCore.getModel().getTexture().setHasTransparency(true);
        testRunner.normalMappedEntities.add(puppetCore);
        puppetSystem = new PuppetSystem(puppetCore, puppetAnimationFile, loader);
    }

    public PuppetSystem getPuppetSystem() {
        return puppetSystem;
    }
}
