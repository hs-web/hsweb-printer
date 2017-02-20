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
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.List;

public class LablePrint extends ArrayList<LablePrintLine> {

    /**
     * 文字形态的label集合
     */
    private Set<String> otherLables=new HashSet<String>(Arrays.asList("QR"));

    /**
     * 文字形态的label集合
     */
    private Set<String> fontLables=new HashSet<String>(Arrays.asList("B","G","GB","W","H"));
    /**
     * 对齐方式lable
     */
    private Set<String> alignLables=new HashSet<String>(Arrays.asList("L","R","C"));


    /**
     * 文字形态label对应的字体
     */
    private Map<String, Font> lableFontMap;

    /**
     * 文字样式队列
     */
    private List<String> fonts=new FontStyleList();

    /**
     *对齐方式队列
     */
    private List<String> align=new FontAlignList();


    private double width;//文档宽度
    private String printString;//需要打印的串
    private int fontSize;
    private int textLine;


    private LablePrintLineString lastLablePrintLineString=null;
    private boolean lastLablePrintLineStringBR=false;


    public LablePrint(double width,int fontSize, String printString) {
        this.width = width;
        this.printString = printString;
        this.fontSize=fontSize;
        this.textLine=fontSize/8;
        lableFontMap= new LableFontMap(fontSize);
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
        printString=printString.replaceAll("<BR>","\n");

        printString=printString.replaceAll("&lt;",leftReplace).replaceAll("&gt;",rightReplace).replaceAll("<","&lt;").replaceAll(">","&gt;");

        for(String s:fontLables){
            printString=printString.replaceAll("&lt;"+s+"&gt;","<"+s+">").replaceAll("&lt;/"+s+"&gt;","</"+s+">");
        }

        for(String s:alignLables){
            printString=printString.replaceAll("&lt;"+s+"&gt;","<"+s+">").replaceAll("&lt;/"+s+"&gt;","</"+s+">");
        }

        for (String s:otherLables){
            printString=printString.replaceAll("&lt;"+s+"&gt;","<"+s+">").replaceAll("&lt;/"+s+"&gt;","</"+s+">");
        }

        printString=printString.replaceAll(leftReplace,"&lt;").replaceAll(rightReplace,"&gt;");
        //printString=printString.replaceAll("\n","<BR/>");


        String document="<?xml version=\"1.0\" encoding=\"UTF-8\"?><lableprint>%s</lableprint>";
        byte[] bytes=null;
        try {
            String format = String.format(document, printString);
            bytes = format.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(bytes);
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
            throw new RuntimeException("打印解析失败");
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
        int maxText =((int) Math.floor(width / font.getSize2D()))*2;

        String[] split =getPrintStringArray(item);
        for (String s : split) {
            if("".equals(s)||"\n".equals(s)||"<BR>".equals(s)){
                if(lastLablePrintLineStringBR&&lastLablePrintLineString!=null){
                    add(lastLablePrintLineString);
                }
                lastLablePrintLineString=new LablePrintLineString(textLine,align.get(0),(float) width, FontUtil.printFont(Font.PLAIN, fontSize),"","");
                lastLablePrintLineStringBR=true;
                continue;
            }
            if(lastLablePrintLineStringBR&&lastLablePrintLineString!=null){
                add(lastLablePrintLineString);
            }
            lastLablePrintLineStringBR=false;


            /**
            *将字符串以每行最大长度分组打印
            */


            //将当前行已有的文字进行补全
            StringBuilder stringBuilder=new StringBuilder();
            if(lastLablePrintLineString!=null){
                int span=maxText-(int) Math.ceil((width-lastLablePrintLineString.getNextX())/(font.getSize2D()/2));
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
                    lablePrintLineString=new LablePrintLineString(textLine,align.get(0),(float) width);
                    add(lablePrintLineString);
                }

                if(i==strings.size()-1){
                    lastLablePrintLineString=lablePrintLineString;
                }else{
                    lastLablePrintLineString=null;
                }
                lablePrintLineString.add(font,fonts.get(0),s1);
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

       if("\n".equals(nodeValue)||"<BR>".equals(item)){
           return new String[]{"<BR>"};
       }
        //nodeValue= nodeValue.replaceAll("<BR>","\n<BR>\n");
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
        int size=(int)(width/2.5);
        size=size<55?55:size;
        LablePrintLineQrcode lablePrintLineQrcode=new LablePrintLineQrcode((int)(width-size)/2,size,item.getTextContent());
        add(lablePrintLineQrcode);
    }



    public void print(Graphics graphics,double xpadding,double ypadding){
        Color fontColor = Color.BLACK;
        //转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(fontColor);

        float height=fontSize*3;
        for (LablePrintLine lablePrintLine : this) {
            float tempheight=height;
            height+=lablePrintLine.getHeight();
           /* if(!lablePrintLine.getClass().equals(LablePrintLineQrcode.class)){
                tempheight=height;
            }*/
            lablePrintLine.print((float)xpadding,tempheight,g2);
        }
        g2.setFont(new Font("",Font.PLAIN,1));
        g2.setColor(Color.BLACK);
        g2.drawString(".",(int)getWidth(), height);
        //graphics.drawString(" ",0,(int)getHeight());
    }


    private class LableFontMap extends HashMap<String, Font> {


        private int fontSize=8;
        public LableFontMap(int fontSize){
            this.fontSize=fontSize;
            init(fontSize);
        }

        private void init(int fontSize){
            put("B", FontUtil.printFont(Font.BOLD, fontSize));
            put("G", FontUtil.printFont(Font.PLAIN, fontSize*2));
            put("GB",FontUtil.printFont(Font.BOLD, fontSize*2));
            put("W", FontUtil.printFont(Font.PLAIN, fontSize*2));
            put("H", FontUtil.printFont(Font.PLAIN, fontSize));
        }


        @Override
        public Font get(Object key) {
            Font font = super.get(key);
            if(font!=null){
                return font;
            }
            return FontUtil.printFont(Font.PLAIN, fontSize);
        }
    }
    private class FontStyleList extends ArrayList<String> {
        @Override
        public String get(int index) {
            if (size() == 0) {
                return "";
            }
            return super.get(index);
        }
    }
    private class FontAlignList extends ArrayList<String> {
        @Override
        public String get(int index) {
            if(size()==0){
                return "L";
            }
            return super.get(index);
        }
    }
}

