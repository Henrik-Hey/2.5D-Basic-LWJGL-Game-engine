package animation.puppetAnimationSystem;

import core.GameEngineLoop;
import core.testRunner;
import entities.Entity;
import entities.types.Types;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;
import renderer.Loader;
import textures.ModelTexture;

import java.util.ArrayList;

public class PuppetSystem {

    private Entity corePart;
    private boolean hasHitMaxXRot = false;
    private PuppetAnimationFile puppetAnimationFile;
    private ArrayList<Entity>puppetEntities = new ArrayList<>();
    private ArrayList<PuppetPart> puppetParts = new ArrayList<>();

    public PuppetSystem(Entity corePart, PuppetAnimationFile puppetAnimationFile, Loader loader){
        this.corePart = corePart;
        this.puppetAnimationFile = puppetAnimationFile;
        Entity puppetCore = new Entity(corePart.getModel(), 1, new Vector3f (47.5f,1.25f,40),0,0,0,1f, Types.Player);
        puppetCore.getModel().getTexture().setHasTransparency(true);
        PuppetPart spine = new PuppetPart(corePart, -1f);
        puppetParts.add(spine);
        for(int i = 0; i < puppetAnimationFile.getLimbNum(); i++){
            Entity puppetPart = new Entity(corePart.getModel(), puppetAnimationFile.getID().get(i), new Vector3f (
                    corePart.getPosition().x + puppetAnimationFile.getParentXOffset().get(i),
                    corePart.getPosition().y + puppetAnimationFile.getParentYOffset().get(i),
                    corePart.getPosition().z + puppetAnimationFile.getParentZOffset().get(i)
            ),
                    corePart.getRotX(),
                    corePart.getRotY(),
                    corePart.getRotZ(),
                    1f, Types.Player, corePart);
            puppetParts.add(new PuppetPart(puppetParts.get(puppetParts.size() - 1), puppetPart, -1f));
            testRunner.normalMappedEntities.add(puppetPart);
        }
    }

    public void update(){
        for(PuppetPart puppetPart: puppetParts){
            puppetPart.update();
        }
    }

    private void runThroughAnimRot(Entity puppetPart, float minXRot, float maxXRot){
        float currentRot = puppetPart.getRotX();
        if(currentRot >= maxXRot) hasHitMaxXRot = true;
        if(currentRot <= minXRot) hasHitMaxXRot = false;
        if(!hasHitMaxXRot){
            puppetPart.increaseRotation(1.5f,0,0);
        }else{
            puppetPart.increaseRotation(-1.5f,0,0);
        }
    }

}
