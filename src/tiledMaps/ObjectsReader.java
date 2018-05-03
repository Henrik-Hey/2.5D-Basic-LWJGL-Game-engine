package tiledMaps;

import com.sun.deploy.util.StringUtils;
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

public class ObjectsReader {

    private String location;
    private Document document;
    private File file;

    public ObjectsReader(String location){
        this.location = location;
        this.file = new File("res/"+ location + ".tmx");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            this.document = documentBuilder.parse(this.file);
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getObjectsTypes(){
        ArrayList<String> data = new ArrayList<>();
        NodeList objects = document.getElementsByTagName("object");
        for (int i = 0; i < objects.getLength(); i++){
            data.add(objects.item(i).getAttributes().getNamedItem("type").getNodeValue());
        }
        return data;
    }

    public ArrayList<String> getObjectsNames(){
        ArrayList<String> data = new ArrayList<>();
        NodeList objects = document.getElementsByTagName("object");
        for (int i = 0; i < objects.getLength(); i++){
            data.add(objects.item(i).getAttributes().getNamedItem("name").getNodeValue());
        }
        return data;
    }

    public ArrayList<Integer> getObjectsXpos(){
        ArrayList<Integer> data = new ArrayList<>();
        NodeList objects = document.getElementsByTagName("object");
        for (int i = 0; i < objects.getLength(); i++){
            data.add((int)Float.parseFloat(objects.item(i).getAttributes().getNamedItem("x").getNodeValue())/32);
        }
        return data;
    }

    public ArrayList<Integer> getObjectsYpos(){
        ArrayList<Integer> data = new ArrayList<>();
        NodeList objects = document.getElementsByTagName("object");
        for (int i = 0; i < objects.getLength(); i++){
            data.add((int)Float.parseFloat(objects.item(i).getAttributes().getNamedItem("y").getNodeValue())/32);
        }
        return data;
    }

}
