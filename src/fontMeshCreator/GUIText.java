package fontMeshCreator;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderer.DisplayManager;

import java.text.DecimalFormat;

/**
 * Represents a piece of text in the game.
 * 
 * @author Karl
 *
 */
public class GUIText {

	private String textString;
	private float fontSize;

	private int textMeshVao;
	private int vertexCount;
	private Vector3f colour = new Vector3f(0f, 0f, 0f);

	private Vector2f position;
	private float lineMaxSize;
	private int numberOfLines;
	private float slideSpeed = 0.2f;
	private Vector2f originalPos;
	private DecimalFormat df = new DecimalFormat();

	private FontType font;

	private boolean centerText = false;

	public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
			boolean centered) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		this.originalPos = position;
		// load text
	}



	public void slideInX(Vector2f newPosition){
		if(newPosition.x > position.x){
			if(position.x < newPosition.x)setPosition(new Vector2f(position.x+=slideSpeed * DisplayManager.getDelta(), position.y));
		}
	}

	public void slideOutX(){
		df.setMaximumFractionDigits(2);
		if(originalPos.x < position.x){
			setPosition(new Vector2f(position.x-=slideSpeed * DisplayManager.getDelta(), position.y));
		}
	}

	public void remove() {
		// remove text
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getOriginalPos() {
		return originalPos;
	}

	public void setOriginalPos(Vector2f originalPos) {
		this.originalPos = originalPos;
	}

	public float getSlideSpeed() {
		return slideSpeed;
	}

	public void setSlideSpeed(float slideSpeed) {
		this.slideSpeed = slideSpeed;
	}

	public FontType getFont() {
		return font;
	}

	public void setColour(float r, float g, float b) {
		colour.set(r, g, b);
	}

	public Vector3f getColour() {
		return colour;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public Vector2f getPosition() {
		return position;
	}

	public int getMesh() {
		return textMeshVao;
	}

	public void setMeshInfo(int vao, int verticesCount) {
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}

	public int getVertexCount() {
		return this.vertexCount;
	}

	protected float getFontSize() {
		return fontSize;
	}

	protected void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	protected boolean isCentered() {
		return centerText;
	}

	protected float getMaxLineSize() {
		return lineMaxSize;
	}

	public String getTextString() {
		return textString;
	}

	public void setTextString(String textString) {
		this.textString = textString;
	}
}
