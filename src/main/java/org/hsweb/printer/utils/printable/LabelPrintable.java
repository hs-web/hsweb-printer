package org.hsweb.printer.utils.printable;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xiong on 2016/8/20.
 */
public class LabelPrintable implements BasePrintable {

    private static Set set=new HashSet(){
        {
            add("B");
            add("G");
            add("GB");
        }
    };


    private String printName;
    private double width;
    private String[] printStrings;


    private Color fontColor = Color.black;

    private Font normalFont = new Font("黑体", Font.PLAIN,12);
    private Font boldFont = new Font("黑体", Font.BOLD, 12);

    private Font bigFont = new Font("黑体", Font.PLAIN, 24);
    private Font bigBoldFont = new Font("黑体", Font.BOLD, 24);


    private String tagBoldStart = "<B>";
    private String tagBoldEnd = "</B>";

    private String tagBigStart = "<G>";
    private String tagBigEnd = "</G>";

    private String tagBigBoldStart = "<GB>";
    private String tagBigBoldEnd = "</GB>";


    public LabelPrintable(String printName, double width, String printString) {
        this.printName = printName;
        this.width = width;
        this.printStrings = printString.split("\n");
    }

    @Override
    public String getPrintDocName() {
        return printName;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double gehHeight() {
        int normalRow = 0;
        int bigRow = 0;
        for (String printString : printStrings) {
            if (printString.indexOf(tagBigStart) != -1 || printString.indexOf(tagBigBoldStart) != -1) {
                bigRow++;
            } else {
                normalRow++;
            }
        }
        return getYpadding() * 2 + normalRow * normalFont.getSize2D() + bigRow * bigFont.getSize2D();
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex>0){
            return NO_SUCH_PAGE;
        }
        //转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(fontColor);


        float y=20;
        for (String printString : printStrings) {
            String printNewString=printString;
            g2.setFont(normalFont);

            if(printString.indexOf(tagBigStart)!=-1){
                y+=bigFont.getSize2D();
                g2.setFont(bigFont);

                printNewString=printString.replace(tagBigStart,"").replace(tagBigEnd,"");
            }else if(printString.indexOf(tagBigBoldStart)!=-1){
                y+=bigBoldFont.getSize2D();
                g2.setFont(bigBoldFont);

                printNewString=printString.replace(tagBigBoldStart,"").replace(tagBigBoldEnd,"");
            }else if(printString.indexOf(tagBoldStart)!=-1){
                y+=boldFont.getSize2D();
                g2.setFont(boldFont);

                printNewString=printString.replace(tagBoldStart,"").replace(tagBoldEnd,"");
            }else {
                y+=normalFont.getSize2D();
            }

            g2.drawString(printNewString, (float) getXpadding()*2, y);
        }
        return PAGE_EXISTS;
    }


    private void xxx(String x){
        x.indexOf("<");

    }

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
        List<String> lableList=new ArrayList<String>();
        while (true){
            if(x==null||x.length()==0){
                return;
            }
            LableIndex lableInde = getLableInde(x);
            if(lableInde==null){
                System.out.println("最后打印正常体"+x);
                return;
            }



            String substring = x.substring(0, lableInde.getIndex());

            String s =lableList.size()>0?lableList.get(0):null;

            System.out.println("打印"+(s==null?"正常":s)+"体"+ substring);

            if(lableInde.getStart()){
                lableList.add(0,lableInde.getLable());
            }else {
                lableList.remove(0);
            }

            x=x.substring(lableInde.getLastIndex()+1);
        }
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

   /* public static void main(String[] args) {
        //String x="1231231<B>XXXX</B>XXXXX<G>XXXXX</G>XXXXXX<GB>XCCCCC</GB>CCCCC";
        String x="1231231<B>XXXXXXXXX<G>XXXXXXXXXXX<GB>XCCCCC</GB>xxxx</G>CCCCC</B>";


        new LabelPrintable(null,0,"").print(x);
    }*/

    public static void main(String[] args){
        InputStream in=new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +"\n"+
                "<xmla><xxxx>" +"\n  xxxxxx"+

                "<B>" +"\n"+
                "XXXXXX\nXXX" +"\n"+
                "<G>XXXXXXXXXXX" +"\n"+
                "<GB>" +"\n"+
                "XCCCCC" +"\n"+
                "</GB>" +"\n"+
                "xxxx" +"\n"+
                "</G>" +"\n"+
                "CCCCC" +"\n"+
                "</B>xxx" +"\n"+
                "</xxxx></xmla>").getBytes());
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
                    NodeList userMeta = node.getChildNodes();

                    for (int k = 0; k < userMeta.getLength(); k++) {
                        if(userMeta.item(k).getNodeName() == "#text")
                            System.out.println(userMeta.item(k).getNodeName()
                                    + ":" + userMeta.item(k).getTextContent());
                        if(userMeta.item(k).getNodeName() != "#text")
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

