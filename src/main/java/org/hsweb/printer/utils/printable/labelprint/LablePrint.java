package org.hsweb.printer.utils.printable.labelprint;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.ByteArrayInputStream;
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
    private int height=5;
    private int xpadding;


    public LablePrint(int xpadding,double width, String printString) {
        this.xpadding=xpadding;
        this.width = width;
        this.printString = printString;
        init();
    }

    private void init() {
        Node root=getRootNode(printString);
        if(root==null){
            ////todo 2016/8/23 13:40 熊闯 打印出錯呢
        }

        NodeList childNodes = root.getChildNodes();
        nodeList(childNodes);
    }
    private int getLayoutHeight(int height){
        int tempHeight=this.height;
        this.height+=height;
        return this.height;
    }

    private void nodeList(NodeList childNodes){
        for (int i=0;i<childNodes.getLength();i++) {
            Node item = childNodes.item(i);
            if("#text".equals(item.getNodeName())){
                String nodeValue = item.getNodeValue();
                String[] split = nodeValue.split("\n");
                Font font = lableFontMap.get(item.getParentNode().getNodeName());
               // for (String s : split) {
                    LablePrintLineString lablePrintLineString=new LablePrintLineString(xpadding,this.getLayoutHeight((int)Math.ceil(font.getSize2D())),font, nodeValue);
                    add(lablePrintLineString);
                //}
            }else if("qrcod".equals(item.getNodeName())){
                int v = (int)(width * 0.16);
                int size=(int)width-v*2;
                LablePrintLineQrcode lablePrintLineQrcode=new LablePrintLineQrcode(v,this.getLayoutHeight(size),size,item.getTextContent());
                add(lablePrintLineQrcode);
            }else {
                this.nodeList(item.getChildNodes());
            }
        }
    }

    private Node getRootNode(String printString){
        Node node=null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(getDocumentInputStream(printString));
            NodeList lableprint = document.getElementsByTagName("lableprint");
            node=lableprint.item(0);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return node;
    }
    private InputStream getDocumentInputStream(String printString){
       String document="<?xml version=\"1.0\" encoding=\"utf-8\" ?><lableprint>%s</lableprint>";
        return new ByteArrayInputStream(String.format(document,printString).getBytes());
    }



    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public static void main(String[] args) {
        String s="<G>1</G><GB>2\n</GB>\n3\n<B>4\n</B>";

        LablePrint lablePrintLines=new LablePrint(5,200,s);
    }


   /* public static void main(String[] args) {
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
                            System.out.println(userMeta.item(k).getNodeName()+":" + userMeta.item(k).getTextContent());
                        if (userMeta.item(k).getNodeName() != "#text")
                            System.out.println(userMeta.item(k).getNodeName()+ ":" + userMeta.item(k).getTextContent());
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
    }*/


}