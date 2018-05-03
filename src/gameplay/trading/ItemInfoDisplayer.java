package gameplay.trading;

import GUIs.GUIRenderer;
import GUIs.GUITexture;
import org.lwjgl.util.vector.Vector2f;
import renderer.Loader;

import java.util.ArrayList;
import java.util.List;

public class ItemInfoDisplayer {

    public static void DisplayItemInfo(TradingGUIItem item, GUIRenderer guiRenderer, Loader loader){
        GUITexture tex1 = new GUITexture(loader.loadTexture("inventory1"), new Vector2f (-0.25f, (item.getGuiText().getPosition().y * -2f) + 0.975f), new Vector2f(0.25f,0.05f));
        List<GUITexture> textures = new ArrayList<>();
        textures.add(tex1);
        guiRenderer.Render(textures);
    }

}
