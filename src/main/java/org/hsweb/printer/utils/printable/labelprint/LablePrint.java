package org.hsweb.printer.utils.printable.labelprint;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LablePrint extends ArrayList<LablePrintLine> {


    private Set<String> set=new HashSet<String>(){
        {
            add("B");
            add("G");
            add("GB");
            add("QR");
            add("BR");
        }
    };


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

    private List<Font> fonts=new ArrayList<Font>(){
        @Override
        public Font get(int index) {
            if(index==0&&this.size()==0){
                return new Font("黑体", Font.PLAIN, 12);
            }
            return super.get(index);
        }
    };


    private double width;
    String printString;
    private int height=0;


    public LablePrint(double width, String printString) {
        this.width = width;
        this.printString = printString;
        this.print(printString);
        //init();
    }



    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

/*
    private void init() {
        Node root=getRootNode(printString);
        if(root==null){
            ////todo 2016/8/23 13:40 熊闯 打印出錯呢
        }

        NodeList childNodes = root.getChildNodes();
        nodeList(childNodes);
    }
*/
   /* private int getLayoutHeight(int height){
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
            }else if("QR".equals(item.getNodeName())){
                this.qrcodNode(item);
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

    private void stringNode( Node item ){
        Font font = lableFontMap.get(item.getParentNode().getNodeName());
        int maxText =((int)Math.floor(width / font.getSize2D()))*2;

        String[] split =getPrintStringArray(item);
        for (String s : split) {
            List<String> strings = StringUtil.lengthSplit(s, maxText);
            for (String string : strings) {
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
*/





    private LableIndex getLableInde(String x){


        LableIndex lableIndex = new LableIndex();
        int upIndex=0;
        while (true) {
            int _index = x.indexOf("<");
            int _lasIndex = x.indexOf(">");

            if (_index == -1||_lasIndex==-1) {
                return null;
            }

            String x2 = x.substring(_index + 1, _lasIndex);

            int _index2 = x2.lastIndexOf("<");
            if(_index2!=-1) {
                _index = _index2 != -1 ? _lasIndex - _index2 : _index;
                x2 = x.substring(_index + 1, _lasIndex);
            }

            lableIndex.setStart(true);
            if (x2.indexOf("/") == 0) {
                x2=x2.substring(1);
                lableIndex.setStart(false);
            }

            if (!set.contains(x2)) {
                upIndex=upIndex+_lasIndex + 1;
                x= x.substring(_lasIndex + 1);
                continue;
            }

            lableIndex.setIndex(upIndex + _index);
            lableIndex.setLastIndex(upIndex + _lasIndex);
            lableIndex.setLable(x2);

            return lableIndex;
        }

    }
    private void print(String x){
        x=x.replace("\n","<BR>");
        while (true){
            LableIndex lableInde = getLableInde(x);
            if(lableInde==null){
                System.out.println(x);
                break;
            }

            String substring = x.substring(0, lableInde.getIndex());
            printString(substring);


            if("BR".equals(lableInde.getLable())){
                Font font1 = fonts.get(0);
                height+=font1.getSize2D();
            }else if("QR".equals(lableInde.getLable())){
                String x2=x.substring(lableInde.getLastIndex()+1);
                int qrIndex = x2.indexOf("</QR>");
                if(qrIndex!=-1){
                    System.out.println(x2.substring(0,qrIndex));
                    x=x2.substring(qrIndex+5);
                    continue;
                }
            }else {
                Font font = lableFontMap.get(lableInde.getLable());
                fonts.add(0,font);
            }
            x=x.substring(lableInde.getLastIndex()+1);
        }
    }

    private void printString(String s){
        Font font1 = fonts.get(0);
        LablePrintLineString lablePrintLineString=new LablePrintLineString(0,height,font1,s);
        add(lablePrintLineString);
    }




    class LableIndex{
        private Integer index;
        private Integer lastIndex;
        private String lable;
        private Boolean start;

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Integer getLastIndex() {
            return lastIndex;
        }

        public void setLastIndex(Integer lastIndex) {
            this.lastIndex = lastIndex;
        }

        public String getLable() {
            return lable;
        }

        public void setLable(String lable) {
            this.lable = lable;
        }

        public Boolean getStart() {
            return start;
        }

        public void setStart(Boolean start) {
            this.start = start;
        }

        @Override
        public String toString() {
            return "LableIndex{" +
                    "index=" + index +
                    ", lastIndex=" + lastIndex +
                    ", lable='" + lable + '\'' +
                    ", start=" + start +
                    '}';
        }
    }

    public static void main(String[] args) {
       String s= "X11111111111111111111111111111111111111111XXX\n<B>1111111111111111111111111111111111111111111</B>222222222222222222<G>1232</G><QR>xx</QR>";
        new LablePrint(200,s);
    }


}