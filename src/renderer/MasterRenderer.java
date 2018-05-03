package renderer;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import normalMappingRenderer.NormalMappingRenderer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import shaders.StaticShader;
import lighting.*;

import java.util.*;

public class MasterRenderer {

    private Matrix4f projectionMatrix;
    public static final float FOV = 70;
    public static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private StaticShader shader = new StaticShader();
    private Renderer renderer;
    private NormalMappingRenderer normalMappingRenderer;

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private Map<TexturedModel, List<Entity>> normalMappedEntities = new HashMap<TexturedModel, List<Entity>>();

    public MasterRenderer(Loader loader){
        enableCulling();
        createProjectionMatrix();
        renderer              = new Renderer(shader, projectionMatrix);
        normalMappingRenderer = new NormalMappingRenderer(projectionMatrix);
    }

    public static void enableCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public void render(Light sun, Camera camera){
        renderer.prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        shader.stop();
        normalMappingRenderer.render(normalMappedEntities, sun, camera);
        entities.clear();
        normalMappedEntities.clear();
    }

    public void processEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch!=null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void processNormalMapEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = normalMappedEntities.get(entityModel);
        if(batch!=null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            normalMappedEntities.put(entityModel, newBatch);
        }
    }

    public void cleanUp(){
        shader.cleanUp();
        normalMappingRenderer.cleanUp();
    }

    private void createProjectionMatrix()
    {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float fustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / fustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * FAR_PLANE * NEAR_PLANE) / fustum_length);
        projectionMatrix.m33 = 0;
    }
}
