package animation.puppetAnimationSystem;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PuppetAnimationFile {

    private int limbNum;
    private ArrayList<Integer> ID = new ArrayList<>();
    private ArrayList<Float> parentXOffset = new ArrayList<>();
    private ArrayList<Float> parentYOffset = new ArrayList<>();
    private ArrayList<Float> parentZOffset = new ArrayList<>();
    private ArrayList<Float> MinXRot = new ArrayList<>();
    private ArrayList<Float> MinYRot = new ArrayList<>();
    private ArrayList<Float> MinZRot = new ArrayList<>();
    private ArrayList<Float> MaxXRot = new ArrayList<>();
    private ArrayList<Float> MaxYRot = new ArrayList<>();
    private ArrayList<Float> MaxZRot = new ArrayList<>();

    public PuppetAnimationFile(String fileName){
        File file = new File("res/xmlFiles/"+ fileName + ".xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            assert documentBuilder != null;
            Document document = documentBuilder.parse(file);
            document.normalizeDocument();
            NodeList Limbs = document.getElementsByTagName("Limb");
            limbNum = Limbs.getLength();
            for(int i = 0; i < limbNum; i++){
                ID           .add(Integer.parseInt(Limbs.item(i).getAttributes().getNamedItem("ID").getNodeValue()));
                parentXOffset.add(Float.parseFloat(Limbs.item(i).getAttributes().getNamedItem("parentXOffset").getNodeValue()));
                parentYOffset.add(Float.parseFloat(Limbs.item(i).getAttributes().getNamedItem("parentYOffset").getNodeValue()));
                parentZOffset.add(Float.parseFloat(Limbs.item(i).getAttributes().getNamedItem("parentZOffset").getNodeValue()));
                MinXRot      .add(Float.parseFloat(Limbs.item(i).getAttributes().getNamedItem("MinXRot").getNodeValue()));
                MinYRot      .add(Float.parseFloat(Limbs.item(i).getAttributes().getNamedItem("MinYRot").getNodeValue()));
                MinZRot      .add(Float.parseFloat(Limbs.item(i).getAttributes().getNamedItem("MinZRot").getNodeValue()));
                MaxXRot      .add(Float.parseFloat(Limbs.item(i).getAttributes().getNamedItem("MaxXRot").getNodeValue()));
                MaxYRot      .add(Float.parseFloat(Limbs.item(i).getAttributes().getNamedItem("MaxYRot").getNodeValue()));
                MaxZRot      .add(Float.parseFloat(Limbs.item(i).getAttributes().getNamedItem("MaxZRot").getNodeValue()));
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getID() {
        return ID;
    }

    public ArrayList<Float> getParentXOffset() {
        return parentXOffset;
    }

    public ArrayList<Float> getParentYOffset() {
        return parentYOffset;
    }

    public ArrayList<Float> getParentZOffset() {
        return parentZOffset;
    }

    public ArrayList<Float> getMinXRot() {
        return MinXRot;
    }

    public ArrayList<Float> getMinYRot() {
        return MinYRot;
    }

    public ArrayList<Float> getMinZRot() {
        return MinZRot;
    }

    public ArrayList<Float> getMaxXRot() {
        return MaxXRot;
    }

    public ArrayList<Float> getMaxYRot() {
        return MaxYRot;
    }

    public ArrayList<Float> getMaxZRot() {
        return MaxZRot;
    }

    public int getLimbNum() {
        return limbNum;
    }
}
