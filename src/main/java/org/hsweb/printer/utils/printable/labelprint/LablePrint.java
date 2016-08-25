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
import java.util.*;
import java.util.List;

public class LablePrint extends ArrayList<LablePrintLine> {

    /**
     * 文字形态的label集合
     */
    private Set<String> fontLables=new HashSet<String>(){
        {
            add("B");
            add("G");
            add("GB");
        }
    };

    /**
     * 文字形态label对应的字体
     */
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


    /**
     * 对齐方式lable
     */
    private Set<String> alignLables=new HashSet<String>(){
        {
            add("L");
            add("R");
            add("C");
        }
    };


    /**
     * 文字样式队列
     */
    private List<String> fonts=new ArrayList<String>(){
        @Override
        public String get(int index) {
            if(size()==0){
                return "";
            }
            return super.get(index);
        }
    };

    /**
     *对齐方式队列
     */
    private List<String> align=new ArrayList<String>(){
        @Override
        public String get(int index) {
            if(size()==0){
                return "L";
            }
            return super.get(index);
        }
    };


    private double width;//文档宽度
    String printString;//需要打印的串



    private LablePrintLineString lastLablePrintLineString=null;


    public LablePrint(double width, String printString) {
        this.width = width;
        this.printString = printString;
        init();
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



    /**
     * 将打印字符串转换为输入流
     * @param printString
     * @return
     */
    private InputStream getDocumentInputStream(String printString){
        String leftReplace="xia-L-left-L-xia";
        String rightReplace="xia-R-right-R-xia";

        printString=printString.replaceAll("&lt;",leftReplace).replaceAll("&gt;",rightReplace).replaceAll("<","&lt;").replaceAll(">","&gt;");

        for(String s:fontLables){
            printString=printString.replaceAll("&lt;"+s+"&gt;","<"+s+">").replaceAll("&lt;/"+s+"&gt;","</"+s+">");
        }

        for(String s:alignLables){
            printString=printString.replaceAll("&lt;"+s+"&gt;","<"+s+">").replaceAll("&lt;/"+s+"&gt;","</"+s+">");
        }

        printString=printString.replaceAll(leftReplace,"&lt;").replaceAll(rightReplace,"&gt;");


        String document="<?xml version=\"1.0\" encoding=\"utf-8\" ?><lableprint>%s</lableprint>";
        return new ByteArrayInputStream(String.format(document,printString).getBytes());
    }


    /**
     * 将打印字符串 以xml方式解析
     * @param printString
     * @return
     */
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


    /**
     * 初始化
     */
    private void init() {
        Node root=getRootNode(printString);
        if(root==null){
            ////todo 2016/8/23 13:40 熊闯 打印出錯呢
        }

        NodeList childNodes = root.getChildNodes();
        nodeList(childNodes);
    }

    /**
     * 进行lable解析
     * @param childNodes
     */
    private void nodeList(NodeList childNodes){
        for (int i=0;i<childNodes.getLength();i++) {
            Node item = childNodes.item(i);
            if("#text".equals(item.getNodeName())){
                this.stringNode(item);
            }else if("QR".equals(item.getNodeName())) {
                lastLablePrintLineString=null;
                this.qrcodNode(item);
            }else {

                if(alignLables.contains(item.getNodeName())){
                    lastLablePrintLineString=null;
                    align.add(0,item.getNodeName());
                }else if(fontLables.contains(item.getNodeName())){
                    fonts.add(0,item.getNodeName());
                }

                this.nodeList(item.getChildNodes());

                if(alignLables.contains(item.getNodeName())){
                    align.remove(0);
                    lastLablePrintLineString=null;
                }else if(fontLables.contains(item.getNodeName())){
                    fonts.remove(0);
                }
            }
        }
    }

    /**
     * 构造字符串打印
     * @param item
     */
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


            /**
            *将字符串以每行最大长度分组打印
            */


            //将当前行已有的文字进行补全
            StringBuilder stringBuilder=new StringBuilder();
            if(lastLablePrintLineString!=null){
                int span=maxText-(int)Math.ceil((width-lastLablePrintLineString.getNextX())/(font.getSize2D()/2)+font.getSize2D());
                for (int i=0;i<span;i++) {
                    stringBuilder.append(" ");
                }
            }

            //进行分组
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

    /**
     * 将要打印的字符串以换行符分组
     * @param item
     * @return
     */
    private String[] getPrintStringArray(Node item) {
        String nodeValue = item.getNodeValue();

       if("\n".equals(nodeValue)){
           return new String[]{"<BR>"};
       }
        nodeValue= nodeValue.replaceAll("\n","\n<BR>\n");
        String[] split = nodeValue.split("\n");

        // nodeValue=nodeValue.replaceAll("\n","<BR>");
        return  split;
    }


    /**
     * 构造二维码打印
     * @param item
     */

    private void qrcodNode( Node item ){
        int v = (int)(width * 0.16);
        int size=(int)width-v*2;
        LablePrintLineQrcode lablePrintLineQrcode=new LablePrintLineQrcode(v,size,item.getTextContent());
        add(lablePrintLineQrcode);
    }







}