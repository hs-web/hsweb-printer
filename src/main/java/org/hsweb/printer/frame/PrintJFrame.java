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

import org.hsweb.printer.server.PrinterHttpServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by xiongchuang on 2016/8/25 .
 */
public class PrintJFrame extends JFrame {

    private PrintJFrame frame=this;
    private PrinterHttpServer printerHttpServer;
    JPanel jp_center = new JPanel();
    JLabel jLabel=new JLabel();

    public PrintJFrame(String applicationName, PrinterHttpServer printerHttpServer) {
        super(applicationName);
        this.printerHttpServer=printerHttpServer;
        frame.setIconImage(StartMain.icon.getImage());
        frame.setSize(300, 80);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();            //获取屏幕的尺寸
        int screenWidth = screenSize.width;                    //获取屏幕的宽
        int screenHeight = screenSize.height;
        frame.setLocation(screenWidth / 2 - frame.getWidth() / 2, screenHeight / 2 - frame.getWidth() / 2);
        frame.setVisible(false);
        frame.setBackground(Color.white);

        //为主窗体注册窗体事件
        frame.addWindowListener(new PrintJFrameWindowAdapter());
        frame.setResizable(false);

        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setVerticalAlignment(SwingConstants.CENTER);

        frame.add(jLabel);

    }



    private class PrintJFrameWindowAdapter extends WindowAdapter{
        /**
         * 窗口活动
         */
        public void windowActivated(WindowEvent e) {
            jLabel.setText("<html><div style='padding:5px 10px'>打印服务状态："+(printerHttpServer==null||!printerHttpServer.getState()?"<span color='red'>未开启</span>":"<span color='green'>开启中</span>")+"</div></html>");
        }

        /**
         *窗口最小化
         * @param e
         */
        @Override
        public void windowIconified(WindowEvent e) {
            frame.setVisible(false);//使窗口不可视
            frame.dispose();//释放当前窗体资源
        }

        /**
         * 窗口关闭中
         * @param e
         */
        public void windowClosing(WindowEvent e) {
            frame.setVisible(false);//使窗口不可视
            frame.dispose();//释放当前窗体资源
        }

        /**
         * 窗口关闭
         * @param e
         */
        public void windowClosed(WindowEvent e) {
            frame.setVisible(false);//使窗口不可视
            frame.dispose();//释放当前窗体资源
        }
    }
}
