package tiledMaps;

import org.lwjgl.Sys;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TMXFileReader {

    private String fileName;
    private String imageSourcePath;
    private int width;
    private int height;
    private ObjectsReader objectsReader;

    public TMXFileReader(String fileName){
        this.fileName = fileName;
        File file = new File("res/"+ fileName + ".tmx");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert documentBuilder != null;
        try {
            Document document = documentBuilder.parse(file);
            NodeList imageList = document.getElementsByTagName("image");
            imageSourcePath = imageList.item(0).getAttributes().getNamedItem("source").getNodeValue().replaceAll(".png", "");
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        objectsReader = new ObjectsReader(fileName);
    }

    public ArrayList<String>getObjectsTypes(){
        return objectsReader.getObjectsTypes();
    }

    public ArrayList<Integer>getObjectsXpos(){
        return objectsReader.getObjectsXpos();
    }

    public ArrayList<Integer>getObjectsYpos(){
        return objectsReader.getObjectsYpos();
    }

    public ArrayList<Integer> getTileLocations(){
        ArrayList<Integer> positions = new ArrayList<>();
        File file = new File("res/"+ fileName + ".tmx");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            assert documentBuilder != null;
            Document document = documentBuilder.parse(file);
            String posCount;
            posCount = document.getElementsByTagName("data").item(0).getTextContent();
            List<String>strings = Arrays.asList(posCount.split(","));
            for (String s: strings){
                s = s.replaceAll(" ", "");
                s = s.replaceAll("[^0-9]", "");
                positions.add(Integer.parseInt(s) - 1);
            }

            NodeList layerList = document.getElementsByTagName("layer");
            width = Integer.parseInt(layerList.item(0).getAttributes().getNamedItem("width").getNodeValue());
            height = Integer.parseInt(layerList.item(0).getAttributes().getNamedItem("height").getNodeValue());

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return positions;
    }

    public String getFileName() {
        return fileName;
    }

    public String getImageSourcePath() {
        return imageSourcePath;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
