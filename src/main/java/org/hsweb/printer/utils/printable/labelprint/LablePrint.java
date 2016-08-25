package org.hsweb.printer.utils.printable.labelprint;

import org.hsweb.printer.utils.FontUtil;
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
            put("B", FontUtil.deriveFont(Font.BOLD, 12));
            put("G", FontUtil.deriveFont(Font.PLAIN, 24));
            put("GB",FontUtil.deriveFont(Font.BOLD, 24));
        }

        @Override
        public Font get(Object key) {
            Font font = super.get(key);
            if(font!=null){
                return font;
            }
            return FontUtil.deriveFont(Font.PLAIN, 12);
        }
    };
    private List<String> fonts=new ArrayList<String>(){
        @Override
        public String get(int index) {
            if(size()==0){
                return "";
            }


            return super.get(index);
        }
    };

    private List<String> align=new ArrayList<String>(){
        @Override
        public String get(int index) {
            if(size()==0){
                return "L";
            }


            return super.get(index);
        }
    };


    private double width;
    String printString;



    private LablePrintLineString lastLablePrintLineString=null;


    public LablePrint(double width, String printString) {
        this.width = width;
        this.printString = printString;
        init();
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

    private void init() {
        Node root=getRootNode(printString);
        if(root==null){
            ////todo 2016/8/23 13:40 熊闯 打印出錯呢
        }

        NodeList childNodes = root.getChildNodes();
        nodeList(childNodes);
    }


    private void nodeList(NodeList childNodes){
        for (int i=0;i<childNodes.getLength();i++) {
            Node item = childNodes.item(i);
            if("#text".equals(item.getNodeName())){
                this.stringNode(item);
            }else if("QR".equals(item.getNodeName())) {
                lastLablePrintLineString=null;
                this.qrcodNode(item);
            }else {
                switch (item.getNodeName()){
                    case "C":
                    case "R":
                    case "L":
                        {
                            lastLablePrintLineString=null;
                            align.add(0,item.getNodeName());
                            this.nodeList(item.getChildNodes());
                            align.remove(0);
                            lastLablePrintLineString=null;
                        }break;
                    case "B":
                    case "GB":
                    case "G":
                        {
                            fonts.add(0,item.getNodeName());
                            this.nodeList(item.getChildNodes());
                            fonts.remove(0);
                        }break;
                    default: this.nodeList(item.getChildNodes());
                }
               /* if("C".equals(item.getNodeName())){

                }else if("R".equals(item.getNodeName())){
                    lastLablePrintLineString=null;
                    align.add(0,"R");
                }

                this.nodeList(item.getChildNodes())

                if("C".equals(item.getNodeName())){
                    lastLablePrintLineString=null;
                    align.remove(0);
                }else if("R".equals(item.getNodeName())){
                    lastLablePrintLineString=null;
                    align.remove(0);
                }*/
            }
        }
    }


    private void stringNode( Node item ){
        Font font = lableFontMap.get(fonts.get(0));
        int maxText =((int)Math.floor(width / font.getSize2D()))*2;

        String[] split =getPrintStringArray(item);
        for (String s : split) {
            if("".equals(s)||"\n".equals(s)||"<BR>".equals(s)){
                lastLablePrintLineString=new LablePrintLineString(align.get(0),(float) width,font,"");
                add(lastLablePrintLineString);
                continue;
            }
            StringBuilder stringBuilder=new StringBuilder();
            if(lastLablePrintLineString!=null){
                int span=maxText-(int)Math.ceil((width-lastLablePrintLineString.getNextX())/(font.getSize2D()/2)+font.getSize2D());
                for (int i=0;i<span;i++) {
                    stringBuilder.append(" ");
                }
            }

            List<String> strings = StringUtil.lengthSplit(stringBuilder.toString()+s, maxText);
            for (int i=0;i<strings.size();i++){//String string : strings) {
                String s1 = strings.get(i);

                LablePrintLineString lablePrintLineString=null;
                if(i==0&&stringBuilder.length()>0){
                    s1=s1.substring(stringBuilder.length());
                }
                if(i==0&&lastLablePrintLineString!=null){
                    lablePrintLineString = lastLablePrintLineString;
                }else {
                    lablePrintLineString=new LablePrintLineString(align.get(0),(float) width);
                    add(lablePrintLineString);
                }

                if(i==strings.size()-1){
                    lastLablePrintLineString=lablePrintLineString;
                }else{
                    lastLablePrintLineString=null;
                }
                lablePrintLineString.add(font,s1);
            }
        }
    }

    private String[] getPrintStringArray(Node item) {
        String nodeValue = item.getNodeValue();

       /* boolean j=false;
        if (nodeValue.lastIndexOf("\n") == nodeValue.length() - 1) {
            nodeValue = nodeValue+" " + "\n";
            j=true;
        }*/
       if("\n".equals(nodeValue)){
           return new String[]{"<BR>"};
       }
        nodeValue= nodeValue.replaceAll("\n","\n<BR>\n");
        String[] split = nodeValue.split("\n");

        // nodeValue=nodeValue.replaceAll("\n","<BR>");
        return  split;
    }

    private void qrcodNode( Node item ){
        int v = (int)(width * 0.16);
        int size=(int)width-v*2;
        LablePrintLineQrcode lablePrintLineQrcode=new LablePrintLineQrcode(v,size,item.getTextContent());
        add(lablePrintLineQrcode);
    }






    public double getWidth() {
        return width;
    }

    public double getHeight() {
        float maxHeight=0;
        boolean f=true;
        for (LablePrintLine lablePrintLine : this) {
            maxHeight+=lablePrintLine.getHeight()*(f?2:1);
            f=false;
        }
        return maxHeight;
    }

}