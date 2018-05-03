package inventories;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InventoryReader {

    private ArrayList<String>  names  = new ArrayList<>();
    private ArrayList<Integer> weight = new ArrayList<>();
    private ArrayList<Integer> cost   = new ArrayList<>();
    private ArrayList<Integer> damage = new ArrayList<>();
    private ArrayList<String>  type   = new ArrayList<>();
    private ArrayList<Integer> id     = new ArrayList<>();
    private int itemNum;

    public InventoryReader(String location){
        File file = new File("res/xmlFiles/"+ location + ".xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            assert documentBuilder != null;
            Document document = documentBuilder.parse(file);
            document.normalizeDocument();
            NodeList objects = document.getElementsByTagName("weapon");
            itemNum = objects.getLength();
            for (int i = 0; i < objects.getLength(); i++){
                names .add(objects.item(i).getAttributes() .getNamedItem("name").getNodeValue());
                weight.add(Integer.parseInt(objects.item(i).getAttributes().getNamedItem("weight").getNodeValue()));
                cost  .add(Integer.parseInt(objects.item(i).getAttributes().getNamedItem("cost").getNodeValue()));
                damage.add(Integer.parseInt(objects.item(i).getAttributes().getNamedItem("damage").getNodeValue()));
                type  .add(objects.item(i).getAttributes() .getNamedItem("type").getNodeValue());
                id    .add(Integer.parseInt(objects.item(i).getAttributes().getNamedItem("id").getNodeValue()));
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> items(){
        ArrayList<Item> data = new ArrayList<>();
        for(int i = 0; i < itemNum; i++){
            data.add(new Item(
                    id.get(i),
                    type.get(i),
                    damage.get(i),
                    cost.get(i),
                    weight.get(i),
                    names.get(i))
            );
        }
        return data;
    }

}
