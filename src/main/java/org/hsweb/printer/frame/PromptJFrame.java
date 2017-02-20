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

/**
 * Created by xiongchuang on 2016/8/27 .
 */
public class PromptJFrame extends JFrame {
    public PromptJFrame(String applicationName, PrinterHttpServer printerHttpServer) {
        super(applicationName);


        JFrame promptJFrame = this;
        promptJFrame.setIconImage(StartMain.icon.getImage());
        promptJFrame.setSize(300, 150);
        promptJFrame.setResizable(false);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();            //获取屏幕的尺寸
        int screenWidth = screenSize.width;                    //获取屏幕的宽
        int screenHeight = screenSize.height;
        promptJFrame.setLocation(screenWidth / 2 - promptJFrame.getWidth() / 2, screenHeight / 2 - promptJFrame.getWidth() / 2);
        promptJFrame.setVisible(true);
        //promptJFrame.setBackground(Color.white);
        //为主窗体注册窗体事件
        // promptJFrame.addWindowListener(new PrintJFrameWindowAdapter(this));
        JLabel jLabel = new JLabel();
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setVerticalAlignment(SwingConstants.CENTER);
        promptJFrame.add(jLabel);
       promptJFrame.getContentPane().setBackground(Color.WHITE);

        if (printerHttpServer.getState()) {
            for (int i = 3; i > 0; --i) {
                try {
                    jLabel.setText("<html>打印服务启动成功!<br>" + i + "秒后自动关闭窗口!<html>");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            promptJFrame.setVisible(false);
            promptJFrame.dispose();
        } else {
            promptJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jLabel.setText("<html>打印服务启动失败!<br>请检查是否已经启动或重新启动!<html>");
        }
    }
}