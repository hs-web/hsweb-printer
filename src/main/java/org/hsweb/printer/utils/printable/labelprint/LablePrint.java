package org.hsweb.printer.utils.printable.labelprint;

import org.hsweb.printer.utils.StringUtil;
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
import java.util.List;
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
    private int height=0;

    private int x=0;
    private int xfont=0;
    private List<LablePrintLineString> lineLablePrintLine=new ArrayList<LablePrintLineString>();


    public LablePrint(double width, String printString) {
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

    private void initLine(){
        this.x=0;
        this.xfont=0;
        this.lineLablePrintLine.clear();
    }
    private int getLayoutHeight(int height){
        int tempHeight=this.height;
        if(super.size()==0){
            this.height+=height;
            tempHeight=this.height;
        }
        this.height+=height;
        return tempHeight;
    }


    private void nodeList(NodeList childNodes){
        for (int i=0;i<childNodes.getLength();i++) {
            Node item = childNodes.item(i);
            if("#text".equals(item.getNodeName())){
                this.stringNode(item);
            }else if("qrcod".equals(item.getNodeName())){
                this.qrcodNode(item);
                this.initLine();
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

    private String getSpan(int size){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<size;i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private void stringNode( Node item ){
        Font font = lableFontMap.get(item.getParentNode().getNodeName());
        int maxText =((int)Math.floor(width / font.getSize2D()))*2;

        String[] split =getPrintStringArray(item);
        for (String s : split) {
            String span = getSpan(xfont);
            List<String> strings = StringUtil.lengthSplit(span+s, maxText);
            boolean f=true;
            for (String string : strings) {
                if (f) {
                    string= string.replace(span, "");
                    f=false;
                }
                ////todo 2016/8/23 19:12 熊闯 
                LablePrintLineString lablePrintLineString=new LablePrintLineString(0,this.getLayoutHeight((int)Math.ceil(font.getSize2D())),font, string);
                add(lablePrintLineString);
            }
        }
    }

    private String[] getPrintStringArray(Node item) {
        String nodeValue = item.getNodeValue();
        if(nodeValue.indexOf("\n")!=nodeValue.lastIndexOf("\n")){
            nodeValue=nodeValue.replaceAll("\n","\n \n");
        }
        return  nodeValue.split("\n");
    }

    private void qrcodNode( Node item ){
        int v = (int)(width * 0.16);
        int size=(int)width-v*2;
        LablePrintLineQrcode lablePrintLineQrcode=new LablePrintLineQrcode(v,this.getLayoutHeight(size),size,item.getTextContent());
        add(lablePrintLineQrcode);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}