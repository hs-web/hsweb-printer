/*
 *  Copyright (c) 2015.  meicanyun.com Corporation Limited.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of
 *  meicanyun Company. ("Confidential Information").  You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with meicanyun.com.
 */

package org.hsweb.printer.frame;

import org.hsweb.printer.dtos.PrintHistoryDTO;
import org.hsweb.printer.server.PrinterHttpServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xiongchuang on 2016/8/27 .
 */
public class StartMain {

    private static final String applicationName="打印服务";
    private static PrintJFrame printJFrame;
    private static TrayIcon printTrayIcon;
    private static MenuItemActionListener menuItemActionListener;

    private static int maxHistory=10;

    public static List<PrintHistoryDTO> historyDTOList=new ArrayList<PrintHistoryDTO>(){
        @Override
        public boolean add(PrintHistoryDTO printHistoryDTO) {
            super.add(0,printHistoryDTO);

            if(this.size()>maxHistory){
                this.remove(maxHistory);
            }
            return true;
        }

        @Override
        public void add(int index, PrintHistoryDTO element) {
            throw new RuntimeException("不能添加指定位置");
        }

        @Override
        public boolean addAll(int index, Collection<? extends PrintHistoryDTO> c) {
            throw new RuntimeException("不能添加全部");
        }

        @Override
        public boolean addAll(Collection<? extends PrintHistoryDTO> c) {
            throw new RuntimeException("不能添加全部");
        }
    };



    public static void main(String[] args) {
      /*  PrintResultDTO printResultDTO=new PrintResultDTO();
        printResultDTO.setSuccess(true);

        PrintInputDTO printInputDTO=new PrintInputDTO();
        printInputDTO.setPrintDocName("xxxx");
        printInputDTO.setPageWidth(200d);
        printInputDTO.setPrinterName("");
        printInputDTO.setPrintText("a");


        PrintHistoryDTO printHistoryDTO=new PrintHistoryDTO();
        printHistoryDTO.setPrintResultDTO(printResultDTO);
        printHistoryDTO.setPrintInputDTO(printInputDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);
        historyDTOList.add(printHistoryDTO);*/

        PrinterHttpServer printerHttpServer =new PrinterHttpServer();
        if(printerHttpServer.getState()) {
            printJFrame = new PrintJFrame(applicationName, printerHttpServer);
            addPrintTrayIcon();
        }
        new PromptJFrame(applicationName,printerHttpServer);


    }
    private static void addPrintTrayIcon(){
        URL resource = StartMain.class.getClassLoader().getResource("print_icon.png");
        ImageIcon icon = new ImageIcon(resource); // 将要显示到托盘中的图标
        printTrayIcon = new TrayIcon(icon.getImage(), "打印服务",new PopupMenu());//实例化托盘图标
        printTrayIcon.setImageAutoSize(true);
        printTrayIcon.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)//鼠标双击图标
                {
                    //printTrayIcon.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
                    printJFrame.setExtendedState(JFrame.NORMAL);//设置状态为正常
                    printJFrame.setVisible(true);//显示主窗体
                }
            }
        });  //为托盘图标监听点击事件

        try {
            SystemTray tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
            tray.add(printTrayIcon); // 将托盘图标添加到系统的托盘实例中
            addTryIconItem();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }

    private static MenuItem getMenuItem(MenuItemName menuItemName){
        if(menuItemActionListener==null){
            menuItemActionListener=new MenuItemActionListener(printJFrame);
        }

        MenuItem menuItem = new MenuItem(menuItemName.getLable());
        menuItem.setName(menuItemName.getName());
        menuItem.addActionListener(menuItemActionListener);
        return menuItem;
    }
    private static void addTryIconItem(){
        printTrayIcon.getPopupMenu().add(getMenuItem(MenuItemName.open));
        printTrayIcon.getPopupMenu().add(getMenuItem(MenuItemName.exit));
    }




}
