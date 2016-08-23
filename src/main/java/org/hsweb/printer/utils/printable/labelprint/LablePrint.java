package org.hsweb.printer.utils.printable.labelprint;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LablePrint extends ArrayList<LablePrintLine> {

    private Map<String, Font> lableFontMap = new HashMap<String, Font>() {
        {
            put("B", new Font("黑体", Font.BOLD, 12));
            put("G", new Font("黑体", Font.PLAIN, 24));
            put("GB", new Font("黑体", Font.BOLD, 24));
        }

        @Override
        public Font get(Object key) {
            Font font = super.get(key);
            if(font!=null){
                return font;
            }
            return new Font("黑体", Font.PLAIN, 12);
        }
    };


    private double width;
    String printString;
    private int height;


    public LablePrint(double width, String printString) {
        this.width = width;
        this.printString = printString;
        init();
    }

    private void init() {

    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }




    public static void main(String[] args) {
        InputStream in = new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                "<xxxx>\n" +
                "    xxxx1\n" +
                "    <xxxx>\n" +
                "        xzxxxx2\n" +
                "    </xxxx>\n" +
                "    ddddd3\n" +
                "    <xxxx>\n" +
                "        xxxxxx4\n" +
                "    </xxxx>\n" +
                "    xxxxxx5\n" +
                "</xxxx>").getBytes());
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(in);
            NodeList users = document.getChildNodes();
            // System.out.println(document.getTextContent());
            for (int i = 0; i < users.getLength(); i++) {
                Node user = users.item(i);
                NodeList userInfo = user.getChildNodes();

                for (int j = 0; j < userInfo.getLength(); j++) {
                    Node node = userInfo.item(j);
                    if (node.getNodeName() == "#text")
                        System.out.println(node.getNodeName()
                                + ":" + (node.getTextContent()));
                    if (node.getNodeName() != "#text")
                        System.out.println(node.getNodeName()
                                + ":" + (node.getTextContent()));


                    NodeList userMeta = node.getChildNodes();

                    for (int k = 0; k < userMeta.getLength(); k++) {
                        if (userMeta.item(k).getNodeName() == "#text")
                            System.out.println(userMeta.item(k).getNodeName()
                                    + ":" + userMeta.item(k).getTextContent());
                        if (userMeta.item(k).getNodeName() != "#text")
                            System.out.println(userMeta.item(k).getNodeName()
                                    + ":" + userMeta.item(k).getTextContent());
                    }

                    System.out.println();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}