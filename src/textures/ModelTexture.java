package textures;

public class ModelTexture 
{

    private int textureID;
    private int normalMap;

    private float shineDamper = 1;
    private float reflectivity = 0;

    private boolean hasTransparency = false;
    private boolean hasTranslucency = false;

    private int numberOfRows = 1;

    public ModelTexture(int id)
    {
        this.textureID = id;
    }
    
    public int getID()
    {
        return this.textureID;
    }

    public int getNormalMap() {
        return normalMap;
    }

    public void setNormalMap(int normalMap) {
        this.normalMap = normalMap;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public boolean isHasTransparency() {
        return hasTransparency;
    }

    public boolean isHasTranslucency() {
        return hasTranslucency;
    }

    public void setHasTranslucency(boolean hasTranslucency) {
        this.hasTranslucency = hasTranslucency;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
